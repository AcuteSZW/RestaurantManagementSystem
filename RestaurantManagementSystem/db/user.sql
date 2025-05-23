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

 Date: 24/02/2025 06:32:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID（雪花算法生成）',
  `uuid` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '全局唯一标识符',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名（字母数字+下划线，4-20位）',
  `password_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'BCrypt加密密码',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '已验证邮箱（唯一）',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '加密存储联系电话',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '/default-avatar.webp' COMMENT '头像OSS地址',
  `user_type` tinyint NULL DEFAULT 1 COMMENT '用户角色（1=顾客 2=员工 3=管理员 4=供应商）',
  `permission_mask` bigint UNSIGNED NULL DEFAULT 0 COMMENT '权限位掩码',
  `department_id` int NULL DEFAULT NULL COMMENT '所属部门（员工专用）',
  `status` tinyint NULL DEFAULT 3 COMMENT '账户状态（1=正常 2=锁定 3=未验证）',
  `failed_attempts` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '连续登录失败次数',
  `mfa_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'TOTP加密密钥',
  `last_login_ip` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后登录IP（支持IPv6）',
  `last_login_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后登录时间（yyyy-MM-dd HH:mm:ss）',
  `timezone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'Asia/Shanghai' COMMENT '时区设置',
  `preferred_lang` tinyint NULL DEFAULT 1 COMMENT '界面语言（1=中文 2=英文）',
  `notification_prefs` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '2' COMMENT '通知偏好（逗号分隔数字 1=SMS 2=EMAIL 3=PUSH）',
  `created_at` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建时间（yyyy-MM-dd HH:mm:ss）',
  `updated_at` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改时间（yyyy-MM-dd HH:mm:ss）',
  `version` int UNSIGNED NULL DEFAULT 0 COMMENT '乐观锁版本号',
  `is_delete` int NULL DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统用户主表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
