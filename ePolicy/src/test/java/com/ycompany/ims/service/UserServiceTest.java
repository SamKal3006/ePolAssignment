package com.ycompany.ims.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ycompany.ims.model.User;
import com.ycompany.ims.repository.RoleRepository;
import com.ycompany.ims.repository.UserRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Sameer Kalra
 * 
 * Provides test cases for methods provided by user service.  
 */
public class UserServiceTest {

	@Mock
	private UserRepository mockUserRepository;
	@Mock
	private RoleRepository mockRoleRepository;
	@Mock
	private BCryptPasswordEncoder mockBCryptPasswordEncoder;

	private UserService userServiceUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
		userServiceUnderTest = new UserService(mockUserRepository,
				mockRoleRepository,
				mockBCryptPasswordEncoder);

	}

	@Test
	public void testFindUserByEmail() {
		// Setup
		final String email = "testadmin@test.com";
		final User employee = User.builder()
				.id(1)
				.name("Admin")
				.lastName("1")
				.email("testadmin@test.com")
				.build();

		Mockito.when(mockUserRepository.save(any()))
		.thenReturn(employee);
		Mockito.when(mockUserRepository.findByEmail(anyString()))
		.thenReturn(employee);

		// Run the test
		final User result = userServiceUnderTest.findUserByEmail(email);

		// Verify the results
		assertEquals(email, result.getEmail());
	}

	@Test
	public void testSaveAgent() {
		// Setup
		final String email = "testagent@test.com";
		final User agent = User.builder()
				.id(1)
				.name("Agent")
				.lastName("1")
				.email("testagent@test.com")
				.build();

		Mockito.when(mockUserRepository.save(any()))
		.thenReturn(agent);

		// Run the test
		User result = userServiceUnderTest.saveAgent(User.builder().build());

		// Verify the results
		assertEquals(email, result.getEmail());
	}

	@Test
	public void testSaveEmployee() {
		// Setup
		final String email = "testadmin@test.com";
		final User employee = User.builder()
				.id(1)
				.name("Admin")
				.lastName("1")
				.email("testadmin@test.com")
				.build();

		Mockito.when(mockUserRepository.save(any()))
		.thenReturn(employee);

		// Run the test
		User result = userServiceUnderTest.saveEmployee(User.builder().build());

		// Verify the results
		assertEquals(email, result.getEmail());
	}

	@Test
	public void testSaveCustomer() {
		// Setup
		final String email = "testcustomer@test.com";
		final User customer = User.builder()
				.id(1)
				.name("Customer")
				.lastName("1")
				.email("testcustomer@test.com")
				.build();

		Mockito.when(mockUserRepository.save(any()))
		.thenReturn(customer);

		// Run the test
		User result = userServiceUnderTest.saveCustomer(User.builder().build());

		// Verify the results
		assertEquals(email, result.getEmail());
	}
}
