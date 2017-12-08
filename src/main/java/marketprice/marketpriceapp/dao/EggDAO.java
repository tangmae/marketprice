package marketprice.marketpriceapp.dao;

import java.util.List;

import javax.sql.DataSource;

import marketprice.marketpriceapp.entity.Egg;
import marketprice.marketpriceapp.entity.Rice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EggDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	  private DataSource dataSource;
	  
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private final String ALL_COLUMN = " egg_id, price_per_unit, volumn, import_date, delete_flag, owner_id ";
	private final String TABLE_NAME = " egg ";
	private final String COMMON_CONDITION = "delete_flag IS FALSE";

	public List<Egg> findAll () {
	  StringBuilder query = new StringBuilder();
	  query.append(" SELECT ");
	  query.append(ALL_COLUMN);
	  query.append(" FROM ");
	  query.append(TABLE_NAME);
	  query.append(" WHERE ");
	  query.append(COMMON_CONDITION);
	  query.append(" ORDER BY egg_id ASC ");
	  
	  return this.jdbcTemplate.query(query.toString(), new BeanPropertyRowMapper<Egg>(Egg.class));
	}

	public Egg findById (Egg egg) {
	  StringBuilder query = new StringBuilder();
	  query.append(" SELECT ");
	  query.append(ALL_COLUMN);
	  query.append(" FROM ");
	  query.append(TABLE_NAME);
	  query.append(" WHERE ");
	  query.append(" egg_id = :eggId ");
	  query.append(" AND ");
	  query.append(COMMON_CONDITION);
	  
	  MapSqlParameterSource params = new MapSqlParameterSource();
	  params.addValue("eggId", egg.getEggId());
	  
	  return this.namedParameterJdbcTemplate.queryForObject(query.toString(), params, new BeanPropertyRowMapper<Egg>(Egg.class));
	}
	
	public List<Egg> findExpiredEgg() {
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
		
		return this.jdbcTemplate.query(query.toString(), new BeanPropertyRowMapper<Egg>(Egg.class));
	}
	
	public void updatePricePerUnit (int price, Egg egg) {
		StringBuilder query = new StringBuilder();
		query.append(" UPDATE ");
		query.append(TABLE_NAME);
		query.append(" SET price_per_unit = :priceperunit ");
		query.append(" WHERE ");
		query.append(" egg_id = :eggId ");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("priceperunit", price);
		params.addValue("eggId", egg.getEggId());
		
		this.namedParameterJdbcTemplate.update(query.toString(), params);
		
	}
	
	public void updateDeleteFlag (boolean delete, Egg egg) {
		StringBuilder query = new StringBuilder();
		query.append(" UPDATE ");
		query.append(TABLE_NAME);
		query.append(" SET delete_flag = :deleteFlag ");
		query.append(" WHERE ");
		query.append(" egg_id = :eggId ");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("deleteFlag", true);
		params.addValue("eggId", egg.getEggId());
		
		this.namedParameterJdbcTemplate.update(query.toString(), params);
	}
	
	public void insert (Egg egg) {
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
		params.addValue("price",egg.getPricePerUnit());
		params.addValue("volumn",egg.getVolumn());
		params.addValue("import_date",egg.getImportDate());
		params.addValue("owner_id",egg.getOwnerId());
		
		this.namedParameterJdbcTemplate.update(query.toString(), params);
	}

	public List<Egg> findOutRangePriceProduct(int lowerbound, int upperbound) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT ");
		query.append(ALL_COLUMN);
		query.append(" FROM ");
		query.append(TABLE_NAME);
		query.append(" WHERE ");
		query.append("( price_per_unit > :upperbound ");
		query.append(" OR ");
		query.append(" price_per_unit < :lowerbound )");
		query.append(" AND ");
		query.append(COMMON_CONDITION);
		query.append(" ORDER BY egg_id ASC ");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("upperbound", upperbound);
		params.addValue("lowerbound", lowerbound);
		
		return this.namedParameterJdbcTemplate.query(query.toString(), params, new BeanPropertyRowMapper<Egg>(Egg.class));
	}
}
