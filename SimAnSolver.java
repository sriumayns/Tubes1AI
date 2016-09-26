// Filename: SimAn.java
// Author: Faza Thirafi (13514033)

import java.io.*;
import java.util.*;
import java.lang.Math;

public class SimAnSolver {
	
	
	//Methods
	public SimAnSolver() {
		
	}

	


	public double prob(SimAnState curr, SimAnState succ) {

		double ex = Math.exp((succ.evaluate()-curr.evaluate())/curr.getTemperature());
		
		return ex;
	}
	
	public SimAnState findSuccessor(SimAnState current) {
		
		return current;
	}
	
	public SimAnState solve(SimAnState init) {
		SimAnState curr = new SimAnState(init.getSchedules());
		SimAnState succ = new SimAnState(init.getSchedules());
		boolean solved = false;
		int evalDiff;
		
		//init.initializeSolution();
		
		while (!solved) {
			if (curr.getTemperature() == 0) {
				solved = true;
			}
			else {
				succ = findSuccessor(curr);
				evalDiff = succ.evaluate()-curr.evaluate();
				if (evalDiff>0) {
					curr = succ;
				}
				else {
					if (prob(curr,succ)>0) {curr = succ;}
				}
			}
		}
		
		return curr;
	}


}
