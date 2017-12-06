package marketprice.marketpriceapp.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class ProductTypeReader implements ItemReader<Object> {
	
	private Object value;
	
	private Object result;
	
	private boolean isResultSet = false;
	
	@Autowired
	public RedisTemplate<String, Object> redisTemplate;

	public Object read() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		if (!isResultSet) {
			retrieveDataFromRedis();
			isResultSet = true;
		} else {
			this.result = null;
			this.value = null;
		}
		
		if (this.value != null) {
			Class<?> clazz = Class.forName("marketprice.marketpriceapp.entity." + this.value.toString());
			this.result = clazz.newInstance();
			return this.result;
		}
		
		return null;
	}
	
	public void retrieveDataFromRedis() {
		Object value = this.redisTemplate.opsForValue().get("IMPORT_PRODUCT");
		this.value = value;
	}
	
}
