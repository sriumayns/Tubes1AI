import java.io.*;
import java.lang.String.*;
import java.util.Random;
public class Main{
	public static void main(String [] args){
		try{
			FileReaderMachine.readTestCase();	
		}catch(IOException e){

		}
		
		ScheduleBoard board = new ScheduleBoard();
		board.printScheduleBoard();
		System.out.println("Total Conflict: "+board.countConflict());
		System.out.println("==================================================================================================");
		HillClimbing.hillClimbing(board);
		board.printScheduleBoard();
		System.out.println("Total Conflict: "+board.countConflict());


		
		
	}
}
