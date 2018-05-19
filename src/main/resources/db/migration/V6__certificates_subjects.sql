-- -----------------------------------------------------
-- Table `adcom`.`subject`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adcom`.`subject` (
  `subject_id` TINYINT(2) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`subject_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `adcom`.`certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adcom`.`certificate` (
  `certificate_id` INT(10) UNSIGNED NOT NULL,
  `user_id` INT(10) UNSIGNED NOT NULL,
  `subject_id` TINYINT(2) UNSIGNED NOT NULL,
  `date_of_issue` DATE NOT NULL,
  `point` TINYINT(3) UNSIGNED NOT NULL,
  PRIMARY KEY (`certificate_id`),
  INDEX `fk_Certificate_Subject1_idx` (`subject_id` ASC),
  INDEX `fk_certificate_enrollee_data1_idx` (`user_id` ASC),
  CONSTRAINT `fk_Certificate_Subject1`
    FOREIGN KEY (`subject_id`)
    REFERENCES `adcom`.`subject` (`subject_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_certificate_enrollee_data1`
    FOREIGN KEY (`user_id`)
    REFERENCES `adcom`.`enrollee_data` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


INSERT INTO `adcom`.`subject` (`subject_id`, `name`) VALUES ('1', 'Maths');
INSERT INTO `adcom`.`subject` (`subject_id`, `name`) VALUES ('2', 'Physics');
INSERT INTO `adcom`.`subject` (`subject_id`, `name`) VALUES ('3', 'Русский');

INSERT INTO `adcom`.`certificate` (`certificate_id`, `user_id`, `subject_id`, `date_of_issue`, `point`) VALUES ('1023412', '3', '1', '2017-10-20', '11');
INSERT INTO `adcom`.`certificate` (`certificate_id`, `user_id`, `subject_id`, `date_of_issue`, `point`) VALUES ('1231987', '3', '2', '2017-10-24', '22');
INSERT INTO `adcom`.`certificate` (`certificate_id`, `user_id`, `subject_id`, `date_of_issue`, `point`) VALUES ('1321111', '3', '3', '2017-10-25', '5');

INSERT INTO `adcom`.`certificate` (`certificate_id`, `user_id`, `subject_id`, `date_of_issue`, `point`) VALUES ('5555555', '5', '1', '2017-10-19', '34');
INSERT INTO `adcom`.`certificate` (`certificate_id`, `user_id`, `subject_id`, `date_of_issue`, `point`) VALUES ('6666666', '5', '2', '2017-10-25', '23');
INSERT INTO `adcom`.`certificate` (`certificate_id`, `user_id`, `subject_id`, `date_of_issue`, `point`) VALUES ('7777777', '5', '3', '2017-10-26', '10');

INSERT INTO `adcom`.`certificate` (`certificate_id`, `user_id`, `subject_id`, `date_of_issue`, `point`) VALUES ('1982543', '6', '1', '2017-09-21', '100');
INSERT INTO `adcom`.`certificate` (`certificate_id`, `user_id`, `subject_id`, `date_of_issue`, `point`) VALUES ('1231351', '6', '2', '2017-08-14', '100');
INSERT INTO `adcom`.`certificate` (`certificate_id`, `user_id`, `subject_id`, `date_of_issue`, `point`) VALUES ('1321309', '6', '3', '2017-07-07', '100');

INSERT INTO `adcom`.`basic_certificate` (`user_id`, `bc_id`, `point`, `date_of_issue`) VALUES ('3', '1231231', '75', '2017-06-13');
INSERT INTO `adcom`.`basic_certificate` (`user_id`, `bc_id`, `point`, `date_of_issue`) VALUES ('5', '1010101', '100', '2017-07-12');
INSERT INTO `adcom`.`basic_certificate` (`user_id`, `bc_id`, `point`, `date_of_issue`) VALUES ('6', '1234567', '34', '2017-08-04');