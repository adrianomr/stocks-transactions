-- public."transaction" definition

-- Drop table

-- DROP TABLE public."transaction";

ALTER TABLE transaction add column origin varchar(50) NOT NULL default 'CEI_B3';