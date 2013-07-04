package com.rayboot.housecalculator;

import java.text.DecimalFormat;

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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name + "  明细如下\n" + year1.name + "  公积金：" + getDouble(year1.gjj)
				+ "%  商贷：" + getDouble(year1.sy) + "%\n" + year2.name
				+ "  公积金：" + getDouble(year2.gjj) + "%  商贷："
				+ getDouble(year2.sy) + "%\n" + year4.name + "  公积金："
				+ getDouble(year4.gjj) + "%  商贷：" + getDouble(year4.sy) + "%\n"
				+ year6.name + "  公积金：" + getDouble(year6.gjj) + "%  商贷："
				+ getDouble(year6.sy) + "%";
	}

	private double getDouble(String value) {
		DecimalFormat df = new DecimalFormat("#.##");
		return Double.parseDouble(df.format(Double.valueOf(value) * 100));
	}
}
