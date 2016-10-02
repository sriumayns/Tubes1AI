// Filename: Schedule.java
// Author: Faza Thirafi (13514033)

import java.io.*;
import java.util.*;

public class Schedule {
	private Slot[][] slotTable;
	//slotTable[Hari][Jam]
	private Room room;

	
	public Schedule(Room roomInput) {
		room = roomInput;
		int i,j;
		slotTable = new Slot[6][18];
		for (i = 1; i<6; i++) {
			for (j = 7; j<18; j++) {
				slotTable[i][j] = new Slot();
				if (room.isContainAvailableDay(i)) {
					if ((j >= room.getStartHour())&&(j< room.getEndHour())) {
						slotTable[i][j].openSlot();
					}
					else {
						slotTable[i][j].lockSlot();
					}
				}
				else {
					slotTable[i][j].lockSlot();
				} 
				
			}
		}
	}


	/*
		Untuk mendapatkan Room dari Schedule
	*/
	public Room getRoom() {
		return room;
	}
	
	/*
		Mengecek apakah slot bisa isi atau tidak, berdasarkan konstrain

	*/
	public boolean isScheduleOpen(int day, int hour){
		return slotTable[day][hour].isOpen();
	}
	

	/*
		Untuk debuging lock Slot
	*/
	public void printScheduleLock() {
		System.out.println(room.getRoomName());
		System.out.println(room.getStartHour());
		System.out.println(room.getEndHour());
		System.out.println(room.getAvailableDay());
		
		for (int i =1; i < 6; i++) {
			for (int j = 7; j < 18; j++) {
				if (slotTable[i][j].isOpen()) {
					System.out.print(1);
				}
				else {
					System.out.print(0);
				}
			}
			System.out.println();
		}
	}


	/*
		Digunakan untuk menambahkan course ke slot
	*/
	public void insertCourseToSchedule(int day, int timeStart, Course course) {
		for(int i = 0;i < course.getTotalCredit();i++){
			slotTable[day][timeStart + i].insertCourse(course);	
		}
		
	}


	
	/*	
	public void moveLastInsertedCourse(Room initialRoom, Room finalRoom, int initialDay, int finalDay, int initialHour, int finalHour) {
		Course movedCourse = slotTable[initialDay][initialHour].getAndDeleteLastInsertedCourse();
		slotTable[finalDay][finalHour].insertCourse(movedCourse);
	}*/
	
	/*
		Untuk mengeprint schedule satu ruangan
	*/
	public void printSchedule() {
		System.out.println("===============================================================");
		System.out.println("             Room Name: "+room.getRoomName());
		System.out.println("             Start Hour: "+room.getStartHour()+"   End Hour: "+room.getEndHour());
		System.out.print("             Available Day: ");
		int dayAvailable[] = room.getAvailableDay();
		for (int j =0; j < dayAvailable.length; j++) {
			System.out.print(dayAvailable[j]+" ");
		}
		System.out.println();
		System.out.println("===============================================================");
		System.out.println("  |   Monday  |   Tuesday | Wednesday |  Thursday |   Friday  |");
		System.out.println("===============================================================");
		for (int hour = 7; hour<18; hour++) {
			if (hour < 10) {
				System.out.print(hour+" |");
			}
			else {
				System.out.print(hour+"|");
			}
			for (int day = 1; day <6;day++) {
				if (slotTable[day][hour].getNumberOfCourse() > 0) {
					for (int i=0; i< slotTable[day][hour].getNumberOfCourse() ; i++) {
						System.out.print(slotTable[day][hour].getCourseWithIndex(i).getCourseName());
					}
					if (slotTable[day][hour].getNumberOfCourse() == 1) {
						System.out.print("     |");
					}
					else {
						System.out.print("|");
					}
				}
				else {
					System.out.print("           |");
				}
			}
			System.out.println();
		}
		System.out.println("---------------------------------------------------------------");
	}
	
	/*
		Untuk menghitung konflik dalam slot
	*/
	public int getConflict(int day, int hour) {
		return slotTable[day][hour].getNumberOfConflict();
	}
	

