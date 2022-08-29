-- -----------------------------------------------------
-- Table `book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book` (
  `isbn` CHAR(12) NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `author` VARCHAR(50) NOT NULL,
  `price` INT NOT NULL,
  `content` TEXT NULL,
  `img` VARCHAR(100) NULL,
  PRIMARY KEY (`isbn`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user` (
  `id` VARCHAR(50) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `pass` VARCHAR(100) NOT NULL,
  `rec_id` VARCHAR(50) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_user1_idx` (`rec_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_user1`
    FOREIGN KEY (`rec_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comment` (
  `isbn` CHAR(12) NOT NULL,
  `id` VARCHAR(50) NOT NULL,
  `rating` INT default 5,
  `comment` VARCHAR(1000) NULL,
  constraint check( `rating` >0 and `rating`<=5),
  PRIMARY KEY (`isbn`, `id`),
  INDEX `fk_comment_user1_idx` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_comment_book`
    FOREIGN KEY (`isbn`)
    REFERENCES `book` (`isbn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
