package marketprice.marketpriceapp.dao;

import java.util.List;

import javax.sql.DataSource;

import marketprice.marketpriceapp.entity.Egg;
import marketprice.marketpriceapp.entity.Owner;
import marketprice.marketpriceapp.entity.Rice;
import marketprice.marketpriceapp.entity.Sugar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SugarDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DataSource dataSource;
	  
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private final String ALL_COLUMN = " sugar_id, price_per_unit, volumn, import_date, delete_flag, owner_id ";
	private final String TABLE_NAME = " sugar ";
	private final String COMMON_CONDITION = "delete_flag IS FALSE";

	public List<Sugar> findAll () {
	  StringBuilder query = new StringBuilder();
	  query.append(" SELECT ");
	  query.append(ALL_COLUMN);
	  query.append(" FROM ");
	  query.append(TABLE_NAME);
	  query.append(" WHERE ");
	  query.append(COMMON_CONDITION);
	  query.append(" ORDER BY sugar_id ASC ");
	  
	  return this.jdbcTemplate.query(query.toString(), new BeanPropertyRowMapper<Sugar>(Sugar.class));
	}

	public Sugar findById (Sugar sugar) {
	  StringBuilder query = new StringBuilder();
	  query.append(" SELECT ");
	  query.append(ALL_COLUMN);
	  query.append(" FROM ");
	  query.append(TABLE_NAME);
	  query.append(" WHERE ");
	  query.append(" sugar_id = :sugarId ");
	  query.append(" AND ");
	  query.append(COMMON_CONDITION);
	  
	  MapSqlParameterSource params = new MapSqlParameterSource();
	  params.addValue("sugarId", sugar.getSugarId());
	  
	  return this.namedParameterJdbcTemplate.queryForObject(query.toString(), params, new BeanPropertyRowMapper<Sugar>(Sugar.class)); 
	}
	
	public List<Sugar> findExpiredSugar() {
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
		
		return this.jdbcTemplate.query(query.toString(), new BeanPropertyRowMapper<Sugar>(Sugar.class));
	}
	
	public void updatePricePerUnit (int price, Sugar sugar) {
		StringBuilder query = new StringBuilder();
		query.append(" UPDATE ");
		query.append(TABLE_NAME);
		query.append(" SET price_per_unit = :priceperunit ");
		query.append(" WHERE ");
		query.append(" sugar_id = :sugarId ");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("priceperunit", price);
		params.addValue("sugarId", sugar.getSugarId());
		
		this.namedParameterJdbcTemplate.update(query.toString(), params);
		
	}
	
	public void updateDeleteFlag (boolean delete, Sugar sugar) {
		StringBuilder query = new StringBuilder();
		query.append(" UPDATE ");
		query.append(TABLE_NAME);
		query.append(" SET delete_flag = :deleteFlag ");
		query.append(" WHERE ");
		query.append(" sugar_id = :sugarId ");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("deleteFlag", true);
		params.addValue("sugarId", sugar.getSugarId());
		
		this.namedParameterJdbcTemplate.update(query.toString(), params);
		
	}
	
	public void insert (Sugar sugar) {
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
		params.addValue("price",sugar.getPricePerUnit());
		params.addValue("volumn",sugar.getVolumn());
		params.addValue("import_date",sugar.getImportDate());
		params.addValue("owner_id",sugar.getOwnerId());
		
		this.namedParameterJdbcTemplate.update(query.toString(), params);
	}
	
	public List<Sugar> findOutRangePriceProduct(int lowerbound, int upperbound) {
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
		query.append(" ORDER BY sugar_id ASC ");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("upperbound", upperbound);
		params.addValue("lowerbound", lowerbound);
		
		return this.namedParameterJdbcTemplate.query(query.toString(), params, new BeanPropertyRowMapper<Sugar>(Sugar.class));
	}

}
