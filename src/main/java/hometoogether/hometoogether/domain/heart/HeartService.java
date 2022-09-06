package hometoogether.hometoogether.domain.heart;

import hometoogether.hometoogether.domain.post.domain.Post;
import hometoogether.hometoogether.domain.post.repository.PostRepository;
import hometoogether.hometoogether.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final PostRepository postRepository;
    private final HeartRepository heartRepository;

    @Transactional
    public Long createHeart(User user, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException(""));
        return heartRepository.save(Heart.builder()
                .user(user)
                .post(post)
                .build()).getId();
    }

    @Transactional
    public Long deleteHeart(User user, Long postId) {
        Heart heart = heartRepository.findByUserIdAndPostId(user.getId(), postId)
                .orElseThrow(() -> new RuntimeException(""));
        heartRepository.delete(heart);
        return heart.getId();
    }
}
