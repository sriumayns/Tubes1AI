import java.io.*;
import java.lang.String.*;
import java.util.*;

public class MainClass {
	private static int roomNumber = 0;
	private static int courseNumber = 0;
	private static Room[] rooms = new Room[100];
	private static Course[] courses = new Course[100];
	private static Course emptyCourse = new Course();
	private static Slot emptySlot = new Slot();
	
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
		
		
		Schedule scheduleBoard = new Schedule(rooms[1]);
		scheduleBoard.printSchedule();
		
    }

}
