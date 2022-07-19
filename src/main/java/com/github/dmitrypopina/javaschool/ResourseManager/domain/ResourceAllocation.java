package com.github.dmitrypopina.javaschool.ResourseManager.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;

@Entity
@Table
@Data
public class ResourceAllocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Resource resource;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate allocationDate;

    @Column(nullable = false)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime allocationStartTime;

    @Column(nullable = false)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime allocationEndTime;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
