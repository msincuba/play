package com.msincuba.play.dto;

import com.msincuba.play.domain.enums.TerminateReason;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto implements Serializable {

    private Long memberId;
    private Long id;
    private String dependantNumber;
    private String firstNames;
    private String surname;
    private String idNumber;
    private LocalDate effectiveDate;
    private LocalDate terminatedDate;
    private TerminateReason terminateReason;
    private Long planId;
    private Long employmentId;    
    private Long bankingId;
}
