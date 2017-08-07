CREATE TABLE Users (
    Login varchar(255),
    Password varchar(255) NOT NULL,
    PRIMARY KEY (Login)
);
CREATE TABLE Contacts (
    ContactID int PRIMARY KEY,
    Name varchar(255) NOT NULL,
    Phone_Number int NOT NULL
);
CREATE TABLE Groups (
     Name varchar(255) PRIMARY KEY
);
CREATE TABLE Catalog (
    FKLogin varchar(255) NOT NULL,
    ContactID int DEFAULT NULL,
    FKGroup varchar(255) DEFAULT NULL,
    FOREIGN KEY (FKLogin) REFERENCES Users(Login)
);

ALTER TABLE Catalog
ADD CONSTRAINT FK_ContactID
FOREIGN KEY (ContactID) REFERENCES Contacts(ContactID);

ALTER TABLE Catalog
ADD CONSTRAINT FK_GroupID
FOREIGN KEY (FKGroup) REFERENCES Groups(Name);

CREATE INDEX Contact_Name
ON Contacts (Name);

CREATE INDEX Group_Name
ON Groups (Name);

CREATE INDEX Log_Pass
ON Users (Login,Password)


