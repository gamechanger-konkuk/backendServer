package com.gamechanger.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clothes")
public class Clothes {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long systemClothesId;
    @Column(length = 16)
    @NotNull
    private String clothesName;
    private String roomId;
    @Column(length = 10)
    @NotNull
    private String shape;   // 기본 : 반팔 char나 int로 바이트 수 줄여도 됨
    @Column(length = 20)
    @NotNull
    private String background;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "login_id")
    private User user;

    @OneToMany(mappedBy = "clothes", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @MapKey(name = "fileName")
    private Map<String, Image> imageFileList = new HashMap<>();

    public void addImageFile(Image image) {
        imageFileList.put(image.getFileName(), image);
    }

    public void deleteImageFile(String fileName) {
        imageFileList.remove(fileName);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Clothes{" +
                "clothesName='" + clothesName + '\'' +
                ", userLoginId='" + user.getLoginId() + '\'' +
                ", shape='" + shape + '\'' +
                ", background='" + background + '\'' +
                ", fileList.size()='" + imageFileList.size() + '\'');
        for (int i = 0; i < imageFileList.size(); i++) {
            str.append(", image ").append(i).append("= ").append(imageFileList.get(i).toString());
        }
        return str.toString();
    }
}
