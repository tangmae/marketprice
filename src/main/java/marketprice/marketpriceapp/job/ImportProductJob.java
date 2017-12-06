package marketprice.marketpriceapp.job;

import java.util.List;

import javax.sql.DataSource;

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

import marketprice.marketpriceapp.entity.Product;
import marketprice.marketpriceapp.listener.JobCompletionListener;
import marketprice.marketpriceapp.processor.ImportProductTypeProcessor;
import marketprice.marketpriceapp.reader.ProductTypeReader;
import marketprice.marketpriceapp.writer.ImportProductWriter;

@Configuration 
@EnableBatchProcessing
public class ImportProductJob {
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired 
	private JobCompletionListener jobCompleteListener;
	
	@Bean("ImportProductJob")
	public Job importProductJob() {
		return jobBuilderFactory.get("ImportProductJob")
				.incrementer(new RunIdIncrementer())
				.listener(jobCompleteListener)
				.start(ImportProductTypeStep())
				.build();
	}
	
	@Bean("ImportProductTypeStep")
	public Step ImportProductTypeStep() {
		return stepBuilderFactory.get("GetImportProductTypeStep")
				.<Object, List<Product>>chunk(1)
				.reader(productTypeReader())
				.processor(importProductProcessor())
				.writer(importProductWriter())
				.build();
	}
	
	@Bean("ProductTypeReader")
	@StepScope
	public ProductTypeReader productTypeReader() {
		return new ProductTypeReader();
		
	}
	
	@Bean("ImportProductProcessor")
	@StepScope
	public ImportProductTypeProcessor importProductProcessor() {
		ImportProductTypeProcessor importProductProcessor = new ImportProductTypeProcessor();
		return importProductProcessor;
	}
	
	@Bean("GetImportProductTypeStep")
	@StepScope
	public ImportProductWriter importProductWriter() {
		return new ImportProductWriter();
	}
	
}
