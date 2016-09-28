import java.io.*;
import java.lang.String.*;
import java.util.Random;
public class Main{
	public static void main(String [] args){
		try{
			FileReaderMachine.readTestCase();	
		}catch(IOException e){

		}
		

		ScheduleBoard scheduleBoard = new ScheduleBoard();
		ScheduleBoard scheduleBoard1 = new ScheduleBoard();
		scheduleBoard.printScheduleBoard();
		scheduleBoard1.printScheduleBoard();
		System.out.println(FileReaderMachine.getRoomAtIdx(0).getRoomName());
		System.out.println("Lala!");
	}
}
