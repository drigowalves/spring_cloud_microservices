package com.rodrigoalves.pagamento.controller;

import com.rodrigoalves.pagamento.data.vo.VendaVO;
import com.rodrigoalves.pagamento.service.VendaService;
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
@RequestMapping("/venda")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VendaController {

    private final VendaService vendaService;
    private final PagedResourcesAssembler<VendaVO> vendaVOPagedResourcesAssembler;


    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public VendaVO findById(@PathVariable("id") Long id) {
        VendaVO vendaVO = vendaService.findById(id);
        vendaVO.add(represetationModalLinkTo(id));
        return vendaVO;
    }

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "limit", defaultValue = "12") int limit,
                                     @RequestParam(value = "orderBy", defaultValue = "asc") String orderBy) {
        var sortOrderBy = "desc".equalsIgnoreCase(orderBy) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortOrderBy, "data"));
        Page<VendaVO> vendas = vendaService.findAll(pageable);
        vendas.stream().forEach(venda -> venda.add(represetationModalLinkTo(venda.getId())));
        PagedModel<EntityModel<VendaVO>> pagedModel = vendaVOPagedResourcesAssembler.toModel(vendas);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public VendaVO create(@RequestBody VendaVO vendaVO) {
        VendaVO newVendaVO = vendaService.create(vendaVO);
        newVendaVO.add(represetationModalLinkTo(newVendaVO.getId()));
        return newVendaVO;
    }

    @PutMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public VendaVO update(@RequestBody VendaVO vendaVO) {
        VendaVO newVendaVO = vendaService.update(vendaVO);
        newVendaVO.add(represetationModalLinkTo(newVendaVO.getId()));
        return newVendaVO;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        vendaService.delete(id);
        return ResponseEntity.ok().build();
    }

    private Link represetationModalLinkTo(Long id){
        return linkTo(methodOn(VendaController.class).findById(id)).withSelfRel();
    }

}
