package com.utility.users.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {

    private static final long serialVersionUID = -7486543683246871717L;

    @Id
    @GeneratedValue( strategy=GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private Long id;

    @Column(nullable = false,length = 50)
    private String firstName;

    @Column(nullable = false,length = 50)
    private String lastName;


    @NotNull(message = "email cannot be null")
    @Column(nullable = false,length = 120,unique = true)
    @Email
    private String email;

    @Column(nullable = false,unique = true)
    private String userId;

    @Column(nullable = false,unique = true)
    private String encryptedPassword;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


}
