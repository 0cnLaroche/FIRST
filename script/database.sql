-- --------------------------------------------------------
-- Host:                         10.54.223.154
-- Server version:               5.7.23 - MySQL Community Server (GPL)
-- Server OS:                    Linux
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for costing
CREATE DATABASE IF NOT EXISTS `costing` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `costing`;

-- Dumping structure for table costing.Activity
CREATE TABLE IF NOT EXISTS `Activity` (
  `ID` varchar(4) NOT NULL,
  `Name` varchar(40) NOT NULL,
  `NameFR` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table costing.Admin
CREATE TABLE IF NOT EXISTS `Admin` (
  `ID` varchar(50) NOT NULL,
  `Hash` varchar(150) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table costing.CostCenter
CREATE TABLE IF NOT EXISTS `CostCenter` (
  `ReportsTo` mediumint(9) NOT NULL,
  `ID` mediumint(9) NOT NULL,
  `Name` varchar(150) DEFAULT NULL,
  `Manager` varchar(100) DEFAULT NULL,
  `Directorate` varchar(100) DEFAULT NULL,
  `EffectiveDate` date DEFAULT NULL,
  `ClosingDate` date DEFAULT NULL,
  `Comments` varchar(250) DEFAULT NULL,
  `Level` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table costing.CostElement
CREATE TABLE IF NOT EXISTS `CostElement` (
  `ID` int(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `APM` varchar(200) NOT NULL,
  `PAA` varchar(200) NOT NULL,
  `GCIRExpenditureCategory` varchar(200) NOT NULL,
  `Category` varchar(50) NOT NULL,
  `Group` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table costing.Direct
CREATE TABLE IF NOT EXISTS `Direct` (
  `PostingDate` date DEFAULT NULL,
  `DocumentNumber` varchar(10) DEFAULT NULL,
  `Period` tinyint(4) DEFAULT NULL,
  `CostElement` int(11) DEFAULT NULL,
  `Amount` decimal(12,2) DEFAULT NULL,
  `Fund` varchar(4) DEFAULT NULL,
  `FunctionalArea` varchar(9) DEFAULT NULL,
  `CostCenter` mediumint(9) DEFAULT NULL,
  `PRI` varchar(9) DEFAULT NULL,
  `Object` varchar(30) DEFAULT NULL,
  `NetworkActivity` varchar(4) DEFAULT NULL,
  `Quantity` decimal(7,2) DEFAULT NULL,
  `HourlyRate` decimal(10,4) DEFAULT NULL,
  `StandardRate` decimal(10,4) DEFAULT NULL,
  `TimeMultiplier` decimal(4,2) DEFAULT NULL,
  `FiscalYear` smallint(6) DEFAULT NULL,
  KEY `FK_Direct_CostElement` (`CostElement`),
  CONSTRAINT `FK_Direct_CostElement` FOREIGN KEY (`CostElement`) REFERENCES `CostElement` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- Data exporting was unselected.
-- Dumping structure for procedure costing.import_Solution_Cost
DELIMITER //
//
DELIMITER ;

-- Dumping structure for table costing.ITSM
CREATE TABLE IF NOT EXISTS `ITSM` (
  `Dimension` varchar(100) DEFAULT NULL,
  `Stream` varchar(100) DEFAULT NULL,
  `Category` varchar(100) DEFAULT NULL,
  `Service` varchar(100) DEFAULT NULL,
  `RUN` varchar(6) DEFAULT NULL,
  `CostCenter` mediumint(9) DEFAULT NULL,
  `NetworkActivity` varchar(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table costing.ITSM_Cost
CREATE TABLE IF NOT EXISTS `ITSM_Cost` (
  `Dimension` varchar(100) DEFAULT NULL,
  `Stream` varchar(100) DEFAULT NULL,
  `Category` varchar(100) DEFAULT NULL,
  `Service` varchar(100) DEFAULT NULL,
  `Period` smallint(6) DEFAULT NULL,
  `CostElement` mediumint(9) DEFAULT NULL,
  `Object` varchar(10) DEFAULT NULL,
  `CostCenter` mediumint(9) DEFAULT NULL,
  `Amount` decimal(12,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- Data exporting was unselected.
-- Dumping structure for table costing.Log
CREATE TABLE IF NOT EXISTS `Log` (
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `User` varchar(150) NOT NULL,
  `Event` text NOT NULL,
  `Origin` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table costing.Network
CREATE TABLE IF NOT EXISTS `Network` (
  `ID` varchar(5) NOT NULL,
  `Name` varchar(40) DEFAULT NULL,
  `NameFR` varchar(40) DEFAULT NULL,
  `LongText` varchar(250) DEFAULT NULL,
  `WBS` varchar(50) DEFAULT NULL,
  `Project` varchar(10) DEFAULT NULL,
  `Status` varchar(15) DEFAULT NULL,
  `ClosingDate` date DEFAULT NULL,
  `EffectiveDate` date DEFAULT NULL,
  `ReplacedBy` varchar(5) DEFAULT NULL,
  `Comments` varchar(250) DEFAULT NULL,
  `FunctionalArea` varchar(100) DEFAULT NULL,
  `Stage` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table costing.NSD_Solution
CREATE TABLE IF NOT EXISTS `NSD_Solution` (
  `SystemApplication` varchar(50) DEFAULT NULL,
  `Solution` smallint(6) DEFAULT NULL,
  KEY `FK_NSD_Solution_Solution` (`Solution`),
  CONSTRAINT `FK_NSD_Solution_Solution` FOREIGN KEY (`Solution`) REFERENCES `Solution` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table costing.Object_PAA
CREATE TABLE IF NOT EXISTS `Object_PAA` (
  `Object` varchar(50) DEFAULT NULL,
  `PAA` varchar(150) DEFAULT NULL,
  `Weight` decimal(10,8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table costing.ProjectDefinition
CREATE TABLE IF NOT EXISTS `ProjectDefinition` (
  `ID` varchar(6) NOT NULL,
  `Name` varchar(150) DEFAULT NULL,
  `NameFR` varchar(150) DEFAULT NULL,
  `Model` varchar(40) DEFAULT NULL,
  `Status` varchar(20) DEFAULT NULL,
  `Proposal` varchar(30) DEFAULT NULL,
  `ClosingDate` date DEFAULT NULL,
  `Comments` varchar(250) DEFAULT NULL,
  `ProjectManager` varchar(40) DEFAULT NULL,
  `ITServicesGroups` varchar(200) DEFAULT NULL,
  `DistributedComputingEnvironment` varchar(200) DEFAULT NULL,
  `ADDMByApp` varchar(200) DEFAULT NULL,
  `ADDMByOutcome` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- Data exporting was unselected.
-- Dumping structure for table costing.Project_Solution
CREATE TABLE IF NOT EXISTS `Project_Solution` (
  `Solution` smallint(6) DEFAULT NULL,
  `SolutionName` varchar(150) DEFAULT NULL,
  `Weight` decimal(10,8) DEFAULT NULL,
  `Project` varchar(6) DEFAULT NULL,
  `ProjectName` varchar(150) DEFAULT NULL,
  KEY `FK_Project_Solution_ProjectDefinition` (`Project`),
  KEY `FK_Project_Solution_Solution` (`Solution`),
  CONSTRAINT `FK_Project_Solution_ProjectDefinition` FOREIGN KEY (`Project`) REFERENCES `ProjectDefinition_b` (`ID`),
  CONSTRAINT `FK_Project_Solution_Solution` FOREIGN KEY (`Solution`) REFERENCES `Solution` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table costing.RUN
CREATE TABLE IF NOT EXISTS `RUN` (
  `ID` varchar(6) NOT NULL,
  `Name` varchar(150) DEFAULT NULL,
  `NameFR` varchar(150) DEFAULT NULL,
  `Type` varchar(25) DEFAULT NULL,
  `Responsible` varchar(40) DEFAULT NULL,
  `CostCenter_Responsible` mediumint(9) DEFAULT NULL,
  `CostCenter_Requesting` mediumint(9) DEFAULT NULL,
  `ITServiceCategory` varchar(50) DEFAULT NULL,
  `EffectiveDate` date DEFAULT NULL,
  `ModifiedDate` date DEFAULT NULL,
  `ClosingDate` date DEFAULT NULL,
  `ReplacedBy` varchar(6) DEFAULT NULL,
  `Previous` varchar(20) DEFAULT NULL,
  `Comments` varchar(250) DEFAULT NULL,
  `Status` varchar(20) DEFAULT NULL,
  `LongText` varchar(150) DEFAULT NULL,
  `ITServicesGroups` varchar(200) DEFAULT NULL,
  `DistributedComputingEnvironment` varchar(200) DEFAULT NULL,
  `ADDMByApp` varchar(200) DEFAULT NULL,
  `ADDMByOutcome` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- Data exporting was unselected.
-- Dumping structure for table costing.RUN_Service
CREATE TABLE IF NOT EXISTS `RUN_Service` (
  `RUN` varchar(5) DEFAULT NULL,
  `Service` varchar(100) DEFAULT NULL,
  KEY `FK_RUN_Service_RUN` (`RUN`),
  CONSTRAINT `FK_RUN_Service_RUN` FOREIGN KEY (`RUN`) REFERENCES `RUN_b` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table costing.RUN_Solution
CREATE TABLE IF NOT EXISTS `RUN_Solution` (
  `RUN` varchar(50) DEFAULT NULL,
  `Solution` smallint(6) DEFAULT NULL,
  `Weight` double DEFAULT NULL,
  `Comments` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- Data exporting was unselected.
-- Dumping structure for table costing.Solution
CREATE TABLE IF NOT EXISTS `Solution` (
  `ID` smallint(6) NOT NULL,
  `Name` varchar(150) DEFAULT NULL,
  `NameFR` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table costing.Solution_Cost
CREATE TABLE IF NOT EXISTS `Solution_Cost` (
  `CostType` varchar(40) DEFAULT NULL,
  `CostCategory` varchar(40) DEFAULT NULL,
  `Solution` smallint(6) DEFAULT NULL,
  `CostCenter` mediumint(9) DEFAULT NULL,
  `CostElement` mediumint(9) DEFAULT NULL,
  `Fund` varchar(4) DEFAULT NULL,
  `FunctionalArea` varchar(9) DEFAULT NULL,
  `Period` tinyint(4) DEFAULT NULL,
  `FiscalYear` smallint(6) DEFAULT NULL,
  `TimeMultiplier` decimal(4,2) DEFAULT NULL,
  `Quantity` decimal(9,2) DEFAULT NULL,
  `Amount` decimal(12,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table costing.WBS
CREATE TABLE IF NOT EXISTS `WBS` (
  `ID` varchar(50) NOT NULL,
  `Name` varchar(40) DEFAULT NULL,
  `NameFR` varchar(40) DEFAULT NULL,
  `LongText` varchar(150) DEFAULT NULL,
  `ResponsibleCostCenter` mediumint(9) DEFAULT NULL,
  `RequestingCostCenter` mediumint(9) DEFAULT NULL,
  `Approver` varchar(70) DEFAULT NULL,
  `ProjectDefinition` varchar(6) DEFAULT NULL,
  `Status` varchar(15) DEFAULT NULL,
  `Stage` tinyint(4) DEFAULT NULL,
  `ClosingDate` date DEFAULT NULL,
  `EffectiveDate` date DEFAULT NULL,
  `ReplacedBy` varchar(50) DEFAULT NULL,
  `Comments` varchar(250) DEFAULT NULL,
  `ParentWBS` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
