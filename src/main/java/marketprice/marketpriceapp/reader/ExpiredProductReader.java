package marketprice.marketpriceapp.reader;

import java.util.ArrayList;
import java.util.List;

import marketprice.marketpriceapp.dao.EggDAO;
import marketprice.marketpriceapp.dao.ProductTypeDAO;
import marketprice.marketpriceapp.dao.RiceDAO;
import marketprice.marketpriceapp.dao.SugarDAO;
import marketprice.marketpriceapp.entity.Product;
import marketprice.marketpriceapp.entity.ProductType;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

public class ExpiredProductReader implements ItemReader<Product>{
	
	@Autowired
	private ProductTypeDAO productTypeDao;
	
	@Autowired
	private SugarDAO sugarDao;
	
	@Autowired
	private RiceDAO riceDao;

	@Autowired
	private EggDAO eggDao;
		
	private ArrayList productList;
	
	private int count = 0;
	
	@Override
	public Product read() {
		
		setProductList();
		
		if (count < this.productList.size()) {
			return (Product) this.productList.get(count++);
		}
		
		return null;
	
	}
	
	public List<? extends Product> getProductList() {
		return productList;
	}

	public void setProductList() {
		
		this.productList = new ArrayList();
		
		List<ProductType> pdTypeList = productTypeDao.findAll();
		
		for (ProductType pdT : pdTypeList) {
		
			if (pdT.getName().equalsIgnoreCase("Sugar")) {
				this.productList.addAll(sugarDao.findExpiredSugar());
			}
			if (pdT.getName().equalsIgnoreCase("Rice")) {
				this.productList.addAll(riceDao.findExpiredRice());
			}
			if (pdT.getName().equalsIgnoreCase("Egg")) {
				this.productList.addAll(eggDao.findExpiredEgg());
			}
			
		}

	}

}
