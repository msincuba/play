package com.msincuba.play.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDto implements Serializable {

    private Long memberId;
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

}
