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

import com.ycompany.ims.enums.ApplicationStatus;
import com.ycompany.ims.model.Policy;
import com.ycompany.ims.model.User;
import com.ycompany.ims.model.UserPolicy;
import com.ycompany.ims.repository.UserPolicyRepository;

/**
 * @author Sameer Kalra
 * 
 * Provides test cases for methods provided by userpolicy service.  
 */
public class UserPolicyServiceTest {

	@Mock
	private UserPolicyRepository mockUserPolicyRepository;

	private UserPolicyService userPolicyServiceUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
		userPolicyServiceUnderTest = new UserPolicyService(mockUserPolicyRepository);
	}

	@Test
	public void testFindUserPolicyByApplication() {
		// Setup
		final User user = User.builder()
				.id(1)
				.name("Customer")
				.lastName("1")
				.email("testcustomer@test.com")
				.build();

		final Policy policy = Policy.builder()
				.id(1)
				.name("Easy Term Insurance")
				.type("Term Insurance")
				.build();

		final UserPolicy userPolicy = UserPolicy.builder()
				.id(1)
				.user(user)
				.policy(policy)
				.build();

		Mockito.when(mockUserPolicyRepository.findById(anyInt()))
		.thenReturn(userPolicy);

		// Run the test
		final UserPolicy result = userPolicyServiceUnderTest.findUserPolicyByApplication(1);

		// Verify the results
		assertEquals(userPolicy, result);
	}

	@Test
	public void testFindUserPolicyByUserAndPolicy() {
		// Setup
		final User user = User.builder()
				.id(1)
				.name("Customer")
				.lastName("1")
				.email("testcustomer@test.com")
				.build();

		final Policy policy = Policy.builder()
				.id(1)
				.name("Easy Term Insurance")
				.type("Term Insurance")
				.build();

		final UserPolicy userPolicy = UserPolicy.builder()
				.id(1)
				.user(user)
				.policy(policy)
				.build();

		Mockito.when(mockUserPolicyRepository.findByUserAndPolicy(any(),any()))
		.thenReturn(userPolicy);

		// Run the test
		final UserPolicy result = userPolicyServiceUnderTest.findUserPolicyByUserAndPolicy(user,policy);

		// Verify the results
		assertEquals(userPolicy, result);
	}

	@Test
	public void testFindUserPolicyByUserAndApplication() {
		// Setup
		final User user = User.builder()
				.id(1)
				.name("Customer")
				.lastName("1")
				.email("testcustomer@test.com")
				.build();

		final Policy policy = Policy.builder()
				.id(1)
				.name("Easy Term Insurance")
				.type("Term Insurance")
				.build();

		final UserPolicy userPolicy = UserPolicy.builder()
				.id(1)
				.user(user)
				.policy(policy)
				.build();

		Mockito.when(mockUserPolicyRepository.findByUserAndId(any(),anyInt()))
		.thenReturn(userPolicy);

		// Run the test
		final UserPolicy result = userPolicyServiceUnderTest.findUserPolicyByUserAndApplication(user,1);

		// Verify the results
		assertEquals(userPolicy, result);
	}

	@Test
	public void testFindAllUserPolicies() {
		// Setup
		final User user = User.builder()
				.id(1)
				.name("Customer")
				.lastName("1")
				.email("testcustomer@test.com")
				.build();

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

		final UserPolicy userPolicy = UserPolicy.builder()
				.id(1)
				.user(user)
				.policy(policy)
				.build();

		final UserPolicy userPolicy2 = UserPolicy.builder()
				.id(2)
				.user(user)
				.policy(policy2)
				.build();

		List<UserPolicy> userPolicies = new ArrayList<UserPolicy>();
		userPolicies.add(userPolicy);
		userPolicies.add(userPolicy2);

		Mockito.when(mockUserPolicyRepository.findAll())
		.thenReturn(userPolicies);

		// Run the test
		final List<UserPolicy> result = userPolicyServiceUnderTest.findAllUserPolicies();

		// Verify the results
		assertEquals(userPolicies, result);
	}

	@Test
	public void testFindAllUserPoliciesByUser() {
		// Setup
		final User user = User.builder()
				.id(1)
				.name("Customer")
				.lastName("1")
				.email("testcustomer@test.com")
				.build();

		final Policy policy = Policy.builder()
				.id(1)
				.name("Easy Term Insurance")
				.type("Term Insurance")
				.build();

		final UserPolicy userPolicy = UserPolicy.builder()
				.id(1)
				.user(user)
				.policy(policy)
				.build();

		List<UserPolicy> userPolicies = new ArrayList<UserPolicy>();
		userPolicies.add(userPolicy);

		Mockito.when(mockUserPolicyRepository.findByUser(any()))
		.thenReturn(userPolicies);

		// Run the test
		final List<UserPolicy> result = userPolicyServiceUnderTest.findAllUserPoliciesByUser(user);

		// Verify the results
		assertEquals(userPolicies, result);
	}

	@Test
	public void testSaveUserPolicy() {
		// Setup
		final String applicationStatus = ApplicationStatus.IN_PROGRESS.toString();
		final User user = User.builder()
				.id(1)
				.name("Customer")
				.lastName("1")
				.email("testcustomer@test.com")
				.build();

		final Policy policy = Policy.builder()
				.id(1)
				.name("Easy Term Insurance")
				.type("Term Insurance")
				.build();

		final UserPolicy userPolicy = UserPolicy.builder()
				.id(1)
				.user(user)
				.policy(policy)
				.applicationStatus(ApplicationStatus.IN_PROGRESS.toString())
				.build();

		Mockito.when(mockUserPolicyRepository.save(any()))
		.thenReturn(userPolicy);

		// Run the test
		final UserPolicy result = userPolicyServiceUnderTest.saveUserPolicy(UserPolicy.builder().build());

		// Verify the results
		assertEquals(applicationStatus, result.getApplicationStatus());
	}
}
