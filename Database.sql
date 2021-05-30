CREATE TABLE `users` (
  `UserID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `Department` varchar(255) DEFAULT NULL,
  `Role` varchar(255) DEFAULT NULL,
  `AcademicRank` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `Email` (`Email`)
);

CREATE TABLE `officehours` (
  `OfficeHourID` int NOT NULL AUTO_INCREMENT,
  `From_Time` time DEFAULT NULL,
  `UserID` int NOT NULL,
  `Status` varchar(255) DEFAULT 'Available',
  `Day` varchar(15) DEFAULT NULL,
  `To_Time` time DEFAULT NULL,
  `Location` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`OfficeHourID`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `officehours_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`)
);

CREATE TABLE `staffmembers` (
  `UserID` int DEFAULT NULL,
  `Subject` varchar(255) DEFAULT NULL,
  UNIQUE KEY `UserID` (`UserID`),
  CONSTRAINT `staffmembers_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`)
); 

CREATE TABLE `appointments` (
  `AppointmentID` int NOT NULL AUTO_INCREMENT,
  `StudentID` int DEFAULT NULL,
  `StaffMemberID` int DEFAULT NULL,
  `OfficeHourID` int NOT NULL,
  `OnDayNotification` varchar(255) DEFAULT 'Not Sent',
  `Date` date DEFAULT NULL,
  PRIMARY KEY (`AppointmentID`),
  KEY `StudentID` (`StudentID`),
  KEY `StaffMemberID` (`StaffMemberID`),
  KEY `OfficeHourID` (`OfficeHourID`),
  CONSTRAINT `appointments_ibfk_1` FOREIGN KEY (`StudentID`) REFERENCES `users` (`UserID`),
  CONSTRAINT `appointments_ibfk_2` FOREIGN KEY (`StaffMemberID`) REFERENCES `users` (`UserID`),
  CONSTRAINT `appointments_ibfk_3` FOREIGN KEY (`OfficeHourID`) REFERENCES `officehours` (`OfficeHourID`)
);

CREATE TABLE `history` (
  `HistoryID` int NOT NULL AUTO_INCREMENT,
  `Date` date DEFAULT NULL,
  `StudentID` int DEFAULT NULL,
  `StaffMemberID` int DEFAULT NULL,
  `From_Time` time DEFAULT NULL,
  `To_Time` time DEFAULT NULL,
  `Location` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`HistoryID`),
  KEY `StudentID` (`StudentID`),
  KEY `StaffMemberID` (`StaffMemberID`),
  CONSTRAINT `history_ibfk_1` FOREIGN KEY (`StudentID`) REFERENCES `users` (`UserID`),
  CONSTRAINT `history_ibfk_2` FOREIGN KEY (`StaffMemberID`) REFERENCES `users` (`UserID`)
);

CREATE TABLE `messages` (
  `MessageID` int NOT NULL AUTO_INCREMENT,
  `SenderID` int DEFAULT NULL,
  `ReceiverID` int DEFAULT NULL,
  `Content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`MessageID`),
  KEY `SenderID` (`SenderID`),
  KEY `ReceiverID` (`ReceiverID`),
  CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`SenderID`) REFERENCES `users` (`UserID`),
  CONSTRAINT `messages_ibfk_2` FOREIGN KEY (`ReceiverID`) REFERENCES `users` (`UserID`)
);

CREATE TABLE `notifications` (
  `NoftificationID` int NOT NULL AUTO_INCREMENT,
  `ReceiverID` int DEFAULT NULL,
  `Content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`NoftificationID`),
  KEY `ReceiverID` (`ReceiverID`),
  CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`ReceiverID`) REFERENCES `users` (`UserID`)
);


