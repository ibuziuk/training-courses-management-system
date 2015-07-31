-- MySQL dump 10.13  Distrib 5.7.7-rc, for Win64 (x86_64)
--
-- Host: localhost    Database: training_courses_management_system
-- ------------------------------------------------------
-- Server version	5.7.7-rc-log

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
-- Table structure for table `audience`
--

DROP TABLE IF EXISTS `audience`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audience` (
  `audience_id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`audience_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audience`
--

LOCK TABLES `audience` WRITE;
/*!40000 ALTER TABLE `audience` DISABLE KEYS */;
INSERT INTO `audience` VALUES (1,'Java developers'),(2,'PHP developers'),(3,'JavaScript developers'),(4,'C++ developers'),(5,'Scala developers'),(6,'Go developers'),(7,'Testers');
/*!40000 ALTER TABLE `audience` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_training_user`
--

DROP TABLE IF EXISTS `ex_training_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_training_user` (
  `training_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`training_id`,`user_id`),
  KEY `FK_d56bu2mjy33xop1kbaj28isj6` (`user_id`),
  CONSTRAINT `FK_d56bu2mjy33xop1kbaj28isj6` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK_pl325c95q8g1pod301o1t09j7` FOREIGN KEY (`training_id`) REFERENCES `training` (`training_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_training_user`
--

LOCK TABLES `ex_training_user` WRITE;
/*!40000 ALTER TABLE `ex_training_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `ex_training_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `language`
--

DROP TABLE IF EXISTS `language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `language` (
  `language_id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`language_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `language`
--

LOCK TABLES `language` WRITE;
/*!40000 ALTER TABLE `language` DISABLE KEYS */;
INSERT INTO `language` VALUES (1,'English'),(2,'Russian');
/*!40000 ALTER TABLE `language` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regular_lesson`
--

DROP TABLE IF EXISTS `regular_lesson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `regular_lesson` (
  `lesson_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `location` int(11) DEFAULT NULL,
  `time` varchar(20) DEFAULT NULL,
  `training_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`lesson_id`),
  KEY `FK_dh8ktih722nvl9tqnjh89ehek` (`training_id`),
  CONSTRAINT `FK_dh8ktih722nvl9tqnjh89ehek` FOREIGN KEY (`training_id`) REFERENCES `training` (`training_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regular_lesson`
--

LOCK TABLES `regular_lesson` WRITE;
/*!40000 ALTER TABLE `regular_lesson` DISABLE KEYS */;
INSERT INTO `regular_lesson` VALUES (1,'2015-08-04 16:00:00',342,'16:00 - 17:10',4),(2,'2015-08-11 16:00:00',342,'16:00 - 17:10',4),(3,'2015-08-06 16:00:00',234,'16:00 - 17:10',4),(4,'2015-08-13 16:00:00',234,'16:00 - 17:10',4);
/*!40000 ALTER TABLE `regular_lesson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role` varchar(20) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `UK_bjxn5ii7v7ygwx39et0wawu0q` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'admin'),(2,'user');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `color` varchar(255) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (1,'red','Java'),(2,'yellow','PHP'),(3,'brown','JavaScript'),(4,'green','C++'),(5,'pink','Scala'),(6,'blue','Go'),(7,'purple','English');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training`
--

DROP TABLE IF EXISTS `training`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `training` (
  `training_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `days` varchar(20) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `end` date DEFAULT NULL,
  `external_type` bit(1) DEFAULT NULL,
  `is_approved` bit(1) DEFAULT NULL,
  `location` int(11) DEFAULT NULL,
  `max_visitors_count` int(11) DEFAULT NULL,
  `regular` bit(1) DEFAULT NULL,
  `start` date DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `language_id` int(11) DEFAULT NULL,
  `trainer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`training_id`),
  KEY `FK_kpu07jorm6b09r79qs6mbbudd` (`language_id`),
  KEY `FK_qpxtqvn6p3ey7stx3v45m5ujv` (`trainer_id`),
  CONSTRAINT `FK_kpu07jorm6b09r79qs6mbbudd` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`),
  CONSTRAINT `FK_qpxtqvn6p3ey7stx3v45m5ujv` FOREIGN KEY (`trainer_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training`
--

LOCK TABLES `training` WRITE;
/*!40000 ALTER TABLE `training` DISABLE KEYS */;
INSERT INTO `training` VALUES (1,'2015-08-08 16:00:00',NULL,'Some sentenses about this training',90,NULL,'\0','\0',234,5,'\0',NULL,'16:00 - 17:30','Java developing',2,1),(2,'2015-08-07 06:00:00',NULL,'Some sentenses about this training',120,NULL,'','\0',231,10,'\0',NULL,'06:00 - 08:00','PHP #2',1,2),(3,'2015-08-08 16:30:00',NULL,'Some sentenses about this training',120,NULL,'','\0',432,10,'\0',NULL,'16:30 - 18:30','PHP #1',1,5),(4,NULL,'1 3 ','Some sentenses about this training',70,'2015-08-14','\0','\0',NULL,20,'','2015-07-31','16:0-17:10 16:0-17:10 ','C++',1,4);
/*!40000 ALTER TABLE `training` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_audience`
--

DROP TABLE IF EXISTS `training_audience`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `training_audience` (
  `training_id` bigint(20) NOT NULL,
  `audience_id` int(11) NOT NULL,
  PRIMARY KEY (`training_id`,`audience_id`),
  KEY `FK_2v2lu1kyv99pei6swdvv003xg` (`audience_id`),
  CONSTRAINT `FK_2v2lu1kyv99pei6swdvv003xg` FOREIGN KEY (`audience_id`) REFERENCES `audience` (`audience_id`),
  CONSTRAINT `FK_q5y28hyclav9qmsf0kmryohki` FOREIGN KEY (`training_id`) REFERENCES `training` (`training_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_audience`
--

LOCK TABLES `training_audience` WRITE;
/*!40000 ALTER TABLE `training_audience` DISABLE KEYS */;
INSERT INTO `training_audience` VALUES (1,1),(2,2),(3,2),(2,3),(3,3),(4,4);
/*!40000 ALTER TABLE `training_audience` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_feedback`
--

DROP TABLE IF EXISTS `training_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `training_feedback` (
  `training_feedback_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `effectiveness` int(11) DEFAULT NULL,
  `impression` int(11) DEFAULT NULL,
  `intelligibility` int(11) DEFAULT NULL,
  `interest` int(11) DEFAULT NULL,
  `recommending` bit(1) DEFAULT NULL,
  `text` varchar(4500) DEFAULT NULL,
  `trainer_desire` bit(1) DEFAULT NULL,
  `learn_smth_new` int(11) DEFAULT NULL,
  `training_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `is_approved` bit(1) NOT NULL,
  `is_deleted` bit(1) NOT NULL,
  PRIMARY KEY (`training_feedback_id`),
  KEY `FK_mlwjwvf8ak8o7phe8y8axfrtx` (`training_id`),
  KEY `FK_2tigk6qho8qc7ex525k8i6bhg` (`user_id`),
  CONSTRAINT `FK_2tigk6qho8qc7ex525k8i6bhg` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK_mlwjwvf8ak8o7phe8y8axfrtx` FOREIGN KEY (`training_id`) REFERENCES `training` (`training_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_feedback`
--

LOCK TABLES `training_feedback` WRITE;
/*!40000 ALTER TABLE `training_feedback` DISABLE KEYS */;
INSERT INTO `training_feedback` VALUES (1,'2015-07-31 10:06:15',5,0,0,2,'',NULL,'',2,1,1,'\0','\0');
/*!40000 ALTER TABLE `training_feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_rating`
--

DROP TABLE IF EXISTS `training_rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `training_rating` (
  `training_rating_id` int(11) NOT NULL AUTO_INCREMENT,
  `star_count` int(11) DEFAULT NULL,
  `training_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`training_rating_id`),
  KEY `FK_ifmnueta8qsaps608t3m8ilt2` (`training_id`),
  KEY `FK_gbby2cuxsjq86awtln7q8sstm` (`user_id`),
  CONSTRAINT `FK_gbby2cuxsjq86awtln7q8sstm` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK_ifmnueta8qsaps608t3m8ilt2` FOREIGN KEY (`training_id`) REFERENCES `training` (`training_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_rating`
--

LOCK TABLES `training_rating` WRITE;
/*!40000 ALTER TABLE `training_rating` DISABLE KEYS */;
INSERT INTO `training_rating` VALUES (1,10,1,1);
/*!40000 ALTER TABLE `training_rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_tag`
--

DROP TABLE IF EXISTS `training_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `training_tag` (
  `training_id` bigint(20) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`training_id`,`tag_id`),
  KEY `FK_kxg64pw3vhnu2vxj01lqxel0s` (`tag_id`),
  CONSTRAINT `FK_7pknu13o0pjv98ma8sd43x5rb` FOREIGN KEY (`training_id`) REFERENCES `training` (`training_id`),
  CONSTRAINT `FK_kxg64pw3vhnu2vxj01lqxel0s` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_tag`
--

LOCK TABLES `training_tag` WRITE;
/*!40000 ALTER TABLE `training_tag` DISABLE KEYS */;
INSERT INTO `training_tag` VALUES (1,1),(2,2),(3,2),(2,3),(3,3),(4,4);
/*!40000 ALTER TABLE `training_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_user`
--

DROP TABLE IF EXISTS `training_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `training_user` (
  `training_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`training_id`,`user_id`),
  KEY `FK_ea6heja5wvdswwid81qj1070q` (`user_id`),
  CONSTRAINT `FK_ea6heja5wvdswwid81qj1070q` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK_t1ao42tjaiyqcg63bn1218tlv` FOREIGN KEY (`training_id`) REFERENCES `training` (`training_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_user`
--

LOCK TABLES `training_user` WRITE;
/*!40000 ALTER TABLE `training_user` DISABLE KEYS */;
INSERT INTO `training_user` VALUES (3,1),(4,1),(1,2),(4,2),(1,3),(3,3),(1,4),(3,4),(1,5),(1,6),(2,7),(4,7),(2,8),(4,8),(2,9);
/*!40000 ALTER TABLE `training_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `e_mail` varchar(255) DEFAULT NULL,
  `first_name` varchar(25) NOT NULL,
  `last_name` varchar(25) NOT NULL,
  `login` varchar(255) NOT NULL,
  `password` varchar(60) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_ew1hvam8uwaknuaellwhqchhb` (`login`),
  UNIQUE KEY `UK_5awx5dcb9xdv5m1bl38bmoke0` (`e_mail`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'jdoe@example.com','John','Doe','jdoe','$2a$10$fw1N2/nyt3Qg5WHWv230bOlKFw5fnM9my61L4fsbfHISj5rhGZhdq'),(2,'j.depp@example.com','Johnny','Depp','depp','$2a$10$fw1N2/nyt3Qg5WHWv230bOlKFw5fnM9my61L4fsbfHISj5rhGZhdq'),(3,'cyrus@example.com','Miley','Syrus','miley','$2a$10$fw1N2/nyt3Qg5WHWv230bOlKFw5fnM9my61L4fsbfHISj5rhGZhdq'),(4,'gennady.trubach@mail.ru','Gennady','Trubach','gtrubach','$2a$10$fw1N2/nyt3Qg5WHWv230bOlKFw5fnM9my61L4fsbfHISj5rhGZhdq'),(5,'yaroshevich.yana@gmail.com','Yana','Yaroshevich','yaroshevich','$2a$10$fw1N2/nyt3Qg5WHWv230bOlKFw5fnM9my61L4fsbfHISj5rhGZhdq'),(6,'phantom.rvr@gmail.com','Victor','Romashko','phantom','$2a$10$fw1N2/nyt3Qg5WHWv230bOlKFw5fnM9my61L4fsbfHISj5rhGZhdq'),(7,'toshabely@gmail.com','Anton','Bely','bely','$2a$10$fw1N2/nyt3Qg5WHWv230bOlKFw5fnM9my61L4fsbfHISj5rhGZhdq'),(8,'alexey_hw@tut.by','Alexey','Khvorostovskiy','khvorostovskiy','$2a$10$fw1N2/nyt3Qg5WHWv230bOlKFw5fnM9my61L4fsbfHISj5rhGZhdq'),(9,'shchaurouski.slava@gmail.com','Sviatoslau','Shchaurouski','shchaurouski','$2a$10$fw1N2/nyt3Qg5WHWv230bOlKFw5fnM9my61L4fsbfHISj5rhGZhdq');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_it77eq964jhfqtu54081ebtio` (`role_id`),
  CONSTRAINT `FK_apcc8lxk2xnug8377fatvbn04` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK_it77eq964jhfqtu54081ebtio` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1),(2,1),(4,1),(5,1),(6,1),(8,1),(3,2),(7,2),(9,2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `waiting_list`
--

DROP TABLE IF EXISTS `waiting_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `waiting_list` (
  `waiting_list_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `training_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`waiting_list_id`),
  KEY `FK_m7ajdtxrffqehvwgg1xg29tdr` (`training_id`),
  KEY `FK_hxcnjomo5e02plcgeihmyi24n` (`user_id`),
  CONSTRAINT `FK_hxcnjomo5e02plcgeihmyi24n` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK_m7ajdtxrffqehvwgg1xg29tdr` FOREIGN KEY (`training_id`) REFERENCES `training` (`training_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `waiting_list`
--

LOCK TABLES `waiting_list` WRITE;
/*!40000 ALTER TABLE `waiting_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `waiting_list` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-07-31 16:31:01
