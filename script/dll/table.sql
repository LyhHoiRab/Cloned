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

/** rongcloud_token **/
CREATE TABLE `rongcloud_token` (
  `id` varchar(32) NOT NULL,
  `account_id` varchar(32) NOT NULL,
  `token` varchar(256) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='融云用户登录Token';

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

