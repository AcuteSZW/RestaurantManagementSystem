/*
 Navicat Premium Dump SQL

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 90200 (9.2.0)
 Source Host           : localhost:3306
 Source Schema         : restaurant_management_system

 Target Server Type    : MySQL
 Target Server Version : 90200 (9.2.0)
 File Encoding         : 65001

 Date: 24/02/2025 07:06:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu_item
-- ----------------------------
DROP TABLE IF EXISTS `menu_item`;
CREATE TABLE `menu_item`  (
  `menu_item_id` int NOT NULL AUTO_INCREMENT COMMENT '菜品唯一标识',
  `restaurant_id` int NOT NULL COMMENT '所属餐厅ID（外键关联restaurant表）',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜品名称',
  `price` decimal(10, 2) NOT NULL COMMENT '价格（单位：元）',
  `category` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜品分类（如主菜、甜点、饮品等）',
  `is_available` int NULL DEFAULT 1 COMMENT '是否可用（1=可用 0=不可用）',
  `is_delete` int NULL DEFAULT 0 COMMENT '逻辑删除标志（0=未删除 1=已删除）',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `updated_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`menu_item_id`) USING BTREE,
  INDEX `idx_restaurant_id`(`restaurant_id` ASC) USING BTREE,
  CONSTRAINT `fk_menu_item_restaurant` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
