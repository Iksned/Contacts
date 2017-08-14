CREATE OR REPLACE FUNCTION checkUser(login1 varchar,pass varchar) RETURNS bigint
AS $$
SELECT Count(Login)
FROM Users
Where Login = login1 AND Password = pass
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION countUsers() RETURNS bigint
AS $$ SELECT COUNT(*)
FROM Users
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION countUserContacts() RETURNS TABLE(Login text, contacts bigint)
AS $$ SELECT Login, COUNT(ContactID)
FROM UsersContact
GROUP BY Login
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION countUserGroups() RETURNS TABLE(Login text, groups bigint)
AS $$ SELECT Login, COUNT(GroupName)
FROM UsersGroup
GROUP BY Login
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION avgUsersInGroups() RETURNS numeric AS $$
WITH ContactINGroup AS (SELECT count(ContactGroup.ContactID) AS count,
          ContactGroup.GroupName AS Groups
         FROM ContactGroup
        GROUP BY ContactGroup.GroupName)
SELECT AVG(count)
FROM ContactINGroup
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION avgUserContacts() RETURNS numeric AS $$
WITH UserContacts AS (SELECT count(UsersContact.ContactID) AS count,
          UsersContact.Login AS Users
         FROM UsersContact
        GROUP BY UsersContact.Login)
SELECT AVG(count)
FROM UserContacts
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION inactiveUsers() RETURNS TABLE(Login text)
AS $$ WITH UserContacts AS (SELECT count(UsersContact.ContactID) AS count,
                UsersContact.Login AS Users
               FROM UsersContact
              GROUP BY UsersContact.Login)
      SELECT Users
      FROM UserContacts
      WHERE count < 10
$$
LANGUAGE SQL;



CREATE OR REPLACE FUNCTION getAllContacts(login1 text) RETURNS TABLE(id int,name varchar,phone_num int)
AS $$ SELECT *
FROM Contacts
WHERE Contacts.ContactID IN (SELECT ContactID as ContactID
                                FROM UsersContact
                                WHERE Login = login1)
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION getAllGroups(login1 text) RETURNS TABLE(groupname varchar)
AS $$ SELECT *
FROM Groups
WHERE Groups.Name IN (SELECT GroupName
                      FROM UsersGroup
                      WHERE Login = login1)
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION getContact(login1 varchar,cname varchar,ph_num int) RETURNS TABLE(id int, Name varchar,phone_num int)
AS $$ SELECT Contacts.ContactID,Contacts.name,Contacts.Phone_Number
FROM Contacts JOIN UsersContact ON Contacts.ContactID = UsersContact.ContactID
WHERE Contacts.name = cname AND Contacts.Phone_Number = ph_num AND UsersContact.Login = login1
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION getGroup(login1 varchar, gname varchar) RETURNS TABLE(groupname varchar)
AS $$ SELECT Groups.name
FROM Groups JOIN UsersGroup ON Groups.name = UsersGroup.GroupName
WHERE Groups.name = gname AND UsersGroup.Login = login1
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION  addContact(login1 varchar,cname varchar,num int,groupname varchar) RETURNS void AS
$$
BEGIN
IF (SELECT COUNT(Contacts.ContactID) AS Result
                  FROM Contacts JOIN UsersContact ON Contacts.ContactID = UsersContact.ContactID
                  WHERE Contacts.name = cname AND Contacts.Phone_Number = num AND UsersContact.Login = login1) = 0
THEN
INSERT INTO Contacts (ContactID,name,Phone_Number)
VALUES ((SELECT MAX(ContactID) FROM Contacts)+1,cname,num);
INSERT INTO UsersContact
VALUES (login1,(SELECT MAX(ContactID) FROM Contacts));
INSERT INTO ContactGroup
VALUES ((SELECT MAX(ContactID) FROM Contacts),groupname);
END IF;
END
$$
LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION  addGroup(login1 varchar,groupname1 varchar) RETURNS void AS
$$
BEGIN
IF (SELECT COUNT(Groups.name) AS Result
                  FROM Groups JOIN UsersGroup ON Groups.name = UsersGroup.GroupName
                  WHERE Groups.name = groupname1  AND UsersGroup.Login = login1) = 0
THEN
INSERT INTO Groups (name)
VALUES (groupname1);
INSERT INTO UsersGroup(Login,GroupName)
VALUES (login1,groupname1);
END IF;
END
$$
LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION  updateContact(cname varchar,num int,groupname1 varchar,oldcname varchar,oldnum int) RETURNS void AS
$$
DECLARE
ID int;
BEGIN

SELECT ContactID INTO ID
FROM contacts
WHERE name = oldcname AND Phone_Number = oldnum;

UPDATE Contacts
SET name = cname, Phone_Number = num
WHERE name = oldcname AND Phone_Number = oldnum;

UPDATE ContactGroup
SET GroupName = groupname1
WHERE ContactID = ID;
END
$$
LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION  updateGroup(groupname text,oldgroup text) RETURNS void AS
$$
BEGIN
UPDATE Groups
SET name = groupname
WHERE name = oldgroup;
END
$$
LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION  delContact(cname varchar,num int) RETURNS void AS
$$
BEGIN
DELETE FROM Contacts
WHERE name = cname AND Phone_Number = num;
END
$$
LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION  delGroup(groupname text) RETURNS void AS
$$
BEGIN
DELETE FROM Groups
WHERE name = groupname;
END
$$
LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION getNameByGroup(login1 varchar,groupname1 varchar) RETURNS TABLE(Name varchar)
AS
$$
SELECT Name
FROM Contacts
Where ContactID IN (Select ContactID FROM ContactGroup Where GroupName = groupname1)
AND ContactID IN (Select ContactID FROM UsersContact Where Login = login1)
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION getContactByName(login1 varchar,cname varchar) RETURNS TABLE(Name varchar,Phone_Number int,groupname varchar)
AS
$$
SELECT Name,Phone_Number,Groupname
FROM Contacts JOIN ContactGroup ON Contacts.ContactID = ContactGroup.ContactID
WHERE Contacts.ContactID IN (Select ContactID FROM UsersContact Where Login = login1)
AND Contacts.name = cname;
$$
LANGUAGE SQL;