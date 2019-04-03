SELECT  CUSTOMER.customer_name
FROM CUSTOMER
ORDER BY CUSTOMER.customer_name;

SELECT CUSTOMER.customer_name, REQUEST.request_ID, REQUEST.procedure_type
FROM CUSTOMER, REQUEST
WHERE REQUEST.customer_ID = CUSTOMER.customer_ID;

SELECT REQUEST.*, PAYMENT.payment_status, PAYMENT.payment_balance
FROM CUSTOMER, REQUEST, PAYMENT
WHERE REQUEST.customer_ID = CUSTOMER.customer_ID and CUSTOMER.customer_name = 'Adrian Nastase' and payment_id = request_id;

SELECT CUSTOMER.customer_name, MAX(HISTORY.total_payed)
FROM HISTORY, CUSTOMER;

SELECT ADDRESS.city, count(*) as no_of_clients
FROM ADDRESS
GROUP BY city;

SELECT CUSTOMER.customer_name, HISTORY.total_payed, SUM(REQUEST.request_cost) as debt, REQUEST.request_status
FROM CUSTOMER,HISTORY,PAYMENT,REQUEST
WHERE CUSTOMER.customer_ID = HISTORY.history_ID and REQUEST.customer_ID = CUSTOMER.customer_ID and PAYMENT.payment_id = REQUEST.request_ID
GROUP BY customer_name;

SELECT CUSTOMER.customer_name, HISTORY.total_payed, SUM(REQUEST.request_cost) as debt, REQUEST.request_status
FROM CUSTOMER,HISTORY,PAYMENT,REQUEST
WHERE CUSTOMER.customer_ID = HISTORY.history_ID and REQUEST.customer_ID = CUSTOMER.customer_ID and PAYMENT.payment_id = REQUEST.request_ID and (PAYMENT.payment_status = 'unfulfilled')
GROUP BY customer_name;

