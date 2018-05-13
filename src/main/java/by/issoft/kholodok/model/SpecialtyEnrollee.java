package by.issoft.kholodok.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by dmitrykholodok on 5/13/18
 */

@Entity
@Table(name = "specialty_enrollee_data")
@Getter
@Setter
public class SpecialtyEnrollee {

    @Id
    @Column(name = "user_id")
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private EnrolleeData enrolleeData;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

}
