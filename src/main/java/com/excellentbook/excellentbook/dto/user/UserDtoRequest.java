package com.excellentbook.excellentbook.dto.user;

import com.excellentbook.excellentbook.dto.address.AddressDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDtoRequest {
    @NotBlank(message = "First name cannot be empty")
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must consist of letters only")
    @JsonProperty("first_name")
    private String firstName;
    @NotBlank(message = "Last name cannot be empty")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Last name must consist of letters only")
    @JsonProperty("last_name")
    private String lastName;
    @NotBlank(message = "The email cannot be empty")
    @Size(min = 1, max = 255, message = "The password email be longer than 1, and less that 255 characters ")
    @Pattern(regexp = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b",
            message = "The email must contain a valid ending")
    private String email;
    @NotBlank(message = "The password cannot be empty")
    @Size(min = 8, max = 255, message = "The password must be longer than 8, and less that 255 characters ")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z]).{8,255}$",
            message = "The password must be contain at least one capital and small letter")
    private String password;
    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must consist of 10 digits")
    @JsonProperty("phone_number")
    private String phoneNumber;
    @NotNull
    @Valid
    private AddressDto address;

}
