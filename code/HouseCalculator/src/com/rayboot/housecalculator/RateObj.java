package com.rayboot.housecalculator;

public class RateObj {
	public String name;
	public RateDetailObj year1;
	public RateDetailObj year2;
	public RateDetailObj year4;
	public RateDetailObj year6;

	public RateObj() {
		super();
		// TODO Auto-generated constructor stub
		year1 = new RateDetailObj();
		year2 = new RateDetailObj();
		year4 = new RateDetailObj();
		year6 = new RateDetailObj();
	}

}
