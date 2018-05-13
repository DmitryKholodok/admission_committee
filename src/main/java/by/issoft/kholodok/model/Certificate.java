package by.issoft.kholodok.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "certificate")
public class Certificate {

    @Id
    @Column(name = "certificate_id")
    private int id;

    @Column(name = "point")
    private int point;

    @Column(name = "date_of_issue")
    @Temporal(value = TemporalType.DATE)
    private Date dateOfIssue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private EnrolleeData enrolleeData;

    @OneToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

}
