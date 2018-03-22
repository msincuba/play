package com.msincuba.play.security.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority implements Serializable {
    
    @Id
    @Column(name = "NAME", length = 50)
    @NotNull
    private String name;
    
    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private List<User> users;

}
