package by.issoft.kholodok.model;

import by.issoft.kholodok.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import java.util.Set;

@Getter
@Setter
@Entity(name = "enrollee_data")
public class EnrolleeData {

    @Id
    @Column(name = "user_id", nullable = false)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Valid
    @OneToOne(mappedBy = "enrolleeData", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private BasicCertificate basicCertificate;

    @Valid
    @OneToMany(mappedBy = "enrolleeData", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Certificate> certificateSet;

}
