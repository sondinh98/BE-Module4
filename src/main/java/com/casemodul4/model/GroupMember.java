package com.casemodul4.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Group1 group1;

    @ManyToOne
    private UserAcc user;

    private Date joinedDate;

}