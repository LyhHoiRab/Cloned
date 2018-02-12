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

/** account_organization **/
CREATE TABLE `account_organization` (
  `account_id` varchar(32) NOT NULL,
  `organization_id` varchar(32) NOT NULL,
  PRIMARY KEY (`account_id`,`organization_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账户 - 企业机构关联表';

/** user **/
CREATE TABLE `user` (
  `id` varchar(32) NOT NULL,
  `account_id` varchar(32) NOT NULL,
  `head_img_url` varchar(255) NOT NULL,
  `nickname` varchar(24) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `age` int(1) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `sex` tinyint(1) DEFAULT NULL,
  `autograph` varchar(120) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_id_UNIQUE` (`account_id`)
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

/** wechat_friend **/
CREATE TABLE `wechat_friend` (
  `id` varchar(32) NOT NULL,
  `wechat_id` varchar(32) NOT NULL,
  `nickname` varchar(50) DEFAULT NULL,
  `remarkname` varchar(32) NOT NULL,
  `head_img_url` varchar(255) DEFAULT NULL,
  `sex` tinyint(1) DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信好友信息表';

/** service **/
SELECT * FROM cloned.wechat;CREATE TABLE `service` (
  `id` varchar(32) NOT NULL,
  `wechat_id` varchar(32) NOT NULL,
  `account_id` varchar(32) NOT NULL,
  `name` varchar(30) NOT NULL,
  `head_img_url` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客服信息表';

/** allocation **/
CREATE TABLE `allocation` (
  `id` varchar(32) NOT NULL,
  `service_id` varchar(32) NOT NULL,
  `probability` double(5,2) NOT NULL DEFAULT '0.00',
  `default_probability` double(5,2) NOT NULL DEFAULT '0.00',
  `is_offline_allot` tinyint(1) NOT NULL DEFAULT '1',
  `step` double(5,2) NOT NULL DEFAULT '1.00',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `service_id_UNIQUE` (`service_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客服分配概率信息';
