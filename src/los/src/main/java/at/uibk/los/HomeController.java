package at.uibk.los;


import java.util.Locale;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController
{

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * if u want to deliver data to view: 
	 * model.addAttribute("accessName", theData );
	 * and access data with ${accessName} in view
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(Locale locale, Model model) 
	{
		return "index";
	}
	
}
