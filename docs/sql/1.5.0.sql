
DROP TABLE IF EXISTS `egg_price_extra`;

CREATE TABLE `egg_price_extra` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `day` varchar(10) NOT NULL DEFAULT '' COMMENT '日期: yyyy-MM-dd',
  `weight_adjust` decimal(5,1) NOT NULL COMMENT '重量调整,参加计价',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_day` (`day`),
  KEY `idx_weight_adjust` (`weight_adjust`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='价格其他';