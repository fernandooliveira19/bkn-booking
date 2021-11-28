CREATE TABLE public.traveler (
	id bigserial NOT NULL,
	"document" varchar(255) NULL,
	email varchar(255) NOT NULL,
	name varchar(255) NOT NULL,
	number_phone varchar(255) NOT NULL,
	prefix_phone int4 NOT NULL,
	status varchar(255) NOT NULL,
	CONSTRAINT traveler_pkey PRIMARY KEY (id)
);

INSERT INTO public.traveler (id, "document", email, "name", number_phone, prefix_phone, status)
VALUES(1,'', 'fernando@gmail.com', 'Fernando Augusto', '98888-7777', 11, 'A');
INSERT INTO public.traveler (id, "document", email, "name", number_phone, prefix_phone, status)
VALUES(2,'', 'giovanna@gmail.com', 'Giovanna Andrade', '98888-6666', 12, 'A');
INSERT INTO public.traveler (id, "document", email, "name", number_phone, prefix_phone, status)
VALUES(3,'', 'gustavo@gmail.com', 'Gustavo Andrade', '98888-5555', 13, 'A');
INSERT INTO public.traveler (id, "document", email, "name", number_phone, prefix_phone, status)
VALUES(4,'', 'roberta@gmail.com', 'Roberta Luiza', '98888-4444', 14, 'I');