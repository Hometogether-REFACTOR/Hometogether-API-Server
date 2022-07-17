package hometoogether.hometoogether.domain.room.controller;

import hometoogether.hometoogether.domain.room.dto.CanEnterAndRoutineIdDto;
import hometoogether.hometoogether.domain.room.dto.HostAndClientDto;
import hometoogether.hometoogether.domain.room.dto.RoomDto;
import hometoogether.hometoogether.domain.room.service.RoomService;
import hometoogether.hometoogether.domain.training.domain.TrainingVO;
//import hometoogether.hometoogether.domain.training.domain.TrainingVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/rooms")
    public List<RoomDto> getRooms(){
        List<RoomDto> rooms = roomService.getRooms();
        return rooms;
    }


    @PostMapping("/room/create/{routineId}/host/{email}")
    public Long createRoom(@RequestBody String routine, @PathVariable Long routineId, @PathVariable String email) {
        return roomService.createRoom(routine, routineId, email);
    }

    @GetMapping("/room/{id}/usable")
    public CanEnterAndRoutineIdDto canEnterRoom(@PathVariable Long id){
        return roomService.canEnterRoom(id);
    }

    @PostMapping("/room/{id}/client/{email}")
    public void addClientToRoomMember(@PathVariable Long id, @PathVariable String email){
        roomService.addClientToRoomMember(id, email);
    }

    @GetMapping("/room/{id}/members")
    public HostAndClientDto getRoomMembers(@PathVariable Long id){
        return roomService.getRoomMembers(id);
    }


    @GetMapping("/room/{id}")
    public List<TrainingVO> getRoom(@PathVariable Long id){
        return roomService.getRoom(id);
    }

    @GetMapping("room/{sid}/user/{uuid}/exit")
    public void exitRoom(@PathVariable("sid") final String sid, @PathVariable("uuid") final String uuid){
        roomService.exitRoom(sid, uuid);
    }
}