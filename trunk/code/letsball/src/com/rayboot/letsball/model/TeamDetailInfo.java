package com.rayboot.letsball.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

public class TeamDetailInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String area;
	public String name;
	public String leader;
	public String ground;
	public String net;
	public ArrayList<Object> players = new ArrayList<Object>();

	public TeamDetailInfo(Document doc) {
		// TODO Auto-generated constructor stub

		Element estart = doc.body().getElementsByTag("table").get(0);
		Elements trsElements = estart.getElementsByTag("tr");
		Log.i("test----------------------", trsElements.size() + "");
		for (Element element : trsElements) {
			Elements tdsElements = element.getElementsByTag("td");
			if (tdsElements.size() == 0) {
				continue;
			}
			players.add(new PlayerInfo(tdsElements));
		}

		estart = doc.body().getElementsByAttributeValue("class", "qdtxt")
				.get(0);
		trsElements = estart.getElementsByTag("p");
		for (int i = 0; i < trsElements.size(); i++) {
			if (i == 0) {
				area = trsElements.get(i).getElementsByTag("span").text();
			} else if (i == 3) {
				leader = trsElements.get(i).getElementsByTag("span").text();
			} else if (i == 2) {
				ground = trsElements.get(i).getElementsByTag("span").text();
			} else if (i == 5) {
				net = trsElements.get(i).getElementsByTag("span").text();
			}
		}
	}

}
