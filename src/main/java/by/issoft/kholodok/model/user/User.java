package by.issoft.kholodok.model.user;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    @Enumerated(EnumType.ORDINAL)
    private GenderEnum genderEnum;

    @Column(name = "tel")
    private String tel;

    @OneToOne(fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.DELETE, CascadeType.PERSIST })
    @PrimaryKeyJoinColumn
    private UserAuth userAuth = new UserAuth(this);

    public User() {
    }

}
