package com.globaroman.bookshopproject.dto.order;

import com.globaroman.bookshopproject.model.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private List<OrderItemDto> orderItems;
    private Status status;
    private BigDecimal total;
    private LocalDateTime orderDate;
    private String shippingAddress;


}
