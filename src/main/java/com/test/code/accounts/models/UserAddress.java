package com.test.code.accounts.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
@Data
public class UserAddress {

    @NotBlank(message = "street name is required")
    private String streetName;
    @NotBlank(message = "house number is required")
    private String houseNo;
    @NotBlank(message = "city is required")
    private String city;
    @NotBlank(message = "country should be added")
    private String country;
    @NotBlank(message = "pin code is required")
    private String pinCode;


}
