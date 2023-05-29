CREATE TABLE TB_TUTOR (
id SERIAL NOT NULL PRIMARY KEY,
full_name VARCHAR(150) NOT NULL,
email VARCHAR(50) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
phone VARCHAR(11),
city VARCHAR(100),
about TEXT,
profile_pic VARCHAR(255)
)