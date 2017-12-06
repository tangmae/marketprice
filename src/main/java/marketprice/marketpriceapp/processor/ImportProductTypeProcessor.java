package marketprice.marketpriceapp.processor;

import java.util.ArrayList;
import java.util.List;

import marketprice.marketpriceapp.dao.OwnerDAO;
import marketprice.marketpriceapp.entity.Egg;
import marketprice.marketpriceapp.entity.Product;
import marketprice.marketpriceapp.entity.Rice;
import marketprice.marketpriceapp.entity.Sugar;

import org.joda.time.DateTime;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class ImportProductTypeProcessor implements ItemProcessor<Object, List<Product>> {
	
	@Autowired
	private OwnerDAO ownerDao;
	
	@Override 
	public List process(Object obj) throws Exception {
		try {
			
			List<Product> productList = new ArrayList<Product>();
			
			if (obj instanceof Sugar) {
				
				for (int i = 0 ; i < 1 ; i++ ) {
					Sugar sugar = new Sugar();
					sugar.setOwnerId(ownerDao.randomPickingOwner().getOwnerId());
					sugar.setPricePerUnit((int)Math.round((Math.random() * 2000)));
					sugar.setImportDate(DateTime.now().toDate());
					sugar.setVolumn((int)Math.round((Math.random() * 1000)));
					productList.add(sugar);
				}
			}
			if (obj instanceof Rice) {
				
				for (int i = 0 ; i < 1 ; i++ ) {
					Rice rice = new Rice();
					rice.setOwnerId(ownerDao.randomPickingOwner().getOwnerId());
					rice.setPricePerUnit((int)Math.round((Math.random() * 5000)));
					rice.setImportDate(DateTime.now().toDate());
					rice.setVolumn((int)Math.round((Math.random() * 1000)));
					productList.add(rice);
				}
			}
			if (obj instanceof Egg) {
				
				for (int i = 0 ; i < 1 ; i++ ) {
					Egg egg = new Egg();
					egg.setOwnerId(ownerDao.randomPickingOwner().getOwnerId());
					egg.setPricePerUnit((int)Math.round((Math.random() * 2000)));
					egg.setImportDate(DateTime.now().toDate());
					egg.setVolumn((int)Math.round((Math.random() * 1000)));
					productList.add(egg);
				}
			}
			
			return productList;
			
			
		} catch (SecurityException e) {
	        e.printStackTrace();
	    }

		return null;
		
			
	}
}
