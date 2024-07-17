package net.codejava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Controller
public class SupermarketController {

	@Autowired
	private SupermarketService service;

	@GetMapping("/")
	public String viewHomePage(Model model) {
		List<Product> listProducts = service.getAll();
		model.addAttribute("listProducts", listProducts);
		return "index";
	}

	@GetMapping("/filter")
	public String filterProducts(@RequestParam(required = false) String code, @RequestParam(required = false) String description,
								 @RequestParam(required = false) String category, Model model) {
		List<Product> filteredProducts = service.getProducts(code, description, category);
		model.addAttribute("listProducts", filteredProducts);
		model.addAttribute("code", code);
		model.addAttribute("description", description);
		model.addAttribute("category", category);
		return "index";
	}

	@GetMapping("/create")
	public String createNewProductPage(Model model) {
		if (!model.containsAttribute("product")) {
			Product product = new Product();
			model.addAttribute("product", product);
		}
		return "create";
	}

	@GetMapping("/update/{code}")
	public String showEditProductPage(@PathVariable String code, Model model) {
		if (!model.containsAttribute("product")) {
			Optional<Product> product = service.getProduct(code);
			model.addAttribute("product", product.get());
		}
		return "update";
	}

	@GetMapping("/delete/{code}")
	public String deleteProduct(@PathVariable String code) {
		service.deleteProduct(code);
		return "redirect:/";
	}

	@PostMapping("/save")
	public String saveProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult,
							  @RequestHeader(name = "Referer", required = false) String referer, RedirectAttributes redirectAttributes) {
		if (referer != null)
			if (referer.contains("create") && (bindingResult.hasErrors() || service.existsByCode(product.getCode()))) {
				redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.product", bindingResult);
				redirectAttributes.addFlashAttribute("product", product);
				return "redirect:/create";
			} else if (referer.contains("update") && bindingResult.hasErrors()) {
				redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.product", bindingResult);
				redirectAttributes.addFlashAttribute("product", product);
				return "redirect:/update/" + product.getCode();
			}
		service.save(product);
		return "redirect:/";
	}

	@GetMapping("/search")
	public String showSearchPage(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "search";
	}

	@PostMapping("/search")
	public String searchProduct(@RequestParam("code") String code, Model model) {
		Optional<Product> product = service.getProduct(code);
		if (!product.isPresent())
			return "redirect:/search";
		model.addAttribute("product", product.get());
		return "search";
	}
}
