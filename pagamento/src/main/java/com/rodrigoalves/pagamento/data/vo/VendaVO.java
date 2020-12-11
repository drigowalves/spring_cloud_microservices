package com.rodrigoalves.pagamento.data.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rodrigoalves.pagamento.entity.ProdutoVenda;
import com.rodrigoalves.pagamento.entity.Venda;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({"id", "data", "valorTotal", "produtos"})
public class VendaVO extends RepresentationModel<VendaVO> implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("data")
    private Date data;

    @JsonProperty("valorTotal")
    private BigDecimal valorTotal;

    @JsonProperty("produtos")
    private List<ProdutoVendaVO> produtos;

    public static VendaVO create(Venda venda) {
        return new ModelMapper().map(venda, VendaVO.class);
    }

}


