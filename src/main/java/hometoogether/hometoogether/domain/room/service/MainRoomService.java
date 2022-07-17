//package hometoogether.hometoogether.domain.room.service;
//
//import hometoogether.hometoogether.domain.room.domain.Room;
//import hometoogether.hometoogether.util.Parser;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.Optional;
//import java.util.Set;
//import java.util.concurrent.ThreadLocalRandom;
//
//@Service
//@RequiredArgsConstructor
//public class MainRoomService {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    private static final String REDIRECT = "redirect:/room/create";
//
//    private final RoomService roomService;
//    private final Parser parser;
//
//    /**
//     * main 화면 return
//     */
//    public String displayMainPage(Model model, final Long id, final String uuid) {
//
//        Set<Room> rooms = roomService.getRooms();
//        if(!rooms.isEmpty()){
//            model.addAttribute("rooms", rooms);
//        }
//
//        model.addAttribute("id", id);
//        model.addAttribute("uuid", uuid);
//
//        return "chat/create_room";
//    }
//
//    /**
//     * 룸 만들기 요청
//     * 륨 만들어진 main 화면 return
//     */
//    public String processRoomSelection(Model model, final String sid, final String uuid,
//                                       final BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            // simplified version, no errors processing
//            return REDIRECT;
//        }
//        Optional<Long> optionalId = parser.parseId(sid);
//        optionalId.ifPresent(id -> Optional.ofNullable(uuid).ifPresent(name -> roomService.addRoom(new Room(id))));
//
//        return this.displayMainPage(model, optionalId.orElse(null), uuid);
//    }
//
//
//    /**
//     * main화면에서 room 클릭시 실행되며
//     * char_room.html Return
//     */
//    public String displaySelectedRoom(Model model, final String sid, final String uuid) {
//        // redirect to main page if provided data is invalid
//
//        if (parser.parseId(sid).isPresent()) {
//            Room room = roomService.findRoomByStringId(sid).orElse(null);
//            if(room != null && uuid != null && !uuid.isEmpty()) {
//                logger.debug("User {} is going to join Room #{}", uuid, sid);
//                // open the chat room
//                model.addAttribute("id", sid);
//                model.addAttribute("uuid", uuid);
//            }
//        }
//
//        return "chat/chat_room";
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
//            // implement any logic you need
//        }
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
//}
