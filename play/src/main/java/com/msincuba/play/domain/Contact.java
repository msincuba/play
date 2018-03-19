package com.msincuba.play.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cellNumber;
    private String homeNumber;
    private String workNumber;
    private String emailAddress;
    private String workEmailAddress;
    private String faxNumber;
    
    private String physicalAddress;
    private Integer physicalAddressCode;
    private String postalAddress;
    private Integer postalAddressCode;

    @OneToOne
    private Member mainMember;
}
