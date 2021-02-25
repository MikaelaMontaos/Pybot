DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Vendor;

CREATE TABLE User (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  username TEXT UNIQUE NOT NULL,
  password TEXT NOT NULL,
  firstname TEXT NOT NULL,
  lastname TEXT NOT NULL,
  email TEXT NOT NULL
);

CREATE TABLE Vendor (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT NOT NULL,
  address TEXT NOT NULL,
  phone_no TEXT NOT NULL
);

Insert into Vendor
values(110,'Chipotle','Redwood City','713-792-9390');

Insert into Vendor
values(111,'McDonald','Mountain View','713-668-5882');

Insert into Vendor
values(222,'Panda Express','Mountain View','713-668-7898');

Insert into Vendor
values(333,'Burger King','San Jose','713-989-1111');
