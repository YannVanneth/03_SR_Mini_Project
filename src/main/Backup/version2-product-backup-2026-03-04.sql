--
-- PostgreSQL database dump
--

\restrict arNqTZyMviybBBvZ6Is8JPseoVnsIXfUlrk45NelBZnBiG2hW4HaOvfrfnehdj8

-- Dumped from database version 18.1
-- Dumped by pg_dump version 18.1

-- Started on 2026-03-04 11:29:26

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

ALTER TABLE IF EXISTS ONLY public.products DROP CONSTRAINT IF EXISTS products_pkey;
ALTER TABLE IF EXISTS public.products ALTER COLUMN id DROP DEFAULT;
DROP VIEW IF EXISTS public.v_all_products;
DROP SEQUENCE IF EXISTS public.products_id_seq;
DROP TABLE IF EXISTS public.products;
SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 219 (class 1259 OID 18864)
-- Name: products; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.products (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    unit_price numeric(10,2) NOT NULL,
    quantity integer NOT NULL,
    imported_date date NOT NULL
);


ALTER TABLE public.products OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 18872)
-- Name: products_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.products_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.products_id_seq OWNER TO postgres;

--
-- TOC entry 4971 (class 0 OID 0)
-- Dependencies: 220
-- Name: products_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;


--
-- TOC entry 221 (class 1259 OID 18873)
-- Name: v_all_products; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_all_products AS
 SELECT id,
    name,
    unit_price,
    quantity,
    imported_date
   FROM public.products;


ALTER VIEW public.v_all_products OWNER TO postgres;

--
-- TOC entry 4813 (class 2604 OID 18877)
-- Name: products id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);


--
-- TOC entry 4964 (class 0 OID 18864)
-- Dependencies: 219
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.products (id, name, unit_price, quantity, imported_date) FROM stdin;
1	Grape	1.60	110	2026-03-04
2	Banana	0.50	150	2026-03-04
3	Orange	1.00	120	2026-03-04
\.


--
-- TOC entry 4972 (class 0 OID 0)
-- Dependencies: 220
-- Name: products_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.products_id_seq', 3, true);


--
-- TOC entry 4815 (class 2606 OID 18879)
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


-- Completed on 2026-03-04 11:29:26

--
-- PostgreSQL database dump complete
--

\unrestrict arNqTZyMviybBBvZ6Is8JPseoVnsIXfUlrk45NelBZnBiG2hW4HaOvfrfnehdj8

