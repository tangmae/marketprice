package marketprice.marketpriceapp.dao;

import java.util.List;

import javax.sql.DataSource;

import marketprice.marketpriceapp.entity.Egg;
import marketprice.marketpriceapp.entity.Product;
import marketprice.marketpriceapp.entity.Rice;
import marketprice.marketpriceapp.entity.Sugar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class RiceDAO extends ProductDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	  private DataSource dataSource;
	  
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private final String ALL_COLUMN = " rice_id, price_per_unit, volumn, import_date, delete_flag, owner_id ";
	private final String TABLE_NAME = " rice ";
	private final String COMMON_CONDITION = "delete_flag IS FALSE";

	@Override
	public List<Rice> findAll () {
	  StringBuilder query = new StringBuilder();
	  query.append(" SELECT ");
	  query.append(ALL_COLUMN);
	  query.append(" FROM ");
	  query.append(TABLE_NAME);
	  query.append(" WHERE ");
	  query.append(COMMON_CONDITION);
	  query.append(" ORDER BY rice_id ASC ");
	  
	  return this.jdbcTemplate.query(query.toString(), new BeanPropertyRowMapper<Rice>(Rice.class));
	}

	public Rice findById (Rice rice) {
	  StringBuilder query = new StringBuilder();
	  query.append(" SELECT ");
	  query.append(ALL_COLUMN);
	  query.append(" FROM ");
	  query.append(TABLE_NAME);
	  query.append(" WHERE ");
	  query.append(" rice_id = :riceId ");
	  query.append(" AND ");
	  query.append(COMMON_CONDITION);
	  
	  MapSqlParameterSource params = new MapSqlParameterSource();
	  params.addValue("riceId", rice.getRiceId());
	  
	  return this.namedParameterJdbcTemplate.queryForObject(query.toString(), params, new BeanPropertyRowMapper<Rice>(Rice.class));
	  
	}
	
	public List<Rice> findExpiredRice() {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT ");
		query.append(ALL_COLUMN);
		query.append(" FROM ");
		query.append(TABLE_NAME);
		query.append(" WHERE ");
		query.append(" DATEDIFF(NOW(), DATE(import_date)) > 1 ");
		query.append(" AND ");
		query.append(COMMON_CONDITION);
		query.append(";");
		
		return this.jdbcTemplate.query(query.toString(), new BeanPropertyRowMapper<Rice>(Rice.class));
	}
	
	@Override
	public void updatePricePerUnit (int price, Product product) {
		StringBuilder query = new StringBuilder();
		query.append(" UPDATE ");
		query.append(TABLE_NAME);
		query.append(" SET price_per_unit = :priceperunit ");
		query.append(" WHERE ");
		query.append(" rice_id = :riceId ");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("priceperunit", price);
		params.addValue("riceId", ((Rice)product).getRiceId());
		
		this.namedParameterJdbcTemplate.update(query.toString(), params);
		
	}
	
	public void updateDeleteFlag (boolean delete, Rice rice) {
		StringBuilder query = new StringBuilder();
		query.append(" UPDATE ");
		query.append(TABLE_NAME);
		query.append(" SET delete_flag = :deleteFlag ");
		query.append(" WHERE ");
		query.append(" rice_id = :riceId ");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("deleteFlag", true);
		params.addValue("riceId", rice.getRiceId());
		
		this.namedParameterJdbcTemplate.update(query.toString(), params);	
	}
	
	public void insert (Rice rice) {
		StringBuilder query = new StringBuilder();
		query.append(" INSERT INTO ");
		query.append(TABLE_NAME);
		query.append(" ( ");
		query.append(" price_per_unit, volumn, import_date, owner_id ");
		query.append(" ) ");
		query.append(" VALUE ");
		query.append(" ( ");
		query.append(" :price , ");
		query.append(" :volumn , ");
		query.append(" :import_date , ");
		query.append(" :owner_id ");
		query.append(" ); ");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("price",rice.getPricePerUnit());
		params.addValue("volumn",rice.getVolumn());
		params.addValue("import_date",rice.getImportDate());
		params.addValue("owner_id",rice.getOwnerId());
		
		this.namedParameterJdbcTemplate.update(query.toString(), params);
	}

	public List<Rice> findOutRangePriceProduct(int lowerbound, int upperbound) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT ");
		query.append(ALL_COLUMN);
		query.append(" FROM ");
		query.append(TABLE_NAME);
		query.append(" WHERE ");
		query.append(" ( price_per_unit > :upperbound ");
		query.append(" OR ");
		query.append(" price_per_unit < :lowerbound ) ");
		query.append(" AND ");
		query.append(COMMON_CONDITION);
		query.append(" ORDER BY rice_id ASC ");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("upperbound", upperbound);
		params.addValue("lowerbound", lowerbound);
		
		return this.namedParameterJdbcTemplate.query(query.toString(), params, new BeanPropertyRowMapper<Rice>(Rice.class));
	}

}
