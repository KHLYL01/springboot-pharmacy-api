package com.example.pharmacy_api.controller;

import com.example.pharmacy_api.model.dto.*;
import com.example.pharmacy_api.model.entity.*;
import com.example.pharmacy_api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminRepository adminRepository;

    private final CategoryRepository categoryRepository;

    private final DrugRepository drugRepository;

    private final OrdersRepository ordersRepository;

    private final OrderItemRepository orderItemRepository;

    private final FavoriteRepository favoriteRepository;



    @PostMapping("/category")
    @Transactional
    public ResponseEntity<Category> addCategory(@RequestBody CategoryDto categoryDto){
        Admin admin = adminRepository.findById(categoryDto.getAdminId()).get();
        Category category = Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .admin(admin)
                .build();
        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Category>> getAllCategoryByAdminId(@PathVariable int id){
        return ResponseEntity.ok(categoryRepository.findAllByAdmin_Id(id));
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable int id){
        for(Drug drug : drugRepository.findAllByCategory_Id(id)){
            deleteDrugById(drug.getId());
        }
        categoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/drug")
    @Transactional
    public ResponseEntity<Drug> addDrug(@RequestBody DrugDto drugDto){
        Category category = categoryRepository.findById(drugDto.getCategoryId()).get();
        Drug drug = Drug.builder()
                .id(drugDto.getId())
                .name(drugDto.getName())
                .form(drugDto.getForm())
                .gauge(drugDto.getGauge())
                .imagePath(drugDto.getImagePath())
                .price(drugDto.getPrice())
                .quantity(drugDto.getQuantity())
                .companyName(drugDto.getCompanyName())
                .scientificName(drugDto.getScientificName())
                .isRequiredPrescription(drugDto.isRequiredPrescription())
                .category(category)
                .build();
        return ResponseEntity.ok(drugRepository.save(drug));
    }

    @GetMapping("/drug/category/{categoryId}")
    public ResponseEntity<List<Drug>> getAllDrugByCategoryId(@PathVariable int categoryId){
        return ResponseEntity.ok(drugRepository.findAllByCategory_Id(categoryId));
    }

    @GetMapping("/drug/admin/{adminId}")
    public ResponseEntity<List<Drug>> getAllDrugByAdminId(@PathVariable int adminId){
        return ResponseEntity.ok(drugRepository.findAllByCategory_Admin_Id(adminId));
    }

    @Transactional
    @DeleteMapping("/drug/{id}")
    public ResponseEntity<?> deleteDrugById(@PathVariable int id){
        List<Favorite> favorites = favoriteRepository.findAllByDrug_Id(id);

        favoriteRepository.deleteAll(favorites);

        drugRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orders/admin/{id}")
    public ResponseEntity<List<OrdersDto>> getAllOrderByAdminId(@PathVariable int id){

        List<OrdersDto> ordersDtos = new ArrayList<>();
        for (Orders order : ordersRepository.getAllByAdmin_Id(id)) {

            List<OrderItem> orderItems = orderItemRepository.findOrderItemByOrder_Id(order.getId());
            List<OrderItemDto> orderItemDto = orderItems.stream().map(orderItem ->
                    OrderItemDto.builder()
                            .ordersId(orderItem.getId())
                            .drugId(orderItem.getDrug().getId())
                            .drugName(orderItem.getDrug().getName())
                            .price(orderItem.getPrice())
                            .quantity(orderItem.getQuantity())
                            .build()
            ).toList();

            OrdersDto ordersDto = OrdersDto.builder()
                    .id(order.getId())
                    .pharmacyName(order.getAdmin().getPharmacyName())
                    .userId(order.getUser().getId())
                    .adminId(order.getAdmin().getId())
                    .orderItems(orderItemDto)
                    .status(order.getStatus())
                    .totalPrice(order.getTotalPrice())
                    .build();

            if(order.getImageOrder() != null){
                ordersDto.setImageUrl("/image/"+order.getImageOrder().getName());
            }

            ordersDtos.add(ordersDto);
        }
        return ResponseEntity.ok(ordersDtos);
    }

    @PostMapping("/orders/{id}")
    public ResponseEntity<Orders> updateOrdersStatus(@PathVariable int id, @RequestBody StatusDto statusDto){
        Orders orders = ordersRepository.findById(id).get();

        List<OrderItem> orderItems = orderItemRepository.findOrderItemByOrder_Id(id);

        if(!orders.getStatus().equals("Waiting")){
            for (OrderItem orderItem : orderItems) {
                Drug drug = orderItem.getDrug();
                drug.setQuantity(drug.getQuantity() - orderItem.getQuantity());
                drugRepository.save(drug);
            }
        }

        orders.setStatus(statusDto.getStatus());
        return ResponseEntity.ok(ordersRepository.save(orders));
    }

}
