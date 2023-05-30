CREATE TABLE `project` (
                           `projectID` int NOT NULL AUTO_INCREMENT,
                           `projectTitle` varchar(45) NOT NULL,
                           `deadline` varchar(45) NOT NULL,
                           `HoursPrDay` int NOT NULL,
                           `projectDescription` varchar(300) NOT NULL,
                           PRIMARY KEY (`projectID`),
                           UNIQUE KEY `projectID_UNIQUE` (`projectID`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `task` (
                        `taskID` int NOT NULL AUTO_INCREMENT,
                        `taskDescription` varchar(100) NOT NULL,
                        `taskName` varchar(45) NOT NULL,
                        `taskNrOfHours` int NOT NULL,
                        `taskNrOfUsers` int NOT NULL,
                        `taskHoursPrDay` int NOT NULL,
                        `taskDeadline` varchar(45) NOT NULL,
                        `projectId` int NOT NULL,
                        `completed` tinyint DEFAULT NULL,
                        PRIMARY KEY (`taskID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `user` (
                        `Id` int NOT NULL AUTO_INCREMENT,
                        `email` varchar(45) NOT NULL,
                        `password` varchar(150) NOT NULL,
                        `hourlyWage` int NOT NULL,
                        `name` varchar(45) NOT NULL,
                        `admin` tinyint NOT NULL,
                        PRIMARY KEY (`Id`),
                        UNIQUE KEY `ID_UNIQUE` (`Id`),
                        UNIQUE KEY `email_UNIQUE` (`email`),
                        UNIQUE KEY `password_UNIQUE` (`password`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

