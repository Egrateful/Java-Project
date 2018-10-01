DROP DATABASE IF EXISTS resort;

CREATE DATABASE resort; /*!40100 DEFAULT CHARACTER SET utf8 */

USE resort;

DROP TABLE IF EXISTS customers;
CREATE TABLE  customers (
  id char(10) NOT NULL,
  name varchar(20) NOT NULL,
  gender char(1) NOT NULL,
  password varchar(20) NOT NULL,
  email varchar(45) NOT NULL,
  birthday date DEFAULT NULL,
  phone varchar(20) DEFAULT NULL,
  address varchar(100) DEFAULT NULL,
  married tinyint(1) DEFAULT '0',
  blood_type varchar(2) DEFAULT NULL,
  type varchar(10) DEFAULT NULL,
  discount int(10) unsigned NOT NULL DEFAULT '0',
  status int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO customers (id, name, gender, password, email) 
VALUES("A123456789", "張三", 'M', '123456', 'test@uuu.com.tw');

INSERT INTO customers
(id,name,gender,password,email,birthday,phone,address,married,blood_type,type,discount)
VALUES("A123456770", "王武", 'M', '123456', 'wang.five@gmail.com', '1980-3-5', '0225149191', '台北市復興北路5F', false, 'AB', null, 0);

INSERT INTO customers
(id,name,gender,password,email,birthday,phone,address,married,blood_type,type,discount)
VALUES("A223456781", "陳梅莉", 'F', '123456', 'mary.chen@gmail.com', '1977-8-25', '0225149191', '台北市復興北路15F', false, 'B', 'VIP', 20);

INSERT INTO customers
(id,name,gender,password,email,birthday,phone,address,married,blood_type,type,discount)
VALUES("D273620538", "李海倫", 'F', '123456', 'helen.lee@gmail.com', '1980-10-25', '0225149191', '台北市復興北路15F', false, 'B', 'VIP', 20);

INSERT INTO customers
(id,name,gender,password,email,birthday,phone,address,married,blood_type,type,discount)
VALUES("E238820859", "傅斯", 'M', '123456', 'mark.fu@gmail.com', '1980-10-25', '0225149191', '台北市復興北路15F', false, 'B', 'VIP', 20);


commit;

DROP TABLE IF EXISTS rooms;
CREATE TABLE  rooms (
  id char(3) NOT NULL,
  name varchar(100) NOT NULL,
  unit_price double NOT NULL DEFAULT '0',
  querydate date  NOT NULL,
  url varchar(100) DEFAULT NULL,
  type varchar(30) DEFAULT NULL,
  unoccupied tinyint(1) DEFAULT '1',
  discount int(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE rooms;

INSERT INTO rooms (id, name, unit_price, querydate, url, type, unoccupied, discount) 
	VALUES("A1", "書香之家", 12800, '2016-4-22',
    "http://www.bbnet.com.tw/yupeng/reserve/room_info.php?roomid=1&amp;hotelid=1", "Room", '1', 0);

INSERT INTO rooms (id, name, unit_price, querydate, url, type, unoccupied, discount) 
	VALUES("A2", "樂木之家", 10800, '2016-4-22',
    "http://www.bbnet.com.tw/yupeng/reserve/room_info.php?roomid=2&amp;hotelid=1", "Room", '1', 15);

INSERT INTO rooms (id, name, unit_price, querydate, url, type, unoccupied, discount) 
	VALUES("A3", "夏花之家", 9900, '2016-4-22',
    "http://www.bbnet.com.tw/yupeng/reserve/room_info.php?roomid=3&amp;hotelid=1", "Room", '1', 15);

INSERT INTO rooms (id, name, unit_price, querydate, url, type, unoccupied, discount)  
	VALUES("A4", "青石之家", 8800, '2016-4-22',
    "http://www.bbnet.com.tw/yupeng/reserve/room_info.php?roomid=4&amp;hotelid=1", "Room", '1', 15);

INSERT INTO rooms (id, name, unit_price, querydate, url, type, unoccupied, discount)  
	VALUES("A5", "禾竹之家", 7800, '2016-4-22',
    "http://www.bbnet.com.tw/yupeng/reserve/room_info.php?roomid=5&amp;hotelid=1", "Room", '1', 15);

INSERT INTO rooms (id, name, unit_price, querydate, url, type, unoccupied, discount) 
	VALUES("B1", "精緻二人房", 5800, '2016-4-22',
    "http://www.bbnet.com.tw/yupeng/reserve/room_info.php?roomid=6&amp;hotelid=1", "Promote", '1', 20);

INSERT INTO rooms (id, name, unit_price, querydate, url, type, unoccupied, discount) 
	VALUES("B2", "麗緻二人房", 4800, '2016-4-22',
    "http://www.bbnet.com.tw/yupeng/reserve/room_info.php?roomid=7&amp;hotelid=1", "Promote", '1', 20);

INSERT INTO rooms (id, name, unit_price, querydate, url, type, unoccupied, discount) 
	VALUES("B3", "美緻二人房", 5200, '2016-4-22',
    "http://www.bbnet.com.tw/yupeng/reserve/room_info.php?roomid=8&amp;hotelid=1", "Promote", '1', 20);

INSERT INTO rooms (id, name, unit_price, querydate, url, type, unoccupied, discount) 
	VALUES("B21", "清雅樓中樓四人房", 11800, '2016-4-22',
    "http://www.bbnet.com.tw/yupeng/reserve/room_info.php?roomid=9&amp;hotelid=1", "Promote", '1', 25);
	
INSERT INTO rooms (id, name, unit_price, querydate, url, type, unoccupied, discount) 
	VALUES("B22", "奢華樓中樓六人房", 15800, '2016-4-22',
    "http://www.bbnet.com.tw/yupeng/reserve/room_info.php?roomid=9&amp;hotelid=1", "Promote", '1', 25);


commit;

DROP TABLE IF EXISTS orders;
CREATE TABLE  orders (
    id int(1) unsigned NOT NULL AUTO_INCREMENT,
    customer_id char(1) NOT NULL,
    order_date datetime NOT NULL,
--     payment_type int(10) unsigned NOT NULL,
--   payment_fee double NOT NULL DEFAULT '0',
--   payment_note varchar(40) DEFAULT NULL,
--   shipping_type int(10) unsigned NOT NULL,
--   shipping_fee double NOT NULL DEFAULT '0',
--   shipping_note varchar(40) DEFAULT NULL,
--   receiver_name varchar(20) NOT NULL,
--   receiver_email varchar(45) NOT NULL,
--   receiver_phone varchar(20) NOT NULL,
--   shipping_address varchar(100) NOT NULL,
  status int(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (id),
  KEY FK_orders_customers (customer_id),
  CONSTRAINT FK_orders_customers FOREIGN KEY (customer_id) REFERENCES customers (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


commit;

DROP TABLE IF EXISTS order_items;
CREATE TABLE  order_items (
  order_id int(1) unsigned NOT NULL,
  room_id int(1) unsigned NOT NULL,
  price double NOT NULL,
  discount int(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (order_id, room_id)
--   FOREIGN KEY (order_id) REFERENCES orders (order_id),
--   FOREIGN KEY (room_id) REFERENCES rooms (room_id)
--   CONSTRAINT FK_order_items_orders FOREIGN KEY (order_id) REFERENCES orders (order_id),
--   CONSTRAINT FK_order_items_rooms FOREIGN KEY (room_id) REFERENCES rooms (room_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- DROP TABLE IF EXISTS order_items;
-- CREATE TABLE  order_items (
--   order_id int(1) unsigned NOT NULL,
--   room_id int(1) unsigned NOT NULL,
--   price double NOT NULL,
--   discount int(1) unsigned NOT NULL DEFAULT '0',
--     PRIMARY KEY (order_id, room_id),
-- --   quantity int(10) unsigned NOT NULL,
-- --   PRIMARY KEY (order_id,product_id),
-- --   KEY FK_order_items_products (product_id),
--   CONSTRAINT FK_order_items_orders FOREIGN KEY (order_id) REFERENCES orders (id),
-- --   CONSTRAINT FK_order_items_products FOREIGN KEY (product_id) REFERENCES products (id)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- DROP TABLE IF EXISTS order_logs;
-- CREATE TABLE  order_logs (
--   order_id int(10) unsigned NOT NULL,
--   log_time datetime NOT NULL,
--   note varchar(100) NOT NULL,
--   KEY FK_order_logs_orders (order_id),
--   CONSTRAINT FK_order_logs_orders FOREIGN KEY (order_id) REFERENCES orders (id)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8;