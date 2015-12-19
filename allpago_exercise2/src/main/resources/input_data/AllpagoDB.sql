
CREATE DATABASE  IF NOT EXISTS `allpago` ;
USE `allpago`;
--
-- Database: allpago
-- ------------------------------------------------------

--
-- Table structure for table `offices`
--

DROP TABLE IF EXISTS `offices`;

CREATE TABLE `offices` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(145) NOT NULL,
  `country` varchar(145) NOT NULL,
  `open_from` TIME NOT NULL,
  `open_until` TIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `city_country_UNIQUE` (`city`,`country`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


select * from offices;

insert into offices(`city`,`country`,`open_from`,`open_until`) values ('Chennai','India','10:00', '20:00');

insert into offices(`city`,`country`,`open_from`,`open_until`) values ('Delhi','India','10:00', '23:00');

insert into offices(`city`,`country`,`open_from`,`open_until`) values ('Mumbai','India','10:00', '23:00');

insert into offices(`city`,`country`,`open_from`,`open_until`) values ('Berlin','Germany','10:00', '20:00');

insert into offices(`city`,`country`,`open_from`,`open_until`) values ('London','UK','10:00', '20:00');





select * from offices;

