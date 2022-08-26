package net.codejava;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {
	@Autowired
	private ProductService service;

	@GetMapping("/admin/login")
	public String getLogin() {
		return "login";
	}

	@GetMapping("/index")
	public String viewStudentPage(Model model) {
		List<Users> usersList = service.listAll();
		model.addAttribute("usersList",usersList);
		return "index";
	}

	@GetMapping("/student/login")
	public String getStudentLogin() {
		return "login";
	}

	@RequestMapping("/delete/{id}")
	public String deleteStudent(@PathVariable(name = "id") Long id) {
		service.delete(id);
		return "redirect:/index";
	}

	@GetMapping("/new")
	public String insert(Model model) {
		model.addAttribute("users", new Users());
		return "edit_user";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveUsers(@ModelAttribute("users") Users user) {
		service.save(user);
		return "redirect:/index";
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView showEditStudentPage(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("edit_user");
		Optional<Users> user = service.GetUserById(id);
		mav.addObject("users", user);
		return mav;
	}

}
