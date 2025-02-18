CREATE SCHEMA IF NOT EXISTS filmplus;

CREATE TABLE filmplus.USER (
    id SERIAL,
    name VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    login VARCHAR NOT NULL,
    birthday DATE,
    /*Органичение первичного ключа опреледяем отдельно, чтобы укзать нормальное имя*/
    CONSTRAINT USER_PK PRIMARY KEY (id)
);

CREATE TABLE filmplus.movie (
    id SERIAL,
    title VARCHAR NOT NULL,
    description TEXT,
    release_date DATE,
    duration int NOT NULL,
    genre VARCHAR,
    CONSTRAINT MOVIE_PK PRIMARY KEY (id)
);

CREATE TABLE filmplus.user_friend (
    user_id INT,
    friend_id INT,
    CONSTRAINT USER_FRIEND_PK PRIMARY KEY (user_id, friend_id),
    CONSTRAINT USER_FRIEND_USER_USER_FK FOREIGN KEY (user_id)
        REFERENCES filmplus.USER(id),
    CONSTRAINT USER_FRIEND_USER_FRIEND_FK FOREIGN KEY (friend_id)
        REFERENCES filmplus.USER(id),
    CONSTRAINT USER_FRIEND_CH1 CHECK (user_id <> friend_id)
);

CREATE TABLE filmplus.LIKE (
    user_id INT,
    movie_id INT,
    CONSTRAINT LIKE_PK PRIMARY KEY (user_id, movie_id),
    CONSTRAINT LIKE_USER_FK FOREIGN KEY (user_id)
        REFERENCES filmplus.USER(id),
    CONSTRAINT LIKE_MOVIE_FK FOREIGN KEY (movie_id)
        REFERENCES filmplus.movie(id)
);

CREATE TABLE filmplus.review (
    id SERIAL,
    user_id INT,
    movie_id INT,
    review_text TEXT,
    review_date DATE,
    CONSTRAINT REVIEW_PK PRIMARY KEY (id),
    CONSTRAINT REVIEW_USER_FK FOREIGN KEY (user_id)
        REFERENCES filmplus.USER(id),
    CONSTRAINT REVIEW_MOVIE_FK FOREIGN KEY (movie_id)
        REFERENCES filmplus.movie(id)
)