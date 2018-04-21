package by.issoft.kholodok.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

//@Entity
@Table(name = "certificate")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_id", nullable = false)
    private int id;


    @Column(name = "point")
    private int point;


    @Column(name = "date_of_issue")
    @Temporal(value = TemporalType.DATE)
    private Date dateOfIssue;


    private User user;


    private Subject subject;


}
