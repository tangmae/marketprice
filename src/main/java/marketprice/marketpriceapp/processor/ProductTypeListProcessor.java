package marketprice.marketpriceapp.processor;

import marketprice.marketpriceapp.dao.ProductDAO;
import marketprice.marketpriceapp.entity.ProductType;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

public class ProductTypeListProcessor implements ItemProcessor<int[], String>{
	
	private final String PRODUCT_TYPE = "productype";
	private final String UPPER_BOUND = "upperbound";
	private final String LOWER_BOUND = "lowerbound";
	
	private JobExecution jobExecution;
	
	@BeforeStep
    public void beforeStep(StepExecution stepExecution) {
    	jobExecution = stepExecution.getJobExecution(); 
    }
	
	@Override
	public String process(int[] priceRange) throws Exception {
		
		String pdTypeName = jobExecution.getExecutionContext().getString(PRODUCT_TYPE);
		
		jobExecution.getExecutionContext().putInt(pdTypeName + LOWER_BOUND, priceRange[0]);
		jobExecution.getExecutionContext().putInt(pdTypeName + UPPER_BOUND, priceRange[1]);

		return pdTypeName;
	}
	
	

}
