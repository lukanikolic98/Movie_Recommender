insert into users ( id, email, firstname, lastname, password, role, activated, activation_token)
values (1, 'lukanikolic98@hotmail.com', 'Luka', 'Nikolic', '$2a$10$6e80QpI4vnPYC8kN1wxut.1ZxX/2mGCD3.xRHOYm.5oojvQ9KRVlm', 'ROLE_ADMIN', true, ''),
(2, 'alice@example.com', 'Alice', 'Smith', '$2a$10$6e80QpI4vnPYC8kN1wxut.1ZxX/2mGCD3.xRHOYm.5oojvQ9KRVlm', 'ROLE_USER', true, ''),
(3, 'bob@example.com', 'Bob', 'Johnson', '$2a$10$6e80QpI4vnPYC8kN1wxut.1ZxX/2mGCD3.xRHOYm.5oojvQ9KRVlm', 'ROLE_USER', true, '');
--user password is Lastavica.98

-- insert movies
-- INSERT INTO movies (title, description, director) VALUES 
-- ('The Matrix', 'A computer hacker learns about the true nature of reality.', 'Lana Wachowski');
-- INSERT INTO movies (title, description, director) VALUES 
-- ('Inception', 'A thief enters dreams to steal secrets.', 'Christopher Nolan');
-- INSERT INTO movies (title, description, director) VALUES 
-- ('Interstellar', 'Explorers travel through a wormhole in space.', 'Christopher Nolan');
INSERT INTO movies (title, description, director, posterurl) VALUES
('Shadow Drift', 'A small town hides an ocean of secrets.', 'Marta Vukovic', 'https://picsum.photos/id/10/500/750'),
('Neon Harbor', 'A courier gets lost in a neon city of lies.', 'Jonas Petrov', 'https://picsum.photos/id/11/500/750'),
('Paper Suns', 'Two siblings rebuild their life after a fire.', 'Ana Kovac', 'https://picsum.photos/id/12/500/750'),
('Clockwork Orchard', 'A clockmaker discovers time can be replanted.', 'Marko Ilic', 'https://picsum.photos/id/13/500/750'),
('Glass Atlas', 'Cartographers map a city that refuses to stay still.', 'Sofia Radic', 'https://picsum.photos/id/14/500/750'),
('Midnight Ledger', 'An accountant uncovers a ledger with ghosts.', 'Nikola Jovanovic', 'https://picsum.photos/id/15/500/750'),
('Paper Lanterns', 'A festival changes the fate of a wandering poet.', 'Lena Kovacevic', 'https://picsum.photos/id/16/500/750'),
('Blue Orchard', 'A botanist grows an impossible blue fruit.', 'Dusan Milosevic', 'https://picsum.photos/id/17/500/750'),
('Silent Harbor', 'A retired sailor returns to a silent port.', 'Ivana Markovic', 'https://picsum.photos/id/18/500/750'),
('Iron Meadow', 'Machines and wildflowers fight for the meadow.', 'Petar Nikolic', 'https://picsum.photos/id/19/500/750'),
('Velvet Signal', 'An amateur radio operator intercepts a message.', 'Marija Popovic', 'https://picsum.photos/id/20/500/750'),
('Paper Choir', 'Factory workers form an unlikely choir.', 'Vladimir Petrovic', 'https://picsum.photos/id/21/500/750'),
('Amber Night', 'A detective follows an amber trail through dreams.', 'Tanja Ristic', 'https://picsum.photos/id/22/500/750'),
('Copper Sky', 'Pilots race under a copper colored sky.', 'Milan Stankovic', 'https://picsum.photos/id/23/500/750'),
('The Last Semaphore', 'Two engineers fight to save the last lighthouse.', 'Nina Lazarevic', 'https://picsum.photos/id/24/500/750'),
('Winter Orchard', 'A man returns to harvest a winter crop of memories.', 'Stefan Bogdanovic', 'https://picsum.photos/id/25/500/750'),
('Crimson Thread', 'A seamstress alters the course of history with a stitch.', 'Olga Pavlovic', 'https://picsum.photos/id/26/500/750'),
('Waking Atlas', 'Maps start to change when people wake up in new places.', 'Filip Radanovic', 'https://picsum.photos/id/27/500/750'),
('Low Tide Motel', 'Guests at a roadside motel share impossible stories.', 'Maja Djuric', 'https://picsum.photos/id/28/500/750'),
('Orchid Signal', 'A botanist deciphers a plant based code.', 'Aleksandar Zoric', 'https://picsum.photos/id/29/500/750'),
('Glass Wolves', 'A feral pack guards a glass cathedral.', 'Katarina Srdic', 'https://picsum.photos/id/30/500/750'),
('Paper Beacon', 'A child designs a beacon that guides forgotten ships.', 'Djordje Milic', 'https://picsum.photos/id/31/500/750'),
('Midnight Embers', 'An unlikely romance sparked by leftover embers.', 'Jovana Mladenovic', 'https://picsum.photos/id/32/500/750'),
('Echo Chamber', 'Voices from different decades collide in an apartment.', 'Boris Radenkovic', 'https://picsum.photos/id/33/500/750'),
('Stolen Halos', 'A pair of thieves find old saints and new doubts.', 'Zorana Veljkovic', 'https://picsum.photos/id/34/500/750'),
('Fathomless Post', 'A postman delivers letters that change destinies.', 'Luka Brankovic', 'https://picsum.photos/id/35/500/750'),
('Rust and Roses', 'A romance blooms in an abandoned train yard.', 'Milica Obradovic', 'https://picsum.photos/id/36/500/750'),
('Hollow Compass', 'A lost compass points toward strange fortunes.', 'Goran Milutin', 'https://picsum.photos/id/37/500/750'),
('Paper Skies', 'Airship mechanics chase legends in the clouds.', 'Ivana Stanojkovic', 'https://picsum.photos/id/38/500/750'),
('The Quiet Cartographer', 'A mapmaker erases and redraws his past.', 'Nenad Vasic', 'https://picsum.photos/id/39/500/750'),
('Lantern Boy', 'A boy with a lantern sees paths no one else can.', 'Marin Dimic', 'https://picsum.photos/id/40/500/750'),
('Beneath the Ferris', 'A carnival hides a portal under its Ferris wheel.', 'Slavica Radovanovic', 'https://picsum.photos/id/41/500/750'),
('The Spare Key', 'A lost key opens a door to a parallel weekend.', 'Predrag Tomic', 'https://picsum.photos/id/42/500/750'),
('Second Shore', 'Refugees find a second shore that remembers them.', 'Anja Vasic', 'https://picsum.photos/id/43/500/750'),
('Glassletters', 'Letters written on glass reveal hidden messages.', 'Radovan Peric', 'https://picsum.photos/id/44/500/750'),
('Distant Bell', 'A bell rings from a town that no longer exists.', 'Teodora Vuk', 'https://picsum.photos/id/45/500/750'),
('Paper Moons', 'An astronaut dreams of paper moons back home.', 'Milos Zivkovic', 'https://picsum.photos/id/46/500/750'),
('Silent Orchard', 'A ghost orchard only bears fruit for midnight visitors.', 'Jelena Krstic', 'https://picsum.photos/id/47/500/750'),
('Rose Engine', 'An inventor builds a machine to remember lost faces.', 'Zeljko Vranic', 'https://picsum.photos/id/48/500/750'),
('Blue Ledger', 'A ledger records futures instead of pasts.', 'Nada Kostic', 'https://picsum.photos/id/49/500/750'),
('Gilded Harbor', 'Smugglers trade in stories rather than goods.', 'Vukasin Stanoj', 'https://picsum.photos/id/50/500/750'),
('The Long Knot', 'A sailor ties a knot that shortens time.', 'Dragana Kolar', 'https://picsum.photos/id/51/500/750'),
('Ashen Compass', 'A compass that points to what the holder wants most.', 'Nemanja Ristic', 'https://picsum.photos/id/52/500/750'),
('Moonlit Ledger', 'Night auditors balance accounts with memories.', 'Svetlana Dedic', 'https://picsum.photos/id/53/500/750'),
('Verdant Signal', 'A radio signal brings life back to deserts.', 'Darko Jankovic', 'https://picsum.photos/id/54/500/750'),
('The Lantern Makers', 'Families carve lanterns to keep dreams alive.', 'Mirjana Stankic', 'https://picsum.photos/id/55/500/750'),
('Paper Puppets', 'Puppets begin to perform beyond their strings.', 'Nenad Perovic', 'https://picsum.photos/id/56/500/750'),
('Bitter Sugar', 'A sugar factory hides bitter secrets.', 'Sanja Markos', 'https://picsum.photos/id/57/500/750'),
('Fallen Semaphore', 'Signals fall silent after a mysterious fog.', 'Branko Pavic', 'https://picsum.photos/id/58/500/750'),
('Marigold Train', 'Passengers on a marigold decorated train swap lives.', 'Tanja Simic', 'https://picsum.photos/id/59/500/750'),
('Hidden Oars', 'Rowers cross into parallel coves at dawn.', 'Miloje Grujic', 'https://picsum.photos/id/60/500/750'),
('The Last Draft', 'A writer drafts the future in coffee stains.', 'Dara Kovin', 'https://picsum.photos/id/61/500/750'),
('Silent Cart', 'A vendor sells maps that show where you belong.', 'Radomir Filipovic', 'https://picsum.photos/id/62/500/750'),
('Clocktower Letters', 'Letters appear under the town clock every full hour.', 'Jelena Vasic', 'https://picsum.photos/id/63/500/750'),
('Neon Ledger', 'A neon sign keeps track of lost promises.', 'Filip Kovacic', 'https://picsum.photos/id/64/500/750'),
('The Saffron Key', 'A spice merchant hides a key to a hidden market.', 'Marina Lukic', 'https://picsum.photos/id/65/500/750'),
('Echo Orchard', 'An orchard echoes what you once said aloud.', 'Goran Stefanovic', 'https://picsum.photos/id/66/500/750'),
('Paper Pilots', 'Paper planes carry messages between strangers.', 'Ivana Miljan', 'https://picsum.photos/id/67/500/750'),
('The Hollow Map', 'A map with holes that lead to lost towns.', 'Bojan Ristic', 'https://picsum.photos/id/68/500/750'),
('Amber Compass', 'A compass made from amber reveals hidden paths.', 'Snezana Krajnovic', 'https://picsum.photos/id/69/500/750'),
('Velvet Ledger', 'A ledger sells tickets to moments of redemption.', 'Milan Draskovic', 'https://picsum.photos/id/70/500/750'),
('The Paper Lighthouse', 'A lighthouse made of paper withstands storms of memory.', 'Anica Jovic', 'https://picsum.photos/id/71/500/750'),
('Midnight Loom', 'A weaver knits dreams into blankets.', 'Rade Milenkovic', 'https://picsum.photos/id/72/500/750'),
('Rust Harbor', 'Old ships gather to tell their final stories.', 'Kristina Obrad', 'https://picsum.photos/id/73/500/750'),
('The Glass Orchard', 'Glass trees reflect versions of the past.', 'Vesna Terzic', 'https://picsum.photos/id/74/500/750'),
('Paper Echoes', 'Echoes of past conversations manifest physically.', 'Miroslav Jank', 'https://picsum.photos/id/75/500/750'),
('Lantern Harbor', 'Lanterns guide refugees to a safe cove.', 'Sandra Pavlov', 'https://picsum.photos/id/76/500/750'),
('Blue Ferris', 'A Ferris wheel connects lovers across time.', 'Zoran Nik', 'https://picsum.photos/id/77/500/750'),
('The Long Ledger', 'An endless ledger chronicles small town lives.', 'Milena Vasic', 'https://picsum.photos/id/78/500/750'),
('Orchid Clock', 'Flowers bloom at certain clock ticks.', 'Vladimir Sekulic', 'https://picsum.photos/id/79/500/750'),
('Paper Harbor', 'Ships made of paper brave real seas.', 'Ljiljana Antic', 'https://picsum.photos/id/80/500/750'),
('The Iron Bell', 'A bell tolls only for those who listen closely.', 'Dragan Perisic', 'https://picsum.photos/id/81/500/750'),
('Folding Moon', 'A moon that folds like paper reshapes nights.', 'Tijana Markovic', 'https://picsum.photos/id/82/500/750'),
('The Quiet Loom', 'A loom weaves stories into townspeople lives.', 'Nikola Savic', 'https://picsum.photos/id/83/500/750'),
('Verdant Ledger', 'A ledger that grows leaves instead of pages.', 'Sanja Zdravkovic', 'https://picsum.photos/id/84/500/750'),
('The Last Lantern', 'A single lantern keeps a town from forgetting.', 'Milan Drinic', 'https://picsum.photos/id/85/500/750'),
('Cracked Compass', 'A cracked compass still knows true north of the heart.', 'Ana Trifunovic', 'https://picsum.photos/id/86/500/750'),
('Moonflower Train', 'A train that only runs when the moonflowers bloom.', 'Petar Loncar', 'https://picsum.photos/id/87/500/750'),
('The Paper Bell', 'A bell made of paper rings for new beginnings.', 'Dragica Vuk', 'https://picsum.photos/id/88/500/750'),
('Silent Ledger', 'A ledger records silence as currency.', 'Andrej Milosevic', 'https://picsum.photos/id/89/500/750'),
('Velvet Orchard', 'Velvet fruit that comforts those who taste it.', 'Marija Petres', 'https://picsum.photos/id/90/500/750'),
('Copper Lanterns', 'Lanterns that hum when secrets pass by.', 'Dario Pavlovic', 'https://picsum.photos/id/91/500/750'),
('The Hidden Odometer', 'An odometer counts not miles but memories.', 'Biljana Zoric', 'https://picsum.photos/id/92/500/750'),
('Paper Bridges', 'Bridges made of paper connect lost islands.', 'Ivan Grbic', 'https://picsum.photos/id/93/500/750'),
('The Velvet Key', 'A key wrapped in velvet opens impossible doors.', 'Jasna Milic', 'https://picsum.photos/id/94/500/750'),
('Ash Harbor', 'Ash falls every evening and reveals old maps.', 'Radovan Gligoric', 'https://picsum.photos/id/95/500/750'),
('The Long Lantern', 'A lantern carried across generations lights futures.', 'Ksenija Bozic', 'https://picsum.photos/id/96/500/750'),
('Paper Clocktower', 'A clocktower built of paper keeps better time than others.', 'Dragan Rasic', 'https://picsum.photos/id/97/500/750'),
('Silent Atlas', 'An atlas that points to personal crossroads.', 'Violeta Grubac', 'https://picsum.photos/id/98/500/750'),
('The Saffron Bell', 'A bell that releases spice scented memories.', 'Predrag Zivkov', 'https://picsum.photos/id/99/500/750'),
('Blue Loom', 'Weavers dye cloth with memories of the sea.', 'Marina Kovac', 'https://picsum.photos/id/100/500/750'),
('The Hidden Ledger', 'A ledger only visible at twilight keeps promises.', 'Zoran Tesic', 'https://picsum.photos/id/101/500/750'),
('Paper Mariners', 'Mariners navigate by paper stars they fold each night.', 'Ivana Selak', 'https://picsum.photos/id/102/500/750');


