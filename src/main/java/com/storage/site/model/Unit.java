/*
 * 10x20 $80
 * 5x10 $40
*/

package com.storage.site.model;

import lombok.Data;
import javax.persistence.*;

import java.util.Date;

@Entity(name = "units")
@Table
@Data
public class Unit {

    @Id
    @Column(name = "unit_id", unique= true)
    private Long unitNumber;

    @Column(name = "is_large")
    private boolean isLarge;

    @Column(name = "is_occupied")
    private boolean isOccupied;

    @Column(name = "is_delinquent")
    private boolean isDelinquent;

    @Column(name = "days_delinquent")
    private Long daysDelinquent;

    @Column(name = "start_date")
    private Date startDate;

}

