package com.gamechanger.dto.front.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetImageInfoFromRoomDto {
    @JsonProperty("layerIds")
    @NotNull
    private List<String> imageList;
    @JsonProperty("layers")
    @NotNull
    private Map<String, ImageInfoDto> imageInfo;
}
