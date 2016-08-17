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

package com.example.autoconfigure.modelmapper;

import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(ModelMapper.class)
@EnableConfigurationProperties(ModelMapperProperties.class)
@ConditionalOnProperty(prefix = "modelmapper", name = "enabled", matchIfMissing = true)
public class ModelMapperAutoConfiguration {

	// NOTE: Ensure package name won't be scanned by user apps

	private final ModelMapperProperties properties;

	private final List<PropertyMap<?, ?>> mappings;

	private final List<Converter<?, ?>> converters;

	public ModelMapperAutoConfiguration(ModelMapperProperties properties,
			ObjectProvider<List<PropertyMap<?, ?>>> mappings,
			ObjectProvider<List<Converter<?, ?>>> converters) {
		this.properties = properties;
		this.mappings = mappings.getIfAvailable();
		this.converters = converters.getIfAvailable();
	}

	@Bean
	@ConditionalOnMissingBean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		addMappings(modelMapper);
		addConverters(modelMapper);
		return modelMapper;
	}

	private void addMappings(ModelMapper modelMapper) {
		if (this.properties.isAddMappings() && this.mappings != null) {
			for (PropertyMap<?, ?> mappings : this.mappings) {
				modelMapper.addMappings(mappings);
			}
		}
	}

	private void addConverters(ModelMapper modelMapper) {
		if (this.properties.isAddConverters() && this.converters != null) {
			for (Converter<?, ?> converter : this.converters) {
				modelMapper.addConverter(converter);
			}
		}
	}

}
