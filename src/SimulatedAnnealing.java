// Filename: SimulatedAnnealing.java
// Author: Faza Thirafi (13514033)

import java.io.*;
import java.util.*;
import java.lang.Math;

public class SimulatedAnnealing {
	
	

	/*
		Simulated Annealing main algorithm
	*/
	public static void SimulatedAnnealing(ScheduleBoard init) {
		int currTemperature;
		int intialTemperature = 100;
		int tempReduction = 1;
		ScheduleBoard curr = new ScheduleBoard();
		ScheduleBoard succ = new ScheduleBoard();
		boolean stopLoop = false;
		int evalDiff = 0;
		int probab = 0;
		
		currTemperature = intialTemperature;
		while (!stopLoop) {
			if (currTemperature == 0) {
				stopLoop = true;
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
					float randomProbab = randFloat(0.0f,1.1f);

					//counting Acceptance Probability Function
					double prob = Math.exp((evaluate(curr)-evaluate(succ)) / currTemperature);
					
					//comparing both probability
					if ((float) prob>randomProbab) {
						curr = succ; 
					}
				}
			}
			currTemperature -= tempReduction; //decreasing currTemperature
		}
		

	}
	
	/*
		method for finding the successor of a state (SceduleBoard state)
	*/
	private static ScheduleBoard findSuccessor(ScheduleBoard currentSchedule) {
		int[] searchResult = {0,0}; //pair of day and hour
		boolean conflictFound = false;
		int scheduleIdx = 0;
		Course course;

	/*	
		//sequentially check if there's any conflict in each schedule
		while ((scheduleIdx < currentSchedule.getScheduleBoardLength())&&(!conflictFound)) {
			searchResult = currentSchedule.getScheduleWithIndex(scheduleIdx).getConflictSlot();
			if ((searchResult[1] !=0)&&(searchResult[0]!=0)) {
				conflictFound = true;
			}
			scheduleIdx++;
		}

		//if any conflict found, try to relocate
		if (conflictFound) {
			course = currentSchedule.getScheduleWithIndex(scheduleIdx-1).getAndDeleteLastInsertedCourseFromSLot(searchResult[0],searchResult[1]);

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
		*/
		return currentSchedule;
	}

	/*
		Generating random integer value
	*/	
	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}	

	/*
		Generating random float value
	*/	
	public static float randFloat(float min, float max) {
		Random rand = new Random();
		float randomFloat = rand.nextFloat() * (max - min) + min;

		return randomFloat;
	}
	
	/*
		counting the evaluation value of a state of ScheduleBoard
	*/
	public static int evaluate(ScheduleBoard s) {	
		return s.countConflict(); 
	}



}