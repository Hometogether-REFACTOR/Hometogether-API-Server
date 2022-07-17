package hometoogether.hometoogether.domain.room.domain;

//import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.domain.training.domain.Training;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Setter @Getter
    private Long max_num;

    @Setter @Getter
    private Long cur_num;

    @Setter @Getter
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="routine_id")
    private List<Training> trainings;

    @Getter
    private Long routineId;

    @Getter @Setter
    private String hostUserName;

    @Getter @Setter
    private String clientUserName;

    public Room(Long num) {
        this.max_num = num;
        this.cur_num=0L;
    }

    public Long getId() {
        return id;
    }

//    public Map<String, WebSocketSession> getClients() {
//        // 위의 client 문제 때문에 임의로 getClients 함수 안에 clients 변수를 설정함 나중에 복구 예정
//        Map<String, WebSocketSession> clients = new HashMap<>();
//        return clients;
//    }

//    @Override
//    public boolean equals(final Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        final Room room = (Room) o;
//        return Objects.equals(getId(), room.getId()) &&
//                Objects.equals(getClients(), room.getClients());
//    }

//    @Override
//    public int hashCode() {
//
//        return Objects.hash(getId(), getClients());
//    }

    @Builder
    public Room(Long cur_num, Long max_num, Long routineId, List<Training> trainings,
                String hostUserName, String clientUserName){

        this.cur_num=cur_num;
        this.max_num=max_num;
        this.routineId=routineId;
        this.trainings=trainings;
        this.hostUserName=hostUserName;
        this.clientUserName=clientUserName;
    }
}