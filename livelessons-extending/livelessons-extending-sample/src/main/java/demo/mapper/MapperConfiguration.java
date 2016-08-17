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

package demo.mapper;

import org.modelmapper.ModelMapper;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

	private ModelMapper modelMapper;

	public MapperConfiguration(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Bean
	public OrderDto orderDto() {
		Name name = new Name();
		name.setFirstName("Danger");
		name.setLastName("Mouse");
		Customer customer = new Customer();
		customer.setName(name);
		Address billingAddress = new Address();
		billingAddress.setCity("London");
		billingAddress.setStreet("221c Baker Street");
		Order order = new Order();
		order.setCustomer(customer);
		order.setBillingAddress(billingAddress);
		return this.modelMapper.map(order, OrderDto.class);
	}

	@Bean
	public CommandLineRunner print(OrderDto dto) {
		return (args) -> System.err.println(dto);
	}

}
