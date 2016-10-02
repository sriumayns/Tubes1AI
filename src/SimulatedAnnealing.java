
import java.io.*;
import java.util.*;
import java.lang.Math;

public class SimulatedAnnealing {

	public static ScheduleBoard runSimulatedAnnealing(){
		ScheduleBoard scheduleBoard = new ScheduleBoard();
		simulatedAnnealing(scheduleBoard);
		return scheduleBoard;
	}
	/*
		Simulated Annealing main algorithm
	*/
	public static void simulatedAnnealing(ScheduleBoard inputState) {
		int currTemperature;
		int intialTemperature = 100;
		int tempReduction = 1;
		ScheduleBoard currState = new ScheduleBoard();
		ScheduleBoard succState = new ScheduleBoard();
		boolean stopLoop = false;
		int evalDiff = 0;
		int probab = 0;
		
		currTemperature = intialTemperature;
		currState = inputState;
		while (!stopLoop) {
			if (currTemperature == 0) {
				stopLoop = true;
			}
			else {
				succState = findSuccessor(currState);
				evalDiff = evaluate(succState) - evaluate(currState);
				if (evalDiff <= 0) {
					currState = succState;
				}
				else {
					//making random number between 0.0 ~ 1.0
					//for comparison of probability
					float randomProbab = randFloat(0.0f,1.1f);

					//counting Acceptance Probability Function
					double prob = Math.exp((-1)* (float) Math.abs(evaluate(currState)-evaluate(succState)) / (float) currTemperature);
					
					//comparing both probability
					if ((float) prob>randomProbab) {
						currState = succState; 
					}
				}
				currTemperature -= tempReduction; //decreasing currTemperature
			}
		}
		
		inputState = currState;
	}
	
	/*
		method for finding the successor of a state (SceduleBoard state)
	*/
	private static ScheduleBoard findSuccessor(ScheduleBoard currentSchedule) {
		int currentConflict = currentSchedule.countConflict();

		int scheduleIdx;
		int day;
		int hour;
		int selectedDay;
		int selectedHour;
		int selectedScheduleIdx;
		Course course;
		int courseId;
		int[] temp_result; 

		if (currentConflict > 0) {
			temp_result = currentSchedule.getMaxConflictLocation();
			day = temp_result[0];
			hour = temp_result[1];
			scheduleIdx = temp_result[2];

			courseId = currentSchedule.getLastInsertedCourseId(scheduleIdx,day,hour);
			course = currentSchedule.getAndDeleteCourseById(courseId,scheduleIdx,day,hour);
			temp_result = currentSchedule.searchBestLocation(course.getTotalCredit());

			selectedDay = temp_result[0];
			selectedHour = temp_result[1];
			selectedScheduleIdx = temp_result[2];

			currentSchedule.insertCourse(course,selectedScheduleIdx,selectedDay,selectedHour);
		}	

		return currentSchedule;
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