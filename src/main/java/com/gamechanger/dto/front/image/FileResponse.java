package com.gamechanger.dto.front.image;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {
    private String imageId;
    private String roomId;
    private String imageUrl;
}
