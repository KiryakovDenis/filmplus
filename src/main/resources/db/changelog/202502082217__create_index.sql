CREATE INDEX user_friend_user_user_fki ON filmplus.user_friend(user_id);
CREATE INDEX user_friend_user_friend_fki ON filmplus.user_friend(friend_id);
CREATE INDEX like_movie_fki ON filmplus.like(movie_id);
CREATE INDEX like_user_fki ON filmplus.like(user_id);
CREATE INDEX review_movie_fki ON filmplus.review(movie_id);
CREATE INDEX review_user_fk_fki ON filmplus.review(user_id);