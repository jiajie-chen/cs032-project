PRAGMA foreign_keys = ON;

CREATE TABLE book (
    book_id TEXT
        PRIMARY KEY ON CONFLICT IGNORE,
    title TEXT
        NOT NULL ON CONFLICT IGNORE,
    year INT
);

CREATE TABLE book_author (
    book_id INT
        REFERENCES book(book_id) ON DELETE CASCADE ON UPDATE CASCADE,
    author TEXT
        NOT NULL ON CONFLICT IGNORE
        DEFAULT "Unknown",

    UNIQUE (book_id, author) ON CONFLICT IGNORE
);

CREATE TABLE book_facet (
    book_id INT
        REFERENCES book(book_id) ON DELETE CASCADE ON UPDATE CASCADE,
    facet TEXT
        NOT NULL ON CONFLICT IGNORE,

    UNIQUE (book_id, facet) ON CONFLICT IGNORE
);

CREATE TABLE location (
    region TEXT
        PRIMARY KEY ON CONFLICT IGNORE,
    latitude REAL,
    longitude REAL,

    UNIQUE (latitude, longitude) ON CONFLICT IGNORE
);

CREATE TABLE book_location (
    book_id INT
        REFERENCES book(book_id) ON DELETE CASCADE ON UPDATE CASCADE,
    region TEXT
        REFERENCES location(region) ON DELETE CASCADE ON UPDATE CASCADE,

    UNIQUE (book_id, region) ON CONFLICT IGNORE
);

CREATE TABLE synonym (
    word TEXT
        NOT NULL ON CONFLICT IGNORE,
    synonym TEXT
        NOT NULL ON CONFLICT IGNORE,

    UNIQUE (word, synonym) ON CONFLICT IGNORE
);

CREATE INDEX year_index ON book(year);
CREATE INDEX title_index ON book(title);

CREATE INDEX book_author_index ON book_author(book_id);
CREATE INDEX author_index ON book_author(author);

CREATE INDEX book_facet_index ON book_facet(book_id);
CREATE INDEX facet_index ON book_facet(facet);

CREATE INDEX latitude_index ON location(latitude);
CREATE INDEX longitude_index ON location(longitude);

CREATE INDEX book_location_index ON book_location(book_id);
CREATE INDEX location_book_index ON book_location(region);

CREATE INDEX synonym_index ON synonym(word);

.mode csv
.import book.csv book
.import book_author.csv book_author
.import book_facet.csv book_facet
.import location.csv location
.import book_location.csv book_location
.import synonym.csv synonym
