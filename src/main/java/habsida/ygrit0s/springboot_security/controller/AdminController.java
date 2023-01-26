package habsida.ygrit0s.springboot_security.controller;

import habsida.ygrit0s.springboot_security.entity.*;
import habsida.ygrit0s.springboot_security.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AdminController {

	private final UserService userService;
	private final RoleService roleService;

	@Autowired
	public AdminController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	@GetMapping("/admin/users")
	public String userList(Model model) {
		model.addAttribute("userList", userService.userList());
		model.addAttribute("roleList", roleService.roleList());
		return "admin/users";
	}
	
	@GetMapping("/admin/users/{id}")
	public String getUser(@PathVariable("id") long id, Model model) {
		model.addAttribute("user", userService.getUser(id));
		model.addAttribute("roleList", roleService.roleList());
		return "admin/users/0";
	}

	@GetMapping("/admin/users/new")
	public String add(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("roleList", roleService.roleList());
		return "admin/users/new";
	}
	
	@PostMapping("/admin/users/new")
	public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
		model.addAttribute("roleList", roleService.roleList());
		if (bindingResult.hasErrors()) {
			return "admin/users/new";
		}
		if (!userService.addUser(user)) {
			bindingResult.addError(new ObjectError("", ""));
			return "admin/users/new";
		}
		return "redirect:/admin/users";
	}
	
	@GetMapping("/admin/users/edit/{id}")
	public String update(@PathVariable("id") long id, Model model) {
		model.addAttribute("user", userService.getUser(id));
		model.addAttribute("roleList", roleService.roleList());
		return "admin/users/edit";
	}

	@PatchMapping("/admin/users/edit/{id}")
	public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "admin/users/edit";
		} else {
			userService.updateUser(user);
			return "redirect:/admin/users";
		}
	}

	@DeleteMapping("/admin/users/delete/{id}")
	public String removeUser(@PathVariable("id") long id, Principal principal) {
		userService.removeUser(id, principal);
		return "redirect:/admin/users";
	}
}
