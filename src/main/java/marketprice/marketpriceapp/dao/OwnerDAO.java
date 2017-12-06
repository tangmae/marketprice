package marketprice.marketpriceapp.dao;

import java.util.List;

import javax.sql.DataSource;

import marketprice.marketpriceapp.entity.Owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class OwnerDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private DataSource dataSource;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private final String ALL_COLUMN = " owner_id, name, warning_count, delete_flag ";
	private final String TABLE_NAME = " owner ";
	
	public List<Owner> findAll () {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT ");
		query.append(ALL_COLUMN);
		query.append(" FROM ");
		query.append(TABLE_NAME);
		query.append(" ORDER BY owner_id ASC ");
		
		return this.jdbcTemplate.query(query.toString(), new BeanPropertyRowMapper<Owner>(Owner.class));
	}
	
	public Owner findById (Owner owner) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT ");
		query.append(ALL_COLUMN);
		query.append(" FROM ");
		query.append(TABLE_NAME);
		query.append(" WHERE ");
		query.append(" owner_id = :ownerId ");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("ownerId", owner.getOwnerId());
		
		return this.namedParameterJdbcTemplate.queryForObject(query.toString(), params, new BeanPropertyRowMapper<Owner>(Owner.class));
		
	}
	
	public void updateWarningCount (int count, Owner owner) {
		StringBuilder query = new StringBuilder();
		query.append(" UPDATE ");
		query.append(TABLE_NAME);
		query.append(" SET warning_count = :warningCount ");
		query.append(" WHERE ");
		query.append(" owner_id = :ownerId ");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("warningCount", count);
		params.addValue("ownerId", owner.getOwnerId());
		
		this.jdbcTemplate.update(query.toString(), params);
		
	}
	
	public void updateDeleteFlag (boolean delete, Owner owner) {
		StringBuilder query = new StringBuilder();
		query.append(" UPDATE ");
		query.append(TABLE_NAME);
		query.append(" SET delete_flag = :deleteFlag ");
		query.append(" WHERE ");
		query.append(" owner_id = :ownerId ");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("deleteFlag", delete);
		params.addValue("ownerId", owner.getOwnerId());
		
		this.jdbcTemplate.update(query.toString(), params);
		
	}
	
	public Owner randomPickingOwner () {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT ");
		query.append(ALL_COLUMN);
		query.append(" FROM ");
		query.append(TABLE_NAME);
		query.append(" ORDER BY RAND() LIMIT 1 ;");
		
		return this.jdbcTemplate.queryForObject(query.toString(),new BeanPropertyRowMapper<Owner>(Owner.class));
		
	}

}
