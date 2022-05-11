--
-- PostgreSQL database dump
--

-- Dumped from database version 13.2
-- Dumped by pg_dump version 13.2

-- Started on 2021-04-29 14:21:09

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 207 (class 1259 OID 16600)
-- Name: content_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.content_types (
                                      id integer NOT NULL,
                                      name text NOT NULL
);


ALTER TABLE public.content_types OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 16598)
-- Name: content_types_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.content_types_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.content_types_id_seq OWNER TO postgres;

--
-- TOC entry 3046 (class 0 OID 0)
-- Dependencies: 206
-- Name: content_types_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.content_types_id_seq OWNED BY public.content_types.id;


--
-- TOC entry 211 (class 1259 OID 16631)
-- Name: resources; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.resources (
                                  id integer NOT NULL,
                                  resource_link text NOT NULL,
                                  resource_image_link text NOT NULL,
                                  resource_description text NOT NULL
);


ALTER TABLE public.resources OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 16629)
-- Name: resources_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.resources_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.resources_id_seq OWNER TO postgres;

--
-- TOC entry 3047 (class 0 OID 0)
-- Dependencies: 210
-- Name: resources_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.resources_id_seq OWNED BY public.resources.id;


--
-- TOC entry 203 (class 1259 OID 16566)
-- Name: SectionsServlet; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sections (
                                 id integer NOT NULL,
                                 name text NOT NULL
);


ALTER TABLE public.sections OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 16564)
-- Name: sections_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sections_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sections_id_seq OWNER TO postgres;

--
-- TOC entry 3048 (class 0 OID 0)
-- Dependencies: 202
-- Name: sections_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sections_id_seq OWNED BY public.sections.id;


--
-- TOC entry 205 (class 1259 OID 16582)
-- Name: steps; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.steps (
                              id integer NOT NULL,
                              subsection_id integer NOT NULL,
                              step_number integer NOT NULL,
                              content_type_id integer NOT NULL,
                              content_description text NOT NULL,
                              content_link text NOT NULL,
                              step_description text,
                              step_header text
);


ALTER TABLE public.steps OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16616)
-- Name: steps_resources; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.steps_resources (
                                        id integer NOT NULL,
                                        step_id integer NOT NULL,
                                        resource_id integer NOT NULL
);


ALTER TABLE public.steps_resources OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 16614)
-- Name: steps_resources_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.steps_resources_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.steps_resources_id_seq OWNER TO postgres;

--
-- TOC entry 3049 (class 0 OID 0)
-- Dependencies: 208
-- Name: steps_resources_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.steps_resources_id_seq OWNED BY public.steps_resources.id;


--
-- TOC entry 204 (class 1259 OID 16580)
-- Name: stept_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.stept_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.stept_id_seq OWNER TO postgres;

--
-- TOC entry 3050 (class 0 OID 0)
-- Dependencies: 204
-- Name: stept_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.stept_id_seq OWNED BY public.steps.id;


--
-- TOC entry 201 (class 1259 OID 16555)
-- Name: subsections; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.subsections (
                                    id integer NOT NULL,
                                    name text NOT NULL,
                                    section_id integer
);


ALTER TABLE public.subsections OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 16553)
-- Name: subsections_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.subsections_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.subsections_id_seq OWNER TO postgres;

--
-- TOC entry 3051 (class 0 OID 0)
-- Dependencies: 200
-- Name: subsections_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.subsections_id_seq OWNED BY public.subsections.id;


--
-- TOC entry 2888 (class 2604 OID 16603)
-- Name: content_types id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.content_types ALTER COLUMN id SET DEFAULT nextval('public.content_types_id_seq'::regclass);


--
-- TOC entry 2890 (class 2604 OID 16634)
-- Name: resources id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resources ALTER COLUMN id SET DEFAULT nextval('public.resources_id_seq'::regclass);


--
-- TOC entry 2886 (class 2604 OID 16569)
-- Name: SectionsServlet id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sections ALTER COLUMN id SET DEFAULT nextval('public.sections_id_seq'::regclass);


