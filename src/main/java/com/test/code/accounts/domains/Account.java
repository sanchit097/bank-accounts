package com.test.code.accounts.domains;

import com.test.code.accounts.exceptions.ErrorCode;
import com.test.code.accounts.exceptions.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long accountId;
    private BigDecimal balance;
    private String username;
    private String emailId;
    @Embedded
    private Address address;
    private Integer ssn;


    public Account addBalance(BigDecimal amount) {
        this.balance = balance.add(amount);
        return this;
    }

    public Account withdrawBalance(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new ValidationException(ErrorCode.INCORRECT_AMOUNT);
        }
        this.balance = this.balance.subtract(amount);
        return this;
    }

}
