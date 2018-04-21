package by.issoft.kholodok.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

//@Entity
@Table(name = "specialty")
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialty_id", nullable = false)
    private int id;


    @Column(name = "name", nullable = false)
    private String name;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Faculty faculty;


    @OneToMany(mappedBy = "specialty_id", fetch = FetchType.LAZY)
    private Set<StatementSpecialty> statementSpecialtySet;



    public Specialty() {}



    public Faculty getFaculty() {
        return faculty;
    }
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
