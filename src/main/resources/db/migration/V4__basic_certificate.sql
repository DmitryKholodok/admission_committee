-- -----------------------------------------------------
-- Table `adcom`.`basic_certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adcom`.`basic_certificate` (
  `user_id` INT(10) UNSIGNED NOT NULL,
  `bc_id` INT UNSIGNED NOT NULL,
  `point` TINYINT(3) UNSIGNED NOT NULL,
  `date_of_issue` DATE NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `fk_basic_certificate_enrollee_data1_idx` (`user_id` ASC),
  UNIQUE INDEX `bc_id_UNIQUE` (`bc_id` ASC),
  CONSTRAINT `fk_basic_certificate_enrollee_data1`
    FOREIGN KEY (`user_id`)
    REFERENCES `adcom`.`enrollee_data` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;