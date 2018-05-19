ALTER TABLE `user` ADD age INT;
ALTER TABLE `user` ADD tel CHAR(12);
ALTER TABLE `specialty_enrollee_data` ADD chargeable TINYINT(1);

UPDATE `adcom`.`user` SET `age`='18', `tel`='375291497358' WHERE `user_id`='1';
UPDATE `adcom`.`user` SET `tel`='375291432123' WHERE `user_id`='2';
UPDATE `adcom`.`user` SET `age`='20', `tel`='375291234567' WHERE `user_id`='3';

INSERT INTO `adcom`.`specialty_enrollee_data` (`user_id`, `specialty_id`, `chargeable`) VALUES ('3', '1', '1');



