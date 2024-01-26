package omnivore.bookmark.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import omnivore.bookmark.entity.Bookmark;
import omnivore.bookmark.entity.User;
import omnivore.bookmark.repository.BookmarkRepository;
import omnivore.bookmark.repository.UserRepository;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;

    @Transactional
    public void register (String jwt, String restaurantId) {
        String userEmail = getEmailFromPayload(parseJwtPayload(jwt));
        User user = getUserByEmail(userEmail);
        ObjectId obj_restaurantId = new ObjectId(restaurantId);
        bookmarkRepository.save(Bookmark.builder().userId(user.getId()).restaurantId(obj_restaurantId).build());
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));
    }

    private String parseJwtPayload(String jwt) {
        String base64Payload = jwt.split("\\.")[1];
        byte[] decodedBytes = Base64.getDecoder().decode(base64Payload);
        return new String(decodedBytes);
    }

    private String getEmailFromPayload(String payloadString) {
        JSONObject payloadJson = new JSONObject(payloadString);
        return payloadJson.getString("email");
    }
}
