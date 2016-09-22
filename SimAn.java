// Filename: SimAn.java
// Author: Faza Thirafi (13514033)

import java.io.*;
import java.util.*;
import java.lang.Math;

public class SimAn {

	//Atrtibutes
	private int temperature;
	private List<SimAnState> States;

	//Methods
	public SimAn() {
		temperature = 9999;
	}

	public int gettemperature() {
		return temperature;
	}

	public void settemperature(int T) {
		temperature = T;
	}


	public double prob(SimAnState curr, SimAnState succ) {

		double ex = Math.exp((succ.evaluate()-curr.evaluate())/temperature);
		
		return ex;
	}
	
	public void solve() {
		
	}


}
