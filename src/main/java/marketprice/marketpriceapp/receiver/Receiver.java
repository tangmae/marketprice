package marketprice.marketpriceapp.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    
    private String message;
    
    @RabbitHandler
    public void receive(String msg){
        this.message = msg;
    }
    
    public void setMessage(String message) {
    	this.message = message;
    }
    
    public String getMessage() {
    	return this.message;
    }

}
