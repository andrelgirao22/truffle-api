insert into tb_group (tx_name) value ('ROLE_ADMIN');
insert into tb_group (tx_name) value ('ROLE_USER');
insert into tb_group (tx_name) value ('ROLE_DEV');

INSERT INTO `truffleapp`.`tb_account` (`tx_city`, `tx_state`, `bl_status`, `tx_email`, `tx_password`, `tx_name`)
     VALUES ('Fortaleza', 'CE', true, 'admin@truffles.com.br', '{bcrypt}$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'admin');

INSERT INTO `truffleapp`.`tb_account` (`tx_city`, `tx_state`, `bl_status`, `tx_email`, `tx_password`, `tx_name`)
     VALUE ('Fortaleza','user@truffles.com.br'	'{bcrypt}$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra');