package br.fvc.api.Domain.Dashboard;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class DashboardResponseDTO {

    @JsonInclude(Include.NON_NULL)
    public BigDecimal valueTotal;

    public String month;

    public Integer year;
    
    @JsonInclude(Include.NON_NULL)
    public Long amountTotal;

    public DashboardResponseDTO(BigDecimal valueTotal, String month, Integer year) {
        this.valueTotal = valueTotal;
        this.month = month;
        this.year = year;
    }

    public DashboardResponseDTO(Long amountTotal, String month, Integer year) {
        this.amountTotal = amountTotal;
        this.month = month;
        this.year = year;
    }
}
