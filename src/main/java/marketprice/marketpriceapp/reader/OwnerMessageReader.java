package marketprice.marketpriceapp.reader;

import java.util.HashMap;

import marketprice.marketpriceapp.receiver.OwnerImportProductHandler;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
 
public class OwnerMessageReader implements ItemReader<HashMap<String, Object>> {
	
	private HashMap<String, Object> importProductProposal;
	
	private boolean isMessagesSet;
	
	@Autowired
	private OwnerImportProductHandler ownerImportProductHandler;
	

	@Override
	public HashMap<String, Object> read() {
		
		if (getImportProductProposal() == null && !isMessagesSet) {
			setImportProductProposal(ownerImportProductHandler.getImportProductProposal());
		}
		
		if (isMessagesSet) {
			this.isMessagesSet = false;
			return getImportProductProposal();
		}
		
		clearProposal();
		
		return null;
	}
	
	public void clearProposal() {
		isMessagesSet = false;
		setImportProductProposal(null);
	}


	public HashMap<String, Object> getImportProductProposal() {
		return importProductProposal;
	}


	public void setImportProductProposal(
			HashMap<String, Object> importProductProposal) {
		this.importProductProposal = importProductProposal;
		this.isMessagesSet = true;
	}
	
}
