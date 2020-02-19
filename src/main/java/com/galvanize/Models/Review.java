package com.galvanize.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastUpdated;
    private String reviewText;
    private String reviewTitle;
    private Long movieId;
    private Long reviewerId;
}
