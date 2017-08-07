CREATE TABLE Users (
    PersonID int,
    Login varchar(255) NOT NULL UNIQUE,
    Password varchar(255) NOT NULL,
    PRIMARY KEY (PersonID)
);
CREATE TABLE Catalog (
    CatalogID int PRIMARY KEY,
    PersonID int,
    FOREIGN KEY (PersonID) REFERENCES Users(PersonID)
);
CREATE TABLE Contacts (
    ContactID int PRIMARY KEY,
    Name varchar(255) NOT NULL,
    Phone_Number int NOT NULL,
    CatalogID int,
    FOREIGN KEY (CatalogID) REFERENCES Catalog(CatalogID)
);
CREATE TABLE Groups (
    GroupID int PRIMARY KEY,
    Name varchar(255) NOT NULL,
    CatalogID int,
    FOREIGN KEY (CatalogID) REFERENCES Catalog(CatalogID)
);
CREATE TABLE Contact_Groups (
    ContactID int,
    GroupID int
);

ALTER TABLE Contact_Groups
ADD CONSTRAINT FK_ContactID
FOREIGN KEY (ContactID) REFERENCES Contacts(ContactID);

ALTER TABLE Contact_Groups
ADD CONSTRAINT FK_GroupID
FOREIGN KEY (GroupID) REFERENCES Groups(GroupID);

CREATE INDEX Contact_Name
ON Contacts (Name);

CREATE INDEX Group_Name
ON Groups (Name);

CREATE INDEX Log_Pass
ON Users (Login,Password)
