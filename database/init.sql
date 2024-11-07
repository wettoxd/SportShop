create database if not exists sportshop;
use sportshop;



CREATE TABLE IF NOT EXISTS `sportshop`.`pickuppoints` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  `address` VARCHAR(200) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `sportshop`.`role` (
  `id` INT NOT NULL,
  `name` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;



CREATE TABLE IF NOT EXISTS `sportshop`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `FIO` VARCHAR(100) NULL DEFAULT NULL,
  `login` VARCHAR(55) NULL DEFAULT NULL,
  `pass` VARCHAR(55) NULL DEFAULT NULL,
  `email` VARCHAR(55) NULL DEFAULT NULL,
  `role_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_role` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `sportshop`.`role` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;



CREATE TABLE IF NOT EXISTS `sportshop`.`orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `customer_id` INT NULL DEFAULT NULL,
  `order_date` DATE NULL DEFAULT NULL,
  `total_amount` DECIMAL(10,2) NULL DEFAULT NULL,
  `pickup_point_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user` (`customer_id` ASC) VISIBLE,
  INDEX `fk_pickup_point` (`pickup_point_id` ASC) VISIBLE,
  CONSTRAINT `fk_pickup_point`
    FOREIGN KEY (`pickup_point_id`)
    REFERENCES `sportshop`.`pickuppoints` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_user`
    FOREIGN KEY (`customer_id`)
    REFERENCES `sportshop`.`users` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 57
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;



CREATE TABLE IF NOT EXISTS `sportshop`.`сategory_of_products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;



CREATE TABLE IF NOT EXISTS `sportshop`.`suppliers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `phone_number` VARCHAR(45) NULL DEFAULT NULL,
  `description` VARCHAR(200) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;



CREATE TABLE IF NOT EXISTS `sportshop`.`products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  `category_id` INT NULL DEFAULT NULL,
  `price` INT NULL DEFAULT NULL,
  `stock_quantity` INT NULL DEFAULT NULL,
  `supplier_id` INT NULL DEFAULT NULL,
  `description` VARCHAR(100) NULL DEFAULT NULL,
  `photo` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_supplier` (`supplier_id` ASC) VISIBLE,
  INDEX `fk_category` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_category`
    FOREIGN KEY (`category_id`)
    REFERENCES `sportshop`.`сategory_of_products` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_supplier`
    FOREIGN KEY (`supplier_id`)
    REFERENCES `sportshop`.`suppliers` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `sportshop`.`orderproducts` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NULL DEFAULT NULL,
  `product_id` INT NULL DEFAULT NULL,
  `quantity` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order` (`order_id` ASC) VISIBLE,
  INDEX `fk_product` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_order`
    FOREIGN KEY (`order_id`)
    REFERENCES `sportshop`.`orders` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `sportshop`.`products` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 61
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

INSERT INTO `role` VALUES (1,'Клиент'),(2,'Сотрудник');
INSERT INTO `pickuppoints` VALUES (1,'OZON','ул. Минина, д. 12б');
INSERT INTO `users` VALUES (1,'Гаврилов Петр Семенович','gavrilov89','12345','gavrilov@mail.ru',1),(2,'Петров Петр Владимирович','1petr','123456','petr@mail.ru',1),(3,'Sigma Male','sigma1','123','sigma@mail.ru',2);
INSERT INTO `orders` VALUES (2,1,'2023-11-11',0.00,1),(3,1,'2023-11-11',0.00,1),(4,1,'2023-11-11',0.00,1),(6,1,'2023-11-11',NULL,1),(7,2,'2023-11-11',0.00,1),(8,2,'2023-11-13',0.00,1),(9,1,'2023-11-13',0.00,1),(11,1,'2023-11-15',0.00,1),(12,1,NULL,NULL,NULL),(13,1,'2023-11-15',0.00,1),(14,1,NULL,3500.00,NULL),(15,1,'2023-11-15',0.00,1),(16,1,'2023-11-15',NULL,1),(17,1,'2023-11-15',NULL,1),(18,1,'2023-11-15',NULL,1),(19,1,'2023-11-15',NULL,1),(20,1,'2023-11-15',NULL,1),(21,1,'2023-11-16',NULL,1),(22,1,'2023-11-16',NULL,1),(24,1,'2023-11-16',NULL,1),(25,1,'2023-11-16',0.00,1),(26,1,'2023-11-16',NULL,1),(27,1,'2023-11-16',NULL,1),(28,1,'2023-11-16',0.00,1),(29,1,'2023-11-16',NULL,1),(30,1,'2023-11-16',NULL,1),(31,1,'2023-11-17',NULL,1),(32,1,'2023-11-17',NULL,1),(33,1,'2023-11-19',NULL,1),(34,1,'2023-11-19',NULL,1),(35,1,'2023-11-19',NULL,1),(36,1,'2023-11-19',NULL,1),(37,1,'2023-11-19',NULL,1),(38,1,'2023-11-19',14000.00,1),(39,1,'2023-11-19',38000.00,1),(40,1,'2023-11-19',4000.00,1),(41,1,'2023-11-20',16000.00,1),(42,1,'2023-11-20',42000.00,1),(43,1,'2023-11-20',40000.00,1),(44,1,'2023-11-20',0.00,1),(45,1,'2023-11-20',0.00,1),(46,1,'2023-11-20',0.00,1),(47,1,'2023-11-20',24000.00,1),(48,1,'2023-11-20',26000.00,1),(49,1,'2023-11-20',0.00,1),(50,1,'2023-11-20',26000.00,1),(51,1,'2023-11-20',0.00,1),(52,1,'2023-11-20',0.00,1),(53,1,'2023-11-20',12000.00,1),(54,2,'2023-11-21',2000.00,1),(55,1,'2023-11-21',14000.00,1);
INSERT INTO `сategory_of_products` VALUES (1,'Баскетбол'),(2,'Бокс'),(3,'Футбол '),(4,'Хоккей');
INSERT INTO `suppliers` VALUES (1,'Spalding','spalding@gmail.com','895454545','Американская компания, всемирно известный производитель спортивных товаров.'),(5,'Nike','nikepro@gmail.com ','878676767','Бренд спортивной одежды');
INSERT INTO `products` VALUES (1,'Баскетбольный мяч',1,3500,28,1,'Отличный мяч для игры в помещении и на улице.  ','image/ball.jpg'),(5,'Кросовки',1,12000,10,5,'Удобная обувь. ','image/sneakers.jpg'),(6,'Боксёрские перчатки',2,2000,11,1,'Качественные перчатки. ','image/box.jpg');
INSERT INTO `orderproducts` VALUES (1,14,1,1),(7,6,5,NULL),(8,16,1,NULL),(9,17,1,NULL),(10,17,5,NULL),(11,18,1,NULL),(12,18,5,NULL),(13,19,5,NULL),(14,20,1,NULL),(15,21,1,NULL),(16,22,1,NULL),(17,24,1,NULL),(19,26,6,NULL),(20,26,5,NULL),(21,27,6,NULL),(23,29,6,NULL),(24,29,1,NULL),(25,29,6,NULL),(26,29,5,NULL),(27,30,6,NULL),(28,30,5,NULL),(29,31,5,NULL),(30,32,5,NULL),(31,33,5,NULL),(32,34,6,NULL),(33,35,6,NULL),(34,35,5,NULL),(35,36,6,NULL),(36,36,5,NULL),(37,37,6,NULL),(38,37,5,NULL),(39,38,6,1),(40,38,5,1),(41,39,6,1),(42,39,5,3),(43,40,6,1),(44,40,6,1),(45,41,6,1),(46,41,5,1),(47,41,6,1),(48,42,6,3),(49,42,5,3),(50,43,6,2),(51,43,5,3),(52,47,5,2),(53,48,6,1),(54,48,5,2),(55,50,6,1),(56,50,5,2),(57,53,5,1),(58,54,6,1),(59,55,1,4);


DELIMITER $$
USE `sportshop`$$
CREATE DEFINER=`javafxTest`@`localhost` PROCEDURE `IncreaseStock`()
BEGIN
 UPDATE Products
 SET stock_quantity = stock_quantity + 10;
END$$

DELIMITER ;
USE `sportshop`;

DELIMITER $$
USE `sportshop`$$
CREATE
DEFINER=`javafxTest`@`localhost`
TRIGGER `sportshop`.`check_supplier_phone_number`
BEFORE INSERT ON `sportshop`.`suppliers`
FOR EACH ROW
BEGIN
    IF NEW.phone_number REGEXP '[^0-9+]' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Номер телефона может содержать тоолько цифры и символ + ';
    END IF;
END$$

USE `sportshop`$$
CREATE
DEFINER=`javafxTest`@`localhost`
TRIGGER `sportshop`.`insert_order_total_amount`
AFTER INSERT ON `sportshop`.`orderproducts`
FOR EACH ROW
BEGIN
    UPDATE Orders
    SET total_amount = (SELECT SUM(quantity * price) FROM OrderProducts
                        JOIN Products ON OrderProducts.product_id = Products.id
                        WHERE OrderProducts.order_id = NEW.order_id)
    WHERE id = NEW.order_id;
END$$

USE `sportshop`$$
CREATE
DEFINER=`javafxTest`@`localhost`
TRIGGER `sportshop`.`update_order_total_amount`
AFTER UPDATE ON `sportshop`.`orderproducts`
FOR EACH ROW
BEGIN
    UPDATE Orders
    SET total_amount = (SELECT SUM(quantity * price) FROM OrderProducts
                        JOIN Products ON OrderProducts.product_id = Products.id
                        WHERE OrderProducts.order_id = NEW.order_id)
    WHERE id = NEW.order_id;
END$$

USE `sportshop`$$
CREATE
DEFINER=`javafxTest`@`localhost`
TRIGGER `sportshop`.`update_stock_quantity`
AFTER INSERT ON `sportshop`.`orderproducts`
FOR EACH ROW
BEGIN
    DECLARE current_stock INT;

    SELECT stock_quantity INTO current_stock
    FROM Products
    WHERE id = NEW.product_id;

    IF current_stock - NEW.quantity >= 0 THEN
        UPDATE Products
        SET stock_quantity = stock_quantity - NEW.quantity
        WHERE id = NEW.product_id;
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Not enough stock available';
    END IF;
END$$



DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
