CREATE OR REPLACE FUNCTION countUsers() RETURNS bigint
AS $$ SELECT COUNT(*)
FROM Users
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION countUserContacts() RETURNS TABLE(f1 text, f2 bigint)
AS $$ SELECT Login, COUNT(ContactID)
FROM UsersContact
GROUP BY Login
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION countUserGroups() RETURNS TABLE(f1 text, f2 bigint)
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

CREATE OR REPLACE FUNCTION inactiveUsers() RETURNS TABLE(f1 text)
AS $$ WITH UserContacts AS (SELECT count(UsersContact.ContactID) AS count,
                UsersContact.Login AS Users
               FROM UsersContact
              GROUP BY UsersContact.Login)
      SELECT Users
      FROM UserContacts
      WHERE count < 10
$$
LANGUAGE SQL;



CREATE OR REPLACE FUNCTION getAllContacts(login text) RETURNS TABLE(f1 int,f2 varchar,f3 int)
AS $$ SELECT *
FROM Contacts
WHERE Contacts.ContactID IN (SELECT ContactID
                                FROM UsersContact
                                WHERE Login = login)
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION getAllGroups(login text) RETURNS TABLE(f1 varchar)
AS $$ SELECT *
FROM Groups
WHERE Groups.Name IN (SELECT GroupName
                      FROM UsersGroup
                      WHERE Login = login)
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION getContact(login varchar,cname varchar,ph_num int) RETURNS TABLE(f1 int, f2 varchar,f3 int)
AS $$ SELECT Contacts.ContactID,Contacts.name,Contacts.Phone_Number
FROM Contacts JOIN UsersContact ON Contacts.ContactID = UsersContact.ContactID
WHERE Contacts.name = cname AND Contacts.Phone_Number = ph_num AND UsersContact.Login = login
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION getGroup(login varchar, gname varchar) RETURNS TABLE(f1 varchar)
AS $$ SELECT Groups.name
FROM Groups JOIN UsersGroup ON Groups.name = UsersGroup.GroupName
WHERE Groups.name = gname AND UsersGroup.Login = login
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION  addContact(login varchar,cname text,num int,groupname text) RETURNS void AS
$$
BEGIN

IF (SELECT COUNT(Contacts.ContactID) AS Result
                  FROM Contacts JOIN UsersContact ON Contacts.ContactID = UsersContact.ContactID
                  WHERE Contacts.name = cname AND Contacts.Phone_Number = num AND UsersContact.Login = login) = 0
THEN
INSERT INTO Contacts (name,Phone_Number)
VALUES (cname,num);
WITH ContactID AS (SELECT contacts.ContactID AS ID
               FROM Contacts
               WHERE Contacts.name = cname AND Contacts.Phone_Number = num)
INSERT INTO UsersContact
VALUES (login,ID);
INSERT INTO ContactGroup
VALUES (ID,groupname);
END IF;
END
$$
LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION  addGroup(login text,groupname text) RETURNS void AS
$$
BEGIN
IF (SELECT COUNT(Groups.name) AS Result
                  FROM Groups JOIN UsersGroup ON Groups.name = UsersGroup.GroupName
                  WHERE Groups.name = groupname  AND UsersGroup.Login = login) = 0
THEN
INSERT INTO Groups (name)
VALUES (groupname);
INSERT INTO UsersGroup(Login,GroupName)
VALUES (login,groupname);
END IF;
END
$$
LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION  updateContact(cname text,num int,groupname text,oldcname text,oldnum int) RETURNS void AS
$$
DECLARE
ID int;
BEGIN

UPDATE Contacts
SET name = cname, Phone_Number = num
WHERE Contats.name = oldcname AND Contacts.Phone_Number = oldnum;

SELECT ContactID INTO ID
FROM contacts
WHERE name = cname AND Phone_Number = num;

UPDATE ContactGroup
SET GroupName = groupname
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