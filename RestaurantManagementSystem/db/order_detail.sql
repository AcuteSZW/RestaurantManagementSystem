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

 Date: 24/02/2025 07:07:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `order_detail_id` int NOT NULL AUTO_INCREMENT COMMENT '订单详情唯一标识',
  `order_id` int NOT NULL COMMENT '订单ID',
  `menu_item_id` int NOT NULL COMMENT '菜品ID',
  `quantity` int NOT NULL COMMENT '菜品数量',
  `is_delete` int NULL DEFAULT 0 COMMENT '逻辑删除标志（0=未删除 1=已删除）',
  PRIMARY KEY (`order_detail_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
