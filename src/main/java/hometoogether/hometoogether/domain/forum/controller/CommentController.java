package hometoogether.hometoogether.domain.forum.controller;

import hometoogether.hometoogether.domain.forum.domain.comment.CommentRequestDto;
import hometoogether.hometoogether.domain.forum.domain.comment.CommentResponseDto;
import hometoogether.hometoogether.domain.forum.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/forums")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}/comments")
    public List<CommentResponseDto> getForumComments(@PathVariable("id") Long forumId) {
        return commentService.getForumCommentList(forumId);
    }

    @PostMapping("/{id}/comments")
    public Long save(@PathVariable("id") Long forumId, @RequestBody CommentRequestDto params) {
        return commentService.saveComment(forumId, params);
    }

    @DeleteMapping("/{id}/comments/{commentId}")
    public String delete(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return "Comment Delete Success";
    }

    @PatchMapping("/{id}/comments/{commentId}")
    public Long update(@PathVariable("commentId") Long commentId, @RequestBody CommentRequestDto params) {
        return commentService.updateComment(commentId, params);
    }

    @GetMapping("/comments/count/{userName}")
    public Long getForumCount(@PathVariable("userName") String userName) {
        return commentService.userCommentsCount(userName);
    }

//    @DeleteMapping("/comments/{userName}")
//    public String deleteCommentsByUser(@PathVariable("userName") String userName) {
//        commentService.deleteCommentsByUser(userName);
//        return "SUCCESS";
//    }
}
