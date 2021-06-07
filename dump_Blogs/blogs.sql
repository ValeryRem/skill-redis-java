-- MySQL dump 10.13  Distrib 5.7.24, for Win64 (x86_64)
--
-- Host: localhost    Database: blogs
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
  `timestamp` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `captcha_codes`
--

LOCK TABLES `captcha_codes` WRITE;
/*!40000 ALTER TABLE `captcha_codes` DISABLE KEYS */;
INSERT INTO `captcha_codes` VALUES (153,'depunibano','yozuzozima','2021-05-02 17:31:25.827175');
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
INSERT INTO `global_settings` VALUES (111,_binary '',_binary '',_binary '\0');
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
INSERT INTO `hibernate_sequence` VALUES (154),(154),(154);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_comments`
--

DROP TABLE IF EXISTS `post_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_comments` (
  `comment_id` int(11) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `post_id` int(11) NOT NULL,
  `text` text NOT NULL,
  `time` datetime NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_comments`
--

LOCK TABLES `post_comments` WRITE;
/*!40000 ALTER TABLE `post_comments` DISABLE KEYS */;
INSERT INTO `post_comments` VALUES (114,NULL,86,'Levitation takes matter anyway!','2021-04-08 20:47:07',1),(115,NULL,74,'Unbelivable is good unless truth.','2021-04-08 20:49:20',1),(116,NULL,76,'Tediouse and ambitiouse project!','2021-04-08 20:56:57',1),(117,114,86,'<strong>Tura</strong>, be calm and prudent every time.','2021-04-08 20:58:17',1),(119,NULL,89,'Pay less attention to your faults and would be eager for relax.','2021-04-08 21:08:52',33),(120,115,74,'<strong>Rem</strong>, you are right but too cleverminding, it is furiouse.','2021-04-08 21:10:14',33),(121,NULL,77,'Dramatic dynamics!','2021-04-10 14:31:22',32),(122,NULL,90,'Bon appetit to you!','2021-04-20 12:12:31',21),(123,121,77,'<strong>Diogen</strong>, pay attention to your backyard!','2021-04-20 12:13:43',21),(124,NULL,5,'Goods are delivered to hub.&nbsp;','2021-04-23 09:00:46',33),(125,NULL,90,'Why the date is invalid?&nbsp;','2021-04-23 09:03:27',32),(126,117,86,'<strong>Tura</strong>, be sure it is not important.','2021-04-23 09:23:17',32),(127,NULL,91,'Do not worry. It will not succeed.','2021-04-23 11:08:45',1),(128,124,5,'<strong>Lopatin</strong>, you are terribly right!','2021-04-23 11:13:44',30),(129,127,91,'<strong>Robby</strong>, get rid of it!','2021-04-23 13:06:11',2),(130,116,76,'<b>Liberation is prospective yet idle!</b>','2021-04-23 15:15:34',21),(131,NULL,86,'Debug code if it is suspisious!&nbsp;','2021-04-23 18:41:10',32),(132,117,86,'<strong>Tura</strong>, you are so smart in this aspect!','2021-04-23 18:43:23',32),(133,NULL,78,'Growth is limited! Be careful!','2021-04-25 09:58:48',1),(134,119,89,'Go quicker without stops!','2021-04-25 10:00:23',1),(135,NULL,82,'Buy new version of this soft!','2021-04-25 14:35:34',30),(136,NULL,79,'Go home, be happy!&nbsp;','2021-04-25 14:40:29',30),(138,NULL,92,'To be more precies!','2021-04-30 19:12:17',1),(139,NULL,91,'Never do it so quickly!','2021-04-30 21:47:57',34),(140,129,91,'<strong>Robby</strong>, your idea looks good!','2021-04-30 21:48:41',34),(141,NULL,93,'Drums really are better than violins!','2021-05-02 06:40:33',21);
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
  `post_id` int(11) DEFAULT NULL,
  `time` datetime(6) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `value` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_votes`
--

LOCK TABLES `post_votes` WRITE;
/*!40000 ALTER TABLE `post_votes` DISABLE KEYS */;
INSERT INTO `post_votes` VALUES (4,65,'2021-03-22 20:25:51.135838',21,1),(11,6,'2021-03-23 07:59:22.931102',2,1),(12,8,'2021-03-23 08:00:59.516979',2,1),(14,68,'2021-03-23 09:03:01.727287',30,1),(19,74,'2021-03-24 14:23:29.386868',30,1),(20,76,'2021-03-25 20:59:02.712460',30,1),(21,79,'2021-03-30 12:27:43.783791',1,1),(22,78,'2021-03-30 12:27:46.958935',1,-1),(23,7,'2021-03-30 21:30:22.624674',30,1),(24,80,'2021-03-31 16:22:12.796707',32,1),(25,79,'2021-03-31 16:22:17.232607',32,-1),(26,77,'2021-03-31 16:22:31.399080',32,1),(27,81,'2021-04-01 13:12:22.015778',21,1),(28,77,'2021-04-01 13:12:27.758443',21,-1),(29,75,'2021-04-01 13:12:42.517874',21,1),(30,5,'2021-04-01 13:14:37.589593',21,1),(31,1,'2021-04-01 13:14:39.041579',21,-1),(32,82,'2021-04-02 12:35:20.006803',32,1),(33,79,'2021-04-07 12:39:13.423325',1,1),(34,76,'2021-04-07 12:39:18.957643',1,-1),(35,84,'2021-04-07 20:10:36.878160',1,1),(36,3,'2021-04-07 20:10:51.565217',1,1),(37,1,'2021-04-07 20:40:54.423182',2,1),(38,1,'2021-04-07 20:48:02.029532',2,-1),(39,2,'2021-04-07 20:54:39.867753',2,1),(40,85,'2021-04-07 21:00:17.562775',2,1),(41,86,'2021-04-07 21:17:46.833941',2,-1),(42,87,'2021-04-08 01:01:03.797182',2,1),(43,88,'2021-04-08 01:06:10.325957',2,1),(44,4,'2021-04-08 01:06:29.440451',2,1),(45,86,'2021-04-08 20:48:23.115103',1,1),(46,77,'2021-04-20 12:13:06.288568',21,1),(47,91,'2021-04-23 13:05:48.851445',2,1),(48,86,'2021-04-23 18:44:00.388143',32,1),(49,79,'2021-04-25 14:40:43.807060',30,1),(50,90,'2021-04-30 19:08:18.557145',34,1),(51,92,'2021-04-30 19:11:53.553727',1,1),(52,78,'2021-04-30 19:13:22.162152',1,1),(53,93,'2021-05-02 06:39:31.071944',21,1),(54,94,'2021-05-02 10:49:29.009916',21,-1);
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
  `is_active` int(11) NOT NULL,
  `moderation_status` varchar(255) NOT NULL,
  `moderator_id` int(11) NOT NULL,
  `text` mediumtext NOT NULL,
  `timestamp` datetime NOT NULL,
  `title` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  `view_count` int(11) NOT NULL,
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,1,'ACCEPTED',1,'Try to escape from here, and as soon as possible, my darling!','2021-04-07 20:01:43','Optional description out of bounds.',1,24),(2,1,'ACCEPTED',2,'This is a new testing text to be processed by the code under #Spring, #PHP, #Python tags but Rubi.','2021-04-08 01:02:22','The testing post #555',2,29),(3,1,'ACCEPTED',1,'This is a new testing text to be processed by the code underCuller tag.','2021-04-07 19:52:30','The testing post #4',1,159),(4,1,'ACCEPTED',1,'This is a new testing text to be processed by the code under #Spring, #PHP, #Python tags','2020-12-22 21:02:16','The testing post',1,124),(5,1,'ACCEPTED',2,'New testing text to be processed by the code under #Spring, #PHP, #Python tags plus #River.','2021-04-08 20:50:58','Ferrit testing post #22',1,30),(74,1,'ACCEPTED',1,'Minimal creativity is the must.','2021-04-08 12:30:27','Creativity is exit.',32,13),(75,1,'ACCEPTED',1,'Chellenge&nbsp;Chellenge&nbsp;Chellenge&nbsp;Chellenge&nbsp;Chellenge&nbsp;Chellenge&nbsp;Chellenge&nbsp;Chellenge','2021-03-24 14:26:58','Chellenge',30,4),(76,1,'ACCEPTED',1,'Misfunction is critical!&nbsp;','2021-04-08 12:31:04','Bifocal tool. ',32,17),(77,1,'ACCEPTED',1,'Deadline is nearby!&nbsp;Deadline is nearby!&nbsp;Deadline is nearby!&nbsp;Deadline is nearby!&nbsp;Deadline is nearby!&nbsp;Deadline is nearby!&nbsp;Deadline is nearby!&nbsp;Deadline is nearby!&nbsp;','2021-03-28 19:44:13','Deadline is nearby! ',30,10),(78,1,'ACCEPTED',1,'Deliberation is harmful and costy as known everywhere.','2021-04-08 12:26:38','Deliberation',32,9),(79,1,'ACCEPTED',1,'Lobotomy&nbsp;is hurty but useful.','2021-04-08 11:36:12','Talant is seldom so valuable!',21,16),(80,1,'ACCEPTED',1,'Duratuion of process&nbsp;Duratuion of process&nbsp;Duratuion of process&nbsp;Duratuion of process','2021-03-30 21:35:14','Duratuion of process',30,0),(81,1,'ACCEPTED',1,'Tolerance of mistakes is 0. Tolerance of mistakes is 0. Tolerance of mistakes is 0. Tolerance of mistakes is 0. Tolerance of mistakes is 0.&nbsp;','2021-03-31 12:13:16','Zero-level',32,6),(82,1,'ACCEPTED',1,'Territory zero may be empty but still valid.','2021-04-08 11:51:05','Nobody is guilty!',21,7),(83,1,'ACCEPTED',1,'The sooner the better! The sooner the better! The sooner the better! The sooner the better! The sooner the better!&nbsp;','2021-04-07 16:19:05','Culture shock!',21,2),(84,1,'ACCEPTED',1,'Terrible history indead. Terrible history indead. Terrible history indead.&nbsp;','2021-04-07 19:54:58','Pellican file.',1,2),(85,1,'ACCEPTED',1,'Nobody trusts why. Nobody trusts why. Nobody trusts why. Nobody trusts why.&nbsp;','2021-04-07 21:00:00','Dramatic settlement!',2,1),(86,1,'ACCEPTED',1,'X-Rays are dangerouse! Nevertheless they are popular.','2021-04-08 12:49:22','Challenge #21',2,10),(87,1,'ACCEPTED',1,'This app is new and sophisticated.','2021-04-07 23:31:45','Pinoccio will come back!',30,6),(88,1,'ACCEPTED',1,'Territory is vast and wild. Territory is vast and wild. Territory is vast and wild.&nbsp;','2021-04-07 23:30:23','Columbus',30,3),(89,1,'ACCEPTED',1,'Collapse of a system is just new challenge for it.','2021-04-08 01:09:37','Collapse!',2,6),(90,1,'ACCEPTED',1,'Liberlization of&nbsp;Borsch cooking take time and power.','2021-04-08 21:05:46','Borsch is cute!',33,5),(91,1,'ACCEPTED',1,'Kotlin tries to catch the coding field! Kotlin gets to be one of the best!','2021-04-23 11:07:01','Kotlin invades!',21,5),(92,1,'ACCEPTED',1,'Nnnnnnnnnnnnnn Nnnnnnnnnnnnnnnn Nnnnnnnnnnnnnnnnnnnn','2021-04-30 19:09:41','Collins Jr',34,1),(93,1,'ACCEPTED',1,'Do not trust this sentence! Do not trust this sentence! Do not trust this sentence!&nbsp;','2021-04-30 21:46:15','Reebok on rout!',34,1),(94,1,'ACCEPTED',1,'Nes from the forests: dodos to be involved in fighting!','2021-05-02 10:36:44','Dodo forever!',35,0),(95,1,'ACCEPTED',1,'Snow goes strictly to us from North.','2021-05-02 10:51:23','Snow comes.',30,0),(96,1,'NEW',1,'Do not look on this topic without sense.&nbsp;','2021-05-02 11:56:05','Belt of horror!',35,0),(97,1,'ACCEPTED',1,'Nobody but you could do it tomorrow!','2021-05-02 12:03:36','Linging to steps.',30,0),(98,1,'NEW',1,'Coming summer promises to be hot.','2021-05-02 14:30:14','Winner comes soon!',37,0),(99,1,'ACCEPTED',1,'<span style=\"font-weight: bold;\">Territory</span><span style=\"font-style: italic;\"> is empty!</span>&nbsp;<span style=\"text-decoration-line: line-through;\">Territory</span> is empty!&nbsp;Territory is empty!','2021-05-02 19:49:36','Territory is empty!',2,0),(100,1,'ACCEPTED',1,'Trouble is boring but useful. Do not worry!','2021-05-02 20:19:50','Retired cop.',2,0),(101,1,'ACCEPTED',1,'Lombok is good. Lombok is good. Lombok is good.','2021-05-02 20:35:56','Tribune',39,0);
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
  `timestamp` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`session_id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session`
--

LOCK TABLES `session` WRITE;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;
INSERT INTO `session` VALUES (42,'08FD933B54FBC81ACB3F8BE26D2BBAAC','2021-05-02 20:33:58',39);
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
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag_2_post`
--

