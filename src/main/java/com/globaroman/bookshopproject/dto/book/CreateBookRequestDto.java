package com.globaroman.bookshopproject.dto.book;

import com.globaroman.bookshopproject.validation.Isbn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Set;
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
    @NotNull
    @PositiveOrZero
    private BigDecimal price;
    @NotBlank
    @Length(max = 1055)
    private String description;
    @NotBlank
    private String coverImage;
    @NotEmpty
    private Set<Long> categories;
}
