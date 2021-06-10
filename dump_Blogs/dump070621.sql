-- MySQL dump 10.13  Distrib 5.7.24, for Win64 (x86_64)
--
-- Host: localhost    Database: BlogsIT
-- ------------------------------------------------------
-- Server version	5.7.24-log

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
-- Table structure for table `captcha_codes`
--

DROP TABLE IF EXISTS `captcha_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `captcha_codes` (
  `id` int(11) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `captcha_secret` varchar(255) DEFAULT NULL,
  `timestamp` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `captcha_codes`
--

LOCK TABLES `captcha_codes` WRITE;
/*!40000 ALTER TABLE `captcha_codes` DISABLE KEYS */;
INSERT INTO `captcha_codes` VALUES (6,'duvaletita','cuhayuxaj','2021-05-28 09:01:49.066870');
/*!40000 ALTER TABLE `captcha_codes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `global_settings`
--

DROP TABLE IF EXISTS `global_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `global_settings` (
  `id` int(11) NOT NULL,
  `multiuser_mode` bit(1) NOT NULL,
  `post_premoderation` bit(1) NOT NULL,
  `statistics_is_public` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `global_settings`
--

LOCK TABLES `global_settings` WRITE;
/*!40000 ALTER TABLE `global_settings` DISABLE KEYS */;
INSERT INTO `global_settings` VALUES (1,_binary '',_binary '',_binary '');
/*!40000 ALTER TABLE `global_settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (17),(17),(17);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_comments`
--

DROP TABLE IF EXISTS `post_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_comments` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `post_id` int(11) NOT NULL,
  `text` varchar(255) NOT NULL,
  `time` datetime NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FKaawaqxjs3br8dw5v90w7uu514` (`post_id`),
  CONSTRAINT `FKaavkjr54c6whhud0t5xrxyehj` FOREIGN KEY (`comment_id`) REFERENCES `posts` (`post_id`),
  CONSTRAINT `FKaawaqxjs3br8dw5v90w7uu514` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_comments`
--

LOCK TABLES `post_comments` WRITE;
/*!40000 ALTER TABLE `post_comments` DISABLE KEYS */;
INSERT INTO `post_comments` VALUES (1,NULL,6,'Good job. Go further!','2021-06-06 21:11:02',3),(2,NULL,1,'Luuuuuuuuuuuuuuuuuuuuu!','2021-06-07 06:42:32',3),(3,NULL,5,'Noooooooooooooooooooooo!','2021-06-07 06:44:15',3),(4,NULL,3,'Ttttttttttttttttttttttttttt!','2021-06-07 07:06:14',2),(5,NULL,6,'Sssssssssssssssssssssss!','2021-06-07 07:20:09',2),(6,NULL,6,'Ddddddddddddddddddd!','2021-06-07 07:36:49',2);
/*!40000 ALTER TABLE `post_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_votes`
--

DROP TABLE IF EXISTS `post_votes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_votes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `post_id` int(11) NOT NULL,
  `time` datetime NOT NULL,
  `user_id` int(11) NOT NULL,
  `value` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9jh5u17tmu1g7xnlxa77ilo3u` (`post_id`),
  CONSTRAINT `FK9jh5u17tmu1g7xnlxa77ilo3u` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`),
  CONSTRAINT `FKhpebcbfrex3uv28qr3xh230c0` FOREIGN KEY (`id`) REFERENCES `posts` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_votes`
--

LOCK TABLES `post_votes` WRITE;
/*!40000 ALTER TABLE `post_votes` DISABLE KEYS */;
INSERT INTO `post_votes` VALUES (1,1,'2021-05-27 13:07:34',2,1),(2,3,'2021-05-27 15:07:34',1,-1),(3,4,'2021-06-05 05:31:08',2,1),(4,1,'2021-06-05 05:31:26',2,-1),(5,3,'2021-06-05 05:44:42',2,1),(6,2,'2021-06-06 10:40:00',1,1);
/*!40000 ALTER TABLE `post_votes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `posts` (
  `post_id` int(11) NOT NULL AUTO_INCREMENT,
  `is_active` tinyint(1) NOT NULL,
  `moderation_status` enum('NEW','ACCEPTED','DECLINED') NOT NULL,
  `moderator_id` int(11) DEFAULT NULL,
  `text` varchar(255) NOT NULL,
  `timestamp` datetime NOT NULL,
  `title` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  `view_count` int(11) NOT NULL,
  `user_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`post_id`),
  KEY `FK3m7wp2fcoamknkfua1sav2li6` (`user_user_id`),
  CONSTRAINT `FK3m7wp2fcoamknkfua1sav2li6` FOREIGN KEY (`user_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,1,'ACCEPTED',1,'Get started with Spring 5 and Spring Boot 2, through the reference Learn Spring course:','2021-05-27 13:12:02','Spring Boot',1,4,NULL),(2,1,'ACCEPTED',1,'Profiles could be shorter however not now!','2021-05-27 13:21:23','Profiles in Spring',2,7,NULL),(3,1,'ACCEPTED',2,'Soft delivery to be strictly on time!','2021-05-27 18:22:00','Soft delivery',1,4,NULL),(4,1,'ACCEPTED',2,'PHP progress is impossible without generic reduce. ))','2021-05-28 09:04:11','PHP progress',3,7,NULL),(5,1,'ACCEPTED',3,'Cubic elements in construction loose their popularity.','2021-05-31 12:27:16','Cube',1,6,NULL),(6,1,'ACCEPTED',3,'Deserialization is a problem while you do not understand the core.','2021-06-04 08:33:00','Deserialization ',3,26,NULL);
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session`
--

DROP TABLE IF EXISTS `session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `session` (
  `session_id` int(11) NOT NULL AUTO_INCREMENT,
  `session_name` varchar(255) DEFAULT NULL,
  `timestamp` datetime(6) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`session_id`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session`
--

LOCK TABLES `session` WRITE;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;
INSERT INTO `session` VALUES (130,'18964134139EFF8A4CBA4504B1F00FB6','2021-06-07 09:31:21.000000',1);
/*!40000 ALTER TABLE `session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag_2_post`
--

DROP TABLE IF EXISTS `tag_2_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_2_post` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `post_id` int(11) DEFAULT NULL,
  `tag_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag_2_post`
--

LOCK TABLES `tag_2_post` WRITE;
/*!40000 ALTER TABLE `tag_2_post` DISABLE KEYS */;
INSERT INTO `tag_2_post` VALUES (1,1,1),(2,2,2),(3,3,3),(4,4,4);
/*!40000 ALTER TABLE `tag_2_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` VALUES (1,'Spring '),(2,'Profiles '),(3,'Soft '),(4,'PHP');
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `e_mail` varchar(255) NOT NULL,
  `is_moderator` tinyint(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `photo` tinytext,
  `reg_time` datetime NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_ehv2osdo3bfokh566calsfx32` (`e_mail`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'v6eRxlKHx7vPr0zQ','pop@lono.lm',1,'Pop','pw0011','/upload/226/43/78/38_Composer.jpg','2021-05-27 13:07:34'),(2,'30ZOYwe9bfFfszQv','dodik@boo.bo',1,'Dodik','pw0022','/upload/226/43/78/71_221.jpg','2021-05-27 13:15:09'),(3,'2q2HrSKUlMRV9j8j','trik@tutu.op',1,'Ted','pw0033','/upload/226/43/78/82_Darwin.jpg','2021-05-28 09:02:38');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-07 12:55:52
