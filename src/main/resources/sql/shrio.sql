/*
 Navicat Premium Data Transfer

 Source Server         : aliyunLc
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : 39.98.255.98:3306
 Source Schema         : shrio

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 24/06/2020 16:39:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_auth`;
CREATE TABLE `sys_auth`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名',
  `auth_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0：页面路由，1：权限',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父级路由',
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `rank` int(255) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_auth
-- ----------------------------
INSERT INTO `sys_auth` VALUES (1, '业务处理平台', '/ymly/1', '0', 0, '2020-06-24 13:37:11', 1);
INSERT INTO `sys_auth` VALUES (2, '业务配置', '/ywpz/1', '0', 1, '2020-06-24 15:23:54', 5);
INSERT INTO `sys_auth` VALUES (3, '业务配置管理', '/qx/ywpz/1', '1', 2, '2020-06-24 13:37:23', 2);
INSERT INTO `sys_auth` VALUES (4, '第三方结构管理', '/ymly/2', '0', 1, '2020-06-24 15:23:51', 1);
INSERT INTO `sys_auth` VALUES (5, 'gps管理', '/gps/1', '1', 4, '2020-06-24 13:37:26', 4);
INSERT INTO `sys_auth` VALUES (6, '保后管理平台', '/baohou', '0', 0, '2020-06-24 13:37:22', 2);
INSERT INTO `sys_auth` VALUES (7, '处置评估', '/chuzhi', '1', 6, '2020-06-24 13:37:29', 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '客户经理', '2020-06-24 10:29:49');
INSERT INTO `sys_role` VALUES (2, '人事专员', '2020-06-24 10:29:49');
INSERT INTO `sys_role` VALUES (3, '经销商', '2020-06-24 10:29:49');
INSERT INTO `sys_role` VALUES (4, '保洁', '2020-06-24 10:29:49');

-- ----------------------------
-- Table structure for sys_role_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_auth`;
CREATE TABLE `sys_role_auth`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `auth_id` int(11) NULL DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_auth
-- ----------------------------
INSERT INTO `sys_role_auth` VALUES (1, 1, 1);
INSERT INTO `sys_role_auth` VALUES (4, 1, 2);
INSERT INTO `sys_role_auth` VALUES (5, 1, 3);
INSERT INTO `sys_role_auth` VALUES (6, 1, 4);
INSERT INTO `sys_role_auth` VALUES (7, 2, 1);
INSERT INTO `sys_role_auth` VALUES (8, 2, 4);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '123123', '123123', '2020-06-24 10:54:33');
INSERT INTO `sys_user` VALUES (2, '111111', '111111', '2020-06-24 15:45:01');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `role_id` int(11) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1, '2020-06-24 11:10:29');
INSERT INTO `sys_user_role` VALUES (2, 1, 2, '2020-06-24 11:10:33');

SET FOREIGN_KEY_CHECKS = 1;
