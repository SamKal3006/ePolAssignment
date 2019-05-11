package com.ycompany.ims.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.ycompany.ims.model.Policy;
import com.ycompany.ims.repository.PolicyRepository;

/**
 * @author Sameer Kalra
 * 
 * Provides test cases for methods provided by policy service.  
 */
public class PolicyServiceTest {

	@Mock
	private PolicyRepository mockPolicyRepository;

	private PolicyService policyServiceUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
		policyServiceUnderTest = new PolicyService(mockPolicyRepository);

	}

	@Test
	public void testFindPolicyById() {
		// Setup
		final int id = 1;
		final Policy policy = Policy.builder()
				.id(1)
				.name("Easy Term Insurance")
				.type("Term Insurance")
				.build();

		Mockito.when(mockPolicyRepository.save(any()))
		.thenReturn(policy);
		Mockito.when(mockPolicyRepository.findById(anyInt()))
		.thenReturn(policy);

		// Run the test
		final Policy result = policyServiceUnderTest.findPolicyById(id);

		// Verify the results
		assertEquals(id, result.getId());
	}

	@Test
	public void testFindAllPolicies() {
		// Setup
		final Policy policy = Policy.builder()
				.id(1)
				.name("Easy Term Insurance")
				.type("Term Insurance")
				.build();
		final Policy policy2 = Policy.builder()
				.id(2)
				.name("Basic Life Insurance")
				.type("Life Insurance")
				.build();
		final List<Policy> policies = new ArrayList<Policy>();
		policies.add(policy);
		policies.add(policy2);
		Mockito.when(mockPolicyRepository.findAll())
		.thenReturn(policies);

		// Run the test
		final List<Policy> result = policyServiceUnderTest.findAllPolicies();

		// Verify the results
		assertEquals(policies, result);
	}


}
