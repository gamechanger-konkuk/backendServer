package com.gamechanger.dto.front.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageInfoDto {
    private String fileName;
    private String fileUrl;
    private int type;
    private double x;
    private double y;
    private double height;
    private double width;
}
