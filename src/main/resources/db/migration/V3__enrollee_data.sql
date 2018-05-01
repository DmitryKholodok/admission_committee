-- -----------------------------------------------------
-- Table `adcom`.`enrollee_data`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adcom`.`enrollee_data` (
  `user_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `fk_enrollee_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_enrollee_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `adcom`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;