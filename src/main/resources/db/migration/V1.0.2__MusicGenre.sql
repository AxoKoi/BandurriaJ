
-- create new table
CREATE CACHED TABLE "PUBLIC"."MUSIC_GENRE"(
                                        "ID" BIGINT NOT NULL PRIMARY KEY,
                                        "NAME" VARCHAR(255)
);

ALTER TABLE "PUBLIC"."MUSIC_GENRE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_12" UNIQUE("NAME");

-- create join table disc/music genre
CREATE CACHED TABLE "PUBLIC"."DISC_GENRES"(
                                                        "DISC_ID" BIGINT NOT NULL,
                                                        "GENRES_ID" BIGINT NOT NULL
);
ALTER TABLE "PUBLIC"."DISC_GENRES" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_13" PRIMARY KEY("DISC_ID", "GENRES_ID");

ALTER TABLE "PUBLIC"."DISC_GENRES" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_15" FOREIGN KEY("GENRES_ID")
    REFERENCES "PUBLIC"."MUSIC_GENRE"("ID") NOCHECK;

ALTER TABLE "PUBLIC"."DISC_GENRES" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_16" FOREIGN KEY("DISC_ID")
    REFERENCES "PUBLIC"."DISC"("ID") NOCHECK;

