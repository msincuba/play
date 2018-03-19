package com.msincuba.play.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "mds_plan")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanDto implements Serializable {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;

}
