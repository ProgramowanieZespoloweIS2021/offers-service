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
  id SERIAL PRIMARY KEY,
  value TEXT
);

CREATE TABLE offers_tags (
    offer_id SERIAL NOT NULL,
    tag_id SERIAL NOT NULL,
    PRIMARY KEY(offer_id, tag_id),
    CONSTRAINT FK_OFFER FOREIGN KEY (offer_id) REFERENCES offers(id),
    CONSTRAINT FK_TAG FOREIGN KEY (tag_id) REFERENCES tags(id)
);

CREATE TABLE tiers (
    id SERIAL PRIMARY KEY,
    offer_id SERIAL NOT NULL,
    title TEXT,
    description TEXT,
    price NUMERIC
);
