DROP TABLE IF EXISTS owner;
CREATE TABLE owner (
  owner_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(40),
  warning_count INT DEFAULT 0,
  delete_flag BOOLEAN DEFAULT false
) ENGINE=INNODB;

DROP TABLE IF EXISTS sugar;
CREATE TABLE sugar (
  sugar_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  price_per_unit INT NOT NULL,
  volumn INT NOT NULL,
  import_date VARCHAR(20),
  delete_flag BOOLEAN DEFAULT false,
  owner_id INT NOT NULL
) ENGINE=INNODB;

ALTER TABLE sugar ADD CONSTRAINT FK_sugar_owner_id FOREIGN KEY (owner_id) REFERENCES owner(owner_id)
ON UPDATE CASCADE ON DELETE CASCADE;

DROP TABLE IF EXISTS rice;
CREATE TABLE rice (
  rice_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  price_per_unit INT NOT NULL,
  volumn INT NOT NULL,
  import_date VARCHAR(20),
  delete_flag BOOLEAN DEFAULT false,
  owner_id INT NOT NULL
) ENGINE=INNODB;

ALTER TABLE rice ADD CONSTRAINT FK_rice_owner_id FOREIGN KEY (owner_id) REFERENCES owner(owner_id)
ON UPDATE CASCADE ON DELETE CASCADE;

DROP TABLE IF EXISTS egg;
CREATE TABLE egg (
  egg_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  price_per_unit INT NOT NULL,
  volumn INT NOT NULL,
  import_date VARCHAR(20),
  delete_flag BOOLEAN DEFAULT false,
  owner_id INT NOT NULL
) ENGINE=INNODB;

ALTER TABLE egg ADD CONSTRAINT FK_egg_owner_id FOREIGN KEY (owner_id) REFERENCES owner(owner_id)
ON UPDATE CASCADE ON DELETE CASCADE;

DROP TABLE IF EXISTS product_type;
CREATE TABLE product_type (
  producttype_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(50),
  import_date VARCHAR(20),
  delete_flag BOOLEAN DEFAULT false
) ENGINE=INNODB;
