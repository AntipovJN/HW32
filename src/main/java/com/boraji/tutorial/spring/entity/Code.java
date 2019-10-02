package com.boraji.tutorial.spring.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="code")
public class Code {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code_value")
    private int codeValue;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    public Code(long id, int code, User user) {
        this.id = id;
        this.codeValue = code;
        this.user = user;
    }

    public Code(int code, User user) {
        this.codeValue = code;
        this.user = user;
    }

    public Code() {
    }

    public int getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(int code) {
        this.codeValue = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Code code1 = (Code) o;

        if (getCodeValue() != code1.getCodeValue())
            return false;
        return getUser() != null ? getUser().equals(code1.getUser()) : code1.getUser() == null;
    }

    @Override
    public int hashCode() {
        int result = getCodeValue();
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        return result;
    }
}