	/*
		Untuk mencari konflik yang ada dalam ruangan itu yang pertama didapat. Searching dilakukan dari jam dulu.
		output:
		int [0] = hari(1-5)
		int [1] = jam(7-17)

		jika tidak ditemukan maka hasilnya: 
		int[0] = 0;
		int[1] = 0; 
	*/
	public int[] getConflictSlot() {
		int day = 1;
		int hour = 7;
		boolean found = false;
		int[] result = new int[2];
		//result[0] = day
		//result[1] = hour;
		while ((day < 6)&&(!found)) {
			hour = 7;
			while ((hour < 18)&&(!found)) {
				if (slotTable[day][hour].getNumberOfCourse() > 1) {
					found = true;
				}
			hour++;
			}
		day++;
		}
		if ((day==6)&&(hour==18)) {
			result[0] = 0;
			result[1] = 0;
		}
		else {
			result[0] = day-1;
			result[1] = hour-1;
		}
		return result;
	}
	/*
		Untuk mencari konflik yang ada dalam ruangan itu dan mengembalikan koordinat slot yang memiliki konflik terbanyak.
		Serta koordinat slot tidak terkunci. 
		output:
		int [0] = hari(1-5)
		int [1] = jam(7-17)

		jika tidak ditemukan maka hasilnya: 
		int[0] = 0;
		int[1] = 0; 
	*/


	public int[] getMaxConflictSlot() {
		int currentDay = 0;
		int currentHour = 0;
		int currentConflict =0;
		int[] result = new int[2];
		for (int day = 1; day < 6; day++) {
			for(int hour=7; hour < 18; hour++) {
				if ((currentConflict < slotTable[day][hour].getNumberOfCourse())&&(slotTable[day][hour].isOpen())) {
					currentConflict = slotTable[day][hour].getNumberOfCourse();
					currentDay = day;
					currentHour = hour;
				}

			}
		}

		if ((currentDay == 0)&&(currentHour==0)){
			result[0] = 0;
			result[1] = 0;
		}
		else {
			result[0] = currentDay;
			result[1] = currentHour;
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
	public int[] searchBestSlot(int totalCredit) {
		int currentDay = 0;
		int currentStartHour = 0;
		int currentFreeIndex = 10000;
		boolean isOpen;
		int countConflict;
		int[] result = new int[3];
		for (int day = 1; day < 6; day++) {
			for(int hour =7; hour < 18-totalCredit+1; hour++) {
				countConflict = 0;
				isOpen = true;
				for(int i = hour; i < hour+totalCredit; i++) {
					if (!slotTable[day][i].isOpen()) {
						isOpen = false;
					}
					countConflict += slotTable[day][i].getNumberOfCourse();
				}
				if ((isOpen)&&(countConflict < currentFreeIndex)) {
					currentFreeIndex = countConflict;
					currentStartHour = hour;
					currentDay = day;
					
				}
			}
		}
		if (currentFreeIndex == 9000) {
			result[0] = 0;
			result[1] = 0;
			result[2] = 0;
		}
		else {
			result[0] = currentDay;
			result[1] = currentStartHour;
			result[2] = currentFreeIndex;
		}
		return result;

	}

	/*
		Untuk mengambil course dari slot dan menghapus course tersebut pada slot
	*/
	public Course getAndDeleteLastInsertedCourseFromSLot(int day, int hour) {
		return slotTable[day][hour].getAndDeleteLastInsertedCourse();
	}
	/*
		Mengembalikan id dari course
	*/
	public int getLastInsertedCourseId(int day, int hour) {
		return slotTable[day][hour].getLastInsertedCourseId();
	}
	/*
		Mengambil dan mengapus course berdasarkan courseId pada day dan hour yang diberikan, kemudian membersihkan course
		serupa (memiliki id yang sama) di sekitar course tersebut.
	*/
	public Course getAndDeleteCourseById(int courseId, int day, int hour) {
		Course course = slotTable[day][hour].getAndDeleteCourseById(courseId);
		Course cleanCourse;
		for (int i = hour; ((i < 18)&&(i < hour + course.getTotalCredit()));i++) {
			cleanCourse = slotTable[day][i].getAndDeleteCourseById(courseId);
		}
		for (int j = hour; ((j > 6)&&(j > hour - course.getTotalCredit( )));j--) {
			cleanCourse = slotTable[day][j].getAndDeleteCourseById(courseId);
		}
		return course;
	}

}
