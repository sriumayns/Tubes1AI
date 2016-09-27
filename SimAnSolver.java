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
		course = scheduleBoard[scheduleIdx-1].getAndDeleteLastInsertedCourseFromSLot(searchResult[0],searchResult[1]);
				//
		int randomRoomIndex;
		String choosenRoomName;
		int randomDayIdx;
		int[] availDay;
		int randomDay=0;
		int randomHour=0;
		boolean slotLock = false;
		int newRoomIdx = 0;
		while (!slotLock) {
			if (course.getRoomConstraint().equals("-")) {
				randomRoomIndex = randInt(0,roomNumber-1);
				choosenRoomName = rooms[randomRoomIndex].getRoomName();
			}
			else {
				choosenRoomName = course.getRoomConstraint();
			}
			randomDayIdx = randInt(0,course.getDayConstraint().length-1);
			availDay = course.getDayConstraint();
			randomDay = availDay[randomDayIdx];
			randomHour = randInt(course.getStartHourConstraint(),course.getEndHourConstraint()-1);
			newRoomIdx =0;
			while (!scheduleBoard[newRoomIdx].getRoom().getRoomName().equals(choosenRoomName)) {
				newRoomIdx++;
			}
			if (scheduleBoard[newRoomIdx].isScheduleLocked(randomDay,randomHour)) {
				slotLock = true;
			}
		}
		if ((randomDay !=0)&&(randomHour !=0)) {
			scheduleBoard[newRoomIdx].insertCourseToSchedule(randomDay,randomHour,course);
		}

		current.setTemperature(current.getTemperature()-10);
		return current;
	}
	
	public SimAnState solve(SimAnState init) {
		SimAnState curr = new SimAnState(init.getSchedules());
		SimAnState succ = new SimAnState(init.getSchedules());
		boolean solved = false;
		int evalDiff;
			
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
