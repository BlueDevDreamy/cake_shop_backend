ALTER TABLE diplomskidb.authority  MODIFY COLUMN created_at datetime(6) DEFAULT current_timestamp() NOT NULL;

ALTER TABLE diplomskidb.codebook_category  MODIFY COLUMN created_at datetime(6) DEFAULT current_timestamp() NOT NULL;

ALTER TABLE diplomskidb.codebook_flavor  MODIFY COLUMN created_at datetime(6) DEFAULT current_timestamp() NOT NULL;

ALTER TABLE diplomskidb.cake MODIFY COLUMN created_at datetime(6) DEFAULT current_timestamp() NOT NULL;


INSERT INTO diplomskidb.authority (name) VALUES("ADMIN");

INSERT INTO diplomskidb.authority (name) VALUES("CLIENT");

INSERT INTO diplomskidb.codebook_category (name) VALUES('Svadbene');

INSERT INTO diplomskidb.codebook_category (name) VALUES('Dečije');

INSERT INTO diplomskidb.codebook_category (name) VALUES('Svečane');

INSERT INTO diplomskidb.codebook_category (name) VALUES('Specijal');

INSERT INTO diplomskidb.codebook_flavor (allergens, description, ingredients, name) VALUES('Badem', 'Korice sa bademom – čoko krem sa seckanom belom čokoladom i seckanim pečenim bademom', 'Brašno, jaja, šećer', 'Badem Torta');

INSERT INTO diplomskidb.codebook_flavor (allergens, description, ingredients, name) VALUES('Orasi', 'Sočne kore sa orahom i zrncima termostabilne čokolade, preko sloj italijanskog Moccawafer krema, vanil lešnik krem', 'Brašno, jaja, šećer', 'Mocca Torta');

INSERT INTO diplomskidb.codebook_flavor (allergens, description, ingredients, name) VALUES('Lešnik', 'Sočne čoko kore, fil sa višnjama, čoko i vanil krem, čoko glazura ili miror višnja od gore.', 'Brašno, jaja, šećer', 'Amarena Torta');

INSERT INTO diplomskidb.codebook_flavor (allergens, description, ingredients, name) VALUES('Orasi', 'Korice od belanaca i oraha, filovane vanil kremom sa dodatkom oraha i bele čokolade, laganim vanil kremom sa seckanim bananama.', 'Brašno, jaja, šećer', 'Banana Torta');

INSERT INTO diplomskidb.codebook_flavor (allergens, description, ingredients, name) VALUES('Lešnik', 'Četiri tanke čokoladne kore natopljene italijanskom tečnom čokoladom , preko svake bogat fil od maline, na njega čokoladni krem.', 'Brašno, jaja, šećer', 'Baron Torta');

INSERT INTO diplomskidb.codebook_flavor (allergens, description, ingredients, name) VALUES('Lešnik', 'Kore od belanaca, kakaa i seckanog lešnika – na svaku vanil krem i preko njega čokoladni krem', 'Brašno, jaja, šećer', 'Boem Torta');

INSERT INTO diplomskidb.codebook_flavor (allergens, description, ingredients, name) VALUES('Orasi', 'Kore sa orahom – preko njih umešena Plazma® keks sa sokom od pomorandže – vanil krem – Jaffa® keks umočen u sok od pomorandže', 'Brašno, jaja, šećer', 'Jaffa Torta');

INSERT INTO diplomskidb.codebook_flavor (allergens, description, ingredients, name) VALUES('Oreo', 'Podloga od mlevenog Oreo® keksa, putera i mleka- krem od slatke pavlake Ala Kajmaka i bele čokolade. Od gore posut Oreo Crumbs®', 'Brašno, jaja, šećer', 'Čizkejk Malina');

INSERT INTO diplomskidb.codebook_flavor (allergens, description, ingredients, name) VALUES('Orasi', 'Kore od penastog žutog patišpanja filovane čoko kremom, na poslednju koricu glazura od šećernog karamela.', 'Brašno, jaja, šećer', 'Doboš Torta');

INSERT INTO diplomskidb.codebook_flavor (allergens, description, ingredients, name) VALUES('Orasi', 'Kore sa Plazma® keksom – vanil krem sa dodatkom seckanih oraha, listića čokolade i lomljenog Plazma® keksa', 'Brašno, jaja, šećer', 'Grčka Torta');

INSERT INTO diplomskidb.codebook_flavor (allergens, description, ingredients, name) VALUES('Lešnik', 'Lagane, bele kore – na svaku bogat fil od jagoda -lagan vanil krem – sloj topljene čokolade', 'Brašno, jaja, šećer', 'Kapri Torta');


INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Pink Mašna', 2200, 1400, 2, 30, 1, 1, 'https://img.freepik.com/free-photo/delicious-cake-with-fruits_23-2150661066.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 1);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Božuri', 2500, 1600, 1, 20, 1, 3, 'ttps://img.freepik.com/free-photo/view-beautifully-ornate-weeding-cake_23-2151379544.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 1);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Beli Cvetovi', 2000, 1200, 1, 25, 1, 4, 'https://img.freepik.com/free-photo/delicious-cake-with-fruits_23-2150727576.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 1);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Orijental', 2300, 1500, 1.5, 28, 1, 3, 'https://img.freepik.com/free-photo/3d-design-delicious-wedding-cake_23-2151109600.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 1);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Orhideja', 2400, 1300, 2, 22, 1, 3, 'https://img.freepik.com/free-photo/3d-design-delicious-wedding-cake_23-2151109614.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 1);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Pink Perle', 2600, 1700, 1, 26, 1, 4, 'https://img.freepik.com/free-photo/3d-design-delicious-wedding-cake_23-2151109611.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 1);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Sweet Pink Torta', 2700, 1800, 1.5, 24, 1, 3, 'https://img.freepik.com/free-photo/delicious-cake-with-fruits_23-2150727752.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 2);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Macaroons Torta', 2800, 1900, 2, 30, 2, 4, 'https://img.freepik.com/free-photo/3d-cake-with-lit-candles-top-macarons_23-2150915506.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 2);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Princeza Arijel', 2500, 1600, 1, 25, 1, 3, 'https://img.freepik.com/free-photo/delicious-cake-with-fruits_23-2150727554.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 2);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Klovn Torta', 2300, 1500, 1.5, 27, 1, 3, 'https://img.freepik.com/free-photo/delicious-cake-with-cute-character_23-2150727695.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 2);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Astronaut Torta', 2400, 1800, 1.5, 27, 1, 3, 'https://img.freepik.com/free-photo/delicious-astronaut-3d-cake_23-2151184993.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 2);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Medvedići Torta', 2200, 1100, 1.5, 27, 1, 3, 'https://img.freepik.com/free-photo/delicious-cake-with-cute-character_23-2150727760.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 2);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Zebra Torta', 2100, 1200, 1.5, 27, 1, 3, 'https://img.freepik.com/free-photo/elegant-candlelight-arrangement-rustic-wooden-table-generated-by-ai_188544-45471.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 3);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Šumsko Carstvo Torta', 2400, 1000, 1.5, 27, 1, 3, 'https://img.freepik.com/free-photo/3d-design-delicious-wedding-cake_23-2151109606.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 3);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Zimska Čarolija Torta', 2300, 1500, 1.5, 27, 1, 3, 'https://img.freepik.com/free-photo/delicious-cake-with-fruits_23-2150727725.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 3);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Cvetna Borovnica Torta', 2600, 1300, 1.5, 27, 1, 3, 'https://img.freepik.com/free-photo/3d-wedding-cake-design_23-2151109494.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 3);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Romboid Iluzija Torta', 2200, 1500, 1.5, 27, 1, 3, 'https://img.freepik.com/free-photo/birthday-cake-with-raspberry-strawberry-blueberry-decoration-generated-by-artificial-intelligence_25030-67262.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 3);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Zlatni Božuri Torta', 2400, 1800, 1.5, 27, 1, 3, 'https://img.freepik.com/free-photo/delicious-cake-with-fruits_23-2150661079.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 3);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Borovnica Mafini', 2100, 1000, 1.5, 27, 1, 3, 'https://img.freepik.com/free-photo/view-delicious-muffin_23-2150777671.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 4);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Jagoda Cheesecake', 2100, 1100, 1.5, 27, 1, 3, 'https://img.freepik.com/free-photo/cake-with-slice-cut-out-it-with-strawberries-blueberries-it_1340-29903.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 4);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Lava Kolač', 2300, 1700, 1.5, 27, 1, 3, 'https://img.freepik.com/free-photo/delicious-chocolate-cake-with-flowers_23-2150727548.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 4);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Karamel Ogledalo', 2400, 1400, 1.5, 27, 1, 3, 'https://img.freepik.com/free-photo/delicious-cake-with-fruits_23-2150727534.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 4);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Srce Torta', 2300, 1400, 1, 27, 1, 3, 'https://img.freepik.com/free-photo/view-heart-shaped-delicious-cake_23-2150824970.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 4);

INSERT INTO diplomskidb.cake (title, price_per_kilo, decoration_price, min_weight, max_weight, min_tier, max_tier, image_url, category_id) VALUES ('Srce Mafin', 2300, 1500, 1.5, 27, 1, 3, 'https://img.freepik.com/free-photo/delicious-cupcakes-with-heart-shapes_23-2150873237.jpg?uid=R91655651&ga=GA1.1.360058317.1716372183&semt=ais_user_ai_gen', 4);

