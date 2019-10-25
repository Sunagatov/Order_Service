package com.zufar.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.GenerationType;

@Api("Order category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @ApiModelProperty(notes = "Category id", name = "id", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
    @SequenceGenerator(name = "category_sequence", sequenceName = "category_seq")
    private Long id;

    @ApiModelProperty(notes = "Category name", name = "name", required = true)
    @Column(name = "name", length = 256, nullable = false, unique = true)
    private String name;
}
