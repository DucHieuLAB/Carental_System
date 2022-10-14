package com.example.create_entity.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "booking_had_drivers")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BookingHadDriverEntity {
    @Id
    @Column(name = "HadDriver_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private BookingEntity bookingEntity;

    @Column(name = "pickup_district_id")
    private long pickup_district_id;

    @Column(name = "return_district_id")
    private long return_district_id;

    @Column(name = "pickup_address")
    private String pickup_address;

    @Column(name = "is_one_way")
    private boolean is_one_way;

    @Column(name = "return_address")
    private String return_address;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private Date lastModifiedDate;
}
