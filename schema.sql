DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Vendor;
DROP TABLE IF EXISTS Food_Items;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS Payment;
DROP TABLE IF EXISTS Cart;
CREATE TABLE User (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  email TEXT UNIQUE NOT NULL,
  firstname TEXT NOT NULL,
  lastname TEXT NOT NULL,
  password TEXT NOT NULL
);

CREATE TABLE Vendor (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT NOT NULL,
  address TEXT NOT NULL,
  phone_no TEXT NOT NULL
);

CREATE TABLE Food_Items (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT NOT NULL,
  price REAL NOT NULL,
  calories INTEGER NOT NULL
);

CREATE TABLE Orders (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  user_id INTEGER,
  status TEXT,
  total REAL,
  delivery_address TEXT,
  FOREIGN KEY(user_id) REFERENCES User(id)

);

CREATE TABLE Payment(
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  order_id INTEGER,
  card_num INTEGER NOT NULL,
  name_on_card TEXT NOT NULL,
  exp_date DATE NOT NULL,
  cvv INTEGER NOT NULL,
  FOREIGN KEY(order_id) REFERENCES Orders(id)
);
CREATE TABLE Cart(
  order_id INTEGER,
  item_id INTEGER,
  quantity INTEGER,
  FOREIGN KEY(order_id) REFERENCES Orders(id),
  FOREIGN KEY(item_id) REFERENCES Food_Items(id)
);

Insert into Vendor
values(110,'Chipotle','Redwood City','713-792-9390');

Insert into Food_Items
values (1, 'Burrito', 17.00, 400);

Insert into Food_Items
values (2, ' Burrito bowl Bowl', 12.00, 200);

Insert into Food_Items
values (3, 'Mexican pizza', 22.00, 200);

Insert into Food_Items
values (4, 'Salad', 8.00, 200); 
