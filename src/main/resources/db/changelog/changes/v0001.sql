-- public."transaction" definition

-- Drop table

-- DROP TABLE public."transaction";

CREATE TABLE IF NOT EXISTS public."transaction" (
	id int8 NOT NULL,
	amount numeric(19, 2) NOT NULL,
	"date" date NOT NULL,
	price numeric(19, 2) NOT NULL,
	"sequence" int4 NOT NULL,
	ticker varchar(255) NOT NULL,
	"type" varchar(255) NOT NULL,
	user_id int8 NOT NULL,
	CONSTRAINT transaction_pkey PRIMARY KEY (id)
);