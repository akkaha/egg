CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `seller` varchar(64) NOT NULL DEFAULT '' COMMENT '卖家',
  `status` varchar(16) NOT NULL DEFAULT '1' COMMENT '状态',
  `created_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='组织信息';