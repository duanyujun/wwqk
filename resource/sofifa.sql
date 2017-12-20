/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.6.21-log : Database - wwqk
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `sofifa` */

CREATE TABLE `sofifa` (
  `id` varchar(50) NOT NULL,
  `fifa_name` varchar(50) DEFAULT NULL,
  `foot` varchar(50) DEFAULT NULL,
  `inter_rep` varchar(5) DEFAULT NULL COMMENT '国际声誉',
  `unuse_foot` varchar(5) DEFAULT NULL COMMENT '逆足能力',
  `trick` varchar(5) DEFAULT NULL COMMENT '花式技巧',
  `work_rate` varchar(30) DEFAULT NULL COMMENT '积极性',
  `body_type` varchar(30) DEFAULT NULL COMMENT '身体条件',
  `release_clause` varchar(20) DEFAULT NULL COMMENT '违约金',
  `position` varchar(20) DEFAULT NULL COMMENT '位置',
  `number` varchar(10) DEFAULT NULL COMMENT '号码',
  `contract` varchar(15) DEFAULT NULL COMMENT '合同到期',
  `pac` varchar(10) DEFAULT NULL COMMENT '速度',
  `sho` varchar(10) DEFAULT NULL COMMENT '射门',
  `pas` varchar(10) DEFAULT NULL COMMENT '传球',
  `dri` varchar(10) DEFAULT NULL COMMENT '盘带',
  `def` varchar(10) DEFAULT NULL COMMENT '防守',
  `phy` varchar(10) DEFAULT NULL COMMENT '力量',
  `overall-rate` varchar(10) DEFAULT NULL COMMENT '综合能力',
  `potential` varchar(10) DEFAULT NULL COMMENT '潜力',
  `market-value` varchar(20) DEFAULT NULL COMMENT '身价',
  `wage` varchar(20) DEFAULT NULL COMMENT '周薪',
  `player_id` varchar(50) DEFAULT NULL COMMENT '球员id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sofifa` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
