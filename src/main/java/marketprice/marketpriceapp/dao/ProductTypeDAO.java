package marketprice.marketpriceapp.dao;

import java.util.List;

import javax.sql.DataSource;

import marketprice.marketpriceapp.entity.ProductType;
import marketprice.marketpriceapp.entity.Rice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductTypeDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
    private DataSource dataSource;
	
	private final String ALL_COLUMN = " producttype_id, name, description, import_date, delete_flag ";
	private final String TABLE_NAME = " product_type ";
	
	
	public List<ProductType> findAll() {
		StringBuilder query = new StringBuilder();
		query.append("SELECT ");
		query.append(ALL_COLUMN);
		query.append(" FROM ");
		query.append(TABLE_NAME);
		query.append(" ORDER BY producttype_id asc");
		
		return this.jdbcTemplate.query(query.toString(), new BeanPropertyRowMapper<ProductType>(ProductType.class));
	}
	
	public ProductType findById (ProductType productType) {
		  StringBuilder query = new StringBuilder();
		  query.append(" SELECT ");
		  query.append(ALL_COLUMN);
		  query.append(" FROM ");
		  query.append(TABLE_NAME);
		  query.append(" WHERE ");
		  query.append(" producttype_id = :producttypeId ");
		  
		  MapSqlParameterSource params = new MapSqlParameterSource();
		  params.addValue("producttypeId", productType.getProducTypeId());
		  
		  return this.namedParameterJdbcTemplate.queryForObject(query.toString(), params, new BeanPropertyRowMapper<ProductType>(ProductType.class));
		  
		}
	
}
