package com.globaroman.bookshopproject.dto.cart;

import java.util.List;

public record ShoppingCartDto(Long id, Long userId, List<CarItemDto> carItems) {
}
