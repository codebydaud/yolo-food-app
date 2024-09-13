package com.yolo.customer.userProfile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserProfileDTO {
    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "\\d{13}", message = "Contact number must be 13 digits")
    private String contact_number;

    @NotBlank(message = "House is required")
    @Size(max = 16, message = "House must be less than 16 characters")
    private String house;

    @NotBlank(message = "Street is required")
    @Size(max = 16, message = "Street must be less than 16 characters")
    private String street;

    @Size(max = 32, message = "Area must be less than 32 characters")
    private String area;

    @NotNull(message = "Zip code is required")
    @Size(min = 4, max = 5, message = "Zip code must be between 4 and 5 characters")
    private String zip_code;

    @NotBlank(message = "City is required")
    @Size(max = 32, message = "City must be less than 32 characters")
    private String city;

    @NotBlank(message = "Country is required")
    @Size(max = 32, message = "Country must be less than 32 characters")
    private String country;

    @NotBlank(message = "Currency code is required")
    private String currency_code;
}
