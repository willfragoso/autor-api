-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema schema_autor
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `schema_autor` DEFAULT CHARACTER SET utf8;
USE `schema_autor`;

-- -----------------------------------------------------
-- Table `schema_autor`.`autor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schema_autor`.`autor`
(
    `id`              INT         NOT NULL AUTO_INCREMENT,
    `nome`            VARCHAR(50) NOT NULL,
    `pseudonimo`      VARCHAR(50) NOT NULL,
    `data_nascimento` DATE        NOT NULL,
    PRIMARY KEY (`ID`)
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SCHEMA_AUTOR`.`LIVRO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schema_autor`.`livro`
(
    `id`              INT         NOT NULL AUTO_INCREMENT,
    `id_autor`        INT         NOT NULL,
    `nome`            VARCHAR(50) NOT NULL,
    `numero_paginas`  INT         NOT NULL,
    `data_publicacao` DATE        NOT NULL,
    PRIMARY KEY (`ID`),
    INDEX `fk_livro_autor_idx` (`id_autor` ASC) VISIBLE,
    CONSTRAINT `fk_livro_autor`
        FOREIGN KEY (`id_autor`)
            REFERENCES `schema_autor`.`autor` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
