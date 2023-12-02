package com.cydeo.entity;

import com.cydeo.dto.AccountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transations")
public class Transaction extends BaseEntity{

    @ManyToOne
    private Account sender;

    @ManyToOne
    private Account receiver;

    private BigDecimal amount;

    private  String message;
    @Column(columnDefinition = "timestamp")
    private Date creationDate;

}
