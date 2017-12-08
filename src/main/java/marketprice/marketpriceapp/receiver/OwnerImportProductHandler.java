package marketprice.marketpriceapp.receiver;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import marketprice.marketpriceapp.job.ImportViaMessageJob;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OwnerImportProductHandler {
	
	private HashMap<String, Object> importProductProposal;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private ImportViaMessageJob importViaMessageJob;
    
    @RabbitHandler
    public void receive(LinkedHashMap<String, String> msg) {
    	
    	if (importProductProposal == null) {
    		importProductProposal = new HashMap<String, Object>();
    		for (Map.Entry<String, String> entry : msg.entrySet()) {
    			importProductProposal.put(entry.getKey(), entry.getValue());
        	}
    	}
    	
    	Map<String, JobParameter> confMap = new HashMap<>();
		confMap.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters jobParameters = new JobParameters(confMap);
		 try {
	            jobLauncher.run(importViaMessageJob.importViaMessageJob(), jobParameters);
	        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
	                | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {
	            System.out.println(e.getMessage());
	        }
		 setImportProductProposal(null);
    }
    
    public void setImportProductProposal(HashMap<String, Object> importProductProposal) {
    	this.importProductProposal = importProductProposal;
    }
    
    public HashMap<String, Object> getImportProductProposal() {
    	return this.importProductProposal;
    }
}
