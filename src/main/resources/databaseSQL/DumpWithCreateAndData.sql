CREATE DATABASE  IF NOT EXISTS `employee_managment` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `employee_managment`;
-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (x86_64)
--
-- Host: localhost    Database: employee_managment
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'admin','2000-11-11',NULL,'backend','admin','$2a$10$Y7FK6YcnE9KCSoakl9JQc.zgi/uHgnuc3.3SfOHthpWGriFdTMeaa'),(8,'TestName22345','2023-03-17',NULL,'TestPosition532','test2','$2a$10$lFvAKpGA03.kf4beaBPTIuvK6Bgn.IBkAC1KmX6sB3e22cZQGrmGC'),(47,'Ognjen Barovic','2023-04-07',NULL,'Backend','gio','$2a$10$m8kyCp4lqecBSQdG6ByFy.b1mdJJjWaLPMzXy5HqbNuFPVju/iSjC'),(48,'Test','2023-04-12',NULL,'Frontend','TestTest2','$2a$10$BPf78m08eTThc37oS6rlS.hoUG7UwOSMW7KfMRst1Vfc6Orxg1GsO'),(49,'test2','2023-04-07',NULL,'pos','test34','$2a$10$a4kXemzmisKFWZJZ01fecuv8vmIDTN6kK1824yzEc9XQUyy8r5iZK'),(51,'Tst1','2023-04-24',NULL,'Backend','Tst1','$2a$10$hxlY1u.g7Tbt543bBbjeaeAQ5HB8C8Y4xiQH6lCGXr8e2FvRUYdWa'),(52,'Tst23','2023-04-12',NULL,'TestPosition','tst2','$2a$10$6qwFiVCbvyq66LAfG0bfOuJNEvtTURv5EiFSMR6IACVwi5mRbeGc2');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_role`
--

DROP TABLE IF EXISTS `employee_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_role` (
  `employee_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`employee_id`,`role_id`),
  KEY `employee_role_role_fk_idx` (`role_id`),
  CONSTRAINT `employee_role_employee_fk` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `employee_role_role_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_role`
--

LOCK TABLES `employee_role` WRITE;
/*!40000 ALTER TABLE `employee_role` DISABLE KEYS */;
INSERT INTO `employee_role` VALUES (1,1),(47,1),(48,1),(52,1),(8,2),(47,2),(48,2),(49,2),(51,2),(52,2),(52,4),(48,23);
/*!40000 ALTER TABLE `employee_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `past_employment`
--

DROP TABLE IF EXISTS `past_employment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `past_employment` (
  `employee_id` int NOT NULL,
  `company_name` varchar(255) NOT NULL,
  `from_date` date NOT NULL,
  `to_date` date DEFAULT NULL,
  PRIMARY KEY (`employee_id`,`company_name`,`from_date`),
  CONSTRAINT `employe_id_pste_fk` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `past_employment`
--

LOCK TABLES `past_employment` WRITE;
/*!40000 ALTER TABLE `past_employment` DISABLE KEYS */;
INSERT INTO `past_employment` VALUES (8,'abc','2010-02-26','2010-12-11'),(8,'CompanyTest','2011-11-11','2012-11-12'),(8,'ds','2012-11-11','2012-11-11'),(8,'qbc','1993-01-31','2005-01-28'),(47,'PMF','2021-06-05','2023-04-22'),(48,'Com','2023-04-12','2023-04-14'),(51,'Google','2016-02-24','2023-04-01'),(52,'Com1','2015-09-25','2021-12-25');
/*!40000 ALTER TABLE `past_employment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ADMIN','Unlimited power'),(2,'ROLE_USER','Limited power'),(4,'ROLE_TEST2','Role description test2'),(19,'ROLE_TEST3','Role description test3'),(23,'ROLE_SADF',''),(25,'ROLE_ME','ROLE TEST');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vacation`
--

DROP TABLE IF EXISTS `vacation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vacation` (
  `employee_id` int NOT NULL,
  `from_date` date NOT NULL,
  `to_date` date NOT NULL,
  PRIMARY KEY (`employee_id`,`from_date`),
  CONSTRAINT `employee_id_fk` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vacation`
--

LOCK TABLES `vacation` WRITE;
/*!40000 ALTER TABLE `vacation` DISABLE KEYS */;
INSERT INTO `vacation` VALUES (1,'2023-04-11','2023-04-12'),(1,'2023-04-13','2023-04-14'),(1,'2023-04-26','2023-04-27'),(8,'2005-06-24','2005-06-25'),(8,'2011-11-11','2011-11-12'),(8,'2023-04-08','2023-04-09'),(8,'2023-04-25','2023-04-26'),(47,'2023-04-12','2023-04-15'),(52,'2023-04-21','2023-04-29'),(52,'2023-05-29','2023-05-30');
/*!40000 ALTER TABLE `vacation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vacation_request`
--

DROP TABLE IF EXISTS `vacation_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vacation_request` (
  `employee_id` int NOT NULL,
  `from_date` date NOT NULL,
  `to_date` date DEFAULT NULL,
  PRIMARY KEY (`employee_id`,`from_date`),
  CONSTRAINT `vr_employee_id_fk` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vacation_request`
--

LOCK TABLES `vacation_request` WRITE;
/*!40000 ALTER TABLE `vacation_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `vacation_request` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-25 11:11:53
