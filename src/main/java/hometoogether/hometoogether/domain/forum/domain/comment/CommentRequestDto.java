package hometoogether.hometoogether.domain.forum.domain.comment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentRequestDto {

    private Long forumId;
    private String writer;
    private String contents;
    private char delYn;

    public Comment toEntity() {
        return Comment.builder()
                .writer(writer)
                .contents(contents)
                .delYn(delYn)
                .forumId(forumId)
                .build();
    }
}
