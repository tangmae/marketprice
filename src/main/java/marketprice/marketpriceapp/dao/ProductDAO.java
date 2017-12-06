package marketprice.marketpriceapp.dao;

import java.util.List;

import marketprice.marketpriceapp.entity.Egg;
import marketprice.marketpriceapp.entity.Product;
import marketprice.marketpriceapp.entity.Rice;
import marketprice.marketpriceapp.entity.Sugar;

public abstract class ProductDAO {
	
	public abstract List<? extends Product> findOutRangePriceProduct(int lowerbound, int upperbound);
	public abstract void updatePricePerUnit(int price, Product pr);
	public abstract List<? extends Product> findAll();
//	public abstract void updatePricePerUnit(int price, Sugar sugar);
//	public abstract void updatePricePerUnit(int price, Egg egg);

}
