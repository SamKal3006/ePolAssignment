package com.ycompany.ims.enums;

/**
 * @author Sameer Kalra
 * 
 * Provides commission rates in percentage.  
 */
public enum CommissionRate {
	TWO(2),
	FIVE(5),
	TEN(10);

	private int val;

	private CommissionRate(int val) {
		this.val = val;
	}

	public int getVal() {
		return val;
	}

	@Override
	public String toString() {
		return String.valueOf(val);
	}
}
