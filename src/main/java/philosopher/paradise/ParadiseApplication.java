package philosopher.paradise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import philosopher.paradise.config.MessagingConfig;

@SpringBootApplication
public class ParadiseApplication {

	public static void main(String[] args) throws Exception {

		ConfigurableApplicationContext ctx = SpringApplication.run(ParadiseApplication.class, args);

		ctx.getBean(MessagingConfig.class).runDemo();
		ctx.close();
	}
}
