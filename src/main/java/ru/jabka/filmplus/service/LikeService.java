package ru.jabka.filmplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jabka.filmplus.model.Like;
import ru.jabka.filmplus.repository.LikeRepository;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    @Transactional(rollbackFor = Exception.class)
    public void create(Like like) {
        likeRepository.insert(like);
    }
}