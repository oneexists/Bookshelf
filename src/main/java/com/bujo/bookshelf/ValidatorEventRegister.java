package com.bujo.bookshelf;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.validation.Validator;

/**
 * ValidatorEventRegister is a class that listens for repository events and validates the associated entities.
 *
 * @author skylar
 * @version 1.0
 * @since 1.0
 */
public class ValidatorEventRegister implements InitializingBean {
	@Autowired
	ValidatingRepositoryEventListener validatingRepositoryEventListener;
	@Autowired
	private Map<String, Validator> validators;

	@Override
	public void afterPropertiesSet() throws Exception {
		List<String> events = Arrays.asList("beforeCreate", "beforeSave");
		for (Map.Entry<String, Validator> entry : validators.entrySet()) {
			events.stream()
					.filter(p -> entry.getKey().startsWith(p))
					.findFirst()
					.ifPresent(p -> validatingRepositoryEventListener.addValidator(p, entry.getValue()));
		}
	}

}
