INSERT INTO public.traveler (id, document, email, name, number_phone, prefix_phone, status)
VALUES(1,'', 'ana_maria@gmail.com', 'Ana Maria', '98888-1111', 11, 'A');
INSERT INTO public.traveler (id, document, email, name, number_phone, prefix_phone, status)
VALUES(2,'', 'joao_carlos@gmail.com', 'Joao Carlos', '98888-2222', 22, 'A');
INSERT INTO public.traveler (id, document, email, name, number_phone, prefix_phone, status)
VALUES(3,'', 'maria_silva@gmail.com', 'Maria da Silva', '98888-3333', 33, 'A');
INSERT INTO public.traveler (id, document, email, name, number_phone, prefix_phone, status)
VALUES(4,'', 'beatriz_lisboa@gmail.com', 'Beatriz Lisboa', '98888-4444', 44, 'I');

INSERT INTO public.booking(id, traveler_id,check_in, check_out, amount, adults, children, booking_status,payment_status, insert_date, last_update, rating, amount_pending, observation)
VALUES(10, 1, '2021-10-15 10:00', '2021-10-25 18:30', 1500.00, 5, 2, 'RESERVED', 'PENDING', '2021-10-05 12:40', null, null, 200.0, 'Primeira reserva');

INSERT INTO public.launch (id, booking_id, amount, schedule_date, payment_type, payment_status, payment_date)
VALUES (100, 10, 1000.00, '2021-09-10', 'PIX', 'PAID', '2021-09-10' );
INSERT INTO public.launch (id, booking_id, amount, schedule_date, payment_type, payment_status, payment_date)
VALUES (101, 10, 300.00, '2021-09-20', 'TRANSFER', 'PAID', '2021-09-20' );
INSERT INTO public.launch (id, booking_id, amount, schedule_date, payment_type, payment_status, payment_date)
VALUES (102, 10, 200.00, '2021-09-30', 'LOCAL', 'PENDING', null );