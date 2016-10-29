package hello;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ViewController {
	@Inject
	ProductService productService;
	@Value("classpath:/public/phones/*.jpg")
	Resource[] resources;
	List<Person> persons = new ArrayList();

	@RequestMapping(value = "/greeting", method = RequestMethod.GET)
	public String greeting(Model model,
			@RequestHeader(value = "X-Requested-With", required = false) String ajaxHeader) {
		model.addAttribute("person", new Person());
		if (ajaxHeader != null) {
			return "greeting :: lista";

		}
		return "greeting";
	}

	@RequestMapping(value = "/greeting", method = RequestMethod.POST)
	public String save(@RequestParam("file") MultipartFile file, @ModelAttribute("person") @Valid Person person,
			BindingResult bs) {
		if (bs.hasErrors()) {
			return "greeting";
		}
		productService.veryReportGeneration();
		productService.veryReportGeneration();
		productService.veryReportGeneration();
		person.phone = resources[persons.size()].getFilename();
		persons.add(person);
		return "redirect:/greeting";
	}

	public static class Person {
		@NotEmpty
		private String name;
		private String phone;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}
	}

	@RequestMapping(value = "/asJSON", method = RequestMethod.GET)
	@ResponseBody
	public List<Person> asJson() {
		return persons;
	}

	@ModelAttribute("persons")
	public List<Person> provideList() {
		return persons;
	}

}
