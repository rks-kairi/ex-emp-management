package jp.co.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Administrator;
import jp.co.sample.repository.AdministratorRepository;

/**
 * 管理者情報を操作するサービス.
 * 
 * @author kairi.hashimoto
 *
 */
@Service
@Transactional
public class AdministratorService {
	@Autowired
	private AdministratorRepository addAdministratorRepository;

	/**
	 * 管理者情報を挿入する.
	 * 
	 * @param administrator 挿入する管理者情報
	 */
	public void insert(Administrator administrator) {
		addAdministratorRepository.insert(administrator);
	}

	/**
	 * ログイン処理をする
	 * 
	 * @param mailAddress メールアドレス
	 * @param password    パスワード
	 * @return 検索された管理者情報(1件も存在しなければnullを返す)
	 */
	public Administrator login(String mailAddress, String password) {
		return addAdministratorRepository.findByMailAddressAndPassword(mailAddress, password);
	}
}
