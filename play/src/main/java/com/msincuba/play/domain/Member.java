package com.msincuba.play.domain;

import com.msincuba.play.domain.enums.TerminateReason;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "mds_member")
@Table(name = "mds_member")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member implements Serializable {

    @ManyToOne
    private Member mainMember;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Builder.Default
    private String dependantNumber = "01";
    private String firstNames;
    private String surname;
    private String idNumber;
    private LocalDate effectiveDate;
    private LocalDate terminatedDate;
    private TerminateReason terminateReason;
    @OneToOne
    private Plan plan;
    @OneToMany(mappedBy = "mainMember")
    private Set<Member> dependants;
    @OneToOne
    private Employment employment;    
    @OneToOne
    private Banking banking;
    @OneToOne(mappedBy = "mainMember")
    private Contact contact;
}
