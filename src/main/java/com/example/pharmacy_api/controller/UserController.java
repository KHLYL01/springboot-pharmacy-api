package com.example.pharmacy_api.controller;

import com.example.pharmacy_api.model.dto.FavoriteDto;
import com.example.pharmacy_api.model.dto.OrderItemDto;
import com.example.pharmacy_api.model.dto.OrdersDto;
import com.example.pharmacy_api.model.entity.*;
import com.example.pharmacy_api.repository.*;
import com.example.pharmacy_api.service.UserService;
import com.example.pharmacy_api.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final FavoriteRepository favoriteRepository;

    private final DrugRepository drugRepository;

    private final OrdersRepository ordersRepository;

    private final AdminRepository adminRepository;

    private final OrderItemRepository orderItemRepository;

    private final ImageOrderRepository imageOrderRepository;

    @GetMapping("/admins/{region}")
    public ResponseEntity<List<Admin>> getAllAdminByRegion(@PathVariable String region){
        return ResponseEntity.ok(adminRepository.findAllByRegion(region));
    }

    @GetMapping("/drug/admin/{adminId}")
    public ResponseEntity<List<Drug>> getAllDrugByAdminId(@PathVariable int adminId){
        return ResponseEntity.ok(drugRepository.findAllByCategory_Admin_Id(adminId));
    }

    @PostMapping("/favorites")
    @Transactional
    public ResponseEntity<Favorite> addFavorite(@RequestBody FavoriteDto favoriteDto){

        User user = userService.findById(favoriteDto.getUserId()).get();
        Drug drug = drugRepository.findById(favoriteDto.getDrugId()).get();

        Favorite favorite = Favorite.builder()
                .user(user)
                .drug(drug)
                .build();

        return ResponseEntity.ok(favoriteRepository.save(favorite));
    }

    @GetMapping("/favorites/{id}")
    public ResponseEntity<List<Favorite>> getAllDrugFavoriteByUserId(@PathVariable int id){
        return ResponseEntity.ok(favoriteRepository.findAllByUser_Id(id));
    }

    @DeleteMapping("/favorites/{id}")
    public ResponseEntity<?> deleteFavorite(@PathVariable int id){
        favoriteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping(value = "/orders")
    @Transactional
    public ResponseEntity<Orders> addOrder(@RequestBody OrdersDto ordersDto){
        Admin admin  = adminRepository.findById(ordersDto.getAdminId()).get();
        User user = userService.findById(ordersDto.getUserId()).get();


        Orders orders = Orders.builder()
                .user(user)
                .admin(admin)
                .status("Requesting")
                .totalPrice(ordersDto.getTotalPrice())
                .build();

        for (OrderItemDto orderItemDto : ordersDto.getOrderItems()) {
            Drug drug = drugRepository.findById(orderItemDto.getDrugId()).get();
            OrderItem orderItem = OrderItem.builder()
                    .order(orders)
                    .drug(drug)
                    .quantity(orderItemDto.getQuantity())
                    .price(orderItemDto.getPrice())
                    .build();

            orderItemRepository.save(orderItem);
        }

        return ResponseEntity.ok(ordersRepository.save(orders));
    }

    @GetMapping("/orders/user/{id}")
    public ResponseEntity<List<OrdersDto>> getAllOrderByUserId(@PathVariable int id){

        List<OrdersDto> ordersDtos = new ArrayList<>();
        for (Orders order : ordersRepository.getAllByUser_Id(id)) {

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

    public ImageOrder uploadImage(MultipartFile file) throws IOException {

        ImageOrder imageOrder = ImageOrder.builder()
                .name(file.getOriginalFilename())
                .imageDate(ImageUtils.compressImage(file.getBytes()))
                .build();

        return imageOrderRepository.save(imageOrder);
    }


    @PostMapping(value = "/image/{orderId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public  ResponseEntity<?> addImageToOrder(@ModelAttribute MultipartFile image,@PathVariable int orderId) throws IOException {
        ImageOrder imageOrder = uploadImage(image);
        Orders orders = ordersRepository.findById(orderId).get();
        orders.setImageOrder(imageOrder);
        ordersRepository.save(orders);
        return ResponseEntity.ok(imageOrder);
    }

}
