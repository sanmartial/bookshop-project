package com.globaroman.bookshopproject.dto;

import com.globaroman.bookshopproject.validation.Isbn;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class CreateBookRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotBlank
    @Isbn
    private String isbn;
    @NotBlank
    @PositiveOrZero
    private BigDecimal price;
    @NotBlank
    @Length(max = 1055)
    private String description;
    @NotBlank
    private String coverImage;
}
