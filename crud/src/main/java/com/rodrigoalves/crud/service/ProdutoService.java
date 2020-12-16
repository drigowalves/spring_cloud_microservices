package com.rodrigoalves.crud.service;

import com.rodrigoalves.crud.data.vo.ProdutoVO;
import com.rodrigoalves.crud.entity.Produto;
import com.rodrigoalves.crud.exceptions.ResourseNotFoundException;
import com.rodrigoalves.crud.message.ProdutoCreateSendMessage;
import com.rodrigoalves.crud.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoCreateSendMessage produtoSendMessage;

    public ProdutoVO create(ProdutoVO produtoVO) {
        ProdutoVO newProdutoVO = ProdutoVO.create(produtoRepository.save(Produto.create(produtoVO)));
        produtoSendMessage.sendMessage(newProdutoVO);
        return newProdutoVO;
    }

    public Page<ProdutoVO> findAll(Pageable pageable) {
        var page = produtoRepository.findAll(pageable);
        return page.map(this::convertToProdutoVO);
    }

    private ProdutoVO convertToProdutoVO(Produto produto) {
        return ProdutoVO.create(produto);
    }

    public ProdutoVO findById(Long id) {
        var entity = produtoRepository.findById(id).orElseThrow(() -> new ResourseNotFoundException("No records found for this ID!"));
        return ProdutoVO.create(entity);
    }

    public ProdutoVO update(ProdutoVO produtoVO) {
        final Optional<Produto> optionalProduto = produtoRepository.findById(produtoVO.getId());
        if (optionalProduto.isPresent()) {
            new ResourseNotFoundException("No records found for this ID!");
        }
        return ProdutoVO.create(produtoRepository.save(Produto.create(produtoVO)));
    }

    public void delete(Long id) {
        Produto produto = Produto.create(this.findById(id));
        produtoRepository.delete(produto);
    }
}
