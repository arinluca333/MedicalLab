DROP DATABASE IF EXISTS MEDICAL_LAB;
CREATE DATABASE MEDICAL_LAB;
USE MEDICAL_LAB;

CREATE TABLE IF NOT EXISTS CUSTOMER
(
customer_ID int(11) primary key unique auto_increment,
customer_name varchar(40),
customer_gender varchar(1),
customer_dayOfBirth date,
customer_joinDate date
);

CREATE TABLE IF NOT EXISTS ADDRESS
(
address_ID int (11) auto_increment unique primary key,
street varchar(30),
st_no int(11),
apartment int(11),
city varchar(30),
state_county varchar(30),
other varchar(200)
);

CREATE TABLE IF NOT EXISTS PHONE_NO
(
phone_id int(11) unique auto_increment primary key,
phone_no1 varchar(12),
phone_no2 varchar(12)
);

CREATE TABLE IF NOT EXISTS REQUEST
(
request_ID  int(11) unique auto_increment primary key,
customer_ID int(11) ,
request_date date,
procedure_date date,
procedure_type varchar(30),
request_status varchar(20),
request_cost float(10)
);

CREATE TABLE IF NOT EXISTS SAMPLE
(
sample_ID int(11) auto_increment unique primary key,
sample_status varchar(20),
sample_location varchar(20),
sample_type varchar(20)
);

CREATE TABLE IF NOT EXISTS RESULT
(
result_ID int(11) auto_increment unique primary key,
result_status varchar(20),
result_description varchar(300),
customer_history_ID int(11)
);

CREATE TABLE IF NOT EXISTS HISTORY
(
history_ID int(11) auto_increment unique primary key,
total_payed float(20),
no_of_procedures int(10)
); 

CREATE TABLE IF NOT EXISTS PAYMENT
(
payment_ID int(11) auto_increment unique primary key,
payment_balance float(10),
payment_status varchar(20)
);