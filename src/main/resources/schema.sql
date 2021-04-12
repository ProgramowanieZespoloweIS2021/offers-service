DROP TABLE IF EXISTS offers_tags;
DROP TABLE IF EXISTS offers;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS tiers;

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
    price NUMERIC
);
