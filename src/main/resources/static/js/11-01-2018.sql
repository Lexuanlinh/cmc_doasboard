-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: dashboard
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `group_permission`
--

DROP TABLE IF EXISTS `group_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_permission` (
  `group_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`group_id`,`permission_id`),
  KEY `FKbg3iyfbnf6or1qxh7951yuo9s` (`permission_id`),
  CONSTRAINT `FKbg3iyfbnf6or1qxh7951yuo9s` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`permission_id`),
  CONSTRAINT `FKnos1ous56491i1916vpqkyy6g` FOREIGN KEY (`group_id`) REFERENCES `groups` (`group_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_permission`
--

LOCK TABLES `group_permission` WRITE;
/*!40000 ALTER TABLE `group_permission` DISABLE KEYS */;
INSERT INTO `group_permission` VALUES (336,1),(338,1),(339,1),(340,1),(341,1),(335,2),(336,3),(338,3),(339,3),(340,3),(341,3),(335,4),(335,5),(335,6);
/*!40000 ALTER TABLE `group_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `created_on` datetime DEFAULT NULL,
  `group_desc` varchar(255) DEFAULT NULL,
  `group_name` varchar(100) NOT NULL,
  `updated_on` datetime DEFAULT NULL,
  `group_rank` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`group_id`),
  KEY `fk_group_role_idx` (`role_id`),
  CONSTRAINT `FKd5kq8feweud4jwbbblgm0t7xl` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `fk_group_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=343 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
INSERT INTO `groups` VALUES (335,'2017-12-11 08:41:23','Project Manager','PM','2017-12-11 08:41:23',NULL,2),(336,'2017-12-11 08:41:36','Quanlity Assurance','QA','2017-12-11 08:41:36',NULL,3),(337,'2017-12-11 08:43:04','DUL','DUL','2017-12-11 08:43:04',NULL,1),(338,'2017-12-11 08:42:53','DU1 Lead','DUL_1','2017-12-11 08:42:53',NULL,1),(339,'2017-12-11 08:42:56','DU2 Lead','DUL_2','2017-12-11 08:42:56',NULL,1),(340,'2017-12-11 08:42:59','DU3 Lead','DUL_3','2017-12-11 08:42:59',NULL,1),(341,'2017-12-11 08:43:02','Board Of Directors','BOD','2017-12-11 08:43:02',NULL,4),(342,'2017-12-11 08:43:04','Resource Reserve  Center','RRC','2017-12-11 08:43:04',NULL,1);
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `overloaded_plans`
--

DROP TABLE IF EXISTS `overloaded_plans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `overloaded_plans` (
  `overloaded_plans_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_plan_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `from_date` datetime DEFAULT NULL,
  `to_date` datetime DEFAULT NULL,
  `created_on` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_on` datetime DEFAULT CURRENT_TIMESTAMP,
  `total_effort` float DEFAULT NULL,
  PRIMARY KEY (`overloaded_plans_id`),
  KEY `FK1agvnctnas4hcjfxcw5ggvi4a` (`user_plan_id`),
  CONSTRAINT `user_plan_id_user_plan_id` FOREIGN KEY (`user_plan_id`) REFERENCES `user_plan` (`user_plan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `overloaded_plans`
--

LOCK TABLES `overloaded_plans` WRITE;
/*!40000 ALTER TABLE `overloaded_plans` DISABLE KEYS */;
/*!40000 ALTER TABLE `overloaded_plans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission_action`
--

DROP TABLE IF EXISTS `permission_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission_action` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `access` int(11) NOT NULL DEFAULT '0',
  `create` int(11) NOT NULL DEFAULT '0',
  `edit` int(11) NOT NULL DEFAULT '0',
  `delete` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission_action`
--

LOCK TABLES `permission_action` WRITE;
/*!40000 ALTER TABLE `permission_action` DISABLE KEYS */;
INSERT INTO `permission_action` VALUES (1,0,0,0,0),(2,0,0,0,1),(3,0,0,1,0),(4,0,0,1,1),(5,0,1,0,0),(6,0,1,0,1),(7,0,1,1,0),(8,0,1,1,1),(9,1,0,0,0),(10,1,0,0,1),(11,1,0,1,0),(12,1,0,1,1),(13,1,1,0,0),(14,1,1,0,1),(15,1,1,1,0),(16,1,1,1,1);
/*!40000 ALTER TABLE `permission_action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permissions` (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(45) NOT NULL,
  `permission_desc` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
INSERT INTO `permissions` VALUES (1,'RESOURCE_ACCESS','Access'),(2,'RESOURCE_CREATE','Create'),(3,'RESOURCE_EDIT','Edit'),(4,'RESOURCE_DELETE','Delete'),(5,'BILLABLE_ACCESS','Access'),(6,'BILLABLE_CREATE','Create'),(7,'BILLABLE_EDIT','Edit'),(8,'BILLABLE_DELETE','Delete'),(9,'CSS_ACCESS','Access'),(10,'CSS_CREATE','Create'),(11,'CSS_EDIT','Edit'),(12,'CSS_DELETE','Delete'),(13,'ADMIN','Admin');
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_billable`
--

DROP TABLE IF EXISTS `project_billable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_billable` (
  `project_billable_id` int(11) NOT NULL AUTO_INCREMENT,
  `billable_month` text NOT NULL,
  `billable_value` float NOT NULL,
  `issue_code` varchar(45) DEFAULT NULL,
  `project_id` int(11) NOT NULL,
  `project_name` varchar(100) NOT NULL,
  `pm_name` varchar(255) DEFAULT NULL,
  `delivery_unit` varchar(45) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  PRIMARY KEY (`project_billable_id`)
) ENGINE=InnoDB AUTO_INCREMENT=196 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_billable`
--

LOCK TABLES `project_billable` WRITE;
/*!40000 ALTER TABLE `project_billable` DISABLE KEYS */;
INSERT INTO `project_billable` VALUES (1,'04-2017',2,'GLB171043',107,'DU1_InternalProjects','Vu Duc Thuan, Bui Manh Hung','OSD1','2017-04-01','2018-03-31',NULL,'2017-12-26 14:16:42'),(2,'05-2017',3,'GLB171041',107,'DU1_InternalProjects','Vu Duc Thuan, Bui Manh Hung','OSD1','2017-04-01','2018-03-31',NULL,'2017-12-26 14:16:42'),(3,'06-2017',4,'GLB179394',107,'DU1_InternalProjects','Vu Duc Thuan, Bui Manh Hung','OSD2','2017-04-01','2018-03-31',NULL,NULL),(4,'07-2017',3,'GLB121434',107,'DU1_InternalProjects','Vu Duc Thuan, Bui Manh Hung','OSD2','2017-04-01','2018-03-31',NULL,NULL),(5,'12-2017',5,'GLB232332',107,'DU1_InternalProjects','Vu Duc Thuan, Bui Manh Hung','OSD1','2017-04-01','2018-03-31',NULL,NULL),(153,'09-2017',0,NULL,107,'DU1_InternalProjects','Vu Duc Thuan, Bui Manh Hung','OSD2','2017-04-01','2018-03-31',NULL,NULL),(154,'10-2017',0,NULL,107,'DU1_InternalProjects','Vu Duc Thuan, Bui Manh Hung','OSD2','2017-04-01','2018-03-31',NULL,NULL),(155,'11-2017',0,NULL,107,'DU1_InternalProjects','Vu Duc Thuan, Bui Manh Hung','OSD2','2017-04-01','2018-03-31',NULL,NULL),(156,'12-2017',10,NULL,107,'DU1_InternalProjects','Vu Duc Thuan, Bui Manh Hung','OSD2','2017-04-01','2018-03-31',NULL,NULL),(157,'01-2018',0,NULL,107,'DU1_InternalProjects','Vu Duc Thuan, Bui Manh Hung','OSD2','2017-04-01','2018-03-31',NULL,NULL),(158,'02-2018',0,NULL,107,'DU1_InternalProjects','Vu Duc Thuan, Bui Manh Hung','OSD2','2017-04-01','2018-03-31',NULL,NULL),(159,'03-2018',0,NULL,107,'DU1_InternalProjects','Vu Duc Thuan, Bui Manh Hung','OSD2','2017-04-01','2018-03-31',NULL,NULL),(160,'04-2017',0,NULL,110,'DU1_Jupiter_042017_ODC','Hoang Minh Duc','OSD2','2017-04-03','2017-09-30',NULL,NULL),(161,'05-2017',0,NULL,110,'DU1_Jupiter_042017_ODC','Hoang Minh Duc','OSD2','2017-04-03','2017-09-30',NULL,NULL),(162,'06-2017',0,NULL,110,'DU1_Jupiter_042017_ODC','Hoang Minh Duc','OSD2','2017-04-03','2017-09-30',NULL,NULL),(163,'07-2017',0,NULL,110,'DU1_Jupiter_042017_ODC','Hoang Minh Duc','OSD2','2017-04-03','2017-09-30',NULL,NULL),(164,'08-2017',0,NULL,110,'DU1_Jupiter_042017_ODC','Hoang Minh Duc','OSD2','2017-04-03','2017-09-30',NULL,NULL),(166,'09-2017',0,NULL,110,'DU1_Jupiter_042017_ODC','Hoang Minh Duc','OSD2','2017-04-03','2017-09-30',NULL,NULL),(167,'04-2017',0,NULL,11,'DU2_Internal','','OSD2','2017-04-01','2018-03-31',NULL,NULL),(168,'05-2017',0,NULL,11,'DU2_Internal','','OSD2','2017-04-01','2018-03-31',NULL,NULL),(169,'06-2017',0,NULL,11,'DU2_Internal','','OSD2','2017-04-01','2018-03-31',NULL,NULL),(170,'07-2017',0,NULL,11,'DU2_Internal','','OSD2','2017-04-01','2018-03-31',NULL,NULL),(171,'08-2017',0,NULL,11,'DU2_Internal','','OSD2','2017-04-01','2018-03-31',NULL,NULL),(172,'09-2017',0,NULL,11,'DU2_Internal','','OSD2','2017-04-01','2018-03-31',NULL,NULL),(173,'10-2017',0,NULL,11,'DU2_Internal','','OSD2','2017-04-01','2018-03-31',NULL,NULL),(174,'11-2017',0,NULL,11,'DU2_Internal','','OSD2','2017-04-01','2018-03-31',NULL,NULL),(175,'12-2017',0,NULL,11,'DU2_Internal','','OSD2','2017-04-01','2018-03-31',NULL,NULL),(176,'01-2018',0,NULL,11,'DU2_Internal','','OSD2','2017-04-01','2018-03-31',NULL,NULL),(177,'02-2018',0,NULL,11,'DU2_Internal','','OSD2','2017-04-01','2018-03-31',NULL,NULL),(178,'03-2018',0,NULL,11,'DU2_Internal','','OSD2','2017-04-01','2018-03-31',NULL,NULL),(179,'11-2016',0,NULL,4,'Yellow Page - Solr Configuration System','Nguyen Duong Hong Giang, Nguyen An Hung, Dao Tuan Anh, Do Trong Luc','','2016-11-01','2018-03-31',NULL,NULL),(180,'12-2016',0,NULL,4,'Yellow Page - Solr Configuration System','Nguyen Duong Hong Giang, Nguyen An Hung, Dao Tuan Anh, Do Trong Luc','','2016-11-01','2018-03-31',NULL,NULL),(181,'01-2017',0,NULL,4,'Yellow Page - Solr Configuration System','Nguyen Duong Hong Giang, Nguyen An Hung, Dao Tuan Anh, Do Trong Luc','','2016-11-01','2018-03-31',NULL,NULL),(182,'02-2017',0,NULL,4,'Yellow Page - Solr Configuration System','Nguyen Duong Hong Giang, Nguyen An Hung, Dao Tuan Anh, Do Trong Luc','','2016-11-01','2018-03-31',NULL,NULL),(183,'03-2017',0,NULL,4,'Yellow Page - Solr Configuration System','Nguyen Duong Hong Giang, Nguyen An Hung, Dao Tuan Anh, Do Trong Luc','','2016-11-01','2018-03-31',NULL,NULL),(184,'04-2017',0,NULL,4,'Yellow Page - Solr Configuration System','Nguyen Duong Hong Giang, Nguyen An Hung, Dao Tuan Anh, Do Trong Luc','','2016-11-01','2018-03-31',NULL,NULL),(185,'05-2017',0,NULL,4,'Yellow Page - Solr Configuration System','Nguyen Duong Hong Giang, Nguyen An Hung, Dao Tuan Anh, Do Trong Luc','','2016-11-01','2018-03-31',NULL,NULL),(186,'06-2017',0,NULL,4,'Yellow Page - Solr Configuration System','Nguyen Duong Hong Giang, Nguyen An Hung, Dao Tuan Anh, Do Trong Luc','','2016-11-01','2018-03-31',NULL,NULL),(187,'07-2017',0,NULL,4,'Yellow Page - Solr Configuration System','Nguyen Duong Hong Giang, Nguyen An Hung, Dao Tuan Anh, Do Trong Luc','','2016-11-01','2018-03-31',NULL,NULL),(188,'08-2017',0,NULL,4,'Yellow Page - Solr Configuration System','Nguyen Duong Hong Giang, Nguyen An Hung, Dao Tuan Anh, Do Trong Luc','','2016-11-01','2018-03-31',NULL,NULL),(189,'09-2017',0,NULL,4,'Yellow Page - Solr Configuration System','Nguyen Duong Hong Giang, Nguyen An Hung, Dao Tuan Anh, Do Trong Luc','','2016-11-01','2018-03-31',NULL,NULL),(190,'10-2017',0,NULL,4,'Yellow Page - Solr Configuration System','Nguyen Duong Hong Giang, Nguyen An Hung, Dao Tuan Anh, Do Trong Luc','','2016-11-01','2018-03-31',NULL,NULL),(191,'11-2017',0,NULL,4,'Yellow Page - Solr Configuration System','Nguyen Duong Hong Giang, Nguyen An Hung, Dao Tuan Anh, Do Trong Luc','','2016-11-01','2018-03-31',NULL,NULL),(192,'12-2017',0,NULL,4,'Yellow Page - Solr Configuration System','Nguyen Duong Hong Giang, Nguyen An Hung, Dao Tuan Anh, Do Trong Luc','','2016-11-01','2018-03-31',NULL,NULL),(193,'01-2018',0,NULL,4,'Yellow Page - Solr Configuration System','Nguyen Duong Hong Giang, Nguyen An Hung, Dao Tuan Anh, Do Trong Luc','','2016-11-01','2018-03-31',NULL,NULL),(194,'02-2018',0,NULL,4,'Yellow Page - Solr Configuration System','Nguyen Duong Hong Giang, Nguyen An Hung, Dao Tuan Anh, Do Trong Luc','','2016-11-01','2018-03-31',NULL,NULL),(195,'03-2018',0,NULL,4,'Yellow Page - Solr Configuration System','Nguyen Duong Hong Giang, Nguyen An Hung, Dao Tuan Anh, Do Trong Luc','','2016-11-01','2018-03-31',NULL,NULL);
/*!40000 ALTER TABLE `project_billable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_css`
--

DROP TABLE IF EXISTS `project_css`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_css` (
  `project_css_id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL,
  `project_name` varchar(100) DEFAULT NULL,
  `score_value` float NOT NULL,
  `delivery_unit` varchar(45) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `time` int(11) NOT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  PRIMARY KEY (`project_css_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_css`
--

LOCK TABLES `project_css` WRITE;
/*!40000 ALTER TABLE `project_css` DISABLE KEYS */;
INSERT INTO `project_css` VALUES (1,4,'Project Name 2',89,'OSD2','2017-10-19 00:00:00','2018-11-11 00:00:00',1,NULL,NULL),(2,4,'Project Name 2',92,'OSD2','2017-11-11 00:00:00','2018-01-10 00:00:00',2,NULL,NULL),(3,109,NULL,95,'OSD1','2017-05-03 00:00:00','2017-06-06 00:00:00',1,'2017-12-12 15:25:13','2017-12-12 15:25:13'),(4,109,NULL,97,'OSD1','2017-06-06 00:00:00','2017-07-26 00:00:00',2,'2017-12-12 15:25:20','2017-12-12 15:25:20'),(5,163,NULL,85,'OSD3','2017-09-01 00:00:00','2017-09-10 00:00:00',1,'2017-12-12 15:28:03','2017-12-12 15:28:03'),(6,163,NULL,89,'OSD3','2017-09-10 00:00:00','2017-09-20 00:00:00',2,'2017-12-12 15:31:30','2017-12-12 15:31:30'),(7,163,NULL,87,'OSD3','2017-09-20 00:00:00','2017-09-30 00:00:00',3,'2017-12-12 15:31:30','2017-12-12 15:31:30'),(8,119,NULL,90,'OSD2','2017-10-08 00:00:00','2017-12-30 00:00:00',1,'2017-12-18 15:50:20','2017-12-12 15:31:30'),(9,121,NULL,99,'OSD2','2017-06-19 00:00:00','2017-08-25 00:00:00',1,'2017-12-12 15:31:30','2017-12-12 15:31:30'),(10,156,NULL,98,'Training','2017-09-01 00:00:00','2017-10-01 00:00:00',1,'2017-12-12 15:31:30','2017-12-12 15:31:30'),(11,156,NULL,97,'Training','2017-10-01 00:00:00','2017-10-31 00:00:00',2,'2017-12-12 15:31:30','2017-12-12 15:31:30'),(12,4,'Yellow Page - Solr Configuration System',90,'','2018-01-11 00:00:00','2018-02-12 00:00:00',3,NULL,NULL);
/*!40000 ALTER TABLE `project_css` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) NOT NULL,
  `permissionName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'DUL',NULL),(2,'PM',NULL),(3,'QA',NULL),(4,'BoD',NULL),(5,'ADMIN',NULL);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  `enable` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_role_idx` (`role_id`),
  KEY `fk_permission_idx` (`permission_id`),
  CONSTRAINT `FK2xn8qv4vw30i04xdxrpvn3bdi` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`permission_id`),
  CONSTRAINT `FKonkb3lbd5mpvfcobxnklep1bo` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `fk_permission` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`permission_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission` VALUES (1,1,1,1),(2,1,2,1),(3,1,3,1),(4,1,4,1),(5,1,5,1),(6,1,6,0),(7,1,7,0),(8,1,8,0),(9,1,9,1),(10,1,10,0),(11,1,11,0),(12,1,12,0),(13,2,1,1),(14,2,2,1),(15,2,3,1),(16,2,4,1),(17,2,5,1),(18,2,6,1),(19,2,7,1),(20,2,8,1),(21,2,9,1),(22,2,10,1),(23,2,11,1),(24,2,12,1),(25,3,1,1),(26,3,2,1),(27,3,3,1),(28,3,4,1),(29,3,5,1),(30,3,6,0),(31,3,7,0),(32,3,8,0),(33,3,9,1),(34,3,10,1),(35,3,11,1),(36,3,12,1),(37,4,1,1),(38,4,2,1),(39,4,3,1),(40,4,4,1),(41,4,5,1),(42,4,6,0),(43,4,7,0),(44,4,8,0),(45,4,9,1),(46,4,10,0),(47,4,11,0),(48,4,12,0),(49,5,13,1);
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_plan`
--

DROP TABLE IF EXISTS `user_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_plan` (
  `user_plan_id` int(11) NOT NULL AUTO_INCREMENT,
  `created_on` datetime DEFAULT NULL,
  `effort_per_day` float DEFAULT NULL,
  `from_date` datetime NOT NULL,
  `man_day` float DEFAULT NULL,
  `man_month` float DEFAULT NULL,
  `project_id` int(11) NOT NULL,
  `to_date` datetime NOT NULL,
  `updated_on` datetime DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `is_overloaded` bit(1) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_plan_id`),
  KEY `FK1agvnctnas4hcjfxcw5ggvi4a` (`user_id`),
  CONSTRAINT `FK1agvnctnas4hcjfxcw5ggvi4a` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_plan`
--

LOCK TABLES `user_plan` WRITE;
/*!40000 ALTER TABLE `user_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_plan_detail`
--

DROP TABLE IF EXISTS `user_plan_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_plan_detail` (
  `id` int(11) NOT NULL,
  `plan_month` varchar(45) NOT NULL,
  `man_day` float NOT NULL,
  `delivery_unit` varchar(45) DEFAULT NULL,
  `user_plan_id` int(11) NOT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Fk_userPlan_idx` (`user_plan_id`),
  CONSTRAINT `FK99vr5o7m295ombttmbufjg81` FOREIGN KEY (`user_plan_id`) REFERENCES `user_plan` (`user_plan_id`),
  CONSTRAINT `Fk_userPlan` FOREIGN KEY (`user_plan_id`) REFERENCES `user_plan` (`user_plan_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_plan_detail`
--

LOCK TABLES `user_plan_detail` WRITE;
/*!40000 ALTER TABLE `user_plan_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_plan_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `created_on` datetime DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `hashed_password` varchar(255) NOT NULL,
  `is_admin` bit(1) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `status` bit(1) NOT NULL,
  `updated_on` datetime DEFAULT NULL,
  `user_name` varchar(100) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK_k8d0f2n7n88w1a16yhua64onx` (`user_name`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UKk8d0f2n7n88w1a16yhua64onx` (`user_name`),
  KEY `FKemfuglprp85bh5xwhfm898ysc` (`group_id`),
  CONSTRAINT `FKemfuglprp85bh5xwhfm898ysc` FOREIGN KEY (`group_id`) REFERENCES `groups` (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=345 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (3,'2017-12-11 08:44:24','dvngoc@cmc.com.vn','Doan Van Ngoc','sda;lkfjasdfupakhksdjf','','sdaklfhjasdfknsad,fni','','2017-12-11 09:06:28','dvngoc',335),(4,'2017-12-11 08:44:44','ldkien@cmc.com.vn','Le Doan Kien','sda;lkfjasdfupakhksdjf','\0','sdaklfhjasdfknsad,fni','\0','2017-12-11 09:06:38','ldkien',336),(5,'2017-12-11 08:44:47','nman@cmc.com.vn','Nguyen Minh An','sda;lkfjasdfupakhksdjf','','sdaklfhjasdfknsad,fni','','2017-12-11 09:06:42','nman',338),(6,'2017-12-11 08:44:50','thnam@cmc.com.vn','Trinh Hoai Nam','sda;lkfjasdfupakhksdjf','\0','sdaklfhjasdfknsad,fni','\0','2017-12-11 09:06:47','thnam',339),(8,'2017-12-11 09:05:44','nttrung2@cmc.com.vn','Nguyen The Trung','fadysfsdfdskbaiufhasdjklhf','','asfdiufhsjkdfhlksjhlhdsfa','','2017-12-11 09:05:44','nttrung2',341),(9,'2017-12-11 09:05:45','vtson@cmc.com.vn','Vu Tung Son','sdfgsd348eiwurtiuyjk','','oiu34859weriuywtriu','','2017-12-11 09:05:45','vtson',342),(245,'2017-12-11 09:05:45','nxcanh','Ngo Xuan Canh','e72f36523066625b58db34e03fc1ff90137b26ec','\0','29df9b2c79737a49a3f0b4960410175a','','2017-12-11 09:05:45','nxcanh',337),(265,'2017-12-11 09:05:45','dnbao','Dang Ngoc Baoo','e72f36523066625b58db34e03fc1ff90137b26ec','\0','29df9b2c79737a49a3f0b4960410175a','','2017-12-11 09:05:45','dnbao',342),(294,'2017-12-11 08:44:54','nvkhoa@cmc.com.vn','Nguyen Van Khoa','sda;lkfjasdfupakhksdjf','','sdaklfhjasdfknsad,fni','','2017-12-11 09:06:49','nvkhoa',336),(344,'2017-12-11 08:45:54','lvlong@cmc.com.vn','Le Van Long','sda;lkfjasdfupakhksdjf11','','sdaklfhjasdfknsad,fni111','','2017-12-11 09:06:49','lvlong',336);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'dashboard'
--

--
-- Dumping routines for database 'dashboard'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-11 11:13:04
