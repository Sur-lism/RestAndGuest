CREATE TABLE guests (
                       id              bigserial  PRIMARY KEY,
                       first_name      varchar NOT NULL,
                       last_name       varchar NOT NULL,
                       email           varchar NOT NULL UNIQUE,
                       phone           varchar NOT NULL UNIQUE
                   );
CREATE TABLE restaurants (
                       id               bigserial  PRIMARY KEY,
                       name             varchar NOT NULL,
                       legal_entity     varchar NOT NULL,
                       inn              varchar NOT NULL UNIQUE,
                       address          varchar NOT NULL
                   );
CREATE TABLE guestRestRelation (
                      guest_id   bigint NOT NULL REFERENCES guests(id)  ON DELETE CASCADE,
                      rest_id    bigint NOT NULL REFERENCES restaurants(id)  ON DELETE CASCADE,
                      UNIQUE(guest_id, rest_id)
                   );