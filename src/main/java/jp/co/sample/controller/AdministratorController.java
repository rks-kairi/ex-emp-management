package jp.co.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

/**
 * 管理者情報登録画面を表示する処理をするコントローラ.
 * 
 * @author kairi.hashimoto
 *
 */
@Controller
@RequestMapping("/")
public class AdministratorController {
	
	@Autowired
	private AdministratorService administratorService;
	
	/**
	 * LoginFormをインスタンス化しそのままreturnする処理を記述する.
	 * 
	 * @return フォームオブジェクトがrequestスコープに格納される
	 */
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}
	
	/**
	 * フォームオブジェクトをrequestスコープに格納、
	 * このメソッドは@RequestMappingがついているメソッドよりも先に呼ばれます。
	 * @return フォームオブジェクトがrequestスコープに格納される
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}
	/**
	 * 管理者登録画面へフォワード
	 * @return 管理者登録画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}
	
	/**
	 * 管理者を登録する.
	 * 
	 * @param form InsertAdministratorForm
	 * @return
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator administrator = new Administrator();
		administrator.setName(form.getName());
		administrator.setMailAddress(form.getMailAddress());
		administrator.setPassword(form.getPassword());
		
		administratorService.insert(administrator);
		
		return "redirect:/";
	}
	/**
	 * login.htmlにフォワードする処理を記述.
	 * 
	 * @return 
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}
	
}
