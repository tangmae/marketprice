package marketprice.marketpriceapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan("marketprice.marketpriceapp")
public class App 
{
    public static void main( String[] args )
    {
    	SpringApplication application = new SpringApplication(App.class);
        application.setWebEnvironment(false);
    	ConfigurableApplicationContext context = application.run(args);
    }
}
