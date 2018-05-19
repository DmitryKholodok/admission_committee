ALTER TABLE `user` ADD gender TINYINT(1);
ALTER TABLE `user` ADD tel CHAR(12);
ALTER TABLE `specialty_enrollee_data` ADD chargeable TINYINT(1);

UPDATE `adcom`.`user` SET `gender`='0', `tel`='375291497358' WHERE `user_id`='1';
UPDATE `adcom`.`user` SET `gender`='1', `tel`='375291432123' WHERE `user_id`='2';
UPDATE `adcom`.`user` SET `gender`='0', `tel`='375291234567' WHERE `user_id`='3';

UPDATE `adcom`.`user` SET `gender`='0', `tel`='375291111111' WHERE `user_id`='4';
UPDATE `adcom`.`user` SET `gender`='1', `tel`='375291321321' WHERE `user_id`='5';
UPDATE `adcom`.`user` SET `gender`='0', `tel`='375291212121' WHERE `user_id`='6';

INSERT INTO `adcom`.`specialty_enrollee_data` (`user_id`, `specialty_id`, `chargeable`) VALUES ('3', '1', '1');
INSERT INTO `adcom`.`specialty_enrollee_data` (`user_id`, `specialty_id`, `chargeable`) VALUES ('5', '3', '0');
INSERT INTO `adcom`.`specialty_enrollee_data` (`user_id`, `specialty_id`, `chargeable`) VALUES ('6', '5', '0');



