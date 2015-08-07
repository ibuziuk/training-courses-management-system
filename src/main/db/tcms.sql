-- MySQL dump 10.13  Distrib 5.6.25, for Win64 (x86_64)
--
-- Host: localhost    Database: training_courses_management_system
-- ------------------------------------------------------
-- Server version	5.6.25

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
-- Table structure for table `absence`
--

DROP TABLE IF EXISTS `absence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `absence` (
  `absence_id` int(11) NOT NULL AUTO_INCREMENT,
  `is_reasonable` bit(1) DEFAULT NULL,
  `reason_text` varchar(4500) DEFAULT NULL,
  `trainer_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`absence_id`),
  KEY `FK_cn2j1wjv4ugw1nf596n36or88` (`trainer_id`),
  KEY `FK_2meku1gaugffsyq9t8okrg72a` (`user_id`),
  CONSTRAINT `FK_2meku1gaugffsyq9t8okrg72a` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK_cn2j1wjv4ugw1nf596n36or88` FOREIGN KEY (`trainer_id`) REFERENCES `training` (`training_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `absence`
--

LOCK TABLES `absence` WRITE;
/*!40000 ALTER TABLE `absence` DISABLE KEYS */;
/*!40000 ALTER TABLE `absence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `absence_lesson`
--

DROP TABLE IF EXISTS `absence_lesson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `absence_lesson` (
  `absence_lesson_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_reasonable` bit(1) DEFAULT NULL,
  `reason_text` varchar(4500) DEFAULT NULL,
  `lesson_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`absence_lesson_id`),
  KEY `FK_6i2ll1q87mo00e3ku8myu7gaa` (`lesson_id`),
  KEY `FK_gtrx3uiyonq7f28vlr8mjwx7u` (`user_id`),
  CONSTRAINT `FK_6i2ll1q87mo00e3ku8myu7gaa` FOREIGN KEY (`lesson_id`) REFERENCES `regular_lesson` (`lesson_id`),
  CONSTRAINT `FK_gtrx3uiyonq7f28vlr8mjwx7u` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `absence_lesson`
--

LOCK TABLES `absence_lesson` WRITE;
/*!40000 ALTER TABLE `absence_lesson` DISABLE KEYS */;
/*!40000 ALTER TABLE `absence_lesson` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
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
-- Table structure for table `employee_feedback`
--

DROP TABLE IF EXISTS `employee_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee_feedback` (
  `employee_feedback_id` int(11) NOT NULL AUTO_INCREMENT,
  `askQuestions` bit(1) DEFAULT NULL,
  `attitudeToLearning` int(11) DEFAULT NULL,
  `communication` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `focusedOnResults` bit(1) DEFAULT NULL,
  `interest` bit(1) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `mark` varchar(45) DEFAULT NULL,
  `present` bit(1) DEFAULT NULL,
  `text` varchar(4500) DEFAULT NULL,
  `trainer_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`employee_feedback_id`),
  KEY `FK_jupn30lulyra25sfcs7gxdrbo` (`trainer_id`),
  KEY `FK_481kei9j76s2j1li3nq9mr86h` (`user_id`),
  CONSTRAINT `FK_481kei9j76s2j1li3nq9mr86h` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK_jupn30lulyra25sfcs7gxdrbo` FOREIGN KEY (`trainer_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_feedback`
--

LOCK TABLES `employee_feedback` WRITE;
/*!40000 ALTER TABLE `employee_feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee_feedback` ENABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_training_user`
--

LOCK TABLES `ex_training_user` WRITE;
/*!40000 ALTER TABLE `ex_training_user` DISABLE KEYS */;
INSERT INTO `ex_training_user` VALUES (1,5),(5,6);
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
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
-- Table structure for table `lesson_edit`
--

