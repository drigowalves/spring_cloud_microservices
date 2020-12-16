package com.rodrigoalves.pagamento.config.receiver;

import com.rodrigoalves.pagamento.data.vo.ProdutoVO;
import com.rodrigoalves.pagamento.entity.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.rodrigoalves.pagamento.repository.ProdutoRepository;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdutoReceiveMessage {

    private final ProdutoRepository produtoRepository;

    @RabbitListener(queues = {"${crud.rabbitmq.queue}"})
    public void receive(@Payload ProdutoVO produtoVO) {
        Produto produto = produtoRepository.save(Produto.create(produtoVO));
        System.out.println(produto.toString());
    }

}
