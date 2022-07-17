package hometoogether.hometoogether.domain.forum.domain.forum;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ForumResponseDto {

    private Long forumId;
    private String contents;
    private char type;
    private char del_yn;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String writer;
    private String title;
    private int hits;

    public ForumResponseDto(Forum entity) {
        this.forumId = entity.getForumId();
        this.contents = entity.getContents();
        this.type = entity.getType();
        this.del_yn = entity.getDelYn();
        this.createDate = entity.getCreateDate();
        this.updateDate = entity.getUpdateDate();
        this.writer = entity.getWriter();
        this.title = entity.getTitle();
        this.hits = entity.getHits();
    }
}
