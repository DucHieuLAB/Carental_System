package com.example.create_entity.Entity;

import com.example.create_entity.dto.Request.BookingRequest;
import com.example.create_entity.dto.Response.BookingResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BookingEntity {
    @Id
    @Column(name = "booking_id", unique = true)
    private long id;

    @ManyToOne
    @JoinColumn(name = "pickup_parking_id", nullable = false,foreignKey = @ForeignKey(name = "FK_bookings_pickup_parkings"))
    private ParkingEntity pickup_parking;

    @ManyToOne
    @JoinColumn(name = "return_parking_id", nullable = false,foreignKey = @ForeignKey(name = "FK_bookings_return_parkings"))
    private ParkingEntity return_parking;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date expected_start_date;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date expected_end_date;

    @Column
    private String note;

    @Column
    private double expected_rental_price;

    @Column
    private int quantity;

    @Column
    private double deposit_amount;

    @Column
    private boolean had_driver;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false,foreignKey = @ForeignKey(name = "FK_bookings_customer"))
    private AccountEntity customer;

    @Column
    private int status;

    @OneToMany(
            mappedBy = "booking"
    )
    List<BookingDetailEntity> bookingDetailEntityList;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name = "last_modified_date",nullable = false)
    private Date lastModifiedDate;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "create_date",nullable = false)
    private Date createdDate;
    public static BookingResponse convertToBookingResponse(BookingEntity be){
        BookingResponse result = new BookingResponse();
        if (ObjectUtils.isEmpty(be)){
            return null;
        }
        result.setCustomerId(be.getId());
        result.setPickupParkingId(be.getPickup_parking().getId());
        result.setReturnParkingId(be.getReturn_parking().getId());
        result.setExpectedStartDate(be.getExpected_start_date());
        result.setExpectedEndDate(be.getExpected_end_date());
        result.setNote(be.getNote());
        result.setExpectedRentalPrice(be.getExpected_rental_price());
        result.setQuantity(be.getQuantity());
        result.setDepositAmount(be.getDeposit_amount());
        result.setHad_driver(be.isHad_driver());
        result.setCustomerId(be.getCustomer().getID());
        result.setPhoneCustomer(be.customer.getPhone());
        result.setFullName(be.customer.getFullName());
        result.setStatus(be.getStatus());
        result.setCreateDate(be.getCreatedDate());
        result.setLastModifiedDate(be.getLastModifiedDate());

        return result;
    }

}
