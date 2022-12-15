INSERT INTO public.traveler (id, document, email, name, number_phone, prefix_phone, status,insert_date)
VALUES(1,'', 'ana_maria@gmail.com', 'Ana Maria', '98888-1111', 11, 'A','2021-01-05 12:00');
INSERT INTO public.traveler (id, document, email, name, number_phone, prefix_phone, status,insert_date)
VALUES(2,'', 'bianca_silva@gmail.com', 'Bianca Silva', '98888-2222', 22, 'A','2021-02-05 12:10');
INSERT INTO public.traveler (id, document, email, name, number_phone, prefix_phone, status,insert_date)
VALUES(3,'', 'carlos_garcia@gmail.com', 'Carlos Garcia', '98888-3333', 33, 'A','2021-03-05 12:20');
INSERT INTO public.traveler (id, document, email, name, number_phone, prefix_phone, status,insert_date)
VALUES(4,'', 'david_souza@gmail.com', 'David Souza', '98888-4444', 44, 'A','2021-04-05 12:30');
INSERT INTO public.traveler (id, document, email, name, number_phone, prefix_phone, status,insert_date)
VALUES(5,'', 'elaine_matos@gmail.com', 'Elaine Matos', '98888-5555', 55, 'I','2021-05-05 12:30');
INSERT INTO public.traveler (id, document, email, name, number_phone, prefix_phone, status,insert_date)
VALUES(6,'', 'fernando_oliveira@gmail.com', 'Fernando Oliveira', '98888-6666', 66, 'A','2021-05-05 12:30');


INSERT INTO public.booking(id, traveler_id,check_in, check_out, amount, adults, children, booking_status,payment_status, insert_date, last_update, rating, amount_pending, observation, contract_type, amount_paid)
VALUES(10, 1, '2020-12-15 10:00', '2020-12-30 18:00', 1000.00, 3, 2, 'FINISHED', 'PAID', '2020-11-05 12:40', '2020-12-31 20:00', null, 0.0, 'Primeira reserva', 'DIRECT', 1000.0);

INSERT INTO public.booking(id, traveler_id,check_in, check_out, amount, adults, children, booking_status,payment_status, insert_date, last_update, rating, amount_pending, observation, contract_type, amount_paid)
VALUES(20, 2, '2021-01-01 10:00', '2021-01-15 18:00', 1500.00, 4, 3, 'RESERVED', 'PENDING', '2021-01-01 12:40', null, null, 1000.0, 'Segunda reserva', 'DIRECT', 500.0);


INSERT INTO public.booking(id, traveler_id,check_in, check_out, amount, adults, children, booking_status,payment_status, insert_date, last_update, rating, amount_pending, observation, contract_type, amount_paid)
VALUES(30, 3, '2021-01-16 10:00', '2021-01-30 18:00', 2000.00, 3, 2, 'PRE_RESERVED', 'PENDING', '2021-01-05 12:40', '2021-01-06 12:40', null, 2000.0, 'Terceira reserva', 'DIRECT', 0.0);


INSERT INTO public.booking(id, traveler_id,check_in, check_out, amount, adults, children, booking_status,payment_status, insert_date, last_update, rating, amount_pending, observation, contract_type, amount_paid, website_service_fee)
VALUES(40, 4, '2021-02-01 10:00', '2021-02-15 18:00', 2500.00, 6, 1, 'RESERVED', 'TO_RECEIVE', '2021-01-25 12:40', '2021-01-26 12:40', null, 0.0, 'Quarta reserva', 'SITE', 2500.0, 300.0);


INSERT INTO public.booking(id, traveler_id,check_in, check_out, amount, adults, children, booking_status,payment_status, insert_date, last_update, rating, amount_pending, observation, contract_type, amount_paid)
VALUES(50, 5, '2021-02-20 10:00', '2021-02-25 18:00', 3000.00, 2, 0, 'CANCELED', 'CANCELED', '2021-02-10 12:40', '2021-02-11 12:40', null, 3000.0, 'Quinta reserva', 'DIRECT', 0.0);

INSERT INTO public.booking(id, traveler_id,check_in, check_out, amount, adults, children, booking_status,payment_status, insert_date, last_update, rating, amount_pending, observation, contract_type, amount_paid)
VALUES(60, 6, '2021-03-15 10:00', '2021-03-30 18:00', 4000.00, 2, 0, 'RESERVED', 'PAID', '2021-02-10 12:40', '2021-02-11 12:40', null, 4000.0, 'Sexta reserva', 'DIRECT', 4000.0);


INSERT INTO public.launch (id, booking_id, amount, schedule_date, payment_type, payment_status, payment_date)
VALUES (100, 10, 500.00, '2020-12-01', 'PIX', 'PAID', '2020-12-02' );
INSERT INTO public.launch (id, booking_id, amount, schedule_date, payment_type, payment_status, payment_date)
VALUES (101, 10, 250.00, '2020-12-05', 'PIX', 'PAID', '2020-12-06' );
INSERT INTO public.launch (id, booking_id, amount, schedule_date, payment_type, payment_status, payment_date)
VALUES (102, 10, 250.00, '2020-12-08', 'PIX', 'PAID', '2020-12-09' );

INSERT INTO public.launch (id, booking_id, amount, schedule_date, payment_type, payment_status, payment_date)
VALUES (103, 20, 300.00, '2021-01-01', 'PIX', 'PAID', '2021-01-02' );
INSERT INTO public.launch (id, booking_id, amount, schedule_date, payment_type, payment_status, payment_date)
VALUES (104, 20, 200.00, '2021-01-05', 'PIX', 'PAID', '2021-01-06' );
INSERT INTO public.launch (id, booking_id, amount, schedule_date, payment_type, payment_status, payment_date)
VALUES (105, 20, 1000.00, '2021-01-08', 'PIX', 'PENDING', null );

INSERT INTO public.launch (id, booking_id, amount, schedule_date, payment_type, payment_status, payment_date)
VALUES (106, 30, 1200.00, '2021-01-12', 'PIX', 'PENDING', null );
INSERT INTO public.launch (id, booking_id, amount, schedule_date, payment_type, payment_status, payment_date)
VALUES (107, 30, 800.00, '2021-01-16', 'PIX', 'PENDING', null );

INSERT INTO public.launch (id, booking_id, amount, schedule_date, payment_type, payment_status, payment_date)
VALUES (108, 40, 2500.00, '2021-02-01', 'SITE', 'TO_RECEIVE', null );

INSERT INTO public.launch (id, booking_id, amount, schedule_date, payment_type, payment_status, payment_date)
VALUES (109, 50, 3000.00, '2021-02-15', 'DEPOSIT', 'CANCELED', null );

INSERT INTO public.launch (id, booking_id, amount, schedule_date, payment_type, payment_status, payment_date)
VALUES (110, 60, 2000.00, '2021-03-01', 'PIX', 'PAID', '2021-03-02' );

INSERT INTO public.launch (id, booking_id, amount, schedule_date, payment_type, payment_status, payment_date)
VALUES (111, 60, 2000.00, '2021-03-05', 'PIX', 'PAID', '2021-03-06' );