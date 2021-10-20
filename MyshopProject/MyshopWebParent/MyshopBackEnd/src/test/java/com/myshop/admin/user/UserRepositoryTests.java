package com.myshop.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.myshop.common.entity.Role;
import com.myshop.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private UserRepository repo;

	@Autowired
	private TestEntityManager entityManager;

	// test เพิ่มข้อมูล User เข้าฐานข้อมูล และ Role
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userAnongnat = new User("Anongnat@gmail.com", "Anongnat2020", "Anongnat", "Pakot");
		userAnongnat.addRole(roleAdmin);

		User savedUser = repo.save(userAnongnat);

		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	// test เพิ่มข้อมูล User เข้าฐานข้อมูล และ Role
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userLisa = new User("Lisa@gmail.com", "lisa2020", "Lalisa", "manoban");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);

		userLisa.addRole(roleEditor);
		userLisa.addRole(roleAssistant);

		User savedUser = repo.save(userLisa);

		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}

	@Test
	public void testGetUserById() {
		User userAnongnat = repo.findById(1).get();
		System.out.println(userAnongnat);
		assertThat(userAnongnat).isNotNull();
	}

	// test Update Email
	@Test
	public void testUpdateUserDetails() {
		User userNam = repo.findById(1).get();
		userNam.setEnabled(true);
		userNam.setEmail("anongnat5555@gmail.com");

		repo.save(userNam);
	}

	// test Update User Roles
	@Test
	public void testUpdateUserRoles() {
		User userLisa = repo.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSalesperson = new Role(2);

		userLisa.getRoles().remove(roleEditor);
		userLisa.addRole(roleSalesperson);

		repo.save(userLisa);
	}

	// test ลบข้อมูล User
	@Test
	public void testDeleteUser() {
		Integer userId = 2;
		repo.deleteById(userId);

	}

	@Test
	public void testGetUserByEmail() {
		String email = "Lisa@gmail.com";
		User user = repo.getUserByEmail(email);

		assertThat(user).isNotNull();
	}

	@Test
	public void testCountById() {
		Integer id = 1;
		Long countById = repo.countById(id);

		assertThat(countById).isNotNull().isGreaterThan(0);
	}

	@Test
	public void testDisableUser() {
		Integer id = 1;
		repo.updateEnabledStatus(id, false);
	}

	@Test
	public void testEnableUser() {
		Integer id = 6;
		repo.updateEnabledStatus(id, true);

	}

	@Test
	public void testListFirstPage() {
		int pageNumber = 0;
		int pageSize = 4;

		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(pageable);

		List<User> listUsers = page.getContent();

		listUsers.forEach(user -> System.out.println(user));

		assertThat(listUsers.size()).isEqualTo(pageSize);
	}

	@Test
	public void testSearchUsers() {
		String keyword = "bruce";

		int pageNumber = 0;
		int pageSize = 4;

		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(keyword, pageable);

		List<User> listUsers = page.getContent();

		listUsers.forEach(user -> System.out.println(user));

		assertThat(listUsers.size()).isGreaterThan(0);
	}

}
