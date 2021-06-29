
SET character_set_client = utf8mb4 ;

DROP TABLE IF EXISTS `tb_group`;
CREATE TABLE `tb_group` (
  `id_group` bigint(20) NOT NULL AUTO_INCREMENT,
  `tx_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_group`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

insert into tb_group (tx_name) value ('ROLE_ADMIN');
insert into tb_group (tx_name) value ('ROLE_USER');
insert into tb_group (tx_name) value ('ROLE_DEV');

DROP TABLE IF EXISTS `tb_account`;
CREATE TABLE `tb_account` (
  `id_account` bigint(20) NOT NULL AUTO_INCREMENT,
  `dt_end` datetime DEFAULT NULL,
  `dt_start` datetime DEFAULT NULL,
  `tx_email` varchar(255) DEFAULT NULL,
  `dt_last_password_reset` datetime DEFAULT NULL,
  `tx_name` varchar(255) DEFAULT NULL,
  `tx_password` varchar(255) DEFAULT NULL,
  `bl_status` bit(1) DEFAULT NULL,
  `tx_image_user` varchar(255) DEFAULT NULL,
  `tx_register` varchar(255) DEFAULT NULL,
  `tx_image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_account`),
  UNIQUE KEY `UK_2003ey9q1pkaugwaai232f1jg` (`tx_email`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `tb_account_groups`;
CREATE TABLE `tb_account_groups` (
  `id_account` bigint(20) NOT NULL,
  `id_group` bigint(20) NOT NULL,
  KEY `FKc4fd1soxy7x3ohp6u8wi5p35h` (`id_group`),
  KEY `FKs968leeboluvqdh190mo7ltdy` (`id_account`),
  CONSTRAINT `FKc4fd1soxy7x3ohp6u8wi5p35h` FOREIGN KEY (`id_group`) REFERENCES `tb_group` (`id_group`),
  CONSTRAINT `FKs968leeboluvqdh190mo7ltdy` FOREIGN KEY (`id_account`) REFERENCES `tb_account` (`id_account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `tb_state`;
CREATE TABLE `tb_state` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `pais` varchar(255) DEFAULT NULL,
  `uf` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `tb_city`;
CREATE TABLE `tb_city` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `state_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1rn7oty4mwqviyw8vk67crapo` (`state_id`),
  CONSTRAINT `FK1rn7oty4mwqviyw8vk67crapo` FOREIGN KEY (`state_id`) REFERENCES `tb_state` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5565 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `tb_address`;
CREATE TABLE `tb_address` (
  `id_address` bigint(20) NOT NULL AUTO_INCREMENT,
  `tx_address_name` varchar(255) DEFAULT NULL,
  `tx_address_number` varchar(255) DEFAULT NULL,
  `tx_city` varchar(255) DEFAULT NULL,
  `tx_compl` varchar(255) DEFAULT NULL,
  `tx_neighborhood` varchar(255) DEFAULT NULL,
  `tx_state` varchar(255) DEFAULT NULL,
  `account_id` bigint(20) DEFAULT NULL,
  `tx_postal_code` varchar(255) DEFAULT NULL,
  `id_city` bigint(20) DEFAULT NULL,
  `id_state` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_address`),
  KEY `FKbi9kcu2q6ptj1vattex30xt5j` (`account_id`),
  KEY `FKptkxtpfd8lj4au61ig2kxusmp` (`id_city`),
  KEY `FKry5ypmpsbtah59a317k9ecm7i` (`id_state`),
  CONSTRAINT `FKbi9kcu2q6ptj1vattex30xt5j` FOREIGN KEY (`account_id`) REFERENCES `tb_account` (`id_account`),
  CONSTRAINT `FKptkxtpfd8lj4au61ig2kxusmp` FOREIGN KEY (`id_city`) REFERENCES `tb_city` (`id`),
  CONSTRAINT `FKry5ypmpsbtah59a317k9ecm7i` FOREIGN KEY (`id_state`) REFERENCES `tb_state` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category` (
  `id_category` bigint(20) NOT NULL AUTO_INCREMENT,
  `dt_category` datetime DEFAULT NULL,
  `tx_image` varchar(255) DEFAULT NULL,
  `tx_name` varchar(255) DEFAULT NULL,
  `tx_image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_category`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `tb_category` VALUES (1,'2018-05-26 16:40:00','brigadeiros.png','Finos Tradicionais','https://truffle-app.s3.sa-east-1.amazonaws.com/cat1.jpg'),(2,'2018-05-26 16:43:13','alfajos.png','Gourmet ','https://truffle-app.s3.sa-east-1.amazonaws.com/cat2.jpg'),(70,'2019-02-26 17:36:15',NULL,'Diversos','https://truffle-app.s3.sa-east-1.amazonaws.com/cat70.jpg'),(71,'2019-02-26 17:37:53',NULL,'Doces com frutas','https://truffle-app.s3.sa-east-1.amazonaws.com/cat71.jpg');

DROP TABLE IF EXISTS `tb_item`;
CREATE TABLE `tb_item` (
  `id_item` bigint(20) NOT NULL AUTO_INCREMENT,
  `dt_item` datetime DEFAULT NULL,
  `tx_description` varchar(255) DEFAULT NULL,
  `tx_image` varchar(255) DEFAULT NULL,
  `tx_name` varchar(255) DEFAULT NULL,
  `tx_status` varchar(255) DEFAULT NULL,
  `id_category` bigint(20) DEFAULT NULL,
  `tx_image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_item`),
  KEY `FK2nc4qoeyuhaba5bw89d74jsxf` (`id_category`),
  CONSTRAINT `FK2nc4qoeyuhaba5bw89d74jsxf` FOREIGN KEY (`id_category`) REFERENCES `tb_category` (`id_category`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `tb_note`;
CREATE TABLE `tb_note` (
  `id_note` bigint(20) NOT NULL AUTO_INCREMENT,
  `tx_comment` varchar(255) DEFAULT NULL,
  `vl_note` double DEFAULT NULL,
  `id_item` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_note`),
  KEY `FKd0pixqddf7xj83sq16w7rp678` (`id_item`),
  CONSTRAINT `FKd0pixqddf7xj83sq16w7rp678` FOREIGN KEY (`id_item`) REFERENCES `tb_item` (`id_item`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
  `id_order` bigint(20) NOT NULL AUTO_INCREMENT,
  `dt_order` datetime DEFAULT NULL,
  `vl_value` double DEFAULT NULL,
  `tx_status` varchar(255) DEFAULT NULL,
  `id_account` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_order`),
  KEY `FKf86s177equ5ixe74vn8r4con2` (`id_account`),
  CONSTRAINT `FKf86s177equ5ixe74vn8r4con2` FOREIGN KEY (`id_account`) REFERENCES `tb_account` (`id_account`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `tb_order_item`;
CREATE TABLE `tb_order_item` (
  `id_item_order` bigint(20) NOT NULL AUTO_INCREMENT,
  `dt_item_order` datetime DEFAULT NULL,
  `vl_amount` double DEFAULT NULL,
  `bl_status` bit(1) DEFAULT NULL,
  `vl_value` double DEFAULT NULL,
  `id_item` bigint(20) DEFAULT NULL,
  `id_order` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_item_order`),
  KEY `FKauxv3o3mi1sjommkuxndfeace` (`id_item`),
  KEY `FK90dn0gmn8mf14qcj49vcu6h2u` (`id_order`),
  CONSTRAINT `FK90dn0gmn8mf14qcj49vcu6h2u` FOREIGN KEY (`id_order`) REFERENCES `tb_order` (`id_order`),
  CONSTRAINT `FKauxv3o3mi1sjommkuxndfeace` FOREIGN KEY (`id_item`) REFERENCES `tb_item` (`id_item`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `tb_payment`;
CREATE TABLE `tb_payment` (
  `id_payment` bigint(20) NOT NULL AUTO_INCREMENT,
  `tx_auth_code` varchar(255) DEFAULT NULL,
  `tx_currency` varchar(255) DEFAULT NULL,
  `dt_payment` datetime DEFAULT NULL,
  `cc_payment` varchar(255) DEFAULT NULL,
  `tx_status` varchar(255) DEFAULT NULL,
  `vl_value` double DEFAULT NULL,
  `id_order` bigint(20) DEFAULT NULL,
  `dt_expires` datetime DEFAULT NULL,
  PRIMARY KEY (`id_payment`),
  KEY `FKl5g1h9pne687us80yuhqlepf` (`id_order`),
  CONSTRAINT `FKl5g1h9pne687us80yuhqlepf` FOREIGN KEY (`id_order`) REFERENCES `tb_order` (`id_order`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `tb_price`;
CREATE TABLE `tb_price` (
  `id_price` bigint(20) NOT NULL AUTO_INCREMENT,
  `dt_end` datetime DEFAULT NULL,
  `dt_start` datetime DEFAULT NULL,
  `vl_price` double DEFAULT NULL,
  `cc_price` varchar(255) DEFAULT NULL,
  `id_item` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_price`),
  KEY `FKdk932jl02ajesdkqej2modk93` (`id_item`),
  CONSTRAINT `FKdk932jl02ajesdkqej2modk93` FOREIGN KEY (`id_item`) REFERENCES `tb_item` (`id_item`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `tb_delivery`;
CREATE TABLE `tb_delivery` (
  `id_delivery` bigint(20) NOT NULL AUTO_INCREMENT,
  `dt_delivery` datetime DEFAULT NULL,
  `bl_status` bit(1) DEFAULT NULL,
  `id_order` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_delivery`),
  KEY `FK7lgeem6y78616k35280y3yut1` (`id_order`),
  CONSTRAINT `FK7lgeem6y78616k35280y3yut1` FOREIGN KEY (`id_order`) REFERENCES `tb_order` (`id_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;