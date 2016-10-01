import java.io.*;
import java.lang.String.*;
import java.util.Random;
public class ScheduleBoard{
	private Course emptyCourse = new Course();
	private Slot emptySlot = new Slot();
	private Schedule[] scheduleBoard;
	
	/*
		Constructor
	*/
	public ScheduleBoard(){
		scheduleBoard = new Schedule[FileReaderMachine.getRoomSize()];	
		for (int i=0; i < FileReaderMachine.getRoomSize(); i++) {
			scheduleBoard[i] = new Schedule(FileReaderMachine.getRoomAtIdx(i));
		}
		initializeSolutionRandomly();
	}
	
	/*
		Mengembalikan banyaknya Schedule dalam Schedule board
	*/
	public int getScheduleBoardLength() {
		return scheduleBoard.length;
	}
	
	/*
		Mendapatkan semua schedule dalam schedule board
	*/
	public Schedule[] getAllSchedule() {
		return scheduleBoard;
	}
	
	/*
		Merubah seluruh schedule board
	*/
	public void setAllSchedule(Schedule[] schedule) {
		scheduleBoard = schedule;
	}
	
	/*
		Mengembalikan Schedule dengan index tertentu
	*/
	public Schedule getScheduleWithIndex(int idx) {
		return scheduleBoard[idx];
	}
	
	/*
		Merubah schedule dengan index tertentu
	*/
	public void setScheduleWithIndex(int idx, Schedule schedule) {
		scheduleBoard[idx] = schedule;
	}

	public Course getEmptyCourse() {
		return emptyCourse;
	}
	
	/*
		Print Schedule Board
	*/
	public void printScheduleBoard() {
		for (int i=0; i < FileReaderMachine.getRoomSize(); i++) {
			scheduleBoard[i].printSchedule();
		}
	}

	/*
		Print Room
	*/
	public void printRooms(Room[] rooms) {
		System.out.println("Ruangan");
		for (int i=0; i<FileReaderMachine.getRoomSize(); i++) {
			System.out.print(FileReaderMachine.getRoomAtIdx(i).getRoomName() + ";" + FileReaderMachine.getRoomAtIdx(i).getStartHour() +";"+ FileReaderMachine.getRoomAtIdx(i).getEndHour()
				+";");
			for(int j=0; j<FileReaderMachine.getRoomAtIdx(i).getAvailableDay().length; j++) {
				System.out.print(FileReaderMachine.getRoomAtIdx(i).getAvailableDay()[j]+",");
			}
			System.out.println();
		}
	}
	
	/*
		Print Course
	*/
	public void printCourses(Course[] courses) {
		System.out.println("Jadwal");
		for (int i=0; i<FileReaderMachine.getCourseSize(); i++) {
			System.out.print(FileReaderMachine.getCourseAtIdx(i).getCourseName() +";" + FileReaderMachine.getCourseAtIdx(i).getRoomConstraint() + ";" + FileReaderMachine.getCourseAtIdx(i).getStartHourConstraint() +";"+ FileReaderMachine.getCourseAtIdx(i).getEndHourConstraint()
				+";"+FileReaderMachine.getCourseAtIdx(i).getTotalCredit()  + ";");
			for(int j=0; j<FileReaderMachine.getCourseAtIdx(i).getDayConstraint().length; j++) {
				System.out.print(FileReaderMachine.getCourseAtIdx(i).getDayConstraint()[j]+",");
			}
			System.out.println();
		}
	}
	
	/*
		Menginisialisasi dengan random variable
    */
    public void initializeSolutionRandomly() {
		for (int i =0; i < FileReaderMachine.getCourseSize(); i++) {
			int randomDay=0;
			int randomHour=0;
			int randomRoomIndex;
			boolean slotLock = true;
			String choosenRoomName;
			int randomDayIdx;
			int[] availDay;
			int j=0;

			// Meramdom Course sampai course mendapatkan ruangan yang tidak terconstraint
			while (slotLock) {
				if (FileReaderMachine.getCourseAtIdx(i).getRoomConstraint().equals("-")) {
					randomRoomIndex = randInt(0,FileReaderMachine.getRoomSize()-1);
					choosenRoomName = FileReaderMachine.getRoomAtIdx(randomRoomIndex).getRoomName();
				}
				else {
					choosenRoomName = FileReaderMachine.getCourseAtIdx(i).getRoomConstraint();
				}
				randomDayIdx = randInt(0,FileReaderMachine.getCourseAtIdx(i).getDayConstraint().length-1);
				availDay = FileReaderMachine.getCourseAtIdx(i).getDayConstraint();
				randomDay = availDay[randomDayIdx];
				randomHour = randInt(FileReaderMachine.getCourseAtIdx(i).getStartHourConstraint(),FileReaderMachine.getCourseAtIdx(i).getEndHourConstraint()-1);
				j =0;
				while (!scheduleBoard[j].getRoom().getRoomName().equals(choosenRoomName)) {
					j++;
				}
				if (scheduleBoard[j].isScheduleOpen(randomDay,randomHour)) {
					slotLock = false;
				}
			}
			scheduleBoard[j].insertCourseToSchedule(randomDay,randomHour,FileReaderMachine.getCourseAtIdx(i));
		}
			
	}
    
    /*
		Menghitung jumlah confict
    */
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

	/*
		Mengembalikan bilangan random dari min sampai max
	*/
	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		
		return randomNum;
	}

	/*
		Untuk mencari lokasi konflik maksimal pada seluruh board. 
		Mengembalikan indeks schedule dan koordinat slot yang memiliki konflik terbanyak dan tidak terkunci. 
		output:
		int [0] = hari(1-5)
		int [1] = jam(7-17)
		int [2] = schedule(1-n)

		jika tidak ditemukan maka hasilnya: 
		int[0] = 0;
		int[1] = 0; 
		int[2] = 0;
	*/

	public int[] getMaxConflictLocation() {
		int currentDay = 0;
		int currentHour = 0;
		int currentSchedule = 0;
		int currentConflict = 0;
		int[] result_temp;
		int[] result = new int[3];
		for(int i=0; i < scheduleBoard.length; i++) {
			result_temp = scheduleBoard[i].getMaxConflictLocation();
			if((result_temp[0]!=0)&&(result_temp[1]!=0)) {
				if(currentConflict < scheduleBoard[i].getConflict(result_temp[0],result_temp[1])) {
					currentConflict = scheduleBoard[i].getConflict(result_temp[0],result_temp[1]);
					currentDay = result_temp[0];
					currentHour = result_temp[1];
					currentSchedule = i;
				}
			}
		}
		if (currentConflict == 0) {
			result[0] = 0;
			result[1] = 0;
			result[2] = 0;
		}
		else {
			result[0] = currentDay;
			result[1] = currentHour;
			result[2] = currentSchedule;
		}
		return result;
	}
	/*
		Untuk mencara runtutan slot sebanyak total kredit yang memiliki jumlah konflik yang paling sedikit.
		Serta koordinat slot tidak terkunci. 
		output:
		int [0] = hari(1-5)
		int [1] = jam(7-17)
		int [2] = jumlah konflik

		jika tidak ditemukan maka hasilnya: 
		int[0] = 0;
		int[1] = 0; 
		int[2] = 0;
	*/
	public int[] searchBestLocation(int totalCredit) {
		int currentConflict = 10000;
		int currentDay = 0;
		int currentStartHour = 0;
		int currentSchedule = 0;
		int[] result_temp;
		for (int i =0; i < scheduleBoard.lenght; i++) {
			result_temp =  scheduleBoard[i].searchBestSlot(totalCredit);
			if ((result_temp[0]!=0)&&(result_temp[1]!=0)&&(result_temp[2]!=0)) {
				if(result_temp[2] < currentConflict) {
					currentConflict = result_temp[2];
					currentDay =  result_temp[0];
					currentStartHour = result_temp[1];
					currentSchedule = i;
				}
			}
		}
		if (currentConflict == 10000) {
			result[0] = 0;
			result[1] = 0;
			result[2] = 0;
		}
		else {
			result[0] = currentDay;
			result[1] = currentStartHour;
			result[2] = currentSchedule;
		}
		return result;
	}
}
