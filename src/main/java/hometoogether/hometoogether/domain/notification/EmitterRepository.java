package hometoogether.hometoogether.domain.notification;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EmitterRepository {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

    public SseEmitter saveEmitter(String id, SseEmitter sseEmitter) {
        emitters.put(id, sseEmitter);
        return sseEmitter;
    }

    public void saveEventCache(String id, Object event) {
        eventCache.put(id, event);
    }

    public Map<String, SseEmitter> findEmittersByUserId(String userId) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(userId + "_"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, Object> findEventCachesByUserId(String userId) {
        return eventCache.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(userId + "_"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void deleteEmitterById(String id) {
        emitters.remove(id);
    }

    public void deleteEmittersWithUserId(String userId) {
        emitters.forEach(
                (key, emitter) -> {
                    if (key.startsWith(userId + "_")) {
                        emitters.remove(key);
                    }
                }
        );
    }

    public void deleteEventCachesWithId(String userId) {
        eventCache.forEach(
                (key, data) -> {
                    if (key.startsWith(userId + "_")) {
                        eventCache.remove(key);
                    }
                }
        );
    }
}
