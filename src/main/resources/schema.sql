CREATE TABLE IF NOT EXISTS offers (
    id SERIAL PRIMARY KEY,
    owner_id INTEGER,
    title TEXT,
    description TEXT,
    creation_timestamp TIMESTAMP,
    is_archived BOOLEAN
);

CREATE TABLE IF NOT EXISTS tags (
    name TEXT PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS offers_tags (
    offer_id SERIAL NOT NULL,
    tag_name TEXT NOT NULL,
    PRIMARY KEY(offer_id, tag_name),
    CONSTRAINT FK_OFFER FOREIGN KEY (offer_id) REFERENCES offers(id),
    CONSTRAINT FK_TAG FOREIGN KEY (tag_name) REFERENCES tags(name)
);

CREATE TABLE IF NOT EXISTS tiers (
    id SERIAL PRIMARY KEY,
    offer_id SERIAL NOT NULL,
    title TEXT,
    description TEXT,
    price NUMERIC,
    delivery_time INTEGER,
    CONSTRAINT FK_OFFER FOREIGN KEY (offer_id) REFERENCES offers(id)
);

CREATE TABLE IF NOT EXISTS thumbnails (
    id SERIAL PRIMARY KEY,
    offer_id SERIAL NOT NULL,
    url TEXT NOT NULL,
    CONSTRAINT FK_OFFER FOREIGN KEY (offer_id) REFERENCES offers(id)
);
