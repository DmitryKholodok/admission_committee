package by.issoft.kholodok.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

//@Entity
@Table(name = "statement")
public class Statement {

    @OneToMany(mappedBy = "statement", fetch = FetchType.LAZY)
    private Set<StatementSpecialty> statementSpecialtySet = new HashSet<>();

    private User user;

    private User operator;




}
