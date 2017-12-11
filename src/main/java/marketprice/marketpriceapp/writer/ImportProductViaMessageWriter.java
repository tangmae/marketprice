package marketprice.marketpriceapp.writer;

import java.util.List;

import marketprice.marketpriceapp.dao.EggDAO;
import marketprice.marketpriceapp.dao.RiceDAO;
import marketprice.marketpriceapp.dao.SugarDAO;
import marketprice.marketpriceapp.entity.Egg;
import marketprice.marketpriceapp.entity.Product;
import marketprice.marketpriceapp.entity.Rice;
import marketprice.marketpriceapp.entity.Sugar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class ImportProductViaMessageWriter implements ItemWriter<Product> {
	
	private static final Logger LOG = LoggerFactory.getLogger(ImportProductWriter.class);
	
	@Autowired
	private SugarDAO sugarDao;
	
	@Autowired
	private RiceDAO riceDao;
	
	@Autowired
	private EggDAO eggDao;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${rabbit.owner.queuename}")
	private String rabbitQueueName;
	
	@Override
	public void write(List<? extends Product> product) throws Exception {
		for (Product pd : product) {
			if (pd instanceof Sugar) {
				sugarDao.insert((Sugar)pd);
				rabbitTemplate.convertAndSend(rabbitQueueName, pd);
				LOG.info("Import by message :: SUGAR ");
			} 
			if (pd instanceof Rice) {
				riceDao.insert((Rice)pd);
				rabbitTemplate.convertAndSend(rabbitQueueName, pd);
				LOG.info("Import by message :: RICE ");
			}
			if (pd instanceof Egg) {
				eggDao.insert((Egg)pd);
				rabbitTemplate.convertAndSend(rabbitQueueName, pd);
				LOG.info("Import by message :: EGG ");
			}
		}
	}

}
