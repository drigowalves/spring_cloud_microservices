package com.rodrigoalves.pagamento.entity;

import com.rodrigoalves.pagamento.data.vo.ProdutoVO;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "produto")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Produto implements Serializable {

    @Id
    private Long id;

    @Column(name = "estoque", nullable = false, length = 10)
    private Integer estoque;

    public static Produto create(ProdutoVO produtoVO) {
        return new ModelMapper().map(produtoVO, Produto.class);
    }

}
