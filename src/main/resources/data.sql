--Populate SEGMENT table
INSERT INTO Segment (id, credit_modifier) VALUES (1, 100);
INSERT INTO Segment (id, credit_modifier) VALUES (2, 300);
INSERT INTO Segment (id, credit_modifier) VALUES (3, 1000);

--Populate USER table
INSERT INTO "user" (id, name, surname, segment_id) VALUES (49002010965, 'Adam', 'Lambert', 1);
INSERT INTO "user" (id, name, surname, segment_id) VALUES (49002010976, 'Ben', 'Elf', 1);
INSERT INTO "user" (id, name, surname, segment_id) VALUES (49002010987, 'Cinderella', 'Tale', 2);
INSERT INTO "user" (id, name, surname, segment_id) VALUES (49002010998, 'Dana', 'Scully', 3);

--Populate USER_DEBT table
INSERT INTO User_Debt (user_id, start_date, end_date, amount) VALUES (49002010965, '2023-01-27', '2023-08-15', 777);
INSERT INTO User_Debt (user_id, start_date, end_date, amount) VALUES (49002010965, '2023-10-02', '2044-10-01', 100500);
INSERT INTO User_Debt (user_id, start_date, end_date, amount) VALUES (49002010976, '2022-05-09', '2023-05-08', 30.56);
INSERT INTO User_Debt (user_id, start_date, end_date, amount) VALUES (49002010987, '2017-05-09', '2018-05-08', 658);
INSERT INTO User_Debt (user_id, start_date, end_date, amount) VALUES (49002010998, '2012-05-09', '2013-05-08', 12544.99);
