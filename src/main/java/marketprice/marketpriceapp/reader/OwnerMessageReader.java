package marketprice.marketpriceapp.reader;

import marketprice.marketpriceapp.receiver.Receiver;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
 
public class OwnerMessageReader implements ItemReader<String> {
	
	private String messages;
	
	@Autowired
	private Receiver receiver;
	
	@Override
	public String read() {
		setMessages(receiver.getMessage());
		System.out.println(this.messages);
		return this.messages;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}
	
	
}
