package com.gamechanger.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "design")
public class Design {

    @Id
    private String fileName;
    private double locationX;
    private double locationY;
    private String view;
    private String prompt;
}
