-- -----------------------------------------------------
-- Table `adcom`.`faculty`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adcom`.`faculty` (
  `faculty_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`faculty_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `adcom`.`specialty`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adcom`.`specialty` (
  `specialty_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `faculty_id` INT UNSIGNED NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `budgetary_pc` INT UNSIGNED NOT NULL,
  `chargeable_pc` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`specialty_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  INDEX `fk_Specialty_Faculty1_idx` (`faculty_id` ASC),
  CONSTRAINT `fk_Specialty_Faculty1`
    FOREIGN KEY (`faculty_id`)
    REFERENCES `adcom`.`faculty` (`faculty_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `adcom`.`specialty_enrollee_data`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adcom`.`specialty_enrollee_data` (
  `user_id` INT(10) UNSIGNED NOT NULL,
  `specialty_id` INT UNSIGNED NOT NULL,
  INDEX `fk_Specialty_Enrollee_Data_Specialty1_idx` (`specialty_id` ASC),
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_Specialty_Enrollee_Data_Specialty1`
    FOREIGN KEY (`specialty_id`)
    REFERENCES `adcom`.`specialty` (`specialty_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Specialty_Enrollee_Data_enrollee_data1`
    FOREIGN KEY (`user_id`)
    REFERENCES `adcom`.`enrollee_data` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


INSERT INTO `adcom`.`faculty` (`name`) VALUES ('f1_РУССКИЙ_ТЕСТ');
INSERT INTO `adcom`.`faculty` (`name`) VALUES ('f2');
INSERT INTO `adcom`.`faculty` (`name`) VALUES ('f3');


INSERT INTO `adcom`.`specialty` (`faculty_id`, `name`, `chargeable_pc`, `budgetary_pc`) VALUES ('1', 'f1_sp1_РУССКИЙ_ТЕСТ', '10', '10');
INSERT INTO `adcom`.`specialty` (`faculty_id`, `name`, `chargeable_pc`, `budgetary_pc`) VALUES ('1', 'f1_sp2', '20', '25');
INSERT INTO `adcom`.`specialty` (`faculty_id`, `name`, `chargeable_pc`, `budgetary_pc`) VALUES ('1', 'f1_sp3', '30', '25');
INSERT INTO `adcom`.`specialty` (`faculty_id`, `name`, `chargeable_pc`, `budgetary_pc`) VALUES ('2', 'f2_sp1_РУССКИЙ_ТЕСТ', '23', '7');
INSERT INTO `adcom`.`specialty` (`faculty_id`, `name`, `chargeable_pc`, `budgetary_pc`) VALUES ('2', 'f2_sp2', '7', '1');
INSERT INTO `adcom`.`specialty` (`faculty_id`, `name`, `chargeable_pc`, `budgetary_pc`) VALUES ('2', 'f2_sp3', '50', '65');
INSERT INTO `adcom`.`specialty` (`faculty_id`, `name`, `chargeable_pc`, `budgetary_pc`) VALUES ('3', 'f3_sp1_РУССКИЙ_ТЕСТ', '10', '10');
INSERT INTO `adcom`.`specialty` (`faculty_id`, `name`, `chargeable_pc`, `budgetary_pc`) VALUES ('3', 'f3_sp2', '13', '34');
INSERT INTO `adcom`.`specialty` (`faculty_id`, `name`, `chargeable_pc`, `budgetary_pc`) VALUES ('3', 'f3_sp3', '23', '43');



