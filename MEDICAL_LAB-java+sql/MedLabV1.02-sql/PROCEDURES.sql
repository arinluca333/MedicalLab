
-- Adauga un client nou
delimiter //

CREATE PROCEDURE AddCustomer (IN fcustomer_name varchar(40), IN fcustomer_gender varchar(1), IN fcustomer_dayOfBirth date, 
								IN fcustomer_phone varchar(12), IN fstreet varchar(30),IN fst_no int(11), 
								IN fapartment int(11), IN fcity varchar(30), IN fstate_county varchar(30),
                                IN fother varchar(200), IN joinDate date)
BEGIN
	insert into ADDRESS(street, st_no, apartment, city, state_county, other) VALUES(fstreet, fst_no, fapartment,
						fcity, fstate_county, @fother);
    insert into PHONE_NO(phone_no1, phone_no2) VALUES(fcustomer_phone, '');
    insert into HISTORY(total_payed, no_of_procedures) VALUES(0, 0);
	insert into CUSTOMER( customer_name, customer_gender, customer_dayOfBirth, customer_joinDate) 
				VALUE(fcustomer_name, fcustomer_gender, fcustomer_dayOfBirth, joinDate);
END//

delimiter ;

CALL AddCustomer('Ionut Lungu', 'M', '1998-06-12', '0769253926', 'Sintezei', 12, 11, 'Craiova', 'Dolj', '', curdate());

-- Modifica un client deja existent
delimiter //

CREATE PROCEDURE EditCustomer (IN fcustomer_ID int(11), IN fcustomer_name varchar(40), IN fcustomer_gender varchar(1), IN fcustomer_dayOfBirth date)
BEGIN
	UPDATE CUSTOMER
	SET	customer_name = fcustomer_name, customer_gender = fcustomer_gender, customer_dayOfBirth = fcustomer_dayOfBirth
	WHERE customer_ID = fcustomer_ID;
END//

delimiter ;

CALL EditCustomer(5, 'Ionut Adrian Lungu', 'M', '1998-06-12');

DROP PROCEDURE IF EXISTS EditCustomerPhone;
-- Modifica nr-ul de telefon al unui client existent
delimiter //

CREATE PROCEDURE EditCustomerPhone(IN fcustomer_ID int (11), IN fcustomer_phone1 varchar(12), IN fcustomer_phone2 varchar(12))
BEGIN
	UPDATE PHONE_NO
    SET phone_no1 = fcustomer_phone1, phone_no2 = fcustomer_phone2
    WHERE phone_ID = fcustomer_ID;
END//

delimiter ;

CALL EditCustomerPhone(5,'0742343414','');

-- Modificarea adresei unui client existent
delimiter //

CREATE PROCEDURE EditCustomerAddress(IN fcustomer_ID int (11), IN fstreet varchar(30),IN fst_no int(11), 
								IN fapartment int(11), IN fcity varchar(30), IN fstate_county varchar(30),
                                IN fother varchar(200))
BEGIN
	UPDATE ADDRESS
    SET street = fstreet, st_no = fst_no, apartment = fapartment, city = fcity, state_county = fstate_county,
		other = fother
	WHERE address_ID = fcustomer_ID;
END //

delimiter ;

CALL EditCustomerAddress(5, 'Sintezei', 12, 11, 'Craiova', 'Dolj', 'etaj 3');


-- Se modifica istoricul pacientului o data cu inregistrarea unui request
delimiter //

CREATE PROCEDURE AddReqToHistory(IN fID int(11))
BEGIN
	UPDATE HISTORY
    SET HISTORY.no_of_procedures = HISTORY.no_of_procedures + 1
    WHERE HISTORY.history_ID = fID;
END//

delimiter ;

DROP PROCEDURE if exists AddRequest;

-- Se creaza un nou request din partea clientului
delimiter //

CREATE PROCEDURE AddRequest(IN fcustomer_ID int (11), proc_date date, IN proc_type varchar(30), 
							IN req_cost float(10))
BEGIN
	insert into RESULT(result_status, result_description, customer_history_ID) VALUES('in progress', '', fcustomer_ID);
    insert into SAMPLE(sample_status, sample_location, sample_type) VALUES('not extracted', '', '');
    insert into PAYMENT(payment_balance, payment_status) VALUES(0, 'unfulfilled');
    insert into REQUEST(customer_ID, request_date, procedure_date, procedure_type, request_status, request_cost)
				VALUES(fcustomer_ID, curdate(), proc_date, proc_type,
						'in progress', req_cost);
	CALL AddReqToHistory(fcustomer_ID);
