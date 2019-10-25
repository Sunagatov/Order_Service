package com.zufar.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;


@Api("Order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @SequenceGenerator(name = "order_sequence", sequenceName = "order_seq")
    @ApiModelProperty(notes = "Order id", name="id", required=true)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    private Long id;

    @ApiModelProperty(notes = "Goods name in an order", name="goodsName", required=true)
    @Column(name = "goods_name", length = 160, nullable = false)
    private String goodsName;

    @ApiModelProperty(notes = "Goods category in an order", name="category_id", required=true)
    @OneToOne(fetch = FetchType.EAGER)  
    @Fetch(value = FetchMode.JOIN)
    @JoinColumn(name = "category_id")
    private Category category;

    @ApiModelProperty(notes = "Client id in an order", name="clientId", required=true)
    @Column(name = "client_id", nullable = false)
    private Long clientId;
}
