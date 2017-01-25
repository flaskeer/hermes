/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50551
Source Host           : localhost:3306
Source Database       : opensource

Target Server Type    : MYSQL
Target Server Version : 50551
File Encoding         : 65001

Date: 2017-01-06 15:34:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for black_white_list
-- ----------------------------
DROP TABLE IF EXISTS `black_white_list`;
CREATE TABLE `black_white_list` (
  `id` int(11) DEFAULT NULL,
  `black_white_type` int(2) DEFAULT NULL,
  `server` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(100) DEFAULT NULL,
  `keywords` varchar(100) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8350 DEFAULT CHARSET=utf8;
