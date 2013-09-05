package com.rayboot.jietongmap.obj;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "POIObjs")
public class POIObj extends Model {

	@Column(name = "name")
	public String name;
	@Column(name = "type")
	public String type;
	@Column(name = "address")
	public String address;
	@Column(name = "tel")
	public String tel;
	@Column(name = "la")
	public int la;
	@Column(name = "lo")
	public int lo;

	public POIObj() {
		super();
		// TODO Auto-generated constructor stub
	}

	public POIObj(String name, String type, String address, String tel, int la,
			int lo) {
		super();
		this.name = name;
		this.type = type;
		this.address = address;
		this.tel = tel;
		this.la = la;
		this.lo = lo;
	}

	public static List<POIObj> getAllData() {
		return new Select().from(POIObj.class).execute();
	}

}
