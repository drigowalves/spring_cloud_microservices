package com.rodrigoalves.pagamento.data.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rodrigoalves.pagamento.entity.ProdutoVenda;
import com.rodrigoalves.pagamento.entity.Venda;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({"id", "idProduto", "quantidade"})
public class ProdutoVendaVO extends RepresentationModel<ProdutoVO> implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("idProduto")
    private Long idProduto;

    @JsonProperty("quantidade")
    private Integer quantidade;

    public static ProdutoVendaVO create(ProdutoVenda produtoVenda) {
        return new ModelMapper().map(produtoVenda, ProdutoVendaVO.class);
    }

}
