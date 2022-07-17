package hometoogether.hometoogether.socket;

import hometoogether.hometoogether.domain.room.domain.Room;
import hometoogether.hometoogether.domain.room.domain.WebSocketMessage;
import hometoogether.hometoogether.domain.room.dto.UserCountAndTimeDto;
import hometoogether.hometoogether.domain.room.dto.WebSocketMessageDto;
import hometoogether.hometoogether.domain.room.repository.RoomRepository;
//import hometoogether.hometoogether.domain.room.service.JspRoomService;
import hometoogether.hometoogether.domain.room.service.RoomService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class StompCommandListener {

    private final SimpMessagingTemplate messagingTemplate;
//    private final JwtTokenProvider jwtTokenProvider;
//    private final JspRoomService mainRoomService;
    private final RoomService roomService;
    private final RoomRepository roomRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Getter
    List<String> socketParticipateUserList = new ArrayList<String>();
    List<String> socketSessionList = new ArrayList<String>();

//    private Map<String, String> sessionId2UserEmailMap = new HashMap<>();
//    private Map<String, Match> sessionId2Match = new HashMap<>();
    private Map<String, Room> sessionId2Room = new HashMap<>();
    private Map<String, Room> uuid2Room = new HashMap<>();
    private Map<String, String> sessionId2uuid = new HashMap<>();


    private static final String MSG_TYPE_OFFER = "offer";
    private static final String MSG_TYPE_ANSWER = "answer";
    private static final String MSG_TYPE_ICE = "ice";
    private static final String MSG_TYPE_JOIN = "join";
    private static final String MSG_TYPE_LEAVE = "leave";


    @EventListener
    public void handleConnectEvent(SessionConnectEvent event) {
        try {
            socketParticipateUserList.add(event.getUser().getName());
        }catch(Exception e) {

        }
        socketSessionList.add(event.getMessage().getHeaders().get("simpSessionId").toString());
    }


    @EventListener
    public void handleDisconnectEvent(SessionDisconnectEvent event){
        System.out.println("StompCommandListener.handleDisconnectEvent");
        try {
            socketParticipateUserList.remove(event.getUser().getName());
        }catch(Exception e) {

        }
        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();

        socketSessionList.remove(sessionId);

        if(sessionId2Room.containsKey(sessionId)) {
            Room room = sessionId2Room.get(sessionId);
            removeUserFromRoom(sessionId);

            int count = countUserOfRoom(room);
            logger.info("Disconnect : 현재 이 방의 클라이언트 수: {}", count);
            if (count <= room.getMax_num()-1L) {

                if(count==0 && roomRepository.findById(room.getId()).isPresent()) roomRepository.delete(room);

                for (Map.Entry<String, Room> cli : uuid2Room.entrySet()) {
                    sendMessage(cli.getKey(), new WebSocketMessage(
                            "Server",
                            MSG_TYPE_JOIN,
                            "false",
                            null,
                            null));
                }
            }
        }

    }

    @GetMapping("/room/{id}/info")
    @ResponseBody
    public boolean getRoomInfo(@PathVariable String id){
        Long max_num = roomRepository.findById(Long.valueOf(id)).get().getMax_num();
        Long cur_num = socketParticipateUserList.stream().count();

        return max_num>cur_num ? true : false;
    }

    @GetMapping("/start")
    @ResponseBody
    public UserCountAndTimeDto test(){

        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time=new Date();
        String currentTime = format.format(time);
        Long userCount=socketParticipateUserList.stream().count();

        return UserCountAndTimeDto.builder()
                .count(userCount).time(currentTime)
                .build();
    }

    @MessageMapping("/video-signal")
    public void handleTextMessage(WebSocketMessageDto textMessage, SimpMessageHeaderAccessor headerAccessor) {
        WebSocketMessage message = textMessage.toEntity();
//        Authentication authentication = getAuthentication(headerAccessor);
//        logger.info(authentication.getName());
        String sessionId = headerAccessor.getSessionId();

        logger.debug("[ws] Message of {} type from {} received", message.getType(), message.getFrom());
        String userName = message.getFrom(); // origin of the message,, uuid
        String data = message.getData(); // payload

        Room room;
        switch (message.getType()) {
            case MSG_TYPE_OFFER:
            case MSG_TYPE_ANSWER:
            case MSG_TYPE_ICE:
                Object candidate = message.getCandidate();
                Object sdp = message.getSdp();
                room = sessionId2Room.get(sessionId);

                if(room != null) {
                    List<String> uuidOfMatch = uuidListOfRoom(room);

                    uuidOfMatch.forEach(
                            (uuid)-> {
                                if(!uuid.equals(userName)) {
                                    sendMessage(
                                            uuid, new WebSocketMessage(
                                                    userName,
                                                    message.getType(),
                                                    data,
                                                    candidate,
                                                    sdp)
                                    );
                                }});

                }

                break;

            case MSG_TYPE_JOIN:
                logger.info("[ws] {} has joined Room: #{}", userName, message.getData());

                room = roomService.findByRoomId(Long.valueOf(data));
                room.setCur_num(room.getCur_num()+1L);
                roomRepository.save(room);

//                sessionId2UserEmailMap.put(sessionId, authentication.getName());
                sessionId2Room.put(sessionId, room);
                uuid2Room.put(userName, room);
                sessionId2uuid.put(sessionId, userName);

//                int userCount = countUserOfMatch(match);
                long userCount=socketParticipateUserList.stream().count();
                logger.info("Join: 현재 {} 방의 클라이언트 수: {}", room.getId(), userCount);

                String offerData = "true";

                if(userCount <= room.getMax_num()-1L) {
                    offerData= "false";
                }

                sendMessage(userName, new WebSocketMessage(
                        "Server",
                        MSG_TYPE_JOIN,
                        offerData,
                        null,
                        null));


                break;

            case MSG_TYPE_LEAVE:
                logger.info("[ws] {} is going to leave Room: #{}", userName, message.getData());

                break;

            default:
                logger.debug("[ws] Type of the received message {} is undefined!", message.getType());
        }

    }

    private void sendMessage(String uuid, WebSocketMessage message) {
        System.out.println("StompCommandListener.sendMessage");
        messagingTemplate.convertAndSend("/sub/video-signal/" + uuid, message);
    }

//    private Authentication getAuthentication(SimpMessageHeaderAccessor accessor) {
//        String token = accessor.getFirstNativeHeader("Authorization");
//        return jwtTokenProvider.getAuthentication(token);

//    }
//
//
//    @EventListener
//    public void handleDisconnectEvent(SessionDisconnectEvent event){
//        System.out.println("StompCommandListener.handleDisconnectEvent");
//        try {
//            socketParticipateUserList.remove(event.getUser().getName());
//        }catch(Exception e) {
//
//        }
//        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();
//
//        socketSessionList.remove(sessionId);
//
//        if(sessionId2Room.containsKey(sessionId)) {
//            Room room = sessionId2Room.get(sessionId);
//            removeUserFromRoom(sessionId);
//
//            int count = countUserOfRoom(room);
//            logger.info("Disconnect : 현재 이 방의 클라이언트 수: {}", count);
//            if (count <= room.getMax_num()-1L) {
//
//                if(count==0 && roomRepository.findById(room.getId()).isPresent()) roomRepository.delete(room);
//
//                for (Map.Entry<String, Room> cli : uuid2Room.entrySet()) {
//                    sendMessage(cli.getKey(), new WebSocketMessage(
//                            "Server",
//                            MSG_TYPE_JOIN,
//                            "false",
//                            null,
//                            null));
//                }
//            }
//        }
//
//    }
//
//    @GetMapping("/room/{id}/info")
//    @ResponseBody
//    public boolean getRoomInfo(@PathVariable String id){
//        Long max_num = roomRepository.findById(Long.valueOf(id)).get().getMax_num();
//        Long cur_num = socketParticipateUserList.stream().count();
//
//        return max_num>cur_num ? true : false;
//    }
//
//    @GetMapping("/start")
//    @ResponseBody
//    public UserCountAndTimeDto test(){
//
////        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////        Date time=new Date();
////        String currentTime = format.format(time);
//        Long currentTimeMillis = System.currentTimeMillis();
//        Long userCount=socketParticipateUserList.stream().count();
//
//        return UserCountAndTimeDto.builder()
//                .count(userCount).time(currentTimeMillis)
//                .build();
//    }
//
//    @MessageMapping("/video-signal")
//    public void handleTextMessage(WebSocketMessageDto textMessage, SimpMessageHeaderAccessor headerAccessor) {
//        WebSocketMessage message = textMessage.toEntity();
////        Authentication authentication = getAuthentication(headerAccessor);
////        logger.info(authentication.getName());
//        String sessionId = headerAccessor.getSessionId();
//
//        logger.debug("[ws] Message of {} type from {} received", message.getType(), message.getFrom());
//        String userName = message.getFrom(); // origin of the message,, uuid
//        String data = message.getData(); // payload
//
//        Room room;
//        switch (message.getType()) {
//            case MSG_TYPE_OFFER:
//            case MSG_TYPE_ANSWER:
//            case MSG_TYPE_ICE:
//                Object candidate = message.getCandidate();
//                Object sdp = message.getSdp();
//                room = sessionId2Room.get(sessionId);
//
//                if(room != null) {
//                    List<String> uuidOfMatch = uuidListOfRoom(room);
//
//                    uuidOfMatch.forEach(
//                            (uuid)-> {
//                                if(!uuid.equals(userName)) {
//                                    sendMessage(
//                                            uuid, new WebSocketMessage(
//                                                    userName,
//                                                    message.getType(),
//                                                    data,
//                                                    candidate,
//                                                    sdp)
//                                    );
//                                }});
//
//                }
//
//                break;
//
//            case MSG_TYPE_JOIN:
//                logger.info("[ws] {} has joined Room: #{}", userName, message.getData());
//
//                room = mainRoomService.findByRoomId(Long.valueOf(data));
//                room.setCur_num(room.getCur_num()+1L);
//                roomRepository.save(room);
//
////                sessionId2UserEmailMap.put(sessionId, authentication.getName());
//                sessionId2Room.put(sessionId, room);
//                uuid2Room.put(userName, room);
//                sessionId2uuid.put(sessionId, userName);
//
////                int userCount = countUserOfMatch(match);
//                long userCount=socketParticipateUserList.stream().count();
//                logger.info("Join: 현재 {} 방의 클라이언트 수: {}", room.getId(), userCount);
//
//                String offerData = "true";
//
//                if(userCount <= room.getMax_num()-1L) {
//                    offerData= "false";
//                }
//
//                sendMessage(userName, new WebSocketMessage(
//                        "Server",
//                        MSG_TYPE_JOIN,
//                        offerData,
//                        null,
//                        null));
//
//
//                break;
//
//            case MSG_TYPE_LEAVE:
//                logger.info("[ws] {} is going to leave Room: #{}", userName, message.getData());
//
//                break;
//
//            default:
//                logger.debug("[ws] Type of the received message {} is undefined!", message.getType());
//        }
//
//    }
//
//    private void sendMessage(String uuid, WebSocketMessage message) {
//        System.out.println("StompCommandListener.sendMessage");
//        messagingTemplate.convertAndSend("/sub/video-signal/" + uuid, message);
//    }
//
////    private Authentication getAuthentication(SimpMessageHeaderAccessor accessor) {
////        String token = accessor.getFirstNativeHeader("Authorization");
////        return jwtTokenProvider.getAuthentication(token);
////    }

    int countUserOfRoom(Room room) {
        int count = 0;

        for(Map.Entry<String, Room> uuidMatch : uuid2Room.entrySet())  {
            if(uuidMatch.getValue().getId() == room.getId()) {
                count++;
            }
        }
        return count;
    }

    List<String> uuidListOfRoom(Room room) {
        List<String> uuidList = new ArrayList<>();

        for(Map.Entry<String, Room> uuidRoom : uuid2Room.entrySet())  {
            if(uuidRoom.getValue().getId() == room.getId()) {
                uuidList.add(uuidRoom.getKey());
            }
        }

        return uuidList;
    }

    public void removeUserFromRoom(String sessionId) {

        sessionId2Room.remove(sessionId);
//        sessionId2UserEmailMap.remove(sessionId);
        String userName = sessionId2uuid.get(sessionId);
        sessionId2uuid.remove(sessionId);
        uuid2Room.remove(userName);
    }
}
