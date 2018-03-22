package com.msincuba.play.security.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user", uniqueConstraints = {
    @UniqueConstraint(name = "uk_user_username", columnNames = {"USERNAME"})
    ,
        @UniqueConstraint(name = "uk_user_email", columnNames = {"EMAIL"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "USERNAME", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    @Column(name = "PASSWORD", length = 100)
    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    @Column(name = "FIRSTNAME", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String firstname;

    @Column(name = "LASTNAME", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String lastname;

    @Column(name = "EMAIL", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String email;

    @Column(name = "ENABLED")
    @NotNull
    private Boolean enabled;

    @Column(name = "LAST_PASSWORD_RESET_DATE")
    @NotNull
    private Instant lastPasswordResetDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_AUTHORITY",
            joinColumns = {
                @JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {
                @JoinColumn(name = "AUTHORITY_NAME", referencedColumnName = "NAME")})
    private Set<Authority> authorities;

}
