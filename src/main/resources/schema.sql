DROP TABLE IF EXISTS offers_tags;
DROP TABLE IF EXISTS thumbnails;
DROP TABLE IF EXISTS tiers;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS offers;

CREATE TABLE offers (
    id SERIAL PRIMARY KEY,
    owner_id INTEGER,
    title TEXT,
    description TEXT,
    creation_timestamp TIMESTAMP,
    is_archived BOOLEAN
);

CREATE TABLE tags (
    name TEXT PRIMARY KEY
);

CREATE TABLE offers_tags (
    offer_id SERIAL NOT NULL,
    tag_name TEXT NOT NULL,
    PRIMARY KEY(offer_id, tag_name),
    CONSTRAINT FK_OFFER FOREIGN KEY (offer_id) REFERENCES offers(id),
    CONSTRAINT FK_TAG FOREIGN KEY (tag_name) REFERENCES tags(name)
);

CREATE TABLE tiers (
    id SERIAL PRIMARY KEY,
    offer_id SERIAL NOT NULL,
    title TEXT,
    description TEXT,
    price NUMERIC,
    delivery_time INTEGER,
    CONSTRAINT FK_OFFER FOREIGN KEY (offer_id) REFERENCES offers(id)
);

CREATE TABLE thumbnails (
    id SERIAL PRIMARY KEY,
    offer_id SERIAL NOT NULL,
    url TEXT NOT NULL,
    CONSTRAINT FK_OFFER FOREIGN KEY (offer_id) REFERENCES offers(id)
);
