package com.affan.weatherapp.weatherutils;

/**
 * Created by Affan on 29/06/2016.
 */
public class Weather {
	
	public Location location;
	public CurrentCondition currentCondition = new CurrentCondition();
	
	public byte[] iconData;
	
	public  class CurrentCondition {
		private String condition;
		private String descr;
		private String icon;
		public String getCondition() {
			return condition;
		}
		public void setCondition(String condition) {
			this.condition = condition;
		}
		public String getDescr() {
			return descr;
		}
		public void setDescr(String descr) {
			this.descr = descr;
		}
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
	}
}
