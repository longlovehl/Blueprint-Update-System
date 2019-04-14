-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.15-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table migrate_bx.nodetemplate
CREATE TABLE IF NOT EXISTS  nodetemplate  (
   id  varchar(45) NOT NULL,
   maxinstances  varchar(45) DEFAULT NULL,
   mininstances  varchar(45) DEFAULT NULL,
   typ  varchar(45) DEFAULT NULL,
   provider  varchar(45) DEFAULT NULL,
   instancetype  varchar(45) DEFAULT NULL,
   baseimage  varchar(145) DEFAULT NULL,
   packages  varchar(45) DEFAULT NULL,
   rid1  varchar(145) DEFAULT NULL,
   rid1type  varchar(145) DEFAULT NULL,
   rid2  varchar(145) DEFAULT NULL,
   rid2type  varchar(145) DEFAULT NULL,
   cid  varchar(245) DEFAULT NULL,
   cidtype  varchar(145) DEFAULT NULL,
   daname  varchar(245) DEFAULT NULL,
   xmlns  varchar(145) DEFAULT NULL,
   artifactref  varchar(145) DEFAULT NULL,
   artifacttype  varchar(145) DEFAULT NULL
);

-- Dumping data for table migrate_bx.nodetemplate: ~0 rows (approximately)
/*!40000 ALTER TABLE  nodetemplate  DISABLE KEYS */;
/*!40000 ALTER TABLE  nodetemplate  ENABLE KEYS */;

-- Dumping structure for table migrate_bx.viewnodetemplate
-- CREATE TABLE IF NOT EXISTS  viewnodetemplate  (
--    id  varchar(145) DEFAULT NULL,
--    maxinstances  varchar(145) DEFAULT NULL,
--    mininstances  varchar(145) DEFAULT NULL,
--    typ  varchar(145) DEFAULT NULL,
--    provider  varchar(145) DEFAULT NULL,
--    instancetype  varchar(145) DEFAULT NULL,
--    baseimage  varchar(145) DEFAULT NULL,
--    packages  varchar(145) DEFAULT NULL,
--    cid  varchar(145) DEFAULT NULL,
--    cidtype  varchar(145) DEFAULT NULL
-- ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table migrate_bx.viewnodetemplate: ~0 rows (approximately)
/*!40000 ALTER TABLE  viewnodetemplate  DISABLE KEYS */;
/*!40000 ALTER TABLE  viewnodetemplate  ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

\echo 'LOADING nodetemplate'
\i load_nodetemplate.sql