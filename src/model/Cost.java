package model;

import java.io.Serializable;

public class Cost implements Serializable {
	private double buy;
	private double sale;

	public Cost(double buy, double sale) {
		this.buy = buy;
		this.sale = sale;
	}

	public double getBuy() {
		return buy;
	}

	public double getSale() {
		return sale;
	}

	@Override
	public String toString() {
		return buy + "/" + sale;
	}

}
