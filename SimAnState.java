import java.io.*;
import java.util.*;
import java.lang.Math;

public class SimAnState {

	private int numSchedules;
	private int temperature;
	private Schedule[] Schedules = new Schedule[100];

	public SimAnState(Schedule[] input){
		
		numSchedules = input.length;
		Schedules = input;
		temperature = 999;
		
	}
	
	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int T) {
		temperature = T;
	}
	
	public int evaluate() {
		int cons = 0;
		for (int i = 0; i < numSchedules; i++){
			//cons += Schedules[i].countConflicts();
		} 
		
		return cons;
	}
	
	public Schedule[] getSchedules() {
		return Schedules;
		
	}
}
