package com.rodrigoalves.pagamento.repository;

import com.rodrigoalves.pagamento.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
