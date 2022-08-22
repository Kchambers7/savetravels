package com.codingdojo.savetravels.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.codingdojo.savetravels.models.Expense;
import com.codingdojo.savetravels.models.LoginUser;
import com.codingdojo.savetravels.models.User;
import com.codingdojo.savetravels.services.ExpenseService;
import com.codingdojo.savetravels.services.UserService;

@Controller
public class ExpenseController {
	
	private ExpenseService expenseService;
    private UserService userServ;
	
	// Constructor for service
    public ExpenseController(ExpenseService expenseService, UserService userServ) {
    	super();
    	this.expenseService = expenseService;
    	this.userServ = userServ;
    }
    

// routes
	
	// login page
	@GetMapping("/")
    public String userPage(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("newLogin", new LoginUser());
        return "userPage.jsp";
    }

	// Register route
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("newUser") User newUser, BindingResult result, Model model, HttpSession session) {
        
    	userServ.register(newUser, result);
    
        if(result.hasErrors()) {
            model.addAttribute("newLogin", new LoginUser());
            return "userPage.jsp";
        }
        
        session.setAttribute("userName", newUser.getUserName());
        session.setAttribute("user_id", newUser.getId());
        return "redirect:/dashboard";
    }
    
    // Login route
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, BindingResult result, Model model, HttpSession session) {
          
    	User user = userServ.login(newLogin, result);
        if(result.hasErrors()) {
            model.addAttribute("newUser", new User());
            return "userPage.jsp";
        }
    
        session.setAttribute("userName", user.getUserName());
        session.setAttribute("user_id", user.getId());
        return "redirect:/dashboard";
    }
    
   
    @GetMapping("/logout")
    	return "redirect:/";
    }
	
	// routes

	@GetMapping("/dashboard")
	public String dashboard(Model model, HttpSession session) {
		if(session.getAttribute("user_id") != null){
			Long user_id = (Long) session.getAttribute("user_id");
			model.addAttribute("user", userServ.findUser(user_id));
			model.addAttribute("allExpenses", expenseService.findUserExpenses(user_id));
    		return "dashboard.jsp";
    	}else {
    		return "redirect:/";
    	}
	}
	
    @GetMapping("/expenses/new")
    public String newName(Model model, @ModelAttribute("expense") Expense expense, HttpSession session) {
    	Long user_id = (Long) session.getAttribute("user_id");
		model.addAttribute("user", userServ.findUser(user_id));	    	
		return "addExpense.jsp";
    }
    // process
	@PostMapping("/processExpense")
	public String show(Model model, @Valid @ModelAttribute("expense") Expense expense, BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
			Long user_id = (Long) session.getAttribute("user_id");
			model.addAttribute("user", userServ.findUser(user_id));	    	
			return "addExpense.jsp";
		}else {
			expenseService.createExpense(expense);
			return "redirect:/dashboard";
		}
	}
	
	// Render edit page
	@GetMapping("/editExpense/{id}")
	public String editExpense(@PathVariable("id") Long id, Model model, HttpSession session) {
		Expense expense = expenseService.findExpense(id);
		model.addAttribute("expense", expense);
		return "editExpense.jsp";
	}
	
	
	@PutMapping("/editedExpense/{id}")
	public String editedExpense( @Valid @ModelAttribute("expense") Expense expense, BindingResult result, @PathVariable("id") Long id, Model model) {
		if(result.hasErrors()) {
			return "editExpense.jsp";
			
		} else {
			expenseService.updateExpense(expense);
			return "redirect:/dashboard";
		}
	}
	
	// Delete expense
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		expenseService.deleteExpense(id);
		return "redirect:/dashboard";
	}
	
	// render expense
	@GetMapping("/oneExpense/{id}")
	public String oneExpense(@PathVariable("id") Long id, Model model, HttpSession session) {
		model.addAttribute("expense", expenseService.findExpense(id));
	    return "oneExpense.jsp";
	}
	
}