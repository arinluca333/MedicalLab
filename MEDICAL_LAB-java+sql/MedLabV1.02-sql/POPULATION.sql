insert into ADDRESS(street, st_no, apartment, city, state_county, other) VALUES('Grigorescu', 12, 34, 'Oradea', 'Bihor', 'sc C, etaj 2');
insert into ADDRESS(street, st_no, apartment, city, state_county, other) VALUES('Balcescu', 17, 11, 'Sibiu', 'Sibiu', 'sc 2, etaj 7');
insert into ADDRESS(street, st_no, apartment, city, state_county, other) VALUES('Nicolae Iorga', 2, 0, 'Cluj-Napoca', 'Cluj', '');
insert into ADDRESS(street, st_no, apartment, city, state_county, other) VALUES('Dunarii', '7', 0, 'Bucuresti', 'Bucuresti', '');

insert into PHONE_NO(phone_no1, phone_no2) VALUES('0749294821', '');
insert into PHONE_NO(phone_no1, phone_no2) VALUES('0749294822', '');
insert into PHONE_NO(phone_no1, phone_no2) VALUES('0749294823', '');
insert into PHONE_NO(phone_no1, phone_no2) VALUES('0749294824', '');

insert into HISTORY(total_payed, no_of_procedures) VALUES(25, 2);
insert into CUSTOMER( customer_name, customer_gender, customer_dayOfBirth, customer_joinDate) VALUE('Iuliu Maniu', 'M', '1890-03-22', curdate());
insert into HISTORY(total_payed, no_of_procedures) VALUES(150, 1);
insert into CUSTOMER( customer_name, customer_gender, customer_dayOfBirth, customer_joinDate) VALUE('Andrian Nastase', 'M', '1960-02-12', curdate());
insert into HISTORY(total_payed, no_of_procedures) VALUES(250, 1);
insert into CUSTOMER( customer_name, customer_gender, customer_dayOfBirth, customer_joinDate) VALUE('Ion Iliescu', 'M', '1000-06-02', curdate());
insert into HISTORY(total_payed, no_of_procedures) VALUES(0, 0);
insert into CUSTOMER( customer_name, customer_gender, customer_dayOfBirth, customer_joinDate) VALUE('Nicolae Ceausescu', 'M', '1903-03-17', curdate());

insert into RESULT() VALUES(1, 'in progress', '', 1);
insert into RESULT() VALUES(2, 'complete', 'all fuctions normal', 1);
insert into RESULT() VALUES(3, 'complete', 'liver failure', 2);
insert into RESULT() VALUES(4, 'in progress', '', 3);

insert into SAMPLE() VALUES(1, 'not extracted', '', '');
insert into SAMPLE() VALUES(2, 'extracted', 'LAB_4', 'blood');
insert into SAMPLE() VALUES(3, 'extracted', 'CLINIC_1', 'tissue');
insert into SAMPLE() VALUES(4, 'not extracted', '', '');

insert into PAYMENT() VALUES(1, 0, 'unfulfilled');
insert into PAYMENT() VALUES(2, 25, 'unfulfilled');
insert into PAYMENT() VALUES(3, 150, 'fulfilled');
insert into PAYMENT() VALUES(4, 250, 'fulfilled');

insert into REQUEST() VALUES(1, 1, curdate() , '2019-01-12', 'antiHD igM', 'in progress', 320.00); 
insert into REQUEST() VALUES(2, 1, curdate() , '2019-02-02', 'anti-centromer', 'in progress', 99.99); 
insert into REQUEST() VALUES(3, 2, curdate() , '2019-01-22', 'anti-U3 RNP', 'complete', 150.00); 
insert into REQUEST() VALUES(4, 3, curdate() , '2019-01-13', 'anti-LKM1', 'in progress', 250.00); 