-- movie_keywords table: movie_id | keyword
INSERT INTO movie_keywords (movie_id, keyword) VALUES
(1, 'sci-fi'),
(1, 'action'),
(1, 'mind-bending'),
(1, 'reality-questioning'),
(2, 'sci-fi'),
(2, 'thriller'),
(2, 'dreams'),
(2, 'heist'),
(3, 'sci-fi'),
(3, 'space'),
(3, 'emotional'),
(3, 'epic-journey'),
(4, 'mystery'),
(4, 'drama'),
(4, 'small-town'),
(5, 'cyberpunk'),
(5, 'thriller'),
(5, 'action'),
(6, 'family'),
(6, 'coming-of-age'),
(6, 'emotional'),
(7, 'fantasy'),
(7, 'adventure'),
(7, 'magical-realism'),
(8, 'science'),
(8, 'botany'),
(8, 'mystery'),
(9, 'romance'),
(9, 'drama'),
(9, 'suspense'),
(10, 'sci-fi'),
(10, 'nature'),
(10, 'action'),
(11, 'thriller'),
(11, 'radio'),
(11, 'suspense'),
(12, 'music'),
(12, 'friendship'),
(12, 'inspirational'),
(13, 'mystery'),
(13, 'dreams'),
(13, 'romance'),
(14, 'adventure'),
(14, 'sky'),
(14, 'action'),
(15, 'suspense'),
(15, 'thriller'),
(15, 'fantasy'),
(16, 'drama'),
(16, 'romance'),
(16, 'mystery'),
(17, 'fantasy'),
(17, 'adventure'),
(17, 'magical-realism'),
(18, 'mystery'),
(18, 'small-town'),
(18, 'thriller'),
(19, 'science'),
(19, 'puzzle'),
(19, 'adventure'),
(20, 'sci-fi'),
(20, 'thriller'),
(20, 'animals'),
(21, 'fantasy'),
(21, 'friendship'),
(21, 'mystery'),
(22, 'romance'),
(22, 'suspense'),
(22, 'drama'),
(23, 'fantasy'),
(23, 'action'),
(23, 'adventure'),
(24, 'mystery'),
(24, 'drama'),
(24, 'sci-fi'),
(25, 'adventure'),
(25, 'drama'),
(25, 'romance'),
(26, 'fantasy'),
(26, 'mystery'),
(26, 'drama'),
(27, 'adventure'),
(27, 'magic'),
(27, 'sci-fi'),
(28, 'mystery'),
(28, 'thriller'),
(28, 'drama'),
(29, 'fantasy'),
(29, 'romance'),
(29, 'action'),
(30, 'sci-fi'),
(30, 'thriller'),
(30, 'adventure'),
(31, 'fantasy'),
(31, 'drama'),
(31, 'mystery'),
(32, 'romance'),
(32, 'friendship'),
(32, 'fantasy'),
(33, 'thriller'),
(33, 'mystery'),
(33, 'suspense'),
(34, 'crime'),
(34, 'drama'),
(34, 'action'),
(35, 'mystery'),
(35, 'adventure'),
(35, 'romance'),
(36, 'thriller'),
(36, 'romance'),
(36, 'drama'),
(37, 'fantasy'),
(37, 'sci-fi'),
(37, 'adventure'),
(38, 'mystery'),
(38, 'drama'),
(38, 'romance'),
(39, 'thriller'),
(39, 'mystery'),
(39, 'drama'),
(40, 'fantasy'),
(40, 'romance'),
(40, 'adventure');


