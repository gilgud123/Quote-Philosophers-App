package philosopher.paradise.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import philosopher.paradise.entity.Philosopher;
import philosopher.paradise.entity.Quote;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Configuration
public class MessagingConfig {

    private static final String PHILOSOPHER_QUEUE = "philosopher.queue";
    private static final String QUOTE_QUEUE = "quote.queue";
    private static final String RECEIVE_AND_CONVERT_PHILOSOPHER_QUEUE = "receive.and.convert.philosopher";
    private static final String RECEIVE_AND_CONVERT_QUOTE_QUEUE = "receive.and.convert.quote";
    private static final String MAPPED_QUEUE = "mapped.queue";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitTemplate jsonRabbitTemplate;

    @Autowired
    private AmqpAdmin rabbitAdmin;

    private volatile CountDownLatch latch = new CountDownLatch(2);

    public void runDemo() throws Exception{
        String jsonLenin = "{\"name\" : \"Lenin\" }";
        Message jsonPhilosopherMessage = MessageBuilder.withBody(jsonLenin.getBytes())
                .andProperties(MessagePropertiesBuilder.newInstance().setContentType("application/json")
                        .build()).build();

        String jsonQuote = "{\"text\" : \"No amount of political freedom will satisfy the hungry masses.\"}";
        Message jsonQuoteMessage = MessageBuilder.withBody(jsonQuote.getBytes())
                .andProperties(MessagePropertiesBuilder.newInstance().setContentType("application/json")
                        .build()).build();

        this.rabbitTemplate.send(RECEIVE_AND_CONVERT_PHILOSOPHER_QUEUE, jsonPhilosopherMessage);
        this.rabbitTemplate.send(RECEIVE_AND_CONVERT_QUOTE_QUEUE, jsonQuoteMessage);

        Philosopher philosopher = this.jsonRabbitTemplate.receiveAndConvert(
                RECEIVE_AND_CONVERT_PHILOSOPHER_QUEUE,
                10_000, new ParameterizedTypeReference<Philosopher>() {}
        );

        System.out.println();
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx OUTPUT MESSAGING xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        System.out.println("Expected a real Philosopher, got a guy called " + philosopher.getName());

        Quote quote = this.jsonRabbitTemplate.receiveAndConvert(
                RECEIVE_AND_CONVERT_QUOTE_QUEUE,
                10_000, new ParameterizedTypeReference<Quote>() {}
        );
        System.out.println("Expected a Quote by " + philosopher.getName() + ", got the following:  " + quote.getText());

        this.latch = new CountDownLatch(2);
        jsonPhilosopherMessage.getMessageProperties().setHeader("_TypeId_", "philosopher");
        this.rabbitTemplate.send(MAPPED_QUEUE, jsonPhilosopherMessage);
        jsonQuoteMessage.getMessageProperties().setHeader("_TypeId_", "quote");
        this.rabbitTemplate.send(MAPPED_QUEUE, jsonQuoteMessage);
        this.latch.await(10, TimeUnit.MINUTES);

        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        System.out.println();

        this.rabbitAdmin.deleteQueue(RECEIVE_AND_CONVERT_PHILOSOPHER_QUEUE);
        this.rabbitAdmin.deleteQueue(RECEIVE_AND_CONVERT_QUOTE_QUEUE);
    }

    @RabbitListener(queues = PHILOSOPHER_QUEUE)
    public void listenForAPhilosopher(Philosopher philosopher){
        System.out.println("Expected a Philosopher, got a " + philosopher);
        this.latch.countDown();
    }

    @RabbitListener(queues = QUOTE_QUEUE)
    public void listenForAQuote(Quote quote){
        System.out.println("Expected a Quote, got a " + quote);
        this.latch.countDown();
    }

    @Bean
    public SimpleMessageListenerContainer legacyPojoListener(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(MAPPED_QUEUE);
        MessageListenerAdapter messageListener = new MessageListenerAdapter(new Object(){

            @SuppressWarnings("unused")
            public void handleMessage(Object object){
                System.out.println("Got a following json object: " + object);
                MessagingConfig.this.latch.countDown();
            }
        });
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper());
        messageListener.setMessageConverter(jsonConverter);
        container.setMessageListener(messageListener);
        return container;
    }

    @Bean
    public DefaultClassMapper classMapper(){
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("philosopher", Philosopher.class);
        idClassMapping.put("quote", Quote.class);
        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public RabbitTemplate jsonRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonConverter());
        return template;
    }

    @Bean
    public MessageConverter jsonConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue philosopherQueue(){
        return new AnonymousQueue(() -> PHILOSOPHER_QUEUE);
    }

    @Bean
    public Queue quoteQueue(){
        return new AnonymousQueue(() -> QUOTE_QUEUE);
    }

    @Bean
    public Queue convertAndReceivePhilosopher(){
        return new Queue(RECEIVE_AND_CONVERT_PHILOSOPHER_QUEUE);
    }

    @Bean
    public Queue convertAndReceiveQuote(){
        return new Queue(RECEIVE_AND_CONVERT_QUOTE_QUEUE);
    }

    @Bean
    public Queue mapped(){
        return new AnonymousQueue(() -> MAPPED_QUEUE);
    }

}