END//

delimiter ;


CALL AddRequest(5, '2019-01-28', 'antiHD igM', 320.00);

-- Actualizarea istoricului 
delimiter //

CREATE PROCEDURE UpdatePay(IN fpayment_ID int(11))
BEGIN
	if ((SELECT PAYMENT.payment_balance FROM PAYMENT WHERE PAYMENT.payment_ID = fpayment_ID) >= 
								(SELECT REQUEST.request_cost FROM REQUEST WHERE REQUEST.request_ID = fpayment_ID))
		then UPDATE PAYMENT
			SET PAYMENT.payment_status = 'fulfilled'
            WHERE PAYMENT.payment_ID = fpayment_ID;
	end if;

END //

delimiter ;

-- Actualizarea istoricului 
delimiter //

CREATE PROCEDURE AddPayToHistory(IN fpayment_ID int(11), payed float(10))
BEGIN
	UPDATE HISTORY
    SET HISTORY.total_payed = HISTORY.total_payed + payed
    WHERE (SELECT REQUEST.customer_ID FROM REQUEST WHERE REQUEST.request_ID = fpayment_ID) = HISTORY.history_ID;
END//

delimiter ;

-- Plate unui payment, partiala sau totala
delimiter //

CREATE PROCEDURE Pay(IN fpayment_ID int(11), IN payed float(10))
BEGIN
	UPDATE PAYMENT
    SET PAYMENT.payment_balance = PAYMENT.payment_balance + payed
    WHERE PAYMENT.payment_ID = fpayment_ID;
    CALL UpdatePay(fpayment_ID);
    CALL AddPayToHistory(fpayment_ID, payed);
    CALL CheckRequest(fpayment_ID);
END//

delimiter ;





-- Modifica starea unui rezultat

delimiter //

CREATE PROCEDURE ModifyResult(IN fresult_ID int(11), IN fresult_status varchar(20),IN fresult_description varchar(300))

BEGIN
    UPDATE RESULT
    SET RESULT.result_status = fresult_status, RESULT.result_description = fresult_description
    WHERE RESULT.result_ID = fresult_ID;
    CALL CheckRequest(fresult_ID);
END//

delimiter ;

-- Modifica starea unei mostre

delimiter //

CREATE PROCEDURE ModifySample(IN fsample_ID int(11), IN fsample_status varchar(20),
								IN fsample_location varchar(20), IN fsample_type varchar(20))
BEGIN 
	UPDATE SAMPLE
    SET SAMPLE.sample_status = fsample_status, SAMPLE.sample_location = fsample_location, SAMPLE.sample_type = fsample_type
    WHERE SAMPLE.sample_ID = fsample_ID;
    CALL CheckRequest(fsample_ID);
END //

delimiter ;

-- Modifica starea unui request

delimiter //

CREATE PROCEDURE ModifyRequestStatus(IN frequest_ID int(11), IN frequest_status varchar(20))

BEGIN
	UPDATE REQUEST
    SET REQUEST.request_status = frequest_status
    WHERE REQUEST.request_ID = frequest_ID;
END //

delimiter ;                                
                                
-- Verifica starea unui request si decide daca trebuie finalizat sau nu

delimiter //

CREATE PROCEDURE CheckRequest(IN ID int(11))

BEGIN
	if((SELECT SAMPLE.sample_status FROM SAMPLE WHERE SAMPLE.sample_ID = ID) = 'extracted' and
		(SELECT RESULT.result_status FROM RESULT WHERE RESULT.result_ID = ID) = 'complete' and
        (SELECT PAYMENT.payment_status FROM PAYMENT WHERE PAYMENT.payment_ID = ID) = 'fulfilled')
	then CALL ModifyRequestStatus(ID, 'complete');
    end if;
END //

delimiter ;

CALL ModifyResult(4, 'complete', 'SIDA');
CALL ModifySample(4, 'extracted', 'LAB 1', 'blood');
CALL Pay(1, 320.0);


DROP PROCEDURE IF EXISTS DelCustomer;
delimiter //

CREATE PROCEDURE DelCustomer(IN ID int(11))

BEGIN
	DELETE
    FROM ADDRESS
	WHERE ADDRESS.address_ID = ID;
    
	DELETE
    FROM PHONE_NO
	WHERE PHONE_NO.phone_ID = ID;
    
    DELETE
    FROM HISTORY
	WHERE HISTORY.history_ID = ID;
    
    DELETE
    FROM CUSTOMER
	WHERE CUSTOMER.customer_ID = ID;


END //
delimiter ;


