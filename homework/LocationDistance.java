package com.company.ws01;

public class LocationDistance {
	
	static double distance = 0;
	public static double distanceCalc(Location l1, Location l2) {
		distance = Math.sqrt(Math.pow(l1.axisX-l2.axisX, 2) + Math.pow(l1.axisY-l2.axisY, 2));
		return distance;
	}
	
	public static void main(String[] args) {
			
		Location l1 = new Location(6,3);
		Location l2 = new Location(10,5);
		
		System.out.println(distanceCalc(l1,l2));
	}

}
