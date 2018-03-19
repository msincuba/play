package com.msincuba.play.dto;

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
public class BankingDto implements Serializable {

    private Long memberId;
    private Long id;
    private String bankName;
    private String accountNumber;
    private String accountType;
    private String branchCode;
    private String branchName;
    private LocalDate debitOrderDate;

}
