package com.gamechanger.service;

import com.gamechanger.client.LiveblocksClient;
import com.gamechanger.dto.front.room.GetImageInfoFromRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class LiveblocksService {

    private final LiveblocksClient liveblocksClient;

    private final JSONParser parser = new JSONParser();

    public String createRoom(String roomId, String[] defaultAccesses) throws ParseException {
        Map<String, Object> requestBody = Map.of(
                "id", roomId,
                "defaultAccesses", defaultAccesses
        );
        String createResponseJson = liveblocksClient.createRoom(requestBody);
        JSONObject object = (JSONObject) parser.parse(createResponseJson);
        return (String) object.get("id");
    }

    public String getStorage(String roomId) {
        GetImageInfoFromRoom imageInfoFromRoom = liveblocksClient.getStorage(roomId);
        // 룸에 데이터 어떻게 저장될지 확정되면 마저 구현하기
        // 룸-clothes 일대일 대응이니 룸에는 clothes 멤버 데이터가 들어갈 것임
        // 룸의 각 layer는 clothes의 각 요소(image)와 일대일 대응이니 layer에는 image 멤버 데이터가 들어갈 것임
        return null;
    }

    public void deleteRoom(String roomId) {
        liveblocksClient.deleteRoom(roomId);
    }
}
