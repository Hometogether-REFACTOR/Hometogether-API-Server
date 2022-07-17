package hometoogether.hometoogether.domain.forum.domain.forum;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ForumRequestDto {

    private String title;
    private String writer;
    private String contents;
    private char type;
    private char delYn;

    public Forum toEntity() {
        return Forum.builder()
                .title(title)
                .contents(contents)
                .delYn(delYn)
                .hits(0)
                .type(type)
                .writer(writer)
                .build();
    }

}
