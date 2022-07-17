package hometoogether.hometoogether.domain.forum.domain.comment;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long commentId;
    private String writer;
    private String contents;
    private char delYn;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public CommentResponseDto(Comment entity) {
        this.commentId = entity.getCommentId();
        this.writer = entity.getWriter();
        this.contents = entity.getContents();
        this.delYn = entity.getDelYN();
        this.createDate = entity.getCreateDate();
        this.updateDate = entity.getUpdateDate();
    }
}
