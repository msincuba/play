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
public class EmploymentDto implements Serializable {

    private Long memberId;
    private Long id;    
    private String name;
    private String phone;
    private String address;
    private LocalDate employedSince;
    private String payDay;

}
