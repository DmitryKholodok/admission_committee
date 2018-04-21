package by.issoft.kholodok.model;

import lombok.Getter;

@Getter
public enum RoleEnum {

    OPERATOR("ROLE_OPERATOR", 2),
    ADMIN("ROLE_ADMIN", 3),
    ENROLLEE("ROLE_ENROLLEE", 1),
    ANONYMOUS("ROLE_ANONYMOUS", 0);

    private String value;
    private int priority;


    RoleEnum(String value, int priority) {
        this.value = value;
        this.priority = priority;
    }

}
