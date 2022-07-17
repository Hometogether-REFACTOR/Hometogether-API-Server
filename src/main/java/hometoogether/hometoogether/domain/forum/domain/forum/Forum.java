package hometoogether.hometoogether.domain.forum.domain.forum;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "FORUM")
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long forumId;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "type", nullable = false)
    private char type;

    @Column(name = "del_yn", nullable = false)
    private char delYn;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "writer", nullable = false)
    private String writer;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "hits", nullable = false)
    private int hits;

    @Builder
    public Forum(String title, String contents, String writer, int hits, char type, char delYn) {
        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.hits = hits;
        this.type = type;
        this.delYn = delYn;
    }

    public void update(String title, String contents, String writer) {
        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.updateDate = LocalDateTime.now();
    }

    public void hitsUpdate() {
        this.hits += 1;
    }

}
