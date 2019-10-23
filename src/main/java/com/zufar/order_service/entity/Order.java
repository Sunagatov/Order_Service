package com.zufar.order_service.entity;

import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.GenerationType;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    @SequenceGenerator(name = "order_sequence", sequenceName = "order_seq")
    @ApiModelProperty(notes = "order id", name="id", required=true)
    private Long id;

    @Column(name = "goodsName", length = 160, nullable = false)
    @ApiModelProperty(notes = "goods name of an order", name="goodsName", required=true)
    private String goodsName;

    @OneToOne(fetch = FetchType.EAGER)  
    @Fetch(value = FetchMode.JOIN)
    @JoinColumn(name = "category_id")
    @ApiModelProperty(notes = "category of an order", name="category_id", required=true)
    private Category category;

    @Column(name = "clientId", nullable = false)
    @ApiModelProperty(notes = "client id of an order", name="clientId", required=true)
    private Long clientId;
}
