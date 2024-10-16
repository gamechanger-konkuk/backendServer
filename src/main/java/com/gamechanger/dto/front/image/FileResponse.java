package com.gamechanger.dto.front.image;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {
    private String imageId;
    private String roomId;
    private String imageUrl;
}
