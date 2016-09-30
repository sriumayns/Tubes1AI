import java.io.*;
import java.lang.String.*;
import java.util.Random;
public class Main{
	public static void main(String [] args){
		try{
			FileReaderMachine.readTestCase();	
		}catch(IOException e){

		}
		

		for (int i = 0;i < FileReaderMachine.getCourseSize();i++) {
			Course course = FileReaderMachine.getCourseAtIdx(i);
			System.out.println(course.getId());
			System.out.println(course.getCourseName());
			System.out.println(course.getNSlotAvaliable());
		}
	}
}
