package ru.jabka.filmplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jabka.filmplus.model.Like;
import ru.jabka.filmplus.repository.LikeRepository;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    public void create(Like like) {
        likeRepository.insert(like);
    }
}
