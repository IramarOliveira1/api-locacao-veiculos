package br.fvc.api.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.fvc.api.Domain.Dashboard.DashboardResponseDTO;
import br.fvc.api.Models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT NEW br.fvc.api.Domain.Dashboard.DashboardResponseDTO(SUM(p.preco) AS valueTotal, MONTHNAME(p.data_pagamento) AS month, YEAR(p.data_pagamento) AS year) \r\n"
            + //
            "FROM pagamento p \r\n" + //
            "GROUP BY YEAR(p.data_pagamento), MONTH(p.data_pagamento) ORDER BY p.id ASC LIMIT 12")
    List<DashboardResponseDTO> getSumTotalValuesMonth();

}
