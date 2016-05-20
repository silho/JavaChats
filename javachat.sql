-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: javachatdb
-- ------------------------------------------------------
-- Server version	5.7.12-log

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
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friends` (
  `USERNAME` char(20) NOT NULL,
  `FRIENDNAME` char(20) NOT NULL,
  `ISLOGIN` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`USERNAME`,`FRIENDNAME`),
  KEY `FRIENDNAME` (`FRIENDNAME`),
  CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`USERNAME`) REFERENCES `javachatuser` (`USERNAME`),
  CONSTRAINT `friends_ibfk_2` FOREIGN KEY (`FRIENDNAME`) REFERENCES `javachatuser` (`USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends`
--

LOCK TABLES `friends` WRITE;
/*!40000 ALTER TABLE `friends` DISABLE KEYS */;
/*!40000 ALTER TABLE `friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friendsmessage`
--

DROP TABLE IF EXISTS `friendsmessage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friendsmessage` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `SANDER` char(20) NOT NULL,
  `RECEIVEER` char(20) NOT NULL,
  `SANDTIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `RECEIVED` tinyint(1) NOT NULL DEFAULT '0',
  `MESSAGE` varchar(2047) NOT NULL,
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=181 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friendsmessage`
--

LOCK TABLES `friendsmessage` WRITE;
/*!40000 ALTER TABLE `friendsmessage` DISABLE KEYS */;
/*!40000 ALTER TABLE `friendsmessage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups` (
  `GROUPNAME` char(20) NOT NULL,
  `GROUPMEMBERSNUM` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`GROUPNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groupsmembers`
--

DROP TABLE IF EXISTS `groupsmembers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groupsmembers` (
  `GROUPNAME` char(20) NOT NULL,
  `MEMBERNAME` char(20) NOT NULL,
  `ISLOGIN` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`GROUPNAME`,`MEMBERNAME`),
  KEY `MEMBERNAME` (`MEMBERNAME`),
  CONSTRAINT `groupsmembers_ibfk_1` FOREIGN KEY (`GROUPNAME`) REFERENCES `groups` (`GROUPNAME`),
  CONSTRAINT `groupsmembers_ibfk_2` FOREIGN KEY (`MEMBERNAME`) REFERENCES `javachatuser` (`USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groupsmembers`
--

LOCK TABLES `groupsmembers` WRITE;
/*!40000 ALTER TABLE `groupsmembers` DISABLE KEYS */;
/*!40000 ALTER TABLE `groupsmembers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groupsmessage`
--

DROP TABLE IF EXISTS `groupsmessage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groupsmessage` (
  `GROUPNAME` char(20) NOT NULL,
  `SANDER` char(20) NOT NULL,
  `SANDTIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MESSAGE` varchar(2048) NOT NULL,
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`GROUPNAME`,`SANDER`,`SANDTIME`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  CONSTRAINT `groupsmessage_ibfk_1` FOREIGN KEY (`GROUPNAME`, `SANDER`) REFERENCES `groupsmembers` (`GROUPNAME`, `MEMBERNAME`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groupsmessage`
--

LOCK TABLES `groupsmessage` WRITE;
/*!40000 ALTER TABLE `groupsmessage` DISABLE KEYS */;
/*!40000 ALTER TABLE `groupsmessage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `javachatuser`
--

DROP TABLE IF EXISTS `javachatuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `javachatuser` (
  `USERNAME` char(20) NOT NULL,
  `PASSWORD` char(20) NOT NULL,
  `LASTLOGIN` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `REGISTERTIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ISLOGIN` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `javachatuser`
--

LOCK TABLES `javachatuser` WRITE;
/*!40000 ALTER TABLE `javachatuser` DISABLE KEYS */;
/*!40000 ALTER TABLE `javachatuser` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-20 23:13:55