--
-- TOC entry 2887 (class 2604 OID 16585)
-- Name: steps id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.steps ALTER COLUMN id SET DEFAULT nextval('public.stept_id_seq'::regclass);


--
-- TOC entry 2889 (class 2604 OID 16619)
-- Name: steps_resources id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.steps_resources ALTER COLUMN id SET DEFAULT nextval('public.steps_resources_id_seq'::regclass);


--
-- TOC entry 2885 (class 2604 OID 16558)
-- Name: subsections id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subsections ALTER COLUMN id SET DEFAULT nextval('public.subsections_id_seq'::regclass);


--
-- TOC entry 2900 (class 2606 OID 16608)
-- Name: content_types content_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.content_types
    ADD CONSTRAINT content_types_pkey PRIMARY KEY (id);


--
-- TOC entry 2902 (class 2606 OID 16621)
-- Name: steps_resources pkey_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.steps_resources
    ADD CONSTRAINT pkey_id PRIMARY KEY (id);


--
-- TOC entry 2896 (class 2606 OID 16590)
-- Name: steps pkey_steps_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.steps
    ADD CONSTRAINT pkey_steps_id PRIMARY KEY (id);


--
-- TOC entry 2906 (class 2606 OID 16639)
-- Name: resources resources_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resources
    ADD CONSTRAINT resources_pkey PRIMARY KEY (id);


--
-- TOC entry 2894 (class 2606 OID 16574)
-- Name: SectionsServlet sections_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sections
    ADD CONSTRAINT sections_pkey PRIMARY KEY (id);


--
-- TOC entry 2892 (class 2606 OID 16563)
-- Name: subsections subsections_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subsections
    ADD CONSTRAINT subsections_pkey PRIMARY KEY (id);


--
-- TOC entry 2904 (class 2606 OID 16628)
-- Name: steps_resources unique_combination; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.steps_resources
    ADD CONSTRAINT unique_combination UNIQUE (step_id, resource_id);


--
-- TOC entry 2898 (class 2606 OID 16592)
-- Name: steps unique_order_per_subsection; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.steps
    ADD CONSTRAINT unique_order_per_subsection UNIQUE (subsection_id, step_number);


--
-- TOC entry 2909 (class 2606 OID 16609)
-- Name: steps fk_content_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.steps
    ADD CONSTRAINT fk_content_id FOREIGN KEY (content_type_id) REFERENCES public.content_types(id) NOT VALID;


