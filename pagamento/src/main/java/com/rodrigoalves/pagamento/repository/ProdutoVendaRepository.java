package com.rodrigoalves.pagamento.repository;

import com.rodrigoalves.pagamento.entity.ProdutoVenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoVendaRepository extends JpaRepository<ProdutoVenda, Long> {
}
