/*
 * Copyright (c) 2013 Codearte
 */

package eu.codearte.fairyland.producer.util;

import org.yaml.snakeyaml.Yaml;

import javax.inject.Singleton;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Maps.newHashMap;

@Singleton
public class DataMaster {

	public static final String FIRST_NAME = "firstNames";
	public static final String LAST_NAME = "lastNames";
	public static final String PERSONAL_EMAIL = "personalEmails";
	public static final String DOMAIN = "domains";
	public static final String COMPANY_NAME = "companyNames";
	public static final String COMPANY_SUFFIX = "companySuffixes";
	public static final String COMPANY_EMAIL = "companyEmails";
	public static final String TELEPHONE_NUMBER_FORMATS = "telephone_number_formats";


	private Map<String, List<String>> data = newHashMap();

	private DataMaster() {
	}

	void appendData(DataMaster dataMaster) {
		data.putAll(dataMaster.data);
	}

	/**
	 * This method is used by YAML decoder
	 *
	 * @param data fetched from yaml document
	 */
	@SuppressWarnings("unused")
	public void setData(Map<String, List<String>> data) {
		this.data = data;
	}

	/**
	 * Returns list (null safe) of elements for desired key from data files
	 *
	 * @param key desired node key
	 * @return list of elements for desired key
	 * @throws IllegalArgumentException if no element for key has been found
	 */
	@SuppressWarnings("unchecked")
	public List<String> getStringList(String key) {
		return (List<String>) getData(key);
	}

	@SuppressWarnings("unchecked")
	public Map<String, List<String>> getStringMap(String key) {
		return (Map<String, List<String>>) getData(key);
	}

	/**
	 * Returns element (null safe) for desired key from data files
	 *
	 * @param key desired node key
	 * @return string element for desired key
	 * @throws IllegalArgumentException if no element for key has been found
	 */
	public String getString(String key) {
		return (String) getData(key);
	}

	private Object getData(String key) {
		Object element = data.get(key);
		checkArgument(element != null, "No such key: %s", key);
		return element;
	}

	public void readResources(String path) throws IOException {
		Enumeration<URL> resources =
				getClass().getClassLoader().getResources(path);

		Yaml yaml = new Yaml();
		while (resources.hasMoreElements()) {
			appendData(yaml.loadAs(resources.nextElement().openStream(), DataMaster.class));
		}
	}
}