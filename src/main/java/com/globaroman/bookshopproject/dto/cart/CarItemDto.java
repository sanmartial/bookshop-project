package com.globaroman.bookshopproject.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarItemDto {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private int quantity;
}
