package marketprice.marketpriceapp.writer;

import java.util.List;

import marketprice.marketpriceapp.dao.EggDAO;
import marketprice.marketpriceapp.dao.RiceDAO;
import marketprice.marketpriceapp.dao.SugarDAO;
import marketprice.marketpriceapp.entity.Egg;
import marketprice.marketpriceapp.entity.Product;
import marketprice.marketpriceapp.entity.Rice;
import marketprice.marketpriceapp.entity.Sugar;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;



public class UpdateProductPriceWriter implements ItemWriter<Product> {
	
	@Autowired
	private SugarDAO sugarDao;
	
	@Autowired
	private RiceDAO riceDao;

	@Autowired
	private EggDAO eggDao;
	
	@Override
	public void write(List<? extends Product> productList) {
		
		for (Product pd : productList) {
			if (pd instanceof Sugar) {
				Sugar sugar = (Sugar) pd;
				sugarDao.updatePricePerUnit(sugar.getPricePerUnit(), sugar);
			}
			if (pd instanceof Rice) {
				Rice rice = (Rice) pd;
				riceDao.updatePricePerUnit(rice.getPricePerUnit(), rice);
			}
			if (pd instanceof Egg) {
				Egg egg = (Egg) pd;
				eggDao.updatePricePerUnit(egg.getPricePerUnit(), egg);
			}
			System.out.println("UPDATE PRICE FOR " + pd.getClass().getName());
		}
		
		
	}

}
