package com.codingdojo.savetravels.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="expenses")
public class Expense {
	
	// ----- Table Variables -----
	
	//NotNull and NonNull are essentially the same, NotNull allows you to display a message
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    // can be like @Size(min = 2, max = 30, message = "The min amnt of letters is 2, max is 30");
    @Size(min=1, message="Must be higher than 0")
    private String expenseName;
    @NotNull
    @Size(min=1, message="Must be higher than 0")
    private String vendor;
    @NotNull(message="Must be higher than 0")
    @Min(value=(long) 0.1, message="Must be higher than 0")
    private double amount;
    @NotNull
    @Size(min=1, message="Must be higher than 0")
    private String description;
    // This will not allow the createdAt column to be updated after creation
    @Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
        
    // ----- Constructors -----
    
    public Expense(){}
    
    
    
    public Expense(@NotNull @Size(min = 1, message = "Must be higher than 0") String expenseName,
    		@NotNull @Size(min = 1, message = "Must be higher than 0") String vendor,
    		@NotNull(message = "Must be higher than 0") @Min(value = 0, message = "Must be higher than 0") double amount,
    		@NotNull @Size(min = 1, message = "Must be higher than 0") String description,User user) {
    	super();
    	this.expenseName = expenseName;
    	this.vendor = vendor;
    	this.amount = amount;
    	this.description = description;
    	this.user = user;
    }
	
    public Expense(Long id, @NotNull @Size(min = 1, message = "Must be higher than 0") String expenseName,
    		@NotNull @Size(min = 1, message = "Must be higher than 0") String vendor,
    		@NotNull(message = "Must be higher than 0") @Min(value = 0, message = "Must be higher than 0") double amount,
    		@NotNull @Size(min = 1, message = "Must be higher than 0") String description, Date createdAt,
    		Date updatedAt,User user) {
    	super();
    	this.id = id;
    	this.expenseName = expenseName;
    	this.vendor = vendor;
    	this.amount = amount;
    	this.description = description;
    	this.createdAt = createdAt;
    	this.updatedAt = updatedAt;
    	this.user = user;
    }
    
    // ----- Getters and Setters -----
    
    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    public String getExpenseName() {
    	return expenseName;
    }
    
    public void setExpenseName(String expenseName) {
    	this.expenseName = expenseName;
    }
    
    public String getVendor() {
    	return vendor;
    }
    
    public void setVendor(String vendor) {
    	this.vendor = vendor;
    }
    
    public double getAmount() {
    	return amount;
    }
    
    public void setAmount(double amount) {
    	this.amount = amount;
    }
    
    public String getDescription() {
    	return description;
    }
    
    public void setDescription(String description) {
    	this.description = description;
    }
    
    public Date getCreatedAt() {
    	return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
    	this.createdAt = createdAt;
    }
    
    public Date getUpdatedAt() {
    	return updatedAt;
    }
    
    public void setUpdatedAt(Date updatedAt) {
    	this.updatedAt = updatedAt;
    }
    
    public User getUser() {
    	return user;
    }
    
    public void setUser(User user) {
    	this.user = user;
    }
    
	// ----- Created At and Updated At -----

	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }

	@PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
}