LOCK TABLES `tag_2_post` WRITE;
/*!40000 ALTER TABLE `tag_2_post` DISABLE KEYS */;
INSERT INTO `tag_2_post` VALUES (14,2,2),(15,2,3),(16,2,6),(17,4,2),(18,4,3),(19,4,6),(20,5,2),(21,5,3),(22,5,6),(24,88,11),(25,87,11),(26,87,2),(27,2,3),(28,89,16),(29,5,16),(30,79,17),(31,82,18),(32,82,19),(33,78,20),(34,74,21),(35,76,22),(36,86,23),(37,5,24),(38,90,25),(39,91,26),(40,93,27),(41,94,28),(42,95,29),(43,99,30),(44,101,31);
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
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` VALUES (1,'Java'),(2,'PHP'),(3,'Python'),(4,'Excel'),(5,'Darby'),(6,'Spring'),(10,'Pinoccio'),(11,'Columbus'),(16,'River'),(17,'Talant '),(18,'Territory'),(19,'Nobody'),(20,'Deliberation '),(21,'Creativity '),(22,'Bifocal '),(23,'X-Rays'),(24,'Ferrit '),(25,'Borsch '),(26,'Kotlin '),(27,'Reebok '),(28,'Dodo'),(29,'Snow'),(30,'Territory '),(31,'Lombok ');
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
  `e_mail` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `is_moderator` int(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `reg_time` datetime NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `e_mail_UNIQUE` (`e_mail`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Nk0uuWP8H3PAEPg1','list@pol.pl',1,'Lopatin','pw1001','/upload/226/43/78/82_Future.jpg','2020-12-22 20:58:29'),(2,'tyurem','ufro@ggg.ty',1,'Tura','pw1213','/upload/226/43/78/59_Tango pa.jpg','2020-12-22 20:58:47'),(21,'ekinakici','robby@ht.kk',1,'Robby','pw321321','/upload/226/43/78/22_903.jpg','2021-01-28 06:49:42'),(30,NULL,'rex@tr.op',0,'Diogen','pw8989','/upload/226/43/78/67_House.jpg','2021-03-13 14:04:33'),(32,'Mdabez7DQnn6KQB9','remenyuk79@gmail.com',1,'Rem','pw4444','/upload/226/43/78/67_2011.jpg','2021-03-17 09:41:39'),(33,'UgAztbKp5Lxfzckh','tyrod@rrr.bb',0,'Lomova','pw5566','/upload/226/43/78/28_BB.jpg','2021-04-08 21:02:24'),(34,'YaQxsn5YzOSSP9Pz','norton@nor.tg',1,'Norton','pw4747','/upload/226/43/78/97_20210323_164018.jpg','2021-04-30 19:05:13'),(35,'pMTR6dfJLMeCXzUr','dodo@do.do',0,'Dodo','pw7676','/upload/226/43/78/83_Useful.jpg','2021-05-02 10:33:43'),(36,'o4X954u2ppyksNRs','korton@nor.tg',0,'Reutov','pw9089','/upload/226/43/78/74_Eve.jpg','2021-05-02 13:19:46'),(37,'2CewOJeLZjRMqFvg','mufon@ggg.ty',0,'Grumm','pw1111','/upload/226/43/78/35_Winner.jpg','2021-05-02 14:28:16'),(38,'BF55imPpWt4qVrIA','fritur@wert.nm',0,'Morion','pw5533',NULL,'2021-05-02 15:28:25'),(39,'Wpb4vnyo1sTI3IpI','tred@kl.ok',0,'Limonov','pw2121','/upload/226/43/78/16_VVM.jpg','2021-05-02 15:59:43');
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

-- Dump completed on 2021-05-19 13:31:03
