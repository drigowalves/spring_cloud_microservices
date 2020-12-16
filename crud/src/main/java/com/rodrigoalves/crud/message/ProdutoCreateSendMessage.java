package com.rodrigoalves.crud.message;

import com.rodrigoalves.crud.data.vo.ProdutoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdutoCreateSendMessage {

    @Value("${crud.rabbitmq.exchange.create}")
    String exchange;

    @Value("${crud.rabbitmq.routingkey}")
    String routingkey;

    public final RabbitTemplate rabbitTemplate;

    public void sendMessage(ProdutoVO produtoVO) {
        rabbitTemplate.convertAndSend(exchange, routingkey, produtoVO);
    }

}
