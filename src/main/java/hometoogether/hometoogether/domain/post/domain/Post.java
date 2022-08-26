package hometoogether.hometoogether.domain.post.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Entity
public abstract class Post {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }
}
