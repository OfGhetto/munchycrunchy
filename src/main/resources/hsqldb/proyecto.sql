-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema proyecto
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema proyecto
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `proyecto` DEFAULT CHARACTER SET utf8 ;
USE `proyecto` ;

-- -----------------------------------------------------
-- Table `proyecto`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyecto`.`usuario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `rut` VARCHAR(9) NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `apellido` VARCHAR(45) NOT NULL,
  `direccion` VARCHAR(45) NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `rut_UNIQUE` (`rut` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyecto`.`medio_pago`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyecto`.`medio_pago` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `forma_pago` VARCHAR(45) NOT NULL,
  `descripcion` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyecto`.`venta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyecto`.`venta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fecha_resolucion` DATE NOT NULL,
  `n_documento` INT NOT NULL,
  `usuario_id` INT NOT NULL,
  `medio_pago_id` INT NOT NULL,
  `cliente_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_venta_usuario1_idx` (`usuario_id` ASC) VISIBLE,
  INDEX `fk_venta_medio_pago1_idx` (`medio_pago_id` ASC) VISIBLE,
  CONSTRAINT `fk_venta_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `proyecto`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_venta_medio_pago1`
    FOREIGN KEY (`medio_pago_id`)
    REFERENCES `proyecto`.`medio_pago` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyecto`.`categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyecto`.`categoria` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `descripcion` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyecto`.`producto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyecto`.`producto` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `marca` VARCHAR(45) NOT NULL,
  `cantidad` INT NOT NULL,
  `valor` INT NOT NULL,
  `unidadMedida` VARCHAR(5) NOT NULL,
  `categoria_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_producto_categoria1_idx` (`categoria_id` ASC) VISIBLE,
  CONSTRAINT `fk_producto_categoria1`
    FOREIGN KEY (`categoria_id`)
    REFERENCES `proyecto`.`categoria` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyecto`.`ingredientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyecto`.`ingredientes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `marca` VARCHAR(9) NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `unidadMedida` VARCHAR(45) NOT NULL,
  `cantidad` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyecto`.`producto_has_ingredientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyecto`.`producto_has_ingredientes` (
  `producto_id` INT NOT NULL,
  `ingredientes_id` INT NOT NULL,
  PRIMARY KEY (`producto_id`, `ingredientes_id`),
  INDEX `fk_producto_has_ingredientes_ingredientes1_idx` (`ingredientes_id` ASC) VISIBLE,
  INDEX `fk_producto_has_ingredientes_producto1_idx` (`producto_id` ASC) VISIBLE,
  CONSTRAINT `fk_producto_has_ingredientes_producto1`
    FOREIGN KEY (`producto_id`)
    REFERENCES `proyecto`.`producto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_producto_has_ingredientes_ingredientes1`
    FOREIGN KEY (`ingredientes_id`)
    REFERENCES `proyecto`.`ingredientes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `proyecto`.`venta_has_producto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proyecto`.`venta_has_producto` (
  `venta_id` INT NOT NULL,
  `producto_id` INT NOT NULL,
  PRIMARY KEY (`venta_id`, `producto_id`),
  INDEX `fk_venta_has_producto_producto1_idx` (`producto_id` ASC) VISIBLE,
  INDEX `fk_venta_has_producto_venta1_idx` (`venta_id` ASC) VISIBLE,
  CONSTRAINT `fk_venta_has_producto_venta1`
    FOREIGN KEY (`venta_id`)
    REFERENCES `proyecto`.`venta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_venta_has_producto_producto1`
    FOREIGN KEY (`producto_id`)
    REFERENCES `proyecto`.`producto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
