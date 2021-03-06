package com.consulting.mgt.springboot.practica5.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.consulting.mgt.springboot.practica5.entities.User;
import com.consulting.mgt.springboot.practica5.services.impl.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// define controller
@Controller
public class UserController {

	// inyecta UserService por constructor
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	// localhost:8080/?page=1&size=5

	@GetMapping("/")
	public String index(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);

		log.info("retrieving page of Users for page: {} of size: {}", currentPage - 1, pageSize);

		// Implementa
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

		Page<User> usersPage = userService.findPaginated(pageable);

		usersPage = usersPage.getContent().size() > 0 ? usersPage : null;

		model.addAttribute("usersPage", usersPage);

		int totalPages = usersPage == null ? 0 : usersPage.getTotalPages();

		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.range(0, totalPages).boxed().collect(Collectors.toList());

			model.addAttribute("pageNumbers", pageNumbers);
		}

		log.info("going to index view");

		return "index";
	}

	@ResponseBody
	@GetMapping("/all")
	public Page<User> all(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		int currentPage = 0;
		int pageSize = 0;

		log.info("retrieving page of Users for page: {} of size: {}", currentPage - 1, pageSize);

		// Implementa

		return null;
	}

	@GetMapping("/add-user-form")
	public String showSignUpForm(User user) {

		log.info("going to add-user-form view");

		return "add-user-form";
	}

	@PostMapping("/add-user")
	public String addUser(@Valid User user, BindingResult result, Model model) {

		// Implementa
		if (result.hasErrors()) {
			log.info("going to add-user-form view as User is invalid");

			return "add-user-form";
		}

		log.info("user create");

		this.userService.saveOrUpdate(user);

		log.info("redirecting to '/' path");

		return "redirect:/";
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {

		log.info("retrieving User with id: {}", id);

		// Implementa
		User user = this.userService.searchById(id);
		model.addAttribute("user", user);

		log.info("going to update-user-form view");

		return "update-user-form";
	}

	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {

		user.setId(id);

		// Implementa
		if (result.hasErrors()) {
			log.info("going to update-user-form view as User is invalid");

			return "update-user-form";
		}

		log.info("user update");

		this.userService.saveOrUpdate(user);

		log.info("redirecting to '/' path");

		return "redirect:/";
		// return "index";
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {

		// Implementa
		log.info("retrieving User with id: {}", id);

		User user = this.userService.searchById(id);

		log.info("deleting User with id: {}", id);

		this.userService.delete(user);

		log.info("redirecting to '/' path");

		return "redirect:/";
	}
}
