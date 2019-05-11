package com.ycompany.ims.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sameer Kalra
 * 
 * Defines userpolicy entity class and its relationship with database.  
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_policy")
public class UserPolicy {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "app_id")
	private int id;
	@OneToOne
	@JoinColumn(name="app_user", referencedColumnName="user_id")
	private User user;
	@OneToOne
	@JoinColumn(name="app_policy", referencedColumnName="policy_id")
	private Policy policy;
	@Column(name = "premium_payment_plan")
	//@NotEmpty(message = "*Please provide the Premium Payment Plan")
	private String premiumPaymentPlan;
	@Column(name="premium_amount",precision=15, scale=2)
	//@NotEmpty(message = "*Please provide the Premium Amount")
	private BigDecimal premiumAmount;
	@Column(name="premium_pay_date")
	//@NotEmpty(message = "*Please provide the Premium Payment Date")
	private Date premiumPayDate;
	@Column(name="premium_start_date")
	//@NotEmpty(message = "*Please provide the Premium Start Date")
	private Date premiumStartDate;
	@Column(name="premium_next_date")
	//@NotEmpty(message = "*Please provide the Next Premium Due Date")
	private Date premiumNextDate;
	@Column(name="application_status")
	//@NotEmpty(message = "*Please provide the Application Status")
	private String applicationStatus;
	@Column(name="commission_amount",precision=15, scale=2)
	private BigDecimal commissionAmount;
}
