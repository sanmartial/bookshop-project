package com.globaroman.bookshopproject.dto;

import com.globaroman.bookshopproject.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(first = "password", second = "repeatPassword")
public class UserRegistrationRequestDto {
    private static final String REGEX_EMAIL =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    @NotBlank
    @Email(regexp = REGEX_EMAIL)
    private String email;
    @NotBlank
    @Length(min = 8, max = 25)
    private String password;
    @NotBlank
    @Length(min = 8, max = 25)
    private String repeatPassword;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String shippingAddress;
}