--
-- TOC entry 2907 (class 2606 OID 16575)
-- Name: subsections fk_section_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subsections
    ADD CONSTRAINT fk_section_id FOREIGN KEY (section_id) REFERENCES public.sections(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 2910 (class 2606 OID 16622)
-- Name: steps_resources fk_steps_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.steps_resources
    ADD CONSTRAINT fk_steps_id FOREIGN KEY (step_id) REFERENCES public.steps(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2908 (class 2606 OID 16593)
-- Name: steps fk_subsection_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.steps
    ADD CONSTRAINT fk_subsection_id FOREIGN KEY (subsection_id) REFERENCES public.subsections(id) ON UPDATE CASCADE ON DELETE CASCADE;


insert into public.sections(name) values ('1920s');
insert into public.sections(name) values ('1950s');
insert into public.sections(name) values ('1970s');

insert into public.subsections(name, section_id) values ('Hairstyling', 1);
insert into public.subsections(name, section_id) values ('Makeup', 1);
insert into public.subsections(name, section_id) values ('Clothing', 1);

insert into public.subsections(name, section_id) values ('Hairstyling', 2);
insert into public.subsections(name, section_id) values ('Makeup', 2);
insert into public.subsections(name, section_id) values ('Clothing', 2);

insert into public.subsections(name, section_id) values ('Hairstyling', 3);
insert into public.subsections(name, section_id) values ('Makeup', 3);
insert into public.subsections(name, section_id) values ('Clothing', 3);

insert into public.content_types(name) values ('video');
insert into public.content_types(name) values ('image');
insert into public.content_types(name) values ('test');

insert into public.resources(resource_link, resource_image_link, resource_description)
values ('https://www.vo5.co.uk/products/women/enhance/true-curls/vo5--curl-defining-mousse-200ml.html',
        'https://ath2.unileverservices.com/wp-content/uploads/sites/4/2016/04/Vo5-curling-Mousse-300x300.jpg',
        'VO5 Curl Defining Mousse');

insert into public.resources(resource_link, resource_image_link, resource_description)
values ('https://www.tresemme.com/uk/products/heat-protection-spray/tresemm%C3%A9-defence-spray-.html',
        'https://ath2.unileverservices.com/wp-content/uploads/sites/4/2016/04/tres-heat-defence-300x300.jpg',
        'TRESemmé Heat Defence Spray');


insert into public.resources(resource_link, resource_image_link, resource_description) values ('https://www.hairmeetwardrobe.co.uk/product/detail/481397/extreme-style-creation-hairspray', 'https://ath2.unileverservices.com/wp-content/uploads/sites/4/2016/04/TONIGUY-Extreme-Creation-Hairspray-300x300.jpg', 'TONI&GUY Extreme Style Creation Hairspray');

insert into public.resources(resource_link, resource_image_link, resource_description) values ('https://images-na.ssl-images-amazon.com/images/I/81jvLdzkN8L._UY879_.jpg', 'https://images-na.ssl-images-amazon.com/images/I/81jvLdzkN8L._UY879_.jpg', 'TONI&GUY Extreme Style Creation Hairspray');

insert into public.resources(resource_link, resource_image_link, resource_description) values ('https://cdn.shopify.com/s/files/1/0378/5257/7932/products/B6A5412_1.jpg?v=1608474474',
                                                                                        'https://cdn.shopify.com/s/files/1/0378/5257/7932/products/B6A5412_1.jpg?v=1608474474', 'TONI&GUY Extreme Style Creation Hairspray');

insert into public.resources(resource_link, resource_image_link, resource_description) values ('https://cdn.shopify.com/s/files/1/2714/9310/products/80051_1rs.JPG?v=1571711780', 'https://cdn.shopify.com/s/files/1/2714/9310/products/80051_1rs.JPG?v=1571711780', 'TONI&GUY Extreme Style Creation Hairspray');

insert into public.resources(resource_link, resource_image_link, resource_description) values ('https://images-na.ssl-images-amazon.com/images/I/81DfvHRs8dL._UL1500_.jpg', 'https://images-na.ssl-images-amazon.com/images/I/81DfvHRs8dL._UL1500_.jpg', 'TONI&GUY Extreme Style Creation Hairspray');

insert into public.resources(resource_link, resource_image_link, resource_description) values('https://images-na.ssl-images-amazon.com/images/I/71ksClKIJ9L._AC_UY550_.jpg', 'https://images-na.ssl-images-amazon.com/images/I/71ksClKIJ9L._AC_UY550_.jpg', 'TONI&GUY Extreme Style Creation Hairspray');

insert into public.resources(resource_link, resource_image_link, resource_description) values('https://www.hairmeetwardrobe.co.uk/product/detail/481397/extreme-style-creation-hairspray', 'https://ath2.unileverservices.com/wp-content/uploads/sites/4/2016/04/TONIGUY-Extreme-Creation-Hairspray-300x300.jpg', 'TONI&GUY Extreme Style Creation Hairspray');

insert into public.resources(resource_link, resource_image_link, resource_description)
values ('https://images-na.ssl-images-amazon.com/images/I/81jvLdzkN8L._UY879_.jpg',
        'https://images-na.ssl-images-amazon.com/images/I/81jvLdzkN8L._UY879_.jpg',
        'TONI&GUY Extreme Style Creation Hairspray');

insert into public.resources(resource_link, resource_image_link, resource_description)
values ('https://cdn.shopify.com/s/files/1/0378/5257/7932/products/B6A5412_1.jpg?v=1608474474',
        'https://cdn.shopify.com/s/files/1/0378/5257/7932/products/B6A5412_1.jpg?v=1608474474',
        'TONI&GUY Extreme Style Creation Hairspray');

insert into public.resources(resource_link, resource_image_link, resource_description)
values ('https://cdn.shopify.com/s/files/1/2714/9310/products/80051_1rs.JPG?v=1571711780',
        'https://cdn.shopify.com/s/files/1/2714/9310/products/80051_1rs.JPG?v=1571711780',
        'TONI&GUY Extreme Style Creation Hairspray');

insert into public.resources(resource_link, resource_image_link, resource_description)
values ('https://images-na.ssl-images-amazon.com/images/I/81DfvHRs8dL._UL1500_.jpg',
        'https://images-na.ssl-images-amazon.com/images/I/81DfvHRs8dL._UL1500_.jpg',
        'TONI&GUY Extreme Style Creation Hairspray');

insert into public.resources(resource_link, resource_image_link, resource_description)
values ('https://images-na.ssl-images-amazon.com/images/I/71ksClKIJ9L._AC_UY550_.jpg',
        'https://images-na.ssl-images-amazon.com/images/I/71ksClKIJ9L._AC_UY550_.jpg',
        'TONI&GUY Extreme Style Creation Hairspray');



insert into public.steps(subsection_id, step_number, step_description, step_header, content_type_id, content_description, content_link)
values (1, 0, 'Give Gatsby a run for his money with this pretty flapper hairstyle!',
        'Your 9-step guide to ''20s-inspired flapper hair',
        2, '20s hair preview', 'https://ath2.unileverservices.com/wp-content/uploads/sites/4/2016/05/flapper-hair-final-pic-2-782x439.jpg'
       );

insert into public.steps(subsection_id, step_number, step_description, step_header, content_type_id, content_description, content_link) values (1, 1, 'First, get your hair ready for styling by gently combing or brushing to remove any knots or tangles. Then, generously spritz TRESemmé Heat Defence Styling Spray (300ml, £4.99*) all over to help guard your hair from heat.', 'Prep and protect', 2, '20s hair preview', 'https://ath2.unileverservices.com/wp-content/uploads/sites/4/2016/05/flapper-hair-tutorial-011-683x1024.jpg');

insert into public.steps(subsection_id, step_number,
                         step_description, step_header,
                         content_type_id, content_description,
                         content_link)
values
(3,
 0,
 'What did flappers wear? Free of the moral and physical constraints of
the previous decades, flapper costumes were loose but glamorous. During
the day flappers dressed in drop-waist dresses with a small belt or wide
sash to accent the hip line instead of the waist. Flat chests further
enhanced the boyish flapper outfit. The evening brought out knee-length
and longer fringe, beading, and sequin flapper dresses with rhinestone and
feather headbands creating a fun and feminine flapper outfit.',
 'How to Dress Like a 20s Flapper Girl',
 2,
 'Girls dressed in occasion evening dresses 20s dresses',
 'https://cdn.shopify.com/s/files/1/0590/2633/products/The_Kismet-A_1024x1024.jpg?v=1585421197');

insert into public.steps(subsection_id, step_number,
                         step_description, step_header,
                         content_type_id, content_description,
                         content_link)
values
(3,
 1,
 'For your authentic flapper costume, choose a dress made of a light material
like chiffon, silk/satin, or crepe. Be sure to wear a matching slip if the
dress is sheer. Elaborate beading will make the dress stand out but also
expensive, so if budget is a concern, find a plain dress and jazz it up with
sparkling accessories. Your dress should be knee-length or longer for
authenticity (but can be shorter for a 1920s inspired look). It should fit
loose, meaning you may need to buy a size up. It should not hug your curves.',
 'The Dress.',
 2,
 'Girls dressed in occasion evening dresses 20s dresses',
 'https://vintagedancer.com/wp-content/uploads/flaper-dresses-UV-2016-350x438.jpg');

-- Completed on 2021-04-29 14:21:09

--
-- PostgreSQL database dump complete
--

