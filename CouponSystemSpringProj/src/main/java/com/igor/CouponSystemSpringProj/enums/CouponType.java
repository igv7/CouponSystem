package com.igor.CouponSystemSpringProj.enums;

public enum CouponType {
	
	RESTAURANTS
	{
		@Override
	    public String toString() {
	      return "Restaurants";
		}
	},
	
	ELECTRICITY
	{
		@Override
	    public String toString() {
	      return "Electricity";
		}
	},
	
	FOOD
	{
		@Override
	    public String toString() {
	      return "Food";
		}
	},
	
	HEALTH
	{
		@Override
	    public String toString() {
	      return "Health";
		}
	},
	
	SPORTS
	{
		@Override
	    public String toString() {
	      return "Sports";
		}
	},
	
	CAMPING
	{
		@Override
	    public String toString() {
	      return "Camping";
		}
	},
	
	TRAVELING
	{
		@Override
	    public String toString() {
	      return "Traveling";
		}
	}

}
