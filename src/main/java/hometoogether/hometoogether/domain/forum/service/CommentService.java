package hometoogether.hometoogether.domain.forum.service;

import hometoogether.hometoogether.domain.forum.domain.comment.Comment;
import hometoogether.hometoogether.domain.forum.domain.comment.CommentRequestDto;
import hometoogether.hometoogether.domain.forum.domain.comment.CommentResponseDto;
import hometoogether.hometoogether.domain.forum.domain.forum.Forum;
import hometoogether.hometoogether.domain.forum.repository.CommentRepository;
import hometoogether.hometoogether.domain.forum.repository.ForumRepository;
import hometoogether.hometoogether.exception.CustomException;
import hometoogether.hometoogether.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ForumRepository forumRepository;

    public List<CommentResponseDto> getForumCommentList(Long forumId) {
        Forum forum = forumRepository.findById(forumId).get();
        List<Comment> commentsByForum = commentRepository.findCommentsByForumId(forumId);
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        for (Comment c : commentsByForum) {
            commentResponseDtos.add(new CommentResponseDto(c));
        }
        return commentResponseDtos;
    }

    @Transactional
    public Long saveComment(Long forumId, CommentRequestDto commentRequestDto) {
        Optional<Forum> forum = forumRepository.findById(forumId);
        Comment comment = commentRepository.save(commentRequestDto.toEntity());
        return comment.getCommentId();
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public Long updateComment(Long commentId, CommentRequestDto param) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENTS_NOT_FOUND)
        );
        comment.update(param.getContents(), param.getWriter());
        return commentId;
    }

    public Long userCommentsCount(String userName) {
        return Long.valueOf(commentRepository.findCommentsByWriter(userName).size());
    }

    @Transactional
    public void deleteCommentsByUser(String userName) {
        commentRepository.deleteCommentsByWriter(userName);
    }

}
