package marketprice.marketpriceapp.job;

import java.util.List;

import javax.sql.DataSource;

import marketprice.marketpriceapp.entity.Product;
import marketprice.marketpriceapp.entity.ProductType;
import marketprice.marketpriceapp.listener.JobCompletionListener;
import marketprice.marketpriceapp.processor.AdjustPriceProductProcessor;
import marketprice.marketpriceapp.processor.ProductTypeListProcessor;
import marketprice.marketpriceapp.reader.OutboundPriceProductReader;
import marketprice.marketpriceapp.reader.ProductTypeListReader;
import marketprice.marketpriceapp.writer.UpdateProductPriceWriter;

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
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration 
@EnableBatchProcessing
public class CheckPriceJob {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired 
	private JobCompletionListener jobCompleteListener;
	
	@Bean("CheckPriceJob")
	public Job checkPriceJob() {
		return jobBuilderFactory.get("CheckPriceJob")
				.incrementer(new RunIdIncrementer())
				.listener(jobCompleteListener)
				.start(getProductListStep())
				.next(getOutRangePriceProduct())
				.build();
	}
	
	@Bean("GetProductTypeListStep")
	public Step getProductListStep() {
		return stepBuilderFactory.get("GetProductTypeListStep")
				.<int[], String>chunk(1)
				.reader(productTypeListReader())
				.processor(productTypeListProcessor())
				.build();
	}
	
	@Bean("ProductTypeListReader")
	@StepScope
	public ProductTypeListReader productTypeListReader() {
		return new ProductTypeListReader();
	}
	
	@Bean("ProductTypeListProcessor")
	@StepScope
	public ProductTypeListProcessor productTypeListProcessor() {
		return new ProductTypeListProcessor();
	}
	
	@Bean("GetOutRangePriceProduct")
	public Step getOutRangePriceProduct() {
		return stepBuilderFactory.get("GetOutRangePriceProduct")
				.<Product, Product>chunk(1)
				.reader(outboundPriceProductReader())
				.processor(adjustPriceProductProcessor())
				.writer(updateProductPriceWriter())
				.build();
	}
	
	@Bean("OutboundPriceProductReader")
	@StepScope
	public OutboundPriceProductReader outboundPriceProductReader() {
		return new OutboundPriceProductReader();
	}
	
	@Bean("AdjustPriceProductProcessor")
	@StepScope
	public AdjustPriceProductProcessor adjustPriceProductProcessor() {
		return new AdjustPriceProductProcessor();
	}
	
	@Bean("UpdateProductPriceWriter")
	@StepScope
	public UpdateProductPriceWriter updateProductPriceWriter() {
		return new UpdateProductPriceWriter();
	}

}
