package com.gamechanger.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image")
public class Image {
    @Id
    @Column(length = 100)
    @NotNull
    private String fileName;
    @NotNull
    private String fileUrl;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "system_clothes_id")
    private Clothes clothes;

    @PreRemove
    public void removeImageFromClothes() {
        clothes.deleteImageFile(this.fileName);
    }

    @Override
    public String toString() {
        return "Image{" +
                ", fileUrl='" + fileUrl + '\'' +
                ", clothesName='" + clothes.getClothesName() + '\'' +
                '}';
    }
}
