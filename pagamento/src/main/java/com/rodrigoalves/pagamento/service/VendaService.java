package com.rodrigoalves.pagamento.service;

import com.rodrigoalves.pagamento.data.vo.ProdutoVendaVO;
import com.rodrigoalves.pagamento.data.vo.VendaVO;
import com.rodrigoalves.pagamento.entity.ProdutoVenda;
import com.rodrigoalves.pagamento.entity.Venda;
import com.rodrigoalves.pagamento.exceptions.ResourseNotFoundException;
import com.rodrigoalves.pagamento.repository.ProdutoVendaRepository;
import com.rodrigoalves.pagamento.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoVendaRepository produtoVendaRepository;

    public VendaVO create(VendaVO vendaVO) {
        Venda venda = vendaRepository.save(Venda.create(vendaVO));
        List<ProdutoVenda> produtoSalvos = vendaVO.getProdutos().stream().map(produtoVendaVO -> {
            ProdutoVenda produtoVenda = ProdutoVenda.create(produtoVendaVO);
            produtoVenda.setVenda(venda);
            return produtoVendaRepository.save(produtoVenda);
        }).collect(Collectors.toList());
        venda.setProdutos(produtoSalvos);
        return VendaVO.create(venda);
    }

    public Page<VendaVO> findAll(Pageable pageable) {
        var page = vendaRepository.findAll(pageable);
        return page.map(this::convertToVendaVO);
    }

    public VendaVO findById(Long id) {
        var entity = vendaRepository.findById(id).orElseThrow(() -> new ResourseNotFoundException("No records found for this ID!"));
        return VendaVO.create(entity);
    }

    public VendaVO update(VendaVO vendaVO) {
        final Optional<Venda> optionalVenda = vendaRepository.findById(vendaVO.getId());
        if (optionalVenda.isPresent()) {
            new ResourseNotFoundException("No records found for this ID!");
        }
        return VendaVO.create(vendaRepository.save(Venda.create(vendaVO)));
    }

    public void delete(Long id) {
        Venda venda = Venda.create(this.findById(id));
        vendaRepository.delete(venda);
    }

    private VendaVO convertToVendaVO(Venda venda) {
        return VendaVO.create(venda);
    }

}
