
--CREATE USER 'truffle'@'localhost' IDENTIFIED WITH mysql_native_password BY 'Truffl$';
--GRANT ALL PRIVILEGES ON *.* TO 'truffle'@'localhost';


--insert into tb_user (tx_email, tx_password, bl_status, tx_image_user, ts_last_password_reset) 
--values('andrelgirao29@gmail.com','$2a$10$j8W2P/bmggbizFDvjQVLBeDYExZohCULUDLFAdilvrQFh2n3uiaLq',true,'', sysdate());


--insert into tb_category (dt_category, tx_name) values ('2018-03-31','Tradicionais');
--insert into tb_category (dt_category, tx_name) values ('2018-03-31','Finos');


--insert into tb_group (tx_name) value ('ROLE_ADMIN');
--insert into tb_group (tx_name) value ('ROLE_USER');
--insert into tb_group (tx_name) value ('ROLE_DEV');

--INSERT INTO `truffleapp`.`tb_account` (`tx_city`, `tx_state`, `bl_status`, `tx_email`, `tx_password`, `tx_name`) 
--VALUES ('Fortaleza', 'CE', true, 'admin@truffles.com.br', '{bcrypt}$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'admin');

--INSERT INTO `truffleapp`.`tb_account` (`tx_city`, `tx_state`, `bl_status`, `tx_email`, `tx_password`, `tx_name`) 
--VALUE ('Fortaleza','user@truffles.com.br'	'{bcrypt}$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra')
