-- MySQL dump 10.13  Distrib 5.7.18, for Linux (x86_64)
--
-- Host: localhost    Database: accounts
-- ------------------------------------------------------
-- Server version	5.7.18-0ubuntu0.16.10.1
--
-- Table structure for table `role`
--
--CREATE DATABASE IF NOT EXISTS sonar;
CREATE DATABASE IF NOT EXISTS evnsystem;
USE evnsystem;
DROP TABLE IF EXISTS `s_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `roles` varbinary(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;