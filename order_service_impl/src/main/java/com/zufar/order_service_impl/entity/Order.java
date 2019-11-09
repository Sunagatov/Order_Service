package com.zufar.order_service_impl.entity;

import com.zufar.order_management_system_common.dto.Category;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@ApiModel(value = "Order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @ApiModelProperty(notes = "Order id", name = "id", required = true)
    @Id
    @SequenceGenerator(name = "order_sequence", sequenceName = "order_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    private Long id;


    @ApiModelProperty(notes = "Goods name in an order", name = "goodsName", required = true)
    @Column(name = "goods_name", length = 160, nullable = false)
    private String goodsName;

    @ApiModelProperty(notes = "Goods category in an order", name = "category_id", required = true)
    @Enumerated(EnumType.STRING)
    private Category category;

    @ApiModelProperty(notes = "Client id in an order", name = "clientId", required = true)
    @Column(name = "client_id", nullable = false)
    private Long clientId;
}
