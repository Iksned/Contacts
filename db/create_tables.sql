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

CREATE TABLE UsersContact (
    Login varchar(255),
    ContactID int,
    FOREIGN KEY (Login) REFERENCES Users(Login),
    FOREIGN KEY (ContactID) REFERENCES Contacts(ContactID)
                            ON DELETE CASCADE
);

CREATE TABLE UsersGroup (
    Login varchar(255),
    GroupName varchar(255),
    FOREIGN KEY (Login) REFERENCES Users(Login),
    FOREIGN KEY (GroupName) REFERENCES Groups(Name)
                            ON DELETE CASCADE
                            ON UPDATE CASCADE
);

CREATE TABLE ContactGroup (
    ContactID int,
    GroupName varchar(255),
    FOREIGN KEY (ContactID) REFERENCES Contacts(ContactID)
                            ON DELETE CASCADE,
    FOREIGN KEY (GroupName) REFERENCES Groups(Name)
                            ON DELETE CASCADE
                            ON UPDATE CASCADE
);

CREATE INDEX Contact_Name
ON Contacts (Name);

CREATE INDEX Group_Name
ON Groups (Name);

CREATE INDEX Log_Pass
ON Users (Login,Password);

CREATE INDEX Users_ContactIn
ON UsersContact (Login,ContactID);

CREATE INDEX Users_GroupIn
ON UsersGroup (Login,GroupName);

CREATE INDEX Contact_GroupIn
ON ContactGroup (ContactID,GroupName);