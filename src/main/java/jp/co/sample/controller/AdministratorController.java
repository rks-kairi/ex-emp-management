package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@Autowired
	private HttpSession session;

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
	 * フォームオブジェクトをrequestスコープに格納、 このメソッドは@RequestMappingがついているメソッドよりも先に呼ばれます.
	 * 
	 * @return フォームオブジェクトがrequestスコープに格納される
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}

	/**
	 * 管理者登録画面へフォワード.
	 * 
	 * @return 管理者登録画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}

	/**
	 * 管理者を登録する.
	 * 
	 * @param form フォーム
	 * @return ログイン画面(リダイレクト)
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {

		Administrator administrator = new Administrator();

		BeanUtils.copyProperties(form, administrator);

		administratorService.insert(administrator);

		return "redirect:/";
	}

	/**
	 * ログイン画面にフォワードする.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}

	/**
	 * ログインを行う.
	 * 
	 * @param form  フォーム
	 * @param model セッションスコープ
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/login")
	public String login(LoginForm form, Model model) {
		Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());
		if (administrator == null) {
			model.addAttribute("failure", "メールアドレスまたはパスワードが不正です。");
			return "administrator/login";
		}
		session.setAttribute("administratorName", administrator.getName());
		return "forward:/employee/showList";
	}

}
