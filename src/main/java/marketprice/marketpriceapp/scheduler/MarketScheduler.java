package marketprice.marketpriceapp.scheduler;

import java.util.HashMap;
import java.util.Map;

import marketprice.marketpriceapp.job.CheckPriceJob;
import marketprice.marketpriceapp.job.ImportProductJob;
import marketprice.marketpriceapp.job.ImportViaMessageJob;
import marketprice.marketpriceapp.processor.ImportProductTypeProcessor;

import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MarketScheduler {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private ImportProductJob importProductJob;
	
	@Autowired
	private CheckPriceJob checkPriceJob;
	
	@Autowired
	private ImportViaMessageJob importViaMessageJob;
	
	@Scheduled(fixedRate = 20000)
	public void runImportProductJob() {
		Map<String, JobParameter> confMap = new HashMap<>();
		confMap.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters jobParameters = new JobParameters(confMap);
		
		 try {
	            jobLauncher.run(importProductJob.importProductJob(), jobParameters);

	        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
	                | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {
	            System.out.println(e.getMessage());
	        }
	}
	
	@Scheduled(fixedRate = 100000)
	public void runCheckingPriceJob() {
		Map<String, JobParameter> confMap = new HashMap<>();
		confMap.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters jobParameters = new JobParameters(confMap);
		
		 try {
	            jobLauncher.run(checkPriceJob.checkPriceJob(), jobParameters);

	        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
	                | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {
	            System.out.println(e.getMessage());
	        }
		
	}
	

}
