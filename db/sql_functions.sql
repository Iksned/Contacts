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
AS $$ SELECT Login, COUNT(UsersContact.ContactID)
FROM UsersContact
GROUP BY Login
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION countUserGroups() RETURNS TABLE(Login text, groups bigint)
AS $$ SELECT Login, COUNT(GroupId)
FROM UsersGroup
GROUP BY Login
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION avgUsersInGroups() RETURNS numeric AS $$
WITH ContactINGroup AS (SELECT count(ContactGroup.ContactID) AS count1,
          ContactGroup.GroupId AS Groups
         FROM ContactGroup
        GROUP BY ContactGroup.GroupId)
SELECT AVG(count1)
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
WHERE Contacts.ContactID IN (SELECT UsersContact.ContactID as ContactID
                                FROM UsersContact
                                WHERE Login = login1)
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION getAllGroups(login1 text) RETURNS TABLE(groupid int,groupname varchar)
AS $$ SELECT *
FROM Groups
WHERE Groups.GroupId IN (SELECT GroupId
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

CREATE OR REPLACE FUNCTION getGroup(login1 varchar, gid int) RETURNS TABLE(groupid int,groupname varchar)
AS $$ SELECT Groups.GroupId,Groups.name
FROM Groups JOIN UsersGroup ON Groups.GroupId = UsersGroup.GroupId
WHERE Groups.GroupId = gid AND UsersGroup.Login = login1
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION  addContact(login1 varchar,cname varchar,num int,gid int) RETURNS void AS
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
VALUES ((SELECT MAX(ContactID) FROM Contacts),gid);
END IF;
END
$$
LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION  addGroup(login1 varchar,groupname1 varchar) RETURNS void AS
$$
BEGIN
IF (SELECT COUNT(Groups.name) AS Result
                  FROM Groups JOIN UsersGroup ON Groups.GroupId = UsersGroup.GroupId
                  WHERE Groups.name = groupname1  AND UsersGroup.Login = login1) = 0
THEN
INSERT INTO Groups (GroupId,name)
VALUES ((SELECT MAX(GroupId) FROM Groups)+1,groupname1);
INSERT INTO UsersGroup(Login,GroupId)
VALUES (login1,(SELECT MAX(GroupId) FROM Groups));
END IF;
END
$$
LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION  updateContact(cname varchar,num int,gid int,oldcname varchar,oldnum int) RETURNS void AS
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
SET GroupId = gid
WHERE ContactID = ID;
END
$$
LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION  updateGroup(gid int,groupname text) RETURNS void AS
$$
BEGIN
UPDATE Groups
SET name = groupname
WHERE GroupId = gid;
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

CREATE OR REPLACE FUNCTION  delGroup(gid int) RETURNS void AS
$$
BEGIN
DELETE FROM Groups
WHERE GroupId = gid;
END
$$
LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION getNameByGroup(login1 varchar,gid int) RETURNS TABLE(Name varchar)
AS
$$
SELECT Name
FROM Contacts
Where Contacts.ContactID IN (Select ContactGroup.ContactID FROM ContactGroup Where GroupId = gid)
AND Contacts.ContactID IN (Select UsersContact.ContactID FROM UsersContact Where Login = login1)
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION getContactByName(login1 varchar,cname varchar) RETURNS TABLE(id int,Name varchar,Phone_Number int,gid int)
AS
$$
SELECT Contacts.Contactid,Name,Phone_Number,GroupId
FROM Contacts JOIN ContactGroup ON Contacts.ContactID = ContactGroup.ContactID
WHERE Contacts.ContactID IN (Select UsersContact.ContactID FROM UsersContact Where Login = login1)
AND Contacts.name = cname;
$$
LANGUAGE SQL;