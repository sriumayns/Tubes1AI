// Filename: SimulatedAnnealing.java
// Author: Faza Thirafi (13514033)

import java.io.*;
import java.util.*;
import java.lang.Math;

public class SimulatedAnnealing {
	
	
	//Methods
	public static void SimulatedAnnealing(ScheduleBoard init) {
		int temperature = 100;
		int tempReduction = 1;
		ScheduleBoard curr = new ScheduleBoard();
		ScheduleBoard succ = new ScheduleBoard();
		boolean solved = false;
		int evalDiff = 0;
		int probab = 0;
		
		//Simulated Annealing Algorithm			
		while (!solved) {
			if (temperature == 0) {
				solved = true;
			}
			else {
				succ = findSuccessor(curr);
				evalDiff = evaluate(succ) - evaluate(curr);
				if (evalDiff>0) {
					curr = succ;
				}
				else {
					//making random number between 0.0 ~ 1.0
					//for comparison of probability
					Random rand = new Random();
					float randomProbab = rand.nextFloat() * (1.0f - 0.0f) + 0.0f;

					//counting Acceptance Probability Function
					double prob = Math.exp((evaluate(succ)-evaluate(curr)) / temperature);
		
					if (prob>randomProbab) {
						curr = succ;
					}
				}
			}
			temperature -= tempReduction; //decreasing temperature
		}
		

	}
	
	private static ScheduleBoard findSuccessor(ScheduleBoard currentSchedule) {
		int[] searchResult = {0,0};
		boolean conflictFound = false;
		int scheduleIdx = 0;
		Course course;
		while ((scheduleIdx < currentSchedule.length)&&(!conflictFound)) {
			searchResult = currentSchedule[scheduleIdx].getConflictSlot();
			if ((searchResult[1] !=0)&&(searchResult[0]!=0)) {
				conflictFound = true;
			}
		scheduleIdx++;
		}
		if (conflictFound) {
			course = currentSchedule[scheduleIdx-1].getAndDeleteLastInsertedCourseFromSLot(searchResult[0],searchResult[1]);
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
				while (!currentSchedule.getScheduleWithIndex(newRoomIdx).getRoom().getRoomName().equals(choosenRoomName)) {
					newRoomIdx++;
				}
				if (currentSchedule.getScheduleWithIndex(newRoomIdx).isScheduleLocked(randomDay,randomHour)) {
					slotLock = true;
				}
			}
			if ((randomDay !=0)&&(randomHour !=0)) {
				currentSchedule.getScheduleWithIndex(newRoomIdx).insertCourseToSchedule(randomDay,randomHour,course);
			}
			
		}
		return currentSchedule;
	}
	
	
	private static int evaluate(ScheduleBoard s) {	
		return s.countConflict(); 
	}


}