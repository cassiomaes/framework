package br.gov.frameworkdemoiselle.internal.implementation;

import java.io.Serializable;

import br.gov.frameworkdemoiselle.annotation.Ignore;
import br.gov.frameworkdemoiselle.util.Beans;

public class ConfigurationImpl implements Serializable {

	private static final long serialVersionUID = 1L;

	@Ignore
	private boolean loaded = false;

	@SuppressWarnings("unused")
	private synchronized void load(Object instance) {
		if (!loaded) {
			Beans.getReference(ConfigurationLoader.class).load(instance);
			loaded = true;
		}
	}
}