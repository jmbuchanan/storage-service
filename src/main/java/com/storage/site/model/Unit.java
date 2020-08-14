/*
 * 10x20 $80
 * 5x10 $40
*/

package com.storage.site.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class Unit {

    private byte unitNumber;
    private boolean isLarge;
    private boolean isOccupied;
    private boolean isDelinquent;
    private short daysDelinquent;
    private Date startDate;

}