-- Categories
INSERT INTO categories (name) VALUES
('Mystery'),
('Romance'),
('Drama'),
('Fantasy'),
('Comedy'),
('Horror'),
('Thriller'),
('Adventure'),
('Sci-Fi'),
('Animation');

-- Movie-Categories relations
INSERT INTO movie_categories (movie_id, category_id) VALUES
(4, 1),  -- Shadow Drift -> Mystery
(4, 3),  -- Shadow Drift -> Drama
(5, 9),  -- Neon Harbor -> Sci-Fi
(5, 7),  -- Neon Harbor -> Thriller
(6, 2),  -- Paper Suns -> Romance
(6, 3),  -- Paper Suns -> Drama
(7, 4),  -- Clockwork Orchard -> Fantasy
(7, 8),  -- Clockwork Orchard -> Adventure
(8, 9),  -- Blue Orchard -> Sci-Fi
(8, 3),  -- Blue Orchard -> Drama
(9, 2),  -- Silent Harbor -> Romance
(9, 3),  -- Silent Harbor -> Drama
(10, 9), -- Iron Meadow -> Sci-Fi
(10, 8), -- Iron Meadow -> Adventure
(11, 7), -- Velvet Signal -> Thriller
(11, 5), -- Velvet Signal -> Comedy
(12, 6), -- Paper Choir -> Horror
(12, 2), -- Paper Choir -> Romance
(13, 7), -- Amber Night -> Thriller
(13, 1), -- Amber Night -> Mystery
(14, 8), -- Copper Sky -> Adventure
(14, 9), -- Copper Sky -> Sci-Fi
(15, 7), -- The Last Semaphore -> Thriller
(15, 4), -- The Last Semaphore -> Fantasy
(16, 3), -- Winter Orchard -> Drama
(16, 2), -- Winter Orchard -> Romance
(17, 4), -- Crimson Thread -> Fantasy
(17, 1), -- Crimson Thread -> Mystery
(18, 8), -- Waking Atlas -> Adventure
(18, 4), -- Waking Atlas -> Fantasy
(19, 1), -- Low Tide Motel -> Mystery
(19, 7), -- Low Tide Motel -> Thriller
(20, 9), -- Orchid Signal -> Sci-Fi
(20, 1), -- Orchid Signal -> Mystery
(21, 4), -- Glass Wolves -> Fantasy
(21, 8), -- Glass Wolves -> Adventure
(22, 8), -- Paper Beacon -> Adventure
(22, 3), -- Paper Beacon -> Drama
(23, 7), -- Midnight Embers -> Thriller
(23, 2), -- Midnight Embers -> Romance
(24, 7), -- Echo Chamber -> Thriller
(24, 1), -- Echo Chamber -> Mystery
(25, 1), -- Stolen Halos -> Mystery
(25, 3), -- Stolen Halos -> Drama
(26, 9), -- Fathomless Post -> Sci-Fi
(26, 2), -- Fathomless Post -> Romance
(27, 4), -- Rust and Roses -> Fantasy
(27, 2), -- Rust and Roses -> Romance
(28, 8), -- Hollow Compass -> Adventure
(28, 9), -- Hollow Compass -> Sci-Fi
(29, 4), -- Paper Skies -> Fantasy
(29, 8), -- Paper Skies -> Adventure
(30, 1), -- The Quiet Cartographer -> Mystery
(30, 3), -- The Quiet Cartographer -> Drama
(31, 4), -- Lantern Boy -> Fantasy
(31, 2), -- Lantern Boy -> Romance
(32, 8), -- Beneath the Ferris -> Adventure
(32, 1), -- Beneath the Ferris -> Mystery
(33, 7), -- The Spare Key -> Thriller
(33, 3), -- The Spare Key -> Drama
(34, 9), -- Second Shore -> Sci-Fi
(34, 3), -- Second Shore -> Drama
(35, 1), -- Glassletters -> Mystery
(35, 4), -- Glassletters -> Fantasy
(36, 7), -- Distant Bell -> Thriller
(36, 3), -- Distant Bell -> Drama
(37, 4), -- Paper Moons -> Fantasy
(37, 2), -- Paper Moons -> Romance
(38, 8), -- Silent Orchard -> Adventure
(38, 1), -- Silent Orchard -> Mystery
(39, 9), -- Rose Engine -> Sci-Fi
(39, 4), -- Rose Engine -> Fantasy
(40, 7), -- Blue Ledger -> Thriller
(40, 3), -- Blue Ledger -> Drama
(41, 8), -- Gilded Harbor -> Adventure
(41, 5), -- Gilded Harbor -> Comedy
(42, 9), -- The Long Knot -> Sci-Fi
(42, 8), -- The Long Knot -> Adventure
(43, 4), -- Ashen Compass -> Fantasy
(43, 1), -- Ashen Compass -> Mystery
(44, 7), -- Moonlit Ledger -> Thriller
(44, 3), -- Moonlit Ledger -> Drama
(45, 9), -- Verdant Signal -> Sci-Fi
(45, 8), -- Verdant Signal -> Adventure
(46, 4), -- The Lantern Makers -> Fantasy
(46, 2), -- The Lantern Makers -> Romance
(47, 8), -- Paper Puppets -> Adventure
(47, 5), -- Paper Puppets -> Comedy
(48, 6), -- Bitter Sugar -> Horror
(48, 3), -- Bitter Sugar -> Drama
(49, 7), -- Fallen Semaphore -> Thriller
(49, 1), -- Fallen Semaphore -> Mystery
(50, 8), -- Marigold Train -> Adventure
(50, 2), -- Marigold Train -> Romance
(51, 8), -- Hidden Oars -> Adventure
(51, 9), -- Hidden Oars -> Sci-Fi
(52, 4), -- The Last Draft -> Fantasy
(52, 3), -- The Last Draft -> Drama
(53, 1), -- Silent Cart -> Mystery
(53, 9), -- Silent Cart -> Sci-Fi
(54, 7), -- Clocktower Letters -> Thriller
(54, 4), -- Clocktower Letters -> Fantasy
(55, 9), -- Neon Ledger -> Sci-Fi
(55, 7), -- Neon Ledger -> Thriller
(56, 4), -- The Saffron Key -> Fantasy
(56, 8), -- The Saffron Key -> Adventure
(57, 1), -- Echo Orchard -> Mystery
(57, 2), -- Echo Orchard -> Romance
(58, 8), -- Paper Pilots -> Adventure
(58, 9), -- Paper Pilots -> Sci-Fi
(59, 1), -- The Hollow Map -> Mystery
(59, 3), -- The Hollow Map -> Drama
(60, 9), -- Amber Compass -> Sci-Fi
(60, 8), -- Amber Compass -> Adventure
(61, 7), -- Velvet Ledger -> Thriller
(61, 3), -- Velvet Ledger -> Drama
(62, 4), -- The Paper Lighthouse -> Fantasy
(62, 8), -- The Paper Lighthouse -> Adventure
(63, 8), -- Midnight Loom -> Adventure
(63, 4), -- Midnight Loom -> Fantasy
(64, 9), -- Rust Harbor -> Sci-Fi
(64, 3), -- Rust Harbor -> Drama
(65, 4), -- The Glass Orchard -> Fantasy
(65, 2), -- The Glass Orchard -> Romance
(66, 1), -- Paper Echoes -> Mystery
(66, 8), -- Paper Echoes -> Adventure
(67, 8), -- Lantern Harbor -> Adventure
(67, 2), -- Lantern Harbor -> Romance
(68, 8), -- Blue Ferris -> Adventure
(68, 4), -- Blue Ferris -> Fantasy
(69, 7), -- The Long Ledger -> Thriller
(69, 3), -- The Long Ledger -> Drama
(70, 4), -- Orchid Clock -> Fantasy
(70, 9), -- Orchid Clock -> Sci-Fi
(71, 8), -- Paper Harbor -> Adventure
(71, 9), -- Paper Harbor -> Sci-Fi
(72, 7), -- The Iron Bell -> Thriller
(72, 1), -- The Iron Bell -> Mystery
(73, 4), -- Folding Moon -> Fantasy
(73, 8), -- Folding Moon -> Adventure
(74, 8), -- The Quiet Loom -> Adventure
(74, 3), -- The Quiet Loom -> Drama
(75, 9), -- Verdant Ledger -> Sci-Fi
(75, 4), -- Verdant Ledger -> Fantasy
(76, 7), -- The Last Lantern -> Thriller
(76, 3), -- The Last Lantern -> Drama
(77, 9), -- Cracked Compass -> Sci-Fi
(77, 8), -- Cracked Compass -> Adventure
(78, 8), -- Moonflower Train -> Adventure
(78, 4), -- Moonflower Train -> Fantasy
(79, 4), -- The Paper Bell -> Fantasy
(79, 3), -- The Paper Bell -> Drama
(80, 7), -- Silent Ledger -> Thriller
(80, 3), -- Silent Ledger -> Drama
(81, 4), -- Velvet Orchard -> Fantasy
(81, 2), -- Velvet Orchard -> Romance
(82, 8), -- Copper Lanterns -> Adventure
(82, 5), -- Copper Lanterns -> Comedy
(83, 9), -- The Hidden Odometer -> Sci-Fi
(83, 3), -- The Hidden Odometer -> Drama
(84, 8), -- Paper Bridges -> Adventure
(84, 4), -- Paper Bridges -> Fantasy
(85, 4), -- The Velvet Key -> Fantasy
(85, 1), -- The Velvet Key -> Mystery
(86, 9), -- Ash Harbor -> Sci-Fi
(86, 8), -- Ash Harbor -> Adventure
(87, 8), -- The Long Lantern -> Adventure
(87, 4), -- The Long Lantern -> Fantasy
(88, 4), -- Paper Clocktower -> Fantasy
(88, 3), -- Paper Clocktower -> Drama
(89, 9), -- Silent Atlas -> Sci-Fi
(89, 8), -- Silent Atlas -> Adventure
(90, 4), -- The Saffron Bell -> Fantasy
(90, 2); -- The Saffron Bell -> Romance
-- insert reviews (movie_id ≤ 90)
INSERT INTO reviews (comment, rating, movie_id, user_id)
VALUES 
('Amazing movie!', 5, 1, 1),
('Mind-bending!', 5, 2, 1),
('Loved the visuals', 4, 3, 2),
('Classic sci-fi', 5, 1, 3),
('Unexpectedly beautiful — stayed with me for days.', 5, 4, 1),
('Solid pacing but the ending felt rushed.', 4, 5, 2),
('Loved the atmosphere and the main actor.', 5, 6, 3),
('Too slow for my taste but well made.', 3, 7, 1),
('One of the best visuals Ive seen this year.', 5, 8, 2),
('Nice premise, mediocre execution.', 3, 9, 3),
('Powerful performances and a moving story.', 5, 10, 1),
('Quirky and charming — highly recommend.', 4, 11, 2),
('Confusing plot but oddly satisfying.', 4, 12, 3),
('Not my style, but technically impressive.', 3, 13, 1),
('A small gem — character work is superb.', 5, 14, 2),
('I wanted to like it more than I did.', 3, 15, 3),
('Beautiful soundtrack and cinematography.', 5, 16, 1),
('Great concept, some pacing issues.', 4, 17, 2),
('Emotional and thoughtful — stayed with me.', 5, 18, 3),
('Predictable, but the cast carries it.', 3, 19, 1),
('A wild ride from start to finish.', 5, 20, 2),
('Charming characters, weak third act.', 4, 21, 3),
('I laughed, I cried — what more to ask?', 5, 22, 1),
('Clever ideas, uneven tone.', 3, 23, 2),
('Gorgeous world-building and pacing.', 5, 24, 3),
('Too many subplots, lost focus.', 2, 25, 1),
('Superb directing, excellent supporting cast.', 5, 26, 2),
('I enjoyed the mystery, great reveals.', 4, 27, 3),
('A bit slow but rewarded patience.', 4, 28, 1),
('Imaginative and heartfelt.', 5, 29, 2),
('Not as deep as it wanted to be.', 3, 30, 3),
('A modern fable with a soulful lead.', 5, 31, 1),
('Tense and gripping in parts.', 4, 32, 2),
('Solid performances; script could be sharper.', 3, 33, 3),
('Visually arresting and surprisingly funny.', 5, 34, 1),
('Fell flat for me despite the hype.', 2, 35, 2),
('A tender look at human relationships.', 5, 36, 3),
('Interesting concept but overlong.', 3, 37, 1),
('Excellent score and a strong lead.', 5, 38, 2),
('Atmospheric and melancholic — enjoyed it.', 4, 39, 3),
('Dark, eerie, and well-acted.', 5, 40, 1),
('Could have used more focus on the subplot.', 3, 41, 2),
('Delightfully weird and original.', 5, 42, 3),
('Some pacing issues but beautiful moments.', 4, 43, 1),
('A satisfying journey with great chemistry.', 5, 44, 2),
('Predictable beats but nice visuals.', 3, 45, 3),
('Quietly powerful — gives you a lot to think about.', 5, 46, 1),
('I enjoyed the world but not the ending.', 3, 47, 2),
('Strong lead performance carries the film.', 4, 48, 3),
('Fun, energetic, and surprisingly heartfelt.', 5, 49, 1),
('A slow burn that eventually pays off.', 4, 50, 2),
('Fantastic concept, uneven script.', 3, 51, 3),
('The visuals were worth the watch alone.', 5, 52, 1),
('Underrated gem — deserves more attention.', 5, 53, 2),
('Not for everyone, but I loved it.', 4, 54, 3),
('Tension builds beautifully; great finale.', 5, 55, 1),
('Interesting characters, forgettable plot.', 3, 56, 2),
('Warm and surprisingly funny.', 4, 57, 3),
('Great setting and mood, slow in places.', 3, 58, 1),
('Emotional beats hit hard — well done.', 5, 59, 2),
('An ambitious movie that mostly succeeds.', 4, 60, 3),
('The world-building is fantastic.', 5, 61, 1),
('Takes a while to get going, then soars.', 4, 62, 2),
('A handful of great scenes, otherwise meh.', 3, 63, 3),
('Charming and well-acted.', 4, 64, 1),
('Clever writing and nice surprises.', 5, 65, 2),
('A bit too arty for my taste but impressive.', 3, 66, 3),
('Comforting and beautifully shot.', 5, 67, 1),
('Not original, but executed well.', 4, 68, 2),
('A touching story with memorable moments.', 5, 69, 3),
('Some tonal issues but enjoyable overall.', 3, 70, 1),
('Creepy in all the right ways.', 5, 71, 2),
('Lovely visuals; story lags at times.', 4, 72, 3),
('Delivers on its premise; satisfying ending.', 5, 73, 1),
('A unique voice — impressive debut style.', 5, 74, 2),
('Enjoyed the setting and the performances.', 4, 75, 3),
('An intriguing hook, mixed follow-through.', 3, 76, 1),
('Warm, inventive, and heartfelt.', 5, 77, 2),
('A poetic film with strong imagery.', 5, 78, 3),
('Good ideas, messy execution.', 2, 79, 1),
('Comforting and sweet; great for a lazy evening.', 4, 80, 2),
('Funny and surprisingly sharp.', 5, 81, 3),
('A bit long, but worth the ride.', 4, 82, 1),
('Haunting score and excellent mood.', 5, 83, 2),
('A gentle, contemplative film — I liked it.', 4, 84, 3),
('Beautifully made, emotionally resonant.', 5, 85, 1),
('Quirky and memorable.', 4, 86, 2),
('Not everything lands, but the risks pay off.', 4, 87, 3),
('A solid, dependable watch.', 4, 88, 1),
('Satisfying and well-paced.', 5, 89, 2);

