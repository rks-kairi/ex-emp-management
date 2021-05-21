package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;

/**
 * administratorsテーブルを操作するリポジトリ.
 * 
 * @author hashimotokairi
 * 
 */
@Repository
public class AdministratorRepository {
	
	/**
	 * Administratorオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs,i) -> {
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mail_address"));
		administrator.setPassword(rs.getString("password"));
		return administrator;
	};
	
	/** テンプレートを定義*/
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * 渡した管理者情報を保存.
	 * 
	 * @param administrators 管理者情報
	 */
	public void insert(Administrator administrator) {
		
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
		
		String insertSql = "INSERT INTO administrators(name,mail_address,password)"
				+ " VALUES(:name,:mailAddress,:password)";
		template.update(insertSql, param);
		
	}
	
	/**
	 * 渡したメールアドレスとパスワードを使って、管理者情報検索を行う.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password パスワード
	 * @return 検索された管理者情報(1件も存在しない場合nullを返す)
	 */
	public Administrator findByMailAddressAndPassword(String mailAddress, String password){
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress",mailAddress ).addValue("password", password);
				
		String findBySql = "SELECT id,name,mail_address,password FROM administrators WHERE mail_address=:mailAddress AND password=:password;";
		
		List<Administrator> administratorList = template.query(findBySql, param, ADMINISTRATOR_ROW_MAPPER);
		
		if(administratorList.size() == 0) {
			return null;
		}
		return administratorList.get(0);
	}
	
}
