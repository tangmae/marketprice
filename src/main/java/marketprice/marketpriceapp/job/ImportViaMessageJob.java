package marketprice.marketpriceapp.job;

import java.util.List;

import javax.sql.DataSource;

import marketprice.marketpriceapp.entity.Product;
import marketprice.marketpriceapp.listener.JobCompletionListener;
import marketprice.marketpriceapp.processor.ImportProductTypeProcessor;
import marketprice.marketpriceapp.reader.OwnerMessageReader;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
@EnableBatchProcessing
public class ImportViaMessageJob {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired 
	private JobCompletionListener jobCompleteListener;
	
	@Autowired
	private DataSource dataSource;
	
	private Product importProduct;
	
	public Product getImportProduct() {
		return importProduct;
	}

	public void setImportProduct(Product importProduct) {
		this.importProduct = importProduct;
	}

	@Bean("ImportViaMessageJob")
	public Job importViaMessageJob() {
		return jobBuilderFactory.get("ImportViaMessageJob")
				.incrementer(new RunIdIncrementer())
				.listener(jobCompleteListener)
				.start(getImportProductStep())
//				.next(importProductStep())
				.build();
	}
	
	@Bean("GetImportProductStep")
	public Step getImportProductStep() {
		return this.stepBuilderFactory.get("GetImportProductStep")
				.<String, List<Product>>chunk(1)
				.reader(ownerMessageReader())
				.processor(importProductTypeProcessor())
				.build();
	}
	
	@Bean("OwnerMessageReader")
//	@StepScope
	public OwnerMessageReader ownerMessageReader(){
		return new OwnerMessageReader();
	}
	
	
	@Bean("ImportProductTypeProcessor")
//	@StepScope
	public ImportProductTypeProcessor importProductTypeProcessor(){
		return new ImportProductTypeProcessor();
	}
	
	
	
	
}
