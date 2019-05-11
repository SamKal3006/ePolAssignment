package com.ycompany.ims.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * @author Sameer Kalra
 * 
 * Defines policy entity class and its relationship with database.  
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "policy")
public class Policy {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "policy_id")
	private int id;
	@Column(name = "policy_name")
	@NotEmpty(message = "*Please provide a policy name")
	private String name;
	@Column(name = "policy_type")
	@NotEmpty(message = "*Please provide a policy type")
	private String type;
	@Column(name = "policy_term")
	@NotEmpty(message = "*Please provide a policy term")
	private int term;
	@Column(name = "policy_cover",precision=15, scale=2)
	@NotEmpty(message = "*Please provide a policy cover amount")
	private BigDecimal cover;
	@Column(name = "policy_premium",precision=15, scale=2)
	@NotEmpty(message = "*Please provide a policy premium amount")
	private BigDecimal premium;

}
