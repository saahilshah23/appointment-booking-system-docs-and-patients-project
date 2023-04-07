-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: co559
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `appointments`
--

DROP TABLE IF EXISTS `appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointments` (
  `doctor` int NOT NULL,
  `patient` int NOT NULL,
  `date` datetime NOT NULL,
  `symptoms` varchar(200) DEFAULT NULL,
  `prescriptions` varchar(200) DEFAULT NULL,
  `comments` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`doctor`,`patient`,`date`),
  KEY `patient_idx` (`patient`),
  CONSTRAINT `doctor` FOREIGN KEY (`doctor`) REFERENCES `doctors` (`doctorID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `patient` FOREIGN KEY (`patient`) REFERENCES `patients` (`patientID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointments`
--

LOCK TABLES `appointments` WRITE;
/*!40000 ALTER TABLE `appointments` DISABLE KEYS */;
INSERT INTO `appointments` VALUES (1,1,'2021-07-01 14:00:00',NULL,NULL,NULL),(1,5,'2021-02-28 13:30:00','headache','paracetamol','first appointment'),(2,2,'2021-07-01 14:00:00',NULL,NULL,NULL),(2,4,'2021-10-14 14:13:00',NULL,NULL,NULL);
/*!40000 ALTER TABLE `appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctors`
--

DROP TABLE IF EXISTS `doctors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctors` (
  `doctorID` int NOT NULL,
  `name` varchar(100) NOT NULL,
  `lastName` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`doctorID`),
  UNIQUE KEY `doctorID_UNIQUE` (`doctorID`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctors`
--

LOCK TABLES `doctors` WRITE;
/*!40000 ALTER TABLE `doctors` DISABLE KEYS */;
INSERT INTO `doctors` VALUES (1,'Fernando','Ramirez','fr223@kent.ac.uk','fr223'),(2,'Devika','Vasisht','dv217@kent.ac.uk','dv217'),(3,'Ben','Haras-Gummer','bh330@kent.ac.uk','bh330'),(4,'Saahil','Shah','ss2357@kent.ac.uk','ss2357');
/*!40000 ALTER TABLE `doctors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patients` (
  `patientID` int NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `doctor` int NOT NULL,
  `age` int NOT NULL,
  `phoneNumber` int NOT NULL,
  PRIMARY KEY (`patientID`),
  UNIQUE KEY `patientID_UNIQUE` (`patientID`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `doctorID_idx` (`doctor`),
  CONSTRAINT `doctorID` FOREIGN KEY (`doctor`) REFERENCES `doctors` (`doctorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES (1,'Pete','Jr','pete@mail.com',1,20,42145),(2,'Marie','Curie','curie@mail.com',2,12,215),(3,'Alan','Turing','turing@mail.com',3,30,2145),(4,'John','Red','red@mail.com',4,43,2145),(5,'Danielle','Fisher','df@mail.com',1,55,12345),(6,'Tony','Walker','tn@mail.com',1,10,124),(7,'Marie','Church','churh@mail.com',2,5,1245),(8,'Dal','Marie','dm@kent.ac.uk',3,32,1234),(9,'Marica','Sudano','mc@mail.com',4,10,412435),(10,'Mattia','Dervasi','mt12@mail.com',1,33,2134),(11,'Vilde','Kongerp','vd@mail.com',2,44,12341),(12,'Jake','Thompson','jk@mail.com',4,66,12435),(13,'Liv','Stodal','vil@mail.com',3,55,12345),(14,'Matt','Pad','theory@games.com',1,12,12455),(15,'Calli','Fire','amber@mail.com',2,5,123455),(16,'Po','Jr','gr@mail.com',3,8,12345),(17,'Adriana','Lagunas','lv@mail.com',4,23,123456),(18,'Frida','Nathalia','1k@mail.com',1,21,412341),(19,'Natalia','Rivera','kinski@mail.com',2,21,3214312);
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'co559'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-26 20:44:04
