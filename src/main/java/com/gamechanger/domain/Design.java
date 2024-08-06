package com.gamechanger.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@Table(name = "design")
public class Design {

    @Id
    private String fileName;
    private double locationX;
    private double locationY;
    private String view;
    private String prompt;
}
