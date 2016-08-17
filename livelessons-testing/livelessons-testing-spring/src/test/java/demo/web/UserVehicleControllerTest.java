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

import demo.domain.VehicleIdentificationNumber;
import demo.service.VehicleDetails;
import demo.service.VehicleIdentificationNumberNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * {@code @WebMvcTest} based tests for {@link UserVehicleController}.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserVehicleController.class)
public class UserVehicleControllerTest {

	private static final VehicleIdentificationNumber VIN = new VehicleIdentificationNumber(
			"01234567890123456");

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserVehicleService userVehicleService;

	@Test
	public void getVehicleWhenRequestingTextShouldReturnMakeAndModel() throws Exception {
		given(this.userVehicleService.getVehicleDetails("donald"))
				.willReturn(new VehicleDetails("Honda", "Civic"));
		this.mvc.perform(get("/donald/vehicle").accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk()).andExpect(content().string("Honda Civic"));
	}

	@Test
	public void getVehicleWhenRequestingJsonShouldReturnMakeAndModel() throws Exception {
		given(this.userVehicleService.getVehicleDetails("donald"))
				.willReturn(new VehicleDetails("Honda", "Civic"));
		this.mvc.perform(get("/donald/vehicle").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json("{'make':'Honda','model':'Civic'}"));
	}

	@Test
	public void getVehicleWhenRequestingHtmlShouldReturnMakeAndModel() throws Exception {
		given(this.userVehicleService.getVehicleDetails("donald"))
				.willReturn(new VehicleDetails("Honda", "Civic"));
		this.mvc.perform(get("/donald/vehicle.html").accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("<h1>Honda Civic</h1>")));
	}

	@Test
	public void getVehicleWhenUserNotFoundShouldReturnNotFound() throws Exception {
		given(this.userVehicleService.getVehicleDetails("donald"))
				.willThrow(new UserNameNotFoundException("donald"));
		this.mvc.perform(get("/donald/vehicle")).andExpect(status().isNotFound());
	}

	@Test
	public void getVehicleWhenVinNotFoundShouldReturnNotFound() throws Exception {
		given(this.userVehicleService.getVehicleDetails("donald"))
				.willThrow(new VehicleIdentificationNumberNotFoundException(VIN));
		this.mvc.perform(get("/donald/vehicle")).andExpect(status().isNotFound());
	}

}