-- user_liked (20 rows)
INSERT INTO user_liked (user_id, movie_id) VALUES
(1, 4),
(1, 5),
(1, 6),
(2, 7),
(2, 8),
(2, 9),
(3, 10),
(3, 11),
(3, 12),
(1, 13),
(2, 14),
(3, 15),
(1, 16),
(2, 17),
(3, 18),
(1, 19),
(2, 20),
(3, 21),
(1, 22),
(2, 23);

-- user_watched (20 rows)
INSERT INTO user_watched (user_id, movie_id) VALUES
(1, 24),
(1, 25),
(1, 26),
(2, 27),
(2, 28),
(2, 29),
(3, 30),
(3, 31),
(3, 32),
(1, 33),
(2, 34),
(3, 35),
(1, 36),
(2, 37),
(3, 38),
(1, 39),
(2, 40),
(3, 41),
(1, 42),
(2, 43);

-- user_watchlist (20 rows)
INSERT INTO user_watchlist (user_id, movie_id) VALUES
(1, 44),
(1, 45),
(1, 46),
(2, 47),
(2, 48),
(2, 49),
(3, 50),
(3, 51),
(3, 52),
(1, 53),
(2, 54),
(3, 55),
(1, 56),
(2, 57),
(3, 58),
(1, 59),
(2, 60),
(3, 61),
(1, 62),
(2, 63);

-- user_disliked (20 rows)
INSERT INTO user_disliked (user_id, movie_id) VALUES
(1, 64),
(1, 65),
(1, 66),
(2, 67),
(2, 68),
(2, 69),
(3, 70),
(3, 71),
(3, 72),
(1, 73),
(2, 74),
(3, 75),
(1, 76),
(2, 77),
(3, 78),
(1, 79),
(2, 80),
(3, 81),
(1, 82),
(2, 83);
