/*
Navicat MySQL Data Transfer

Source Server         : lj
Source Server Version : 50515
Source Host           : localhost:3306
Source Database       : auth

Target Server Type    : MYSQL
Target Server Version : 50515
File Encoding         : 65001

Date: 2017-07-24 16:50:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `s_app`
-- ----------------------------
DROP TABLE IF EXISTS `s_app`;
CREATE TABLE `s_app` (
  `pid` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `name` varchar(100) DEFAULT '' COMMENT 'app名称',
  `marks` varchar(100) DEFAULT '' COMMENT '标识符',
  `secretKey` varchar(64) DEFAULT '' COMMENT '密码',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态： 0 -- 禁用， 1--启用',
  `info` text COMMENT '备注',
  `createTime` varchar(20) DEFAULT '' COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT '' COMMENT '修改时间',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='appId表';

-- ----------------------------
-- Records of s_app
-- ----------------------------
INSERT INTO `s_app` VALUES ('1', '权限管理', 'auth', '67a9565183311a114d84c442dac80144', '1', null, null, null);

-- ----------------------------
-- Table structure for `s_function`
-- ----------------------------
DROP TABLE IF EXISTS `s_function`;
CREATE TABLE `s_function` (
  `pid` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `appId` varchar(32) DEFAULT '' COMMENT 'appId',
  `parentId` varchar(32) DEFAULT '' COMMENT '父级ID',
  `name` varchar(100) DEFAULT '' COMMENT '名称',
  `marks` varchar(100) DEFAULT '' COMMENT '标识符',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态： 0 -- 禁用， 1--启用',
  `createTime` varchar(20) DEFAULT '' COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT '' COMMENT '修改时间',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='appId表';

-- ----------------------------
-- Records of s_function
-- ----------------------------

-- ----------------------------
-- Table structure for `s_group`
-- ----------------------------
DROP TABLE IF EXISTS `s_group`;
CREATE TABLE `s_group` (
  `pid` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `name` varchar(100) DEFAULT '' COMMENT '名称',
  `marks` varchar(100) DEFAULT '' COMMENT '标识符',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态： 0 -- 禁用， 1--启用',
  `info` text COMMENT '备注',
  `createTime` varchar(20) DEFAULT '' COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT '' COMMENT '修改时间',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='appId表';

-- ----------------------------
-- Records of s_group
-- ----------------------------

-- ----------------------------
-- Table structure for `s_group_app`
-- ----------------------------
DROP TABLE IF EXISTS `s_group_app`;
CREATE TABLE `s_group_app` (
  `pid` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `groupId` varchar(32) DEFAULT '' COMMENT '分组',
  `appId` varchar(32) DEFAULT '' COMMENT '应用',
  PRIMARY KEY (`pid`),
  KEY `index_groupId` (`groupId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='appId表';

-- ----------------------------
-- Records of s_group_app
-- ----------------------------

-- ----------------------------
-- Table structure for `s_group_function`
-- ----------------------------
DROP TABLE IF EXISTS `s_group_function`;
CREATE TABLE `s_group_function` (
  `pid` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `groupId` varchar(32) DEFAULT '' COMMENT '分组',
  `appId` varchar(32) DEFAULT '' COMMENT '应用',
  `funId` varchar(32) DEFAULT '' COMMENT '功能',
  PRIMARY KEY (`pid`),
  KEY `index_groupId` (`groupId`,`appId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='appId表';

-- ----------------------------
-- Records of s_group_function
-- ----------------------------

-- ----------------------------
-- Table structure for `s_group_language`
-- ----------------------------
DROP TABLE IF EXISTS `s_group_language`;
CREATE TABLE `s_group_language` (
  `pid` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `groupId` varchar(32) DEFAULT '' COMMENT '分组',
  `appId` varchar(32) DEFAULT '' COMMENT '应用',
  `langId` varchar(32) DEFAULT '' COMMENT '语言',
  PRIMARY KEY (`pid`),
  KEY `index_groupId` (`groupId`,`appId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='appId表';

-- ----------------------------
-- Records of s_group_language
-- ----------------------------

-- ----------------------------
-- Table structure for `s_group_menu`
-- ----------------------------
DROP TABLE IF EXISTS `s_group_menu`;
CREATE TABLE `s_group_menu` (
  `pid` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `groupId` varchar(32) DEFAULT '' COMMENT '分组',
  `appId` varchar(32) DEFAULT '' COMMENT '应用',
  `menuId` varchar(32) DEFAULT '' COMMENT '菜单',
  PRIMARY KEY (`pid`),
  KEY `index_groupId` (`groupId`,`appId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='appId表';

-- ----------------------------
-- Records of s_group_menu
-- ----------------------------

-- ----------------------------
-- Table structure for `s_group_menu_btn`
-- ----------------------------
DROP TABLE IF EXISTS `s_group_menu_btn`;
CREATE TABLE `s_group_menu_btn` (
  `pid` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `groupId` varchar(32) DEFAULT '' COMMENT '分组',
  `appId` varchar(32) DEFAULT '' COMMENT '应用',
  `menuId` varchar(32) DEFAULT '' COMMENT '菜单',
  `btnId` varchar(32) DEFAULT '' COMMENT '菜单按钮',
  PRIMARY KEY (`pid`),
  KEY `index_groupId` (`groupId`,`appId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='appId表';

-- ----------------------------
-- Records of s_group_menu_btn
-- ----------------------------

-- ----------------------------
-- Table structure for `s_language`
-- ----------------------------
DROP TABLE IF EXISTS `s_language`;
CREATE TABLE `s_language` (
  `pid` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `appId` varchar(32) DEFAULT '' COMMENT 'appId',
  `name` varchar(100) DEFAULT '' COMMENT '名称',
  `marks` varchar(100) DEFAULT '' COMMENT '标识符',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态： 0 -- 禁用， 1--启用',
  `createTime` varchar(20) DEFAULT '' COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT '' COMMENT '修改时间',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='appId表';

-- ----------------------------
-- Records of s_language
-- ----------------------------

-- ----------------------------
-- Table structure for `s_menu`
-- ----------------------------
DROP TABLE IF EXISTS `s_menu`;
CREATE TABLE `s_menu` (
  `pid` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `appId` varchar(32) DEFAULT '' COMMENT 'appId',
  `parentId` varchar(32) DEFAULT '' COMMENT '父级ID',
  `name` varchar(100) DEFAULT '' COMMENT '名称',
  `marks` varchar(100) DEFAULT '' COMMENT '标识符',
  `path` varchar(255) DEFAULT '' COMMENT '路径',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态： 0 -- 禁用， 1--启用',
  `createTime` varchar(20) DEFAULT '' COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT '' COMMENT '修改时间',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='appId表';

-- ----------------------------
-- Records of s_menu
-- ----------------------------

-- ----------------------------
-- Table structure for `s_menu_btn`
-- ----------------------------
DROP TABLE IF EXISTS `s_menu_btn`;
CREATE TABLE `s_menu_btn` (
  `pid` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `appId` varchar(32) DEFAULT '' COMMENT 'appId',
  `menuId` varchar(32) DEFAULT '' COMMENT '菜单ID',
  `name` varchar(100) DEFAULT '' COMMENT '名称',
  `marks` varchar(100) DEFAULT '' COMMENT '标识符',
  `path` varchar(255) DEFAULT '' COMMENT '路径',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态： 0 -- 禁用， 1--启用',
  `createTime` varchar(20) DEFAULT '' COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT '' COMMENT '修改时间',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='appId表';

-- ----------------------------
-- Records of s_menu_btn
-- ----------------------------

-- ----------------------------
-- Table structure for `s_user`
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user` (
  `pid` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `username` varchar(100) DEFAULT '' COMMENT '登录名',
  `password` varchar(32) DEFAULT '' COMMENT '密码 （md5）',
  `thridId` varchar(255) DEFAULT '' COMMENT '第三方ID',
  `nick` varchar(50) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '昵称',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态： 0 -- 禁用， 1--启用',
  `info` text COMMENT '备注',
  `createTime` varchar(20) DEFAULT '' COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT '' COMMENT '修改时间',
  `admin` tinyint(2) DEFAULT '0' COMMENT '管理员 0 -- 普通，1 --超级，2--其他，......',
  PRIMARY KEY (`pid`),
  KEY `index_user` (`username`,`password`) USING BTREE,
  KEY `index_thrid` (`thridId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='appId表';

-- ----------------------------
-- Records of s_user
-- ----------------------------
INSERT INTO `s_user` VALUES ('1', 'admin', '21232f297a57a5a743894a0e4a801fc3', '', '', '1', null, '', '', '1');

-- ----------------------------
-- Table structure for `s_user_group`
-- ----------------------------
DROP TABLE IF EXISTS `s_user_group`;
CREATE TABLE `s_user_group` (
  `pid` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `userId` varchar(32) DEFAULT '' COMMENT '用户',
  `groupId` varchar(32) DEFAULT '' COMMENT '分组',
  PRIMARY KEY (`pid`),
  KEY `index_userId` (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='appId表';

-- ----------------------------
-- Records of s_user_group
-- ----------------------------
