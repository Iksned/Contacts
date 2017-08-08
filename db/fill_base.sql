INSERT INTO Users
VALUES ('User1','Password1');
INSERT INTO Users
VALUES ('User2','Password2');
INSERT INTO Users
VALUES ('User3','Password3');
INSERT INTO Users
VALUES ('User4','Password4');
INSERT INTO Users
VALUES ('User5','Password5');
INSERT INTO Users
VALUES ('User6','Password6');

INSERT INTO Contacts (ContactID,Name,Phone_Number)
VALUES ('1','ConName1','123456');
INSERT INTO Contacts (ContactID,Name,Phone_Number)
VALUES ('2','ConName2','123452');
INSERT INTO Contacts (ContactID,Name,Phone_Number)
VALUES ('3','ConName3','123451');
INSERT INTO Contacts (ContactID,Name,Phone_Number)
VALUES ('4','ConName4','123458');
INSERT INTO Contacts (ContactID,Name,Phone_Number)
VALUES ('5','ConName5','123454');
INSERT INTO Contacts (ContactID,Name,Phone_Number)
VALUES ('6','ConName6','1234512');
INSERT INTO Contacts (ContactID,Name,Phone_Number)
VALUES ('7','ConName7','1235512');


INSERT INTO Groups (Name)
VALUES ('Group1');
INSERT INTO Groups (Name)
VALUES ('Group2');
INSERT INTO Groups (Name)
VALUES ('Group3');
INSERT INTO Groups (Name)
VALUES ('Group4');
INSERT INTO Groups (Name)
VALUES ('Group5');
INSERT INTO Groups (Name)
VALUES ('Group6');

INSERT INTO UsersContact (Login,ContactID)
VALUES ('User1','1');
INSERT INTO UsersContact (Login,ContactID)
VALUES ('User1','2');
INSERT INTO UsersContact (Login,ContactID)
VALUES ('User2','3');
INSERT INTO UsersContact (Login,ContactID)
VALUES ('User3','5');
INSERT INTO UsersContact (Login,ContactID)
VALUES ('User4','4');
INSERT INTO UsersContact (Login,ContactID)
VALUES ('User5','6');

INSERT INTO UsersGroup (Login,GroupName)
VALUES ('User1','Group1');
INSERT INTO UsersGroup (Login,GroupName)
VALUES ('User2','Group2');
INSERT INTO UsersGroup (Login,GroupName)
VALUES ('User2','Group3');
INSERT INTO UsersGroup (Login,GroupName)
VALUES ('User3','Group4');
INSERT INTO UsersGroup (Login,GroupName)
VALUES ('User4','Group5');
INSERT INTO UsersGroup (Login,GroupName)
VALUES ('User5','Group6');

INSERT INTO ContactGroup(ContactID,GroupName)
VALUES ('1','Group6');
INSERT INTO ContactGroup(ContactID,GroupName)
VALUES ('2','Group5');
INSERT INTO ContactGroup(ContactID,GroupName)
VALUES ('3','Group2');
INSERT INTO ContactGroup(ContactID,GroupName)
VALUES ('4','Group3');
INSERT INTO ContactGroup(ContactID,GroupName)
VALUES ('5','Group2');
INSERT INTO ContactGroup(ContactID,GroupName)
VALUES ('6','Group1');










