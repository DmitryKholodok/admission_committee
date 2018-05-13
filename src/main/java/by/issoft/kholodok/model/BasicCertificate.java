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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "basic_certificate")
public class BasicCertificate {

    @Id
    @Column(name = "bc_id")
    private int id;

    @Column(name = "point")
    private int point;

    @Column(name = "date_of_issue")
    @Temporal(value = TemporalType.DATE)
    private Date dateOfIssue;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private EnrolleeData enrolleeData;

}
