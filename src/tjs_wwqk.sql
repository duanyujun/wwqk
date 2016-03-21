/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.6.21-log : Database - jfinal
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `league` */

CREATE TABLE `league` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '联赛id',
  `name` varchar(50) DEFAULT NULL COMMENT '联赛名称（中文）',
  `name_en` varchar(100) DEFAULT NULL COMMENT '联赛名称（英文）',
  `season` varchar(20) DEFAULT NULL COMMENT '赛季（20152016）',
  `source_id` varchar(100) DEFAULT NULL COMMENT '源联赛id',
  `league_url` varchar(300) DEFAULT NULL COMMENT '联赛URL',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `league` */

/*Table structure for table `player` */

CREATE TABLE `player` (
  `id` varchar(45) NOT NULL COMMENT 'ID',
  `first_name` varchar(50) DEFAULT NULL COMMENT '名字',
  `last_name` varchar(50) DEFAULT NULL COMMENT '姓',
  `first_name_en` varchar(50) DEFAULT NULL COMMENT '名字（英文）',
  `last_name_en` varchar(50) DEFAULT NULL COMMENT '姓（英文）',
  `nationality` varchar(80) DEFAULT NULL COMMENT '国籍',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `birth_country` varchar(50) DEFAULT NULL COMMENT '出生国家',
  `birth_place` varchar(50) DEFAULT NULL COMMENT '出生地',
  `position` varchar(30) DEFAULT NULL COMMENT '球场位置',
  `height` int(11) DEFAULT NULL COMMENT '身高',
  `weight` int(11) DEFAULT NULL COMMENT '体重',
  `foot` varchar(50) DEFAULT NULL COMMENT '惯用脚',
  `team_id` varchar(50) DEFAULT NULL COMMENT '所在球队',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `player` */

/*Table structure for table `team` */

CREATE TABLE `team` (
  `id` varchar(50) NOT NULL COMMENT '球队id',
  `name` varchar(50) DEFAULT NULL COMMENT '球队名称（中文）',
  `name_en` varchar(100) DEFAULT NULL COMMENT '球队名称（英文）',
  `setup_time` varchar(20) DEFAULT NULL COMMENT '成立时间',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `country` varchar(100) DEFAULT NULL COMMENT '国家',
  `telphone` varchar(100) DEFAULT NULL COMMENT '电话',
  `fax` varchar(100) DEFAULT NULL COMMENT '传真',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮件',
  `venue_name` varchar(150) DEFAULT NULL COMMENT '体育场名称',
  `venue_name_en` varchar(200) DEFAULT NULL COMMENT '体育场英文名称',
  `venue_address` varchar(300) DEFAULT NULL COMMENT '体育场所在城市',
  `venue_capacity` int(11) DEFAULT NULL COMMENT '容量',
  `venue_img` varchar(300) DEFAULT NULL COMMENT '体育馆照片',
  `team_url` varchar(300) DEFAULT NULL COMMENT 'url地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `team` */

/*Table structure for table `user` */

CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(40) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`age`,`sex`,`remark`) values (1,'李莉',24,1,'学员');
insert  into `user`(`id`,`name`,`age`,`sex`,`remark`) values (4,'周伯通',10,1,'叫兽');
insert  into `user`(`id`,`name`,`age`,`sex`,`remark`) values (5,'宋刚',30,1,'学员\r\n');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
