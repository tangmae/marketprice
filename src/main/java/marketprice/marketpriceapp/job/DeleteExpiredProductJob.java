package marketprice.marketpriceapp.job;

import java.util.List;

import javax.sql.DataSource;

import marketprice.marketpriceapp.entity.Product;
import marketprice.marketpriceapp.listener.JobCompletionListener;
import marketprice.marketpriceapp.processor.ImportProductTypeProcessor;
import marketprice.marketpriceapp.reader.ProductTypeReader;
import marketprice.marketpriceapp.writer.ImportProductWriter;

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
public class DeleteExpiredProductJob {
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired 
	private JobCompletionListener jobCompleteListener;
	
	@Bean("DeleteExpiredProductJob")
	public Job importProductJob() {
		return jobBuilderFactory.get("DeleteExpiredProductJob")
				.incrementer(new RunIdIncrementer())
				.listener(jobCompleteListener)
				.start(deleteExpiredProductStep())
				.build();
	}
	
	@Bean("DeleteExpiredProductStep")
	public Step deleteExpiredProductStep() {
		return stepBuilderFactory.get("DeleteExpiredProductStep")
				.<Object, List<Product>>chunk(1)
				.reader(productTypeReader())
				.writer(importProductWriter())
				.build();
	}
	
	@Bean("ProductTypeReader")
	@StepScope
	public ProductTypeReader productTypeReader() {
		return new ProductTypeReader();
		
	}
	
	@Bean("GetImportProductTypeStep")
	@StepScope
	public ImportProductWriter importProductWriter() {
		return new ImportProductWriter();
	}

}
