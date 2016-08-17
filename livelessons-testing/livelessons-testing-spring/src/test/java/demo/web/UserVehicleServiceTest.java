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

package demo.web;

import demo.domain.User;
import demo.domain.UserRepository;
import demo.domain.VehicleIdentificationNumber;
import demo.service.VehicleDetails;
import demo.service.VehicleDetailsService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

/**
 * Tests for {@link UserVehicleService}.
 */
public class UserVehicleServiceTest {

	private static final VehicleIdentificationNumber VIN = new VehicleIdentificationNumber(
			"01234567890123456");

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private VehicleDetailsService vehicleDetailsService;

	@Mock
	private UserRepository userRepository;

	private UserVehicleService service;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.service = new UserVehicleService(this.userRepository,
				this.vehicleDetailsService);
	}

	@Test
	public void getVehicleDetailsWhenUsernameIsNullShouldThrowException()
			throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("Username must not be null");
		this.service.getVehicleDetails(null);
	}

	@Test
	public void getVehicleDetailsWhenUsernameNotFoundShouldThrowException()
			throws Exception {
		given(this.userRepository.findByUsername(anyString())).willReturn(null);
		this.thrown.expect(UserNameNotFoundException.class);
		this.service.getVehicleDetails("donald");
	}

	@Test
	public void getVehicleDetailsShouldReturnMakeAndModel() throws Exception {
		given(this.userRepository.findByUsername("donald"))
				.willReturn(new User("donald", VIN));
		VehicleDetails details = new VehicleDetails("Honda", "Civic");
		given(this.vehicleDetailsService.getVehicleDetails(VIN)).willReturn(details);
		VehicleDetails actual = this.service.getVehicleDetails("donald");
		assertThat(actual).isEqualTo(details);
	}

}
