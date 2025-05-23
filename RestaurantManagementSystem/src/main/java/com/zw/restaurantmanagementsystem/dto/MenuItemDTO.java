package com.zw.restaurantmanagementsystem.dto;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜品实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 所属餐厅ID（外键关联restaurant表）
     */
    private Integer restaurantId;

    /**
     * 菜品数量
     */
    private Integer quantity;

    /**
     * 菜品名称
     */
    private String name;

    /**
     * 价格（单位：元）
     */
    private Double price;

    /**
     * 菜品分类（如主菜、甜点、饮品等）
     */
    private String category;

    /**
     * 是否可用（1=可用 0=不可用）
     */
    private Boolean isAvailable;

    /**
     * 逻辑删除标志（0=未删除 1=已删除）
     */
    @TableLogic
    @TableField("is_delete")
    private int isDelete;

    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField(value = "updated_at", fill = FieldFill.UPDATE)
    private LocalDateTime updatedAt;
}