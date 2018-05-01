SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';


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
-- Table `adcom`.`user_auth`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adcom`.`user_auth` (
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
AUTO_INCREMENT = 9
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
    REFERENCES `adcom`.`user_auth` (`user_id`)
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