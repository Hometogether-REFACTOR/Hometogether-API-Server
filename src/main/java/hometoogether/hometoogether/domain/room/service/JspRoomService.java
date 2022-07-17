//package hometoogether.hometoogether.domain.room.service;
//
//import hometoogether.hometoogether.domain.room.domain.Room;
//import hometoogether.hometoogether.domain.room.repository.RoomRepository;
//import hometoogether.hometoogether.util.Parser;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//
//import java.util.List;
//import java.util.concurrent.ThreadLocalRandom;
//
//@Service
//@RequiredArgsConstructor
//public class JspRoomService {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    private static final String REDIRECT = "redirect:/room/create";
//
//    private final RoomRepository roomRepository;
//    private final Parser parser;
//
//    /**
//     * main 화면 return
//     */
//    public String displayMainPage(Model model, final Long max_num, final String uuid) {
//
//        List<Room> rooms = roomRepository.findAll();
//        if(!rooms.isEmpty()){
//            model.addAttribute("rooms", rooms);
//        }
////        model.addAttribute("rooms", roomService.getRooms());
//
//        model.addAttribute("max_num", max_num);
//        model.addAttribute("uuid", uuid);
//
//        return "chat/create_room";
//    }
//
//    /**
//     * 룸 만들기 요청
//     * 륨 만들어진 main 화면 return
//     */
//    public String processRoomSelection(Model model, final String max_num, final String uuid,
//                                       final BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return REDIRECT;
//        }
//
//        Long max = Long.valueOf(max_num);
//        roomRepository.save(new Room(max));
//
//        return this.displayMainPage(model, max, uuid);
//    }
//
//
//    /**
//     * main화면에서 room 클릭시 실행되며
//     * char_room.html Return
//     */
//    public String displaySelectedRoom(Model model, final String sid, final String uuid) {
//        // redirect to main page if provided data is invalid
//        Long id = parser.parseId(sid).orElse(null);
//
//        if (id!=null) {
//
//            Room room = roomRepository.findById(id).orElse(null);
//            if(room != null && uuid != null && !uuid.isEmpty()) {
//                logger.info("The room {} max num: {}", room.getId(), room.getMax_num());
//                logger.info("The room {} cur num: {}", room.getId(), room.getCur_num());
//                if(room.getMax_num()>room.getCur_num()){
//                    logger.debug("User {} is going to join Room #{}", uuid, sid);
//
//                    // open the chat room
//
//                    model.addAttribute("id", sid);
//                    model.addAttribute("uuid", uuid);
//                    return "chat/chat_room";
//                }
//                else{
//                    logger.error("Cannot enter chat room because the room is already full");
//                }
//            }
//            else{
//                logger.error("Cannot enter chat room because of many other reason");
//            }
//        }
//        else{
//            logger.error("Cannot enter chat room because of null id");
//        }
//
//        return REDIRECT;
//    }
//
//    /**
//     * room에서 나갈때 실행되며
//     * redirect:/ 되서  main 화면 return 된다.
//     */
//
//    public String processRoomExit(final String sid, final String uuid) {
//        if(sid != null && uuid != null) {
//            logger.debug("User {} has left Room #{}", uuid, sid);
//
//            Room room = roomRepository.findById(Long.valueOf(sid)).orElse(null);
//
//            System.out.println("Before room cur num setting: " + room.getCur_num());
//            room.setCur_num(room.getCur_num()-1L);
//            System.out.println("After room cur num setting: " + room.getCur_num());
//
//            if(room.getCur_num().equals(0L)){
//                System.out.println("Delete room");
//                roomRepository.delete(room);
//            }
//            else{
//                System.out.println("Edit room info");
//                roomRepository.save(room);
//            }
//        }
//        System.out.println("MainRoomService.processRoomExit");
//        return REDIRECT;
//    }
//
//    /**
//     * RoomNumber Random으로 요청시 발생
//     * 랜덤넘버의 room number 생성하여 main화면 return
//     */
//
//    public String requestRandomRoomNumber(Model model, final String uuid) {
//        return this.displayMainPage(model, randomValue(), uuid);
//    }
//
//    private Long randomValue() {
//        return ThreadLocalRandom.current().nextLong(0, 100);
//    }
//
//    public Room findByRoomId(Long id){
//        return roomRepository.findById(id).orElse(null);
//    }
//}
