SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account_coin
-- ----------------------------
DROP TABLE IF EXISTS `account_coin`;
CREATE TABLE `account_coin` (
  `account_id` bigint(12) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(12) NOT NULL COMMENT '账户归属的用户id',
  `coin_usable` int(11) NOT NULL DEFAULT '0' COMMENT '可用币量',
  `coin_freeze` int(11) NOT NULL DEFAULT '0' COMMENT '冻结币量',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '账户状态 :1正常，2冻结',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 1 表示删除，0 表示未删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`account_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=369662 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='用户账户';

-- ----------------------------
-- Records of account_coin
-- ----------------------------
INSERT INTO `account_coin` VALUES ('369274', '369279', '9785', '0', '1', '0', '2020-05-09 09:54:36', '2020-05-09 09:54:36');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT,
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(100) DEFAULT NULL COMMENT '用户名称',
  `gender` int(1) DEFAULT '2' COMMENT '性别：0：女  1:男 2:保密',
  `phone_num` varchar(20) DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `member_no` varchar(20) DEFAULT NULL COMMENT '用户会员编号',
  `qq_open_id` varchar(128) DEFAULT NULL COMMENT 'qq open id',
  `weixin_open_id` varchar(128) DEFAULT NULL COMMENT '微信 open id',
  `identity` int(11) DEFAULT '1' COMMENT '身份 1:普通 2:官方 3:马甲号',
  `state` int(11) DEFAULT '0' COMMENT '状态 :0正常，1禁言',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除 1 表示删除，0 表示未删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_phoneNum` (`phone_num`) USING BTREE COMMENT '用户手机号唯一索引',
  UNIQUE KEY `idx_nickName` (`nick_name`) USING BTREE COMMENT '用户名称唯一'
) ENGINE=InnoDB AUTO_INCREMENT=369663 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='用户基本信息';

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('369279', '123456', 'test', '2', '13832249872', null, null, null, null, '3', '0', '0', '2020-05-09 11:07:24', '2020-05-09 11:07:24');
