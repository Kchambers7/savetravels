package com.codingdojo.savetravels.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codingdojo.savetravels.models.Expense;
import com.codingdojo.savetravels.repositories.ExpenseRepository;

@Service
public class ExpenseService {



    // implement crud 
	
	// inject repository
	private final ExpenseRepository expenseRepository;
	
	// constructor
	public ExpenseService(ExpenseRepository expenseRepository) {
		this.expenseRepository = expenseRepository;
	}
		
	// find expenses
	public List<Expense> allExpenses(){
		return expenseRepository.findAll();
	}
	
	// find one expense  
	public Expense findExpense(Long id) {
		Optional<Expense> optExpense = expenseRepository.findById(id);
		if(optExpense.isPresent()) {
			return optExpense.get();
		}else {
			return null;
		}
	}
	
	// find all expenses from a user 
	public List<Expense> findUserExpenses(Long id) {
		return expenseRepository.findByUser_id(id);
	}

	// Create expense 
	public Expense createExpense(Expense expense) {
		return expenseRepository.save(expense);
	}

    // Update 
	public Expense updateExpense(Expense e) {
		return expenseRepository.save(e);
	}
	
	// delete expense 
	public void deleteExpense(Long id) {
		expenseRepository.deleteById(id);
	}
	

}
