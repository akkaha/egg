
DROP TABLE IF EXISTS `egg_car_order`;
DROP TABLE IF EXISTS `egg_user_order`;
DROP TABLE IF EXISTS `egg_order_item`;
DROP TABLE IF EXISTS `egg_price`;
DROP TABLE IF EXISTS `egg_price_extra`;

CREATE TABLE `egg_user_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `seller` varchar(64) NOT NULL DEFAULT '' COMMENT '卖家',
  `phone` varchar(16) NOT NULL DEFAULT '' COMMENT '手机号',
  `car` int(11) DEFAULT NULL COMMENT '默认车次,可无',
  `bill` longtext COMMENT '账单',
  `status` varchar(16) NOT NULL DEFAULT '1' COMMENT '状态: new,',
  `remark` text COMMENT '备注',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_seller` (`seller`),
  KEY `idx_created_at` (`created_at`),
  KEY `idx_phone` (`phone`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户单信息';

CREATE TABLE `egg_car_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `serial` varchar(64) NOT NULL DEFAULT '' COMMENT '车牌号',
  `driver` varchar(64) NOT NULL DEFAULT '' COMMENT '司机姓名',
  `driver_phone` varchar(16) NOT NULL DEFAULT '' COMMENT '司机手机',
  `bill` longtext COMMENT '账单',
  `status` varchar(16) NOT NULL DEFAULT '' COMMENT '状态',
  `remark` text COMMENT '备注',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_serial` (`serial`),
  KEY `idx_driver` (`driver`),
  KEY `idx_driver_phone` (`driver_phone`),
  KEY `idx_status` (`status`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车单信息';

CREATE TABLE `egg_order_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `weight` decimal(5,1) NOT NULL COMMENT '重量,单位斤',
  `user` int(11) DEFAULT NULL COMMENT '订单外键',
  `car` int(11) DEFAULT NULL COMMENT '车次外键',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_weight` (`weight`),
  KEY `fk_user` (`user`),
  KEY `fk_car` (`car`),
  CONSTRAINT `fk_car` FOREIGN KEY (`car`) REFERENCES `egg_car_order` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_user` FOREIGN KEY (`user`) REFERENCES `egg_user_order` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='单子每箱';

CREATE TABLE `egg_price` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `day` varchar(10) NOT NULL DEFAULT '' COMMENT '日期:天 yyyy-MM-dd',
  `weight` decimal(11,1) NOT NULL COMMENT '斤',
  `price` decimal(11,1) NOT NULL COMMENT '价格',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_day_weight` (`day`,`weight`),
  KEY `idx_day` (`day`),
  KEY `idx_weight` (`weight`),
  KEY `idx_price` (`price`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='价格';

CREATE TABLE `egg_price_extra` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `day` varchar(10) NOT NULL DEFAULT '' COMMENT '日期: yyyy-MM-dd',
  `weight_adjust` decimal(5,1) NOT NULL COMMENT '重量调整,参加计价',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_day` (`day`),
  KEY `idx_weight_adjust` (`weight_adjust`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='价格其他';