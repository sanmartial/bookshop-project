package com.globaroman.bookshopproject.dto.cart;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private List<CarItemDto> carItems;
}
