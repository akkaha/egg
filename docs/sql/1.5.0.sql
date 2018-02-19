
DROP TABLE IF EXISTS `egg_price_extra`;

CREATE TABLE `egg_price_extra` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `day` varchar(10) NOT NULL DEFAULT '' COMMENT '日期: yyyy-MM-dd',
  `box_weight` decimal(5,1) NOT NULL COMMENT '箱重',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_day` (`day`),
  KEY `idx_box_weight` (`box_weight`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='价格其他';