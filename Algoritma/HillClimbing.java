

public class HillClimbing {
	
	public static void hillClimbing(ScheduleBoard scheduleboard) {
		int totalSteps = 10;
		int[] searchResult = {0,0};
		int step = 0;
		boolean conflictFound = false;
		int scheduleIdx = 0;
		Course course;
		while ((scheduleboard.countConflict() > 0)&&(step < totalSteps)) {
			while ((scheduleIdx < scheduleBoard.length)&&(!conflictFound)) {
				searchResult = scheduleBoard[scheduleIdx].getConflictSlot();
				if ((searchResult[1] !=0)&&(searchResult[0]!=0)) {
					conflictFound = true;
				}
			scheduleIdx++;
			}
			if (conflictFound) {
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
					if (scheduleBoard[newRoomIdx].isScheduleOpen(randomDay,randomHour)) {
						slotLock = true;
					}
				}
				if ((randomDay !=0)&&(randomHour !=0)) {
					scheduleBoard[newRoomIdx].insertCourseToSchedule(randomDay,randomHour,course);
				}
				//
			}
		
		step++;
		conflictFound = false;
		scheduleIdx = 0;
		}
		
	}
	
	
}
