CREATE OR REPLACE FUNCTION  addContact(user1 text,coname text,num int,groupname text) RETURNS void AS
$$
BEGIN
WITH conid AS (SELECT ContactID AS ID
FROM contacts
WHERE name = $2 AND Phone_Number = $3)
INSERT INTO Contacts (name,Phone_Number)
VALUES ($2,$3);
INSERT INTO Catalog
VALUES ($1,conid.ID,$4);
END
$$
LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION  addGroup(user1 text,groupname text) RETURNS void AS
$$
BEGIN
INSERT INTO Groups (name)
VALUES ($2);
INSERT INTO Catalog(FKLogin,FKGroup)
VALUES ($1,$2);
END
$$
LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION  updateContact(user1 text,coname text,num int,groupname text,oldname text,oldnum int) RETURNS void AS
$$
BEGIN
WITH conid AS (SELECT ContactID AS ID
FROM contacts
WHERE name = $5 AND Phone_Number = $6)
UPDATE Contacts
SET name = $2, Phone_Number = $3
WHERE ContactID = conid.ID;
UPDATE Catalog
SET FKLogin =$1,FKGroup = $4
WHERE ContactID = conid.ID;
END
$$
LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION  addGroup(user1 text,groupname text,oldgroup text) RETURNS void AS
$$
BEGIN
UPDATE Groups
SET name = $2
WHERE name = $3;
UPDATE Catalog
SET FKLogin =$1,FKGroup = $2
WHERE FKGroup = $3;
END
$$
LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION  delContact(user1 text,coname text,num int,groupname text) RETURNS void AS
$$
BEGIN
WITH conid AS (SELECT ContactID AS ID
FROM contacts
WHERE name = $2 AND Phone_Number = $3)
DELETE FROM Contacts
WHERE ContactID = conid.ID;
DELETE FROM Catalog
WHERE ContactID = conid.ID;
END
$$
LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION  delGroup(user1 text,groupname text) RETURNS void AS
$$
BEGIN
DELETE FROM Groups
WHERE name = $2;
DELETE FROM Catalog
WHERE FKLogin =$1 AND FKGroup = $2;
END
$$
LANGUAGE 'plpgsql';