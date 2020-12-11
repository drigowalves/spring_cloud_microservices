package com.rodrigoalves.crud.controller;

import com.rodrigoalves.crud.data.vo.ProdutoVO;
import com.rodrigoalves.crud.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/produto")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdutoController {

    private final ProdutoService produtoService;
    private final PagedResourcesAssembler<ProdutoVO> produtoVOPagedResourcesAssembler;


    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ProdutoVO findById(@PathVariable("id") Long id) {
        ProdutoVO produtoVO = produtoService.findById(id);
        produtoVO.add(represetationModalLinkTo(id));
        return produtoVO;
    }

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "limit", defaultValue = "12") int limit,
                                     @RequestParam(value = "orderBy", defaultValue = "asc") String orderBy) {
        var sortOrderBy = "desc".equalsIgnoreCase(orderBy) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortOrderBy, "nome"));
        Page<ProdutoVO> produtos = produtoService.findAll(pageable);
        produtos.stream().forEach(produto -> produto.add(represetationModalLinkTo(produto.getId())));
        PagedModel<EntityModel<ProdutoVO>> pagedModel = produtoVOPagedResourcesAssembler.toModel(produtos);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
                consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ProdutoVO create(@RequestBody ProdutoVO produtoVO) {
        ProdutoVO newProdutoVO = produtoService.create(produtoVO);
        newProdutoVO.add(represetationModalLinkTo(newProdutoVO.getId()));
        return newProdutoVO;
    }

    @PutMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ProdutoVO update(@RequestBody ProdutoVO produtoVO) {
        ProdutoVO newProdutoVO = produtoService.update(produtoVO);
        newProdutoVO.add(represetationModalLinkTo(newProdutoVO.getId()));
        return newProdutoVO;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        produtoService.delete(id);
        return ResponseEntity.ok().build();
    }

    private Link represetationModalLinkTo(Long id){
        return linkTo(methodOn(ProdutoController.class).findById(id)).withSelfRel();
    }
}
