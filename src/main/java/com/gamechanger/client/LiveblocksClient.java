package com.gamechanger.client;

import com.gamechanger.config.LiveblocksConfig;
import com.gamechanger.dto.front.room.GetImageInfoFromRoomDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "liveblocksClient", url = "https://api.liveblocks.io/v2", configuration = LiveblocksConfig.class)
public interface LiveblocksClient {

    @PostMapping("/rooms")
    String createRoom(@RequestBody Map<String, Object> roomData);

    @GetMapping("/rooms/{roomId}/storage")
    GetImageInfoFromRoomDto getStorage(@PathVariable String roomId);

    @DeleteMapping("/rooms/{roomId}")
    void deleteRoom(@PathVariable String roomId);

}
