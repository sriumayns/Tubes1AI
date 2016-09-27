import java.io.*;
import java.util.*;
import java.lang.Math;

public class SimAnState {

	private int temperature;
	private Schedule[] Schedules;

	public SimAnState(Schedule[] input){
		Schedules = new Schedule[input.length];
		Schedules = input;
		temperature = 100;
	}
	
	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int T) {
		temperature = T;
	}
	
	public int evaluate() {
		int totalConflict = 0;
		for (int i=0; i< Schedules.length; i++) {
			for (int day = 1; day < 6; day++) {
				for (int hour = 7; hour < 18; hour++) {
					totalConflict += Schedules[i].getConflict(day,hour);
				}
			}
		}
		return totalConflict; 
	}
	
	public Schedule[] getSchedules() {
		return Schedules;
	}
}
