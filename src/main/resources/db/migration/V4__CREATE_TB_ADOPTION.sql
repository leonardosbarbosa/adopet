CREATE TABLE TB_ADOPTION (
id SERIAL NOT NULL PRIMARY KEY,
pet_id BIGINT NOT NULL UNIQUE,
tutor_id BIGINT NOT NULL,
date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
FOREIGN KEY (pet_id) REFERENCES TB_PET(id),
FOREIGN KEY (tutor_id) REFERENCES TB_TUTOR(id)
)