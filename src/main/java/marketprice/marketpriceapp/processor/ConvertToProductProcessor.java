package marketprice.marketpriceapp.processor;

import java.util.HashMap;
import java.util.Map;

import marketprice.marketpriceapp.entity.Egg;
import marketprice.marketpriceapp.entity.Product;
import marketprice.marketpriceapp.entity.Rice;
import marketprice.marketpriceapp.entity.Sugar;

import org.joda.time.DateTime;
import org.springframework.batch.item.ItemProcessor;

public class ConvertToProductProcessor implements ItemProcessor<HashMap<String, Object>, Product> {
	
	private int price;
	
	private Product pd = null;
	
	private final String PRODUCTTYPE_KEY = "productType";
	private final String PRICE_KEY = "price";
	private final String OWNERID_KEY = "ownerId";
	private final String VOLUMN_KEY = "volumn";
	
	@Override
	public Product process(HashMap<String, Object> messages) {
		
		if (messages.get(PRODUCTTYPE_KEY).toString().equalsIgnoreCase("Sugar"))
		{
			Sugar sugar = new Sugar();
			sugar.setPricePerUnit((Integer)messages.get(PRICE_KEY));
			sugar.setVolumn((Integer)messages.get(VOLUMN_KEY));
			sugar.setImportDate(DateTime.now().toDate());
			sugar.setOwnerId((Integer)messages.get(OWNERID_KEY));
			setProduct(sugar);
		}
		if (messages.get(PRODUCTTYPE_KEY).toString().equalsIgnoreCase("Rice"))
		{
			Rice rice = new Rice();
			rice.setPricePerUnit((Integer)messages.get(PRICE_KEY));
			rice.setVolumn((Integer)messages.get(VOLUMN_KEY));
			rice.setImportDate(DateTime.now().toDate());
			rice.setOwnerId((Integer)messages.get(OWNERID_KEY));
			setProduct(rice);
		}
		if (messages.get(PRODUCTTYPE_KEY).toString().equalsIgnoreCase("Egg"))
		{
			Egg egg = new Egg();
			egg.setPricePerUnit((Integer)messages.get(PRICE_KEY));
			egg.setVolumn((Integer)messages.get(VOLUMN_KEY));
			egg.setImportDate(DateTime.now().toDate());
			egg.setOwnerId((Integer)messages.get(OWNERID_KEY));
			setProduct(egg);
		}	
		return getProduct();
	}

	public Product getProduct() {
		return pd;
	}

	public void setProduct(Product pd) {
		this.pd = pd;
	}

	
}
