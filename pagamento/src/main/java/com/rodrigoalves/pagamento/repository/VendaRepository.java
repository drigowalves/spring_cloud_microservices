package com.rodrigoalves.pagamento.repository;

import com.rodrigoalves.pagamento.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}
