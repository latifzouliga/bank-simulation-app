package com.cydeo.entity;

import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity{

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(columnDefinition = "timestamp")
    private Date creationDate;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    // this can be User user;
    private Long userId;

}
