package by.issoft.kholodok.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private int id;


    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "surname", nullable = false)
    private String surname;


    @OneToOne(fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.DELETE, CascadeType.PERSIST})
    @PrimaryKeyJoinColumn
    private UserAuthData userAuthData = new UserAuthData(this);


    public User() {
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public UserAuthData getUserAuthData() {
        return userAuthData;
    }
    public void setUserAuthData(UserAuthData userAuthData) {
        this.userAuthData = userAuthData;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

}
