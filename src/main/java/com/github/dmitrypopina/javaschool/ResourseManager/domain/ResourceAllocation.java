package com.github.dmitrypopina.javaschool.ResourseManager.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;

@Entity
@Table
public class ResourceAllocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Resource resource;
    @Column(nullable = false)
    private LocalDate allocationDate;
    @Column(nullable = false)
    private LocalTime allocationStartTime;
    @Column(nullable = false)
    private LocalTime allocationEndTime;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
