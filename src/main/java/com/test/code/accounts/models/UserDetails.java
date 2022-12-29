package com.test.code.accounts.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDetails {

    private static final String NAME_REGEX = "^[a-zA-Z\\s]+";
    private static final String EMAIL_REGEX ="^(.+)@(\\S+)$";

    @NotEmpty
    @Pattern(regexp = NAME_REGEX, message = "user name is invalid")
    private String name;
    @NotNull(message = "incorrect address")
    private @Valid UserAddress address;
    @NotNull(message = "incorrect ssn number")
    private Integer ssn;
    @Pattern(regexp = EMAIL_REGEX, message = "email is invalid")
    private String emailId;
    private Integer mobileNo;

}
