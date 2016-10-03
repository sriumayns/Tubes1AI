
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
		int boltzmannConstant = 1;
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
					double prob = Math.exp((-1)* (float) Math.abs(evaluate(currState)-evaluate(succState)) / (float) (currTemperature*boltzmannConstant));
					
					//comparing both probability
					if ((float) prob>randomProbab) {
						currState = succState; 
					}
					else {
						stopLoop = true;
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
	private static ScheduleBoard findSuccessor(ScheduleBoard scheduleBoard) {
		int currentConflict = scheduleBoard.countConflict();

		int scheduleIdx;
		int day;
		int hour;
		int selectedDay;
		int selectedHour;
		int selectedScheduleIdx;
		Course course;
		int courseId;
		int[] temp_result = new int[3];
		String roomName;

		if (currentConflict > 0) {
			temp_result = scheduleBoard.getMaxConflictLocation();
			day = temp_result[0];
			hour = temp_result[1];
			scheduleIdx = temp_result[2];
			courseId = scheduleBoard.getLastInsertedCourseId(scheduleIdx,day,hour);
			course = scheduleBoard.getAndDeleteCourseById(courseId,scheduleIdx,day,hour);
			if (!course.getRoomConstraint().equals("-")) {
				roomName = course.getRoomConstraint();
				selectedScheduleIdx = scheduleBoard.getScheduleIdx(roomName);
				temp_result = scheduleBoard.searchBestLocationOnSchedule(selectedScheduleIdx,course.getTotalCredit());
				selectedDay = temp_result[0];
				selectedHour = temp_result[1];
			}
			else {
				temp_result = scheduleBoard.searchBestLocation(course.getTotalCredit());
				selectedDay = temp_result[0];
				selectedHour = temp_result[1];
				selectedScheduleIdx = temp_result[2];
			}

			scheduleBoard.insertCourse(course,selectedScheduleIdx,selectedDay,selectedHour);
			
		}	

		return scheduleBoard;
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