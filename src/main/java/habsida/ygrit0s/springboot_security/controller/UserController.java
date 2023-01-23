package habsida.ygrit0s.springboot_security.controller;

import habsida.ygrit0s.springboot_security.entity.*;
import habsida.ygrit0s.springboot_security.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UserController {
	
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/user/info")
	public String userPage(Model model, Principal principal) {
		Long id = userService.getByUsername(principal.getName()).getId();
		model.addAttribute("user", userService.getUser(id));
		return "user/info";
	}


}
