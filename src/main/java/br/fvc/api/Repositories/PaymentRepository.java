package br.fvc.api.Repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.fvc.api.Domain.Dashboard.DashboardResponseDTO;
import br.fvc.api.Models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT NEW br.fvc.api.Domain.Dashboard.DashboardResponseDTO(SUM(p.preco) AS valueTotal, MONTHNAME(p.data_pagamento) AS month, YEAR(p.data_pagamento) AS year) \r\n"
            + //
            "FROM pagamento p WHERE YEAR(p.data_pagamento) = YEAR(CURDATE()) \r\n" + //
            "GROUP BY YEAR(p.data_pagamento), MONTH(p.data_pagamento)")
    List<DashboardResponseDTO> getSumTotalValuesMonth();

    @Query("SELECT NEW br.fvc.api.Domain.Dashboard.DashboardResponseDTO(COUNT(v.id) AS quantidade_veiculo_alugado, MONTHNAME(r.data_reserva) AS mes, YEAR(r.data_reserva) AS ano) FROM reserva AS r INNER JOIN r.veiculo AS v INNER JOIN v.modelo AS m INNER JOIN r.usuario AS u WHERE r.status = 'FINALIZADO' AND u.id = :id AND YEAR(r.data_reserva) = YEAR(CURDATE()) GROUP BY YEAR(r.data_reserva), MONTH(r.data_reserva)")
    List<DashboardResponseDTO> reserveValueTotalMonth(Long id);

    @Query("SELECT  NEW br.fvc.api.Domain.Dashboard.DashboardResponseDTO(SUM(payment.preco), MONTHNAME(payment.data_pagamento) AS month, YEAR(payment.data_pagamento) AS year) FROM pagamento AS payment\r\n" + //
            "WHERE payment.data_pagamento BETWEEN :start AND :end \r\n" + //
            "GROUP BY YEAR(payment.data_pagamento), MONTH(payment.data_pagamento)")
    List<DashboardResponseDTO> filterInvoicingPeriod(LocalDate start, LocalDate end);
}
