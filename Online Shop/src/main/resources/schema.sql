create table Brands(
  brand_id bigint auto_increment primary key,
  name varchar(255),
  country varchar(255)
);

create table Categories(
    category_id bigint auto_increment primary key,
    name varchar(255)
);

create table Products(
    product_id bigint auto_increment primary key,
    title varchar(255),
    price int,
    category_id bigint,
    quantity int check (quantity >= 0),
    foreign key (category_id) references Categories(category_id) on delete CASCADE
);

create table Characteristics(
    characteristic_id bigint auto_increment primary key,
    battery_capacity int,
    color varchar(255),
    size varchar (255),
    brand_id bigint,
    product_id bigint,
    foreign key (brand_id) references Brands(brand_id) on delete CASCADE,
    foreign key (product_id) references Products(product_id) on delete CASCADE
);

create table Users (
    user_id bigint auto_increment PRIMARY KEY,
    username varchar(255),
    password varchar(255),
    role varchar(255)
);

create table Orders (
    order_id bigint auto_increment PRIMARY KEY,
    client_name varchar(255),
    phone varchar(255),
    email varchar(255),
    address varchar(255),
    order_date DATE
);

create table Orders_Items (
    id bigint auto_increment PRIMARY KEY,
    order_id bigint,
    product_id bigint,
    product_quantity int,
    price int,
    foreign key (order_id) references Orders(order_id) on delete CASCADE,
    foreign key (product_id) references Products(product_id) on delete CASCADE
);
