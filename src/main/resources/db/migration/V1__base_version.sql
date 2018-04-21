SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema adcom
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema adcom
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `adcom` DEFAULT CHARACTER SET utf8 ;
USE `adcom` ;

-- -----------------------------------------------------
-- Table `adcom`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adcom`.`user` (
  `user_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 28
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `adcom`.`enrollee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adcom`.`enrollee` (
  `enrollee_id` INT(10) UNSIGNED NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`enrollee_id`),
  CONSTRAINT `fk_Enrollee_User1`
    FOREIGN KEY (`enrollee_id`)
    REFERENCES `adcom`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


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
  `cerificate_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT(10) UNSIGNED NOT NULL,
  `subject_id` TINYINT(2) UNSIGNED NOT NULL,
  `date_of_issue` DATE NOT NULL,
  `point` TINYINT(3) UNSIGNED NOT NULL,
  PRIMARY KEY (`cerificate_id`),
  INDEX `fk_Certificate_Enrollee1_idx` (`user_id` ASC),
  INDEX `fk_Certificate_Subject1_idx` (`subject_id` ASC),
  CONSTRAINT `fk_Certificate_Enrollee1`
    FOREIGN KEY (`user_id`)
    REFERENCES `adcom`.`enrollee` (`enrollee_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Certificate_Subject1`
    FOREIGN KEY (`subject_id`)
    REFERENCES `adcom`.`subject` (`subject_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `adcom`.`faculty`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adcom`.`faculty` (
  `faculty_id` TINYINT(2) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`faculty_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `adcom`.`login_password`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adcom`.`login_password` (
  `user_id` INT(10) UNSIGNED NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(75) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC),
  CONSTRAINT `fk_Login_Password_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `adcom`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `adcom`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adcom`.`role` (
  `role_id` TINYINT(2) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `adcom`.`specialty`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adcom`.`specialty` (
  `specialty_id` TINYINT(3) UNSIGNED NOT NULL AUTO_INCREMENT,
  `faculty_id` TINYINT(2) UNSIGNED NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`specialty_id`),
  INDEX `fk_Specialty_Faculty1_idx` (`faculty_id` ASC),
  CONSTRAINT `fk_Specialty_Faculty1`
    FOREIGN KEY (`faculty_id`)
    REFERENCES `adcom`.`faculty` (`faculty_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `adcom`.`statement`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adcom`.`statement` (
  `statement_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT(10) UNSIGNED NOT NULL,
  `operator_id` INT(10) UNSIGNED NOT NULL,
  `date_of_registration` DATE NOT NULL,
  PRIMARY KEY (`statement_id`),
  INDEX `fk_Statement_Enrollee1_idx` (`user_id` ASC),
  INDEX `fk_Statement_User1_idx` (`operator_id` ASC),
  CONSTRAINT `fk_Statement_Enrollee1`
    FOREIGN KEY (`user_id`)
    REFERENCES `adcom`.`enrollee` (`enrollee_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Statement_User1`
    FOREIGN KEY (`operator_id`)
    REFERENCES `adcom`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `adcom`.`statement_specialty`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adcom`.`statement_specialty` (
  `statement_id` INT(10) UNSIGNED NOT NULL,
  `specialty_id` TINYINT(3) UNSIGNED NOT NULL,
  `priority` TINYINT(3) NOT NULL,
  PRIMARY KEY (`statement_id`, `specialty_id`),
  INDEX `fk_Statement_has_Specialty_Specialty1_idx` (`specialty_id` ASC),
  INDEX `fk_Statement_has_Specialty_Statement1_idx` (`statement_id` ASC),
  CONSTRAINT `fk_Statement_has_Specialty_Specialty1`
    FOREIGN KEY (`specialty_id`)
    REFERENCES `adcom`.`specialty` (`specialty_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Statement_has_Specialty_Statement1`
    FOREIGN KEY (`statement_id`)
    REFERENCES `adcom`.`statement` (`statement_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `adcom`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adcom`.`user_role` (
  `user_id` INT(10) UNSIGNED NOT NULL,
  `role_id` TINYINT(2) UNSIGNED NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),
  INDEX `fk_Login_Password_has_Role_Role1_idx` (`role_id` ASC),
  INDEX `fk_Login_Password_has_Role_Login_Password1_idx` (`user_id` ASC),
  CONSTRAINT `fk_Login_Password_has_Role_Login_Password1`
    FOREIGN KEY (`user_id`)
    REFERENCES `adcom`.`login_password` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Login_Password_has_Role_Role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `adcom`.`role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
