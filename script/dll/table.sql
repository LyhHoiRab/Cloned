CREATE DATABASE `cloned` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

/** account **/
CREATE TABLE `account` (
  `id` varchar(32) NOT NULL,
  `username` varchar(24) NOT NULL,
  `password` varchar(32) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账户表';

/** user **/
CREATE TABLE `user` (
  `id` varchar(32) NOT NULL,
  `account_id` varchar(32) NOT NULL,
  `head_img_url` varchar(255) NOT NULL,
  `nickname` varchar(24) NOT NULL,
  `age` int(1) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `sex` tinyint(1) DEFAULT NULL,
  `autograph` varchar(120) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

/** wechat **/
CREATE TABLE `wechat` (
  `id` varchar(32) NOT NULL,
  `wxno` varchar(45) NOT NULL,
  `organization_id` varchar(32) NOT NULL,
  `device_id` varchar(32) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信信息表';

/** device **/
CREATE TABLE `device` (
  `id` varchar(32) NOT NULL,
  `organization_id` varchar(32) NOT NULL,
  `type` varchar(30) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `imei` varchar(60) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备信息表';

/** organization **/
CREATE TABLE `organization` (
  `id` varchar(32) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `token` varchar(45) DEFAULT NULL,
  `company_name` varchar(60) DEFAULT NULL,
  `license_number` varchar(45) DEFAULT NULL,
  `legal_person` varchar(30) DEFAULT NULL,
  `company_address` varchar(100) DEFAULT NULL,
  `company_phone` varchar(20) DEFAULT NULL,
  `company_email` varchar(60) DEFAULT NULL,
  `company_website` varchar(60) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='企业信息表';

/** rongcloud_message **/
CREATE TABLE `rongcloud_message` (
  `id` varchar(32) NOT NULL,
  `from_user_id` varchar(32) NOT NULL,
  `to_user_id` varchar(32) NOT NULL,
  `content` json NOT NULL,
  `object_name` tinyint(1) NOT NULL,
  `send_time` datetime NOT NULL,
  `receive_time` datetime NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='融云消息记录表';

/** rongcloud_user **/
CREATE TABLE `rongcloud_user` (
  `id` varchar(32) NOT NULL,
  `name` varchar(30) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `portrait_uri` varchar(255) NOT NULL,
  `token` varchar(256) DEFAULT NULL,
  `app_key` varchar(45) DEFAULT NULL,
  `wechat_id` varchar(32) DEFAULT NULL,
  `role_name` tinyint(1) NOT NULL,
  `login_time` datetime DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='融云用户表';

/** rongcloud_friendship **/
CREATE TABLE `rongcloud_friendship` (
  `id` varchar(32) NOT NULL,
  `master_user_id` varchar(32) NOT NULL,
  `subor_user_id` varchar(32) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='融云用户关系表';

/** rongcloud_allocation **/
CREATE TABLE `rongcloud_allocation` (
  `id` varchar(32) NOT NULL,
  `rongcloud_user_id` varchar(32) NOT NULL,
  `probability` double(5,2) NOT NULL,
  `default_probability` double(5,2) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='融云客服分配概率设置表';
