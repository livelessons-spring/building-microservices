/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package demo.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Data JPA tests for {@link User}.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserEntityTest {

	private static final VehicleIdentificationNumber VIN = new VehicleIdentificationNumber(
			"01234567890123456");

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void createWhenUsernameIsNullShouldThrowException() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("Username must not be empty");
		new User(null, VIN);
	}

	@Test
	public void createWhenUsernameIsEmptyShouldThrowException() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("Username must not be empty");
		new User("", VIN);
	}

	@Test
	public void createWhenVinIsNullShouldThrowException() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("VIN must not be null");
		new User("donald", null);
	}

	@Test
	public void saveShouldPersistData() throws Exception {
		User user = this.entityManager.persistFlushFind(new User("donald", VIN));
		assertThat(user.getUsername()).isEqualTo("donald");
		assertThat(user.getVin()).isEqualTo(VIN);
	}

}
