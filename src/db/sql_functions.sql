CREATE OR REPLACE FUNCTION countUsers() RETURNS bigint
AS $$ SELECT COUNT(*)
FROM Users
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION countUserContacts(in text) RETURNS TABLE(f1 text, f2 bigint)
AS $$ SELECT $1, COUNT(*)
FROM Contacts
   WHERE Contacts.ContactID IN (SELECT ContactID
                                FROM Catalog
                                WHERE FKLogin = $1)

$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION countUserGroups(in text) RETURNS TABLE(f1 text, f2 bigint)
AS $$ SELECT $1, COUNT(*)
FROM Groups
   WHERE Groups.Name IN (SELECT FKGroup
                                FROM Catalog
                                WHERE FKLogin = $1)

$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION avgUsersInGroups() RETURNS numeric AS $$
WITH ContactINGroup AS (SELECT count(catalog.contactid) AS count,
          catalog.fkgroup AS Groups
         FROM catalog
        GROUP BY catalog.fkgroup)
SELECT AVG(count)
FROM ContactINGroup
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION avgUserContacts() RETURNS numeric AS $$
WITH UserContacts AS (SELECT count(catalog.contactid) AS count,
          catalog.fkLogin AS Users
         FROM catalog
        GROUP BY catalog.fkLogin)
SELECT AVG(count)
FROM UserContacts
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION inactiveUsers() RETURNS TABLE(f1 text)
AS $$ WITH UserContacts AS (SELECT count(catalog.contactid) AS count,
                catalog.fkLogin AS Users
               FROM catalog
              GROUP BY catalog.fkLogin)
      SELECT Users
      FROM UserContacts
      WHERE count < 10
$$
LANGUAGE SQL;



CREATE OR REPLACE FUNCTION selectAllContacts(in text) RETURNS TABLE(f1 int,f2 varchar,f3 int)
AS $$ SELECT *
FROM Contacts
WHERE Contacts.ContactID IN (SELECT ContactID
                                FROM Catalog
                                WHERE FKLogin = $1)
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION selectAllGroups(in text) RETURNS TABLE(f1 varchar)
AS $$ SELECT *
FROM Groups
WHERE Groups.Name IN (SELECT Name
                      FROM Catalog
                      WHERE FKLogin = $1)
$$
LANGUAGE SQL;