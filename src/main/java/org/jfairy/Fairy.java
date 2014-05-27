/*
 * Copyright (c) 2013 Codearte
 */
package org.jfairy;

import com.google.inject.Inject;
import org.jfairy.producer.BaseProducer;
import org.jfairy.producer.DateProducer;
import org.jfairy.producer.company.Company;
import org.jfairy.producer.net.NetworkProducer;
import org.jfairy.producer.payment.CreditCard;
import org.jfairy.producer.person.Person;
import org.jfairy.producer.person.PersonFactory;
import org.jfairy.producer.person.PersonProperties;
import org.jfairy.producer.text.TextProducer;

import javax.inject.Provider;
import java.util.Random;

public final class Fairy {

	private final TextProducer textProducer;
	private final PersonFactory personFactory;
	private final Provider<Company> company;
	private final NetworkProducer networkProducer;
	private final BaseProducer baseProducer;
	private final DateProducer dateProducer;
	private final Provider<CreditCard> creditCardProvider;

	@Inject
	Fairy(TextProducer textProducer, PersonFactory personFactory, Provider<Company> company, NetworkProducer networkProducer,
				BaseProducer baseProducer, DateProducer dateProducer, Provider<CreditCard> creditCardProvider) {
		this.textProducer = textProducer;
		this.personFactory = personFactory;
		this.company = company;
		this.networkProducer = networkProducer;
		this.baseProducer = baseProducer;
		this.dateProducer = dateProducer;
		this.creditCardProvider = creditCardProvider;
	}

	public static Fairy create(){
		return Bootstrap.create();
	}

	public static Bootstrap.Builder builder() {
		return Bootstrap.builder();
	}

	/**
	 * Use this method for generating texts
	 *
	 * @return A {@link org.jfairy.producer.text.TextProducer} instance
	 */
	public TextProducer textProducer() {
		return textProducer;
	}

	/**
	 * Use this method for fake persons
	 *
	 * @param personProperties desired person features
	 * @return A {@link org.jfairy.producer.person.Person} instance
	 */
	public Person person(PersonProperties.PersonProperty... personProperties) {
		return personFactory.producePerson(personProperties);
	}

	/**
	 * Use this method to generate fake company
	 *
	 * @return A {@link org.jfairy.producer.company.Company} instance
	 */
	public Company company() {
		return company.get();
	}

	/**
	 * Use this method for get standard tools
	 *
	 * @return A {@link org.jfairy.producer.BaseProducer} instance
	 */
	public BaseProducer baseProducer() {
		return baseProducer;
	}

	public DateProducer dateProducer() {
		return dateProducer;
	}

	public CreditCard creditCard() {
		return creditCardProvider.get();
	}

	public NetworkProducer networkProducer() {
		return networkProducer;
	}
}