DROP TABLE IF EXISTS `lesson_edit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lesson_edit` (
  `lesson_edit_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `is_approved` bit(1) DEFAULT NULL,
  `location` int(11) DEFAULT NULL,
  `time` varchar(20) DEFAULT NULL,
  `lesson_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`lesson_edit_id`),
  KEY `FK_ceak500enalunsh53k2pg49o2` (`lesson_id`),
  CONSTRAINT `FK_ceak500enalunsh53k2pg49o2` FOREIGN KEY (`lesson_id`) REFERENCES `regular_lesson` (`lesson_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lesson_edit`
--

LOCK TABLES `lesson_edit` WRITE;
/*!40000 ALTER TABLE `lesson_edit` DISABLE KEYS */;
/*!40000 ALTER TABLE `lesson_edit` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regular_lesson`
--

LOCK TABLES `regular_lesson` WRITE;
/*!40000 ALTER TABLE `regular_lesson` DISABLE KEYS */;
INSERT INTO `regular_lesson` VALUES (1,'2015-08-07 19:00:00',432,'19:00 - 19:30',4),(2,'2015-08-14 19:00:00',432,'19:00 - 19:30',4);
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
INSERT INTO `role` VALUES (1,'administrator'),(2,'user');
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
-- Table structure for table `trainer_feedback`
--

DROP TABLE IF EXISTS `trainer_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trainer_feedback` (
  `trainer_feedback_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `asks` bit(1) DEFAULT NULL,
  `assimilation` bit(1) DEFAULT NULL,
  `creativity` bit(1) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `explain_difficult_places` bit(1) DEFAULT NULL,
  `high_erudition` bit(1) DEFAULT NULL,
  `explain_how_to_use` bit(1) DEFAULT NULL,
  `interest` bit(1) DEFAULT NULL,
  `mark_main_point` bit(1) DEFAULT NULL,
  `nice` bit(1) DEFAULT NULL,
  `patient` bit(1) DEFAULT NULL,
  `understandably` bit(1) DEFAULT NULL,
  `trainer_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`trainer_feedback_id`),
  KEY `FK_simaqg1k2pl5w9c0yw3xxpd7g` (`trainer_id`),
  KEY `FK_fmtkjyd9ytr5ph6en60e78c2s` (`user_id`),
  CONSTRAINT `FK_fmtkjyd9ytr5ph6en60e78c2s` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK_simaqg1k2pl5w9c0yw3xxpd7g` FOREIGN KEY (`trainer_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trainer_feedback`
--

LOCK TABLES `trainer_feedback` WRITE;
/*!40000 ALTER TABLE `trainer_feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `trainer_feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training`
--

DROP TABLE IF EXISTS `training`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `training` (
  `training_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `continuous` bit(1) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training`
--

LOCK TABLES `training` WRITE;
/*!40000 ALTER TABLE `training` DISABLE KEYS */;
INSERT INTO `training` VALUES (1,'\0','2015-08-07 19:00:00',NULL,'Some sentences about your training',60,NULL,'\0','',123,2,'\0',NULL,'19:00 - 20:00','Java',1,6),(2,'','2015-08-07 19:20:00',NULL,'Some sentences about your training',60,NULL,'\0','',432,4,'\0',NULL,'19:20 - 20:20','PHP #1',2,5),(3,'','2015-08-08 18:20:00',NULL,'Some sentences about your training',60,NULL,'\0','',213,5,'\0',NULL,'18:20 - 19:20','PHP #2',2,5),(4,'\0',NULL,'4 ','Some sentences about your training',30,'2015-08-21','','',NULL,4,'','2015-08-07','19:00-19:30 ','JavaScript',1,4),(5,'\0','2015-08-07 15:00:00',NULL,'Some sentences about your training',60,NULL,'\0','',378,5,'\0',NULL,'15:00 - 16:00','C++',1,7),(6,'\0','2015-08-08 16:00:00',NULL,'Some sentences about your training',60,NULL,'\0','\0',123,2,'\0',NULL,'16:00 - 17:00','Scala',1,8),(7,'\0','2015-08-07 10:00:00',NULL,'Some sentences about your training',60,NULL,'\0','',345,4,'\0',NULL,'10:00 - 11:00','Go',1,9);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_audience`
--

LOCK TABLES `training_audience` WRITE;
/*!40000 ALTER TABLE `training_audience` DISABLE KEYS */;
INSERT INTO `training_audience` VALUES (1,1),(2,2),(3,2),(4,3),(5,4),(6,5),(7,6);
/*!40000 ALTER TABLE `training_audience` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_edit`
--

DROP TABLE IF EXISTS `training_edit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `training_edit` (
  `training_edit_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `is_approved` bit(1) DEFAULT NULL,
  `max_visitors_count` int(11) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `training_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`training_edit_id`),
  KEY `FK_mlvf536njfjd4pn3ekh23ht44` (`training_id`),
  CONSTRAINT `FK_mlvf536njfjd4pn3ekh23ht44` FOREIGN KEY (`training_id`) REFERENCES `training` (`training_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_edit`
--

LOCK TABLES `training_edit` WRITE;
/*!40000 ALTER TABLE `training_edit` DISABLE KEYS */;
/*!40000 ALTER TABLE `training_edit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_feedback`
--

DROP TABLE IF EXISTS `training_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `training_feedback` (
  `training_feedback_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `effectiveness` int(11) DEFAULT NULL,
  `impression` int(11) DEFAULT NULL,
  `intelligibility` int(11) DEFAULT NULL,
  `interest` int(11) DEFAULT NULL,
  `is_approved` bit(1) NOT NULL,
  `is_deleted` bit(1) NOT NULL,
  `recommending` bit(1) DEFAULT NULL,
  `star_count` int(11) DEFAULT NULL,
  `text` varchar(4500) DEFAULT NULL,
  `trainer_desire` bit(1) DEFAULT NULL,
  `learn_smth_new` int(11) DEFAULT NULL,
  `training_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`training_feedback_id`),
  KEY `FK_mlwjwvf8ak8o7phe8y8axfrtx` (`training_id`),
  KEY `FK_2tigk6qho8qc7ex525k8i6bhg` (`user_id`),
  CONSTRAINT `FK_2tigk6qho8qc7ex525k8i6bhg` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK_mlwjwvf8ak8o7phe8y8axfrtx` FOREIGN KEY (`training_id`) REFERENCES `training` (`training_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_feedback`
--

LOCK TABLES `training_feedback` WRITE;
/*!40000 ALTER TABLE `training_feedback` DISABLE KEYS */;
INSERT INTO `training_feedback` VALUES (1,'2015-08-07 10:03:00',5,0,0,2,'\0','\0','',10,NULL,'',2,7,6);
/*!40000 ALTER TABLE `training_feedback` ENABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_tag`
--

LOCK TABLES `training_tag` WRITE;
/*!40000 ALTER TABLE `training_tag` DISABLE KEYS */;
INSERT INTO `training_tag` VALUES (1,1),(2,2),(3,2),(4,3),(5,4),(6,5),(7,6);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_user`
--

LOCK TABLES `training_user` WRITE;
/*!40000 ALTER TABLE `training_user` DISABLE KEYS */;
INSERT INTO `training_user` VALUES (1,4),(2,4),(3,4),(5,4),(7,4),(4,5),(2,6),(3,6),(4,6),(5,6),(7,6),(1,7),(2,7),(3,7),(4,7),(2,8),(3,8),(5,8),(7,8),(5,9);
/*!40000 ALTER TABLE `training_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `upload_file`
--

DROP TABLE IF EXISTS `upload_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `upload_file` (
  `upload_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `training_id` bigint(20) NOT NULL,
  PRIMARY KEY (`upload_id`),
  KEY `FK_hadtko3whdrli9q6sf1xjgw9x` (`training_id`),
  CONSTRAINT `FK_hadtko3whdrli9q6sf1xjgw9x` FOREIGN KEY (`training_id`) REFERENCES `training` (`training_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `upload_file`
--

LOCK TABLES `upload_file` WRITE;
/*!40000 ALTER TABLE `upload_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `upload_file` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'shchaurouski.slava@yandex.ru','John','Doe','jdoe','$2a$10$fw1N2/nyt3Qg5WHWv230bOlKFw5fnM9my61L4fsbfHISj5rhGZhdq'),(2,'j.depp@example.com','Johnny','Depp','depp','$2a$10$fw1N2/nyt3Qg5WHWv230bOlKFw5fnM9my61L4fsbfHISj5rhGZhdq'),(3,'cyrus@example.com','Miley','Syrus','miley','$2a$10$fw1N2/nyt3Qg5WHWv230bOlKFw5fnM9my61L4fsbfHISj5rhGZhdq'),(4,'gennady.trubach@mail.ru','Gennady','Trubach','gtrubach','$2a$10$fw1N2/nyt3Qg5WHWv230bOlKFw5fnM9my61L4fsbfHISj5rhGZhdq'),(5,'yaroshevich.yana@gmail.com','Yana','Yaroshevich','yaroshevich','$2a$10$fw1N2/nyt3Qg5WHWv230bOlKFw5fnM9my61L4fsbfHISj5rhGZhdq'),(6,'phantom.rvr@gmail.com','Victor','Romashko','phantom','$2a$10$fw1N2/nyt3Qg5WHWv230bOlKFw5fnM9my61L4fsbfHISj5rhGZhdq'),(7,'toshabely@gmail.com','Anton','Bely','bely','$2a$10$fw1N2/nyt3Qg5WHWv230bOlKFw5fnM9my61L4fsbfHISj5rhGZhdq'),(8,'alexey_hw@tut.by','Alexey','Khvorostovskiy','khvorostovskiy','$2a$10$fw1N2/nyt3Qg5WHWv230bOlKFw5fnM9my61L4fsbfHISj5rhGZhdq'),(9,'shchaurouski.slava@gmail.com','Sviatoslau','Shchaurouski','shchaurouski','$2a$10$fw1N2/nyt3Qg5WHWv230bOlKFw5fnM9my61L4fsbfHISj5rhGZhdq');
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `waiting_list`
--

LOCK TABLES `waiting_list` WRITE;
/*!40000 ALTER TABLE `waiting_list` DISABLE KEYS */;
INSERT INTO `waiting_list` VALUES (3,'2015-08-07 10:14:29',1,5),(4,'2015-08-07 10:15:53',4,8);
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

-- Dump completed on 2015-08-07 16:38:07
