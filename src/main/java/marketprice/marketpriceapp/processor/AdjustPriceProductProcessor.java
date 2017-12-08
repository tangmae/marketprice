package marketprice.marketpriceapp.processor;

import marketprice.marketpriceapp.entity.Product;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

public class AdjustPriceProductProcessor implements ItemProcessor<Product, Product> {
	
	private JobExecution jobExecution;
	
	private final String UPPER_BOUND = "upperbound";
	private final String LOWER_BOUND = "lowerbound";
	
	@BeforeStep
    public void beforeStep(StepExecution stepExecution) {
    	jobExecution = stepExecution.getJobExecution(); 
    }
	
	@Override
	public Product process(Product product) {
		
		String className = product.getClass().getName().replace("marketprice.marketpriceapp.entity.", "");
		
		int lowerBound = jobExecution.getExecutionContext().getInt(className + LOWER_BOUND);
		int upperBound = jobExecution.getExecutionContext().getInt(className + UPPER_BOUND);

		if (product.getPricePerUnit() > upperBound) {
			product.setPricePerUnit(upperBound);
		} 
		if (product.getPricePerUnit() < lowerBound) {
			product.setPricePerUnit(lowerBound);
		}
		
		return product;
	}

}
