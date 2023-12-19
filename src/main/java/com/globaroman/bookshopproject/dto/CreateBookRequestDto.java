package com.globaroman.bookshopproject.dto;

import com.globaroman.bookshopproject.validation.Isbn;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class CreateBookRequestDto {
    @NotNull
    @NotBlank
    private String title;
    @NotNull
    @NotBlank
    private String author;
    @NotNull
    @NotBlank
    @Isbn
    private String isbn;
    @NotNull
    @NotBlank
    @Min(0)
    private BigDecimal price;
    @NotNull
    @NotBlank
    @Length(min = 0, max = 1055)
    private String description;
    @NotNull
    @NotBlank
    private String coverImage;
}
