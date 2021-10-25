package com.emirates.model;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "promocodes")
public class ApplicationProps {

	private List<Promo> promos;

	public List<Promo> getPromos() {
		return promos;
	}

	public void setPromos(List<Promo> promos) {
		this.promos = promos;
	}
}


