package com.globaroman.bookshopproject.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
}
