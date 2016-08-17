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

package com.example.autoconfigure.failureanalyzer;

import org.modelmapper.ConfigurationException;
import org.modelmapper.spi.ErrorMessage;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

public class ModelMapConfigurationFailureAnalyzer
		extends AbstractFailureAnalyzer<ConfigurationException> {

	@Override
	protected FailureAnalysis analyze(Throwable rootFailure,
			ConfigurationException cause) {
		StringBuilder description = new StringBuilder();
		description.append("ModelMapper configuration failed:\n");
		for (ErrorMessage message : cause.getErrorMessages()) {
			description.append(message.getMessage());
		}
		return new FailureAnalysis(description.toString(),
				"Fix ModelMapper configuration", cause);
	}

}
