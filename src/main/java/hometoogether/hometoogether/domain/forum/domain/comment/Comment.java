package hometoogether.hometoogether.domain.forum.domain.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "COMMENT")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "forum_id", nullable = false)
    private Long forumId;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "writer", nullable = false)
    private String writer;

    @Column(name = "del_yn", nullable = false)
    private char delYN;

//    @ManyToOne
//    @JoinColumn(name = "forum_id")
//    private Forum forum;

    @Builder
    public Comment(String writer, String contents, char delYn, Long forumId) {
        this.writer = writer;
        this.contents = contents;
        this.delYN = delYn;
        this.forumId = forumId;
    }

    public void update(String contents, String writer) {
        this.writer = writer;
        this.contents = contents;
        this.updateDate = LocalDateTime.now();
    }

}
