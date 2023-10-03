package br.fvc.api.Domain.Dashboard;

import java.math.BigDecimal;

public class DashboardResponseDTO {

    public BigDecimal valueTotal;
    public String month;
    public Integer year;

    public DashboardResponseDTO(BigDecimal valueTotal, String month, Integer year) {
        this.valueTotal = valueTotal;
        this.month = month;
        this.year = year;
    }
}
