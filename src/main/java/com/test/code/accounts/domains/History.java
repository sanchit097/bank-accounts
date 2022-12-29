package com.test.code.accounts.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class History {

    @Id
    @GeneratedValue
    private Long id;
    private Long accountId;
    private String username;
    private String transactionType;
    private BigDecimal beforeBalance;
    private BigDecimal afterBalance;

}
