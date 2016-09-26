import java.io.*;
import java.lang.String.*;
import java.util.Random;

public class MainClass {
	private static int roomNumber = 0;
	private static int courseNumber = 0;
	private static Room[] rooms = new Room[100];
	private static Course[] courses = new Course[100];
	private static Course emptyCourse = new Course();
	private static Slot emptySlot = new Slot();
	private static Schedule[] scheduleBoard;
	
	public static Course getEmptyCourse() {
		return emptyCourse;
	}
	
	public void printRooms(Room[] rooms) {
		System.out.println("Ruangan");
		for (int i=0; i<roomNumber; i++) {
			System.out.print(rooms[i].getRoomName() + ";" + rooms[i].getStartHour() +";"+ rooms[i].getEndHour()
				+";");
			for(int j=0; j<rooms[i].getAvailableDay().length; j++) {
				System.out.print(rooms[i].getAvailableDay()[j]+",");
			}
			System.out.println();
		}
	}
	
	public void printCourses(Course[] courses) {
		System.out.println("Jadwal");
		for (int i=0; i<courseNumber; i++) {
			System.out.print(courses[i].getCourseName() +";" + courses[i].getRoomConstraint() + ";" + courses[i].getStartHourConstraint() +";"+ courses[i].getEndHourConstraint()
				+";"+courses[i].getTotalCredit()  + ";");
			for(int j=0; j<courses[i].getDayConstraint().length; j++) {
				System.out.print(courses[i].getDayConstraint()[j]+",");
			}
			System.out.println();
		}
	}
	
	public void readTestCase() throws IOException {
		//READ FROM TXT
		int roomIdx = 0;
		int courseIdx = 0;
		
		boolean readRoomActive = false;
		boolean readCourseActive = false;
		
			BufferedReader in = new BufferedReader(new FileReader("Testcase.txt"));
			String line;
			while((line = in.readLine()) != null)
			{
				if (line.equals("Ruangan")) {
					readRoomActive = true;
					readCourseActive = false;
				}
				else if (line.equals("Jadwal")) {
					readRoomActive = false;
					readCourseActive = true;
				}
				else {
					String[] lineParsed = line.split(";");
					if ((readRoomActive) && (lineParsed[1] != null)) {
						Room readRoom = new Room();
						readRoom.setRoomName(lineParsed[0]);
						readRoom.setStartHour(lineParsed[1]);
						readRoom.setEndHour(lineParsed[2]);
						readRoom.setAvailableDay(lineParsed[3]);
						rooms[roomNumber] = readRoom;
						roomNumber++;
					}
					if (readCourseActive && (lineParsed[1] != null)) {
						Course readCourse = new Course();
						readCourse.setCourseName(lineParsed[0]);
						readCourse.setRoomConstraint(lineParsed[1]);
						readCourse.setStartHourConstraint(lineParsed[2]);
						readCourse.setEndHourConstraint(lineParsed[3]);
						readCourse.setTotalCredit(lineParsed[4]);
						readCourse.setDayConstraint(lineParsed[5]);
						courses[courseNumber] = readCourse;
						courseNumber++;
					}
				}

			}
			in.close();
		}
		
		public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
	
		return randomNum;
	}
    
    public void initializeSolutionRandomly() {
		for (int i =0; i < courseNumber; i++) {
			int randomDay=0;
			int randomHour=0;
			int randomRoomIndex;
			boolean slotLock = false;
			String choosenRoomName;
			int randomDayIdx;
			int[] availDay;
			int j=0;
			while (!slotLock) {
				if (courses[i].getRoomConstraint().equals("-")) {
					randomRoomIndex = randInt(0,roomNumber-1);
					choosenRoomName = rooms[randomRoomIndex].getRoomName();
				}
				else {
					choosenRoomName = courses[i].getRoomConstraint();
				}
				randomDayIdx = randInt(0,courses[i].getDayConstraint().length-1);
				availDay = courses[i].getDayConstraint();
				randomDay = availDay[randomDayIdx];
				randomHour = randInt(courses[i].getStartHourConstraint(),courses[i].getEndHourConstraint()-1);
				j =0;
				while (!scheduleBoard[j].getRoom().getRoomName().equals(choosenRoomName)) {
					j++;
				}
				if (scheduleBoard[j].isScheduleLocked(randomDay,randomHour)) {
					slotLock = true;
				}
			}
			scheduleBoard[j].insertCourseToSchedule(randomDay,randomHour,courses[i]);
		}
			
	}
    
    public int countConflict() {
		int totalConflict = 0;
		for (int i=0; i< scheduleBoard.length; i++) {
			for (int day = 1; day < 6; day++) {
				for (int hour = 7; hour < 18; hour++) {
					totalConflict += scheduleBoard[i].getConflict(day,hour);
				}
			}
		}
		return totalConflict;
	}
	
	public void searchHillClimbing(int totalSteps) {
		int[] searchResult = {0,0};
		int step = 0;
		boolean conflictFound = false;
		int scheduleIdx = 0;
		Course course;
		while ((this.countConflict() > 0)&&(step < totalSteps)) {
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
					if (scheduleBoard[newRoomIdx].isScheduleLocked(randomDay,randomHour)) {
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

    public static void main(String[] args) {
		
		MainClass main = new MainClass();
		try {
			main.readTestCase();
		}
		catch(IOException e) {}
		
		
		//END OF READ FROM TXT
		//TEST YOUR ALGORITHM HERE
		//LIST OF COURSES AVAILABLE ON courses array
		//LIST OF ROOMS AVAILABLE ON rooms array
		
		System.out.println(roomNumber);
		scheduleBoard = new Schedule[roomNumber];
		
		for (int i=0; i < roomNumber; i++) {
			scheduleBoard[i] = new Schedule(rooms[i]);
		}
		
		main.initializeSolutionRandomly();
		
		for (int i=0; i < roomNumber; i++) {
			scheduleBoard[i].printSchedule();
		}
		
		System.out.println("Total Conflict: "+main.countConflict());
		
		if (main.countConflict() > 0) {
			System.out.println("#####################################################################");
			System.out.println("#####################################################################");
			main.searchHillClimbing(10);
			for (int i=0; i < roomNumber; i++) {
				scheduleBoard[i].printSchedule();
			}
			System.out.println("Total Conflict: "+main.countConflict());
		}
		
    }
    


}
