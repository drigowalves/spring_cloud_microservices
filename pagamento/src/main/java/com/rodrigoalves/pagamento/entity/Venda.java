package com.rodrigoalves.pagamento.entity;

import com.rodrigoalves.pagamento.data.vo.VendaVO;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "venda")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Venda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @Column(name = "data", nullable = false)
    private Date data;

    @Column(name = "valorTotal", nullable = false)
    private BigDecimal valorTotal;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "venda", cascade = CascadeType.REFRESH)
    private List<ProdutoVenda> produtos;

    public static Venda create(VendaVO vendaVO) {
        return new ModelMapper().map(vendaVO, Venda.class);
    }

}
