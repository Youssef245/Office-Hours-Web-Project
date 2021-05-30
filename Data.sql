INSERT INTO `users` VALUES (1,'Youssef Mohammed','youssefshoker@gmail.com','000','IS','Student','--');
INSERT INTO `users` VALUES (2,'Omar Khaled','omar15fire@gmail.com','111','IT','Staff Member','Dr.');
INSERT INTO `users` VALUES (3,'Amr Samy','amrsamy622@gmail.com','222','IT','Staff Member','TA');
INSERT INTO `users` VALUES (4,'Abdullah Mohammed','abdullahm7mad255@gmail.com','333','IT','Student','--');
INSERT INTO `users` VALUES (5,'Mohammed Mohey','youssef.mohamed01127792@gmail.com','444','IS','Staff Member','Dr.');
INSERT INTO `users` VALUES (8,'Nabil Tharwat','omar15kh@gmail.com','555','IS','Student','--');

INSERT INTO `staffmembers` VALUES (2,'Web Development');
INSERT INTO `staffmembers` VALUES (3,'Network');
INSERT INTO `staffmembers` VALUES (5,'Software Engineering');

INSERT INTO `officehours` VALUES (1,'10:00:00',2,'Reserved','Sunday','10:30:00','New Building');
INSERT INTO `officehours` VALUES (2,'12:00:00',2,'Reserved','Tuesday','13:00:00','Online');
INSERT INTO `officehours` VALUES (3,'09:00:00',3,'Reserved','Saturday','10:00:00','Old Building');
INSERT INTO `officehours` VALUES (4,'11:00:00',3,'Reserved','Wednesday','12:30:00','Online');
INSERT INTO `officehours` VALUES (5,'08:00:00',5,'Reserved','Monday','09:00:00','Building 23');
INSERT INTO `officehours` VALUES (6,'16:00:00',5,'Available','Tuesday','17:00:00','Online');

INSERT INTO `appointments` VALUES (1,1,5,5,'Not Sent','2021-01-18');
INSERT INTO `appointments` VALUES (2,1,3,4,'Not Sent','2021-01-20');
INSERT INTO `appointments` VALUES (3,4,2,1,'Not Sent','2021-01-17');
INSERT INTO `appointments` VALUES (4,4,3,3,'Not Sent','2021-01-16');
INSERT INTO `appointments` VALUES (5,8,2,2,'Not Sent','2021-01-19');

INSERT INTO `notifications` VALUES (1,1,'You Now Have an Appointment with Mohammed Mohey On 2021-01-18 Monday from 08:00:00 To 09:00:00 at Building 23.');
INSERT INTO `notifications` VALUES (2,5,'You Now Have an Appointment with Youssef Mohammed On 2021-01-18 Monday from 08:00:00 To 09:00:00 at Building 23.');
INSERT INTO `notifications` VALUES (3,1,'You Now Have an Appointment with Amr Samy On 2021-01-20 Wednesday from 11:00:00 To 12:30:00 at Online.');
INSERT INTO `notifications` VALUES (4,3,'You Now Have an Appointment with Youssef Mohammed On 2021-01-20 Wednesday from 11:00:00 To 12:30:00 at Online.');
INSERT INTO `notifications` VALUES (5,4,'You Now Have an Appointment with Omar Khaled On 2021-01-17 Sunday from 10:00:00 To 10:30:00 at New Building.');
INSERT INTO `notifications` VALUES (6,2,'You Now Have an Appointment with Abdullah Mohammed On 2021-01-17 Sunday from 10:00:00 To 10:30:00 at New Building.');
INSERT INTO `notifications` VALUES (7,4,'You Now Have an Appointment with Amr Samy On 2021-01-16 Saturday from 09:00:00 To 10:00:00 at Old Building.');
INSERT INTO `notifications` VALUES (8,3,'You Now Have an Appointment with Abdullah Mohammed On 2021-01-16 Saturday from 09:00:00 To 10:00:00 at Old Building.');
INSERT INTO `notifications` VALUES (9,8,'You Now Have an Appointment with Omar Khaled On 2021-01-19 Tuesday from 12:00:00 To 13:00:00 at Online.');
INSERT INTO `notifications` VALUES (10,2,'You Now Have an Appointment with Nabil Tharwat On 2021-01-19 Tuesday from 12:00:00 To 13:00:00 at Online.');
INSERT INTO `notifications` VALUES (11,5,'Youssef Mohammed Has Sent You a Message.');
INSERT INTO `notifications` VALUES (12,1,'Mohammed Mohey Has Sent You a Message.');
INSERT INTO `notifications` VALUES (13,4,'Omar Khaled Has Sent You a Message.');
INSERT INTO `notifications` VALUES (14,2,'Nabil Tharwat Has Sent You a Message.');
INSERT INTO `notifications` VALUES (15,8,'Omar Khaled Has Sent You a Message.');

INSERT INTO `messages` VALUES (1,1,5,'I\'d Like To Inform You That I have Reserved an Appointment With You.');
INSERT INTO `messages` VALUES (2,5,1,'Yes, I am Aware Of That, Thank You');
INSERT INTO `messages` VALUES (3,2,4,'Bring Your Laptop With You.');
INSERT INTO `messages` VALUES (4,8,2,'We Will Be Using Zoom or Discord ?');
INSERT INTO `messages` VALUES (5,2,8,'Zoom.');

INSERT INTO `history` VALUES (1,'2020-12-27',1,2,'10:00:00','10:30:00','New Building');
INSERT INTO `history` VALUES (2,'2021-01-02',1,3,'09:00:00','10:00:00','Old Building');
INSERT INTO `history` VALUES (3,'2020-12-29',4,5,'16:00:00','17:00:00','Online');
INSERT INTO `history` VALUES (4,'2021-01-13',8,2,'10:00:00','10:30:00','New Building');
INSERT INTO `history` VALUES (5,'2021-01-09',8,3,'09:00:00','10:00:00','Old Building');