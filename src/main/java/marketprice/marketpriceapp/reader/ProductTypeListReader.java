package marketprice.marketpriceapp.reader;

import java.util.List;

import marketprice.marketpriceapp.dao.ProductTypeDAO;
import marketprice.marketpriceapp.entity.ProductType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;


public class ProductTypeListReader implements ItemReader<int[]> {
	
	@Autowired
	private ProductTypeDAO productypeDao;
	
	@Autowired
	public RedisTemplate<String, Object> redisTemplate;
	
	private List<ProductType> productTypeList;
	
	private int count = 0;
	
	private JobExecution jobExecution;
	
    private static final Logger LOG = LoggerFactory.getLogger(ProductTypeListReader.class);
    
    private final String UPPER_BOUND = "UPBOUND_PRICE_";
    private final String LOWER_BOUND = "LOWBOUND_PRICE_";
    
    private final String PRODUCT_TYPE = "productype";

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
    	jobExecution = stepExecution.getJobExecution(); 
    }
	
	@Override
	public int[] read() {
		
		productTypeList = productypeDao.findAll();
		
		if (count < productTypeList.size()) {
			
			ProductType pdT = productTypeList.get(count++);
			
			jobExecution.getExecutionContext().put(PRODUCT_TYPE,pdT.getName());
			
			String[] priceRangString = { UPPER_BOUND + pdT.getName().toUpperCase() , LOWER_BOUND + pdT.getName().toUpperCase() };
			
			int upperBound = Integer.parseInt((String)this.redisTemplate.opsForValue().get(priceRangString[0]));
			int lowerBound = Integer.parseInt((String)this.redisTemplate.opsForValue().get(priceRangString[1]));
			
			return new int[] {lowerBound, upperBound};
		}

		return null;
	}
	

}
