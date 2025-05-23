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

 Date: 24/02/2025 06:33:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for restaurant
-- ----------------------------
DROP TABLE IF EXISTS `restaurant`;
CREATE TABLE `restaurant`  (
  `restaurant_id` int NOT NULL AUTO_INCREMENT COMMENT '餐厅唯一标识',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '餐厅名称',
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '餐厅地址',
  `city` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '餐厅所在城市',
  `postal_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮政编码',
  `phone_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '电子邮件',
  `website` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '餐厅官网链接',
  `latitude` decimal(10, 8) NULL DEFAULT NULL COMMENT '纬度（用于地图定位）',
  `longitude` decimal(11, 8) NULL DEFAULT NULL COMMENT '经度（用于地图定位）',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '餐厅简介',
  `cuisine_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '主打菜系（如中餐、西餐等）',
  `capacity` int NULL DEFAULT NULL COMMENT '最大容纳人数',
  `opening_hours` json NULL COMMENT '营业时间（JSON格式存储每天的营业时间）',
  `average_rating` decimal(3, 2) NULL DEFAULT NULL COMMENT '平均评分（用户评价）',
  `price_range` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '价格区间（如￥、￥￥、￥￥￥）',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `updated_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_delete` int NULL DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`restaurant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
