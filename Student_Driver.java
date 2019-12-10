import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Student_Driver {

	static boolean first = true;
	static Student[] studentArray = null;
	
	public static void main(String[] args) {

		int ch;
		
		String GG = "GG";

		boolean checker = true;
		
		Scanner scanner = new Scanner(System.in);

		do {

			try {

				massege();
				ch = scanner.nextInt();

				switch (ch) {

				case 1:
					displayGrade();
					break;

				case 2:
					displayStudentGrade();
					break;

				case 3:
					displayQuizAverage();
					break;

				case 4:
					modifyStudentGrade();
					break;

				case 5:
					addQuiz();
					break;

				case 6:
					addNewStudent();
					break;

				case 7:
					deleteStudent();
					break;

				case 8:
					Exit();
					break;

				default:
					System.out.println("You must enter choice between 1 and 8");
					break;
				}

			} catch (InputMismatchException l) {
				 scanner.nextLine();
				System.out.println("Error : " + l);
			} catch (IOException l) {

			} catch (IllegalArgumentException l) {
				System.out.println("Error : " + l);
			}

		} while (checker);

	}

	public static void massege() {

		System.out.println("\n");
		System.out.println("1. Display Grade Info for all students");
		System.out.println("2. Display Grade Info for a particular student");
		System.out.println("3. Display quiz averages for all students");
		System.out.println("4. Modify a particular quiz grade for a particular student");
		System.out.println("5. Add quiz grades for a particular quiz for all students  ");
		System.out.println("6. Add New Student");
		System.out.println("7. Delete Student  ");
		System.out.println("8. Exit");
		System.out.println("\n");
		System.out.println("Please select your choice:");
	}

	public static int[] numOfQuizAndLines() throws IOException {

		FileInputStream fileinputstream = new FileInputStream("grades.txt");
		Scanner scanner = new Scanner(fileinputstream);

		int[] numOfQuizAndLines = { 0, 0 };

		while (scanner.hasNextLine()) {

			numOfQuizAndLines[0] = 0;
			numOfQuizAndLines[1]++;

			String line = scanner.nextLine();

			Scanner lineReader = new Scanner(line);

			while (lineReader.hasNextDouble()) {
				numOfQuizAndLines[0]++;
				lineReader.nextDouble();
			}

		}

		return numOfQuizAndLines;
	}

	public static void displayGrade() throws IOException {
		
		if (first) {
			studentArray = firstReading();
			first = false;
		}

		for (int i = 0; i < studentArray.length; i++)
			System.out.println(studentArray[i]);
		pressAnyKeyToContinue();
	}

	public static Student[] firstReading() throws IOException {

		FileInputStream fileinputstream = new FileInputStream("grades.txt");
		Scanner scanner = new Scanner(fileinputstream);

		int[] numOfQuizAndLines = numOfQuizAndLines();
		int ID;
		String name;

		double[] grade = new double[numOfQuizAndLines[0]];

		Student[] studentArray = new Student[numOfQuizAndLines[1]];
		int indexOfLine = 0;
		while (scanner.hasNextLine()) {

			String line = scanner.nextLine();

			Scanner lineReader = new Scanner(line);

			ID = lineReader.nextInt();

			name = lineReader.next();
			name += " " + lineReader.next();

			for (int i = 0; i < numOfQuizAndLines[0]; i++)
				grade[0] = lineReader.nextDouble();

			studentArray[indexOfLine] = new Student(ID, name, grade);
			indexOfLine++;
		}

		return studentArray;
	}
	
	public static void displayStudentGrade() throws IOException {
		
		if (first) {
			studentArray = firstReading();
			first = false;
		}
		
		int ID;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter studentID:");
		ID = scanner.nextInt();
		
		if (searchID(ID) != -1) {
			
			System.out.print("StudentID       Student Name   ");
			
			double[] grade = studentArray[searchID(ID)].getGrades();
			
			for (int i = 0 ; i < studentArray[0].getNumberOfQuizzes() ; i++)
				System.out.printf("Quiz0%d    " , i + 1);
			System.out.println();
			
			System.out.println(studentArray[searchID(ID)]);
		} else {
			System.out.println("Error: Invalid student ID");
		}
		pressAnyKeyToContinue();
	}
	
	public static int searchID(int targetID) {
		
		for (int i = 0 ; i < studentArray.length ; i++) {
			if (studentArray[i].getStudnetID() == targetID)
				return i;
		}
		
		return -1;
		
	}
	
	public static void displayQuizAverage() throws IOException {
		
		if (first) {
			studentArray = firstReading();
			first = false;
		}
		
		System.out.println("StudentID     Student Name     Average");
		
		for (int i = 0 ; i < studentArray.length ; i++)
			System.out.printf("    %d     %s          %.1f%n" , studentArray[i].getStudnetID() , studentArray[i].getName() , studentArray[i].getAverage());
		pressAnyKeyToContinue();
	}
	
	public static void modifyStudentGrade() throws IOException {
		
		if (first) {
			studentArray = firstReading();
			first = false;
		}
		
		int ID , quizNum;
		double grade;
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter studentID:");
		ID = scanner.nextInt();
		
		if (searchID(ID) != -1) {
			
			System.out.println("Please enter quiz number to modify: ");
			quizNum = scanner.nextInt();
			System.out.printf("Please enter new quiz %d grade: %n" , quizNum);
			grade = scanner.nextDouble();
			
			
			System.out.print("Before grade modification:");
			System.out.println(studentArray[searchID(ID)]);
			
			studentArray[searchID(ID)].modifyGrade(grade, quizNum);
			
			System.out.print("After grade modification:");
			System.out.println(studentArray[searchID(ID)]);
			
		} else {
			System.out.println("Error: Invalid student ID");
		}
		pressAnyKeyToContinue();
	}
	
	public static void addQuiz() throws IOException {
		
		if (first) {
			studentArray = firstReading();
			first = false;
		}
		
		System.out.println("Please enter quiz grades for Quiz#" + (studentArray[0].getNumberOfQuizzes() + 1));
		
		Scanner scanner = new Scanner(System.in);
		
		double grade;
		
		for (int i = 0 ; i < studentArray.length ; i++) {
			
			System.out.println("Please enter grade for student : " + studentArray[i].getStudnetID());
			grade = scanner.nextDouble();
			
			studentArray[i].addGrade(grade);
		}
		pressAnyKeyToContinue();
	}
	
	public static void addNewStudent() throws IOException , IllegalArgumentException {
		
		if (first) {
			studentArray = firstReading();
			first = false;
		}
		
		Scanner scanner = new Scanner(System.in);
		
		String name;
		int ID;
		double[] grade = new double[studentArray[0].getNumberOfQuizzes()];
		
		System.out.println("Enter student ID :");
		ID = scanner.nextInt();
		
		if (searchID(ID) != -1)
			throw new IllegalArgumentException("The student ID already there");
		scanner.nextLine();
		System.out.println("Enter student name");
		name = scanner.nextLine();
		
		System.out.println("Enter quizzes grade");
		
		for (int i = 0 ; i < grade.length ; i++) {
			System.out.println("Enter grade for quiz#" + (i + 1));
			grade[i] = scanner.nextDouble();
		}
		
		Student[] copyStudentArray = new Student[studentArray.length + 1];
		
		for(int i = 0 ; i < studentArray.length ; i++) {
			copyStudentArray[i] = new Student(studentArray[i].getStudnetID() , studentArray[i].getName() , studentArray[i].getGrades());
		}
		copyStudentArray[studentArray.length] = new Student(ID , name , grade);
		
		studentArray = copyStudentArray;
		pressAnyKeyToContinue();
	}
	
	public static void deleteStudent() throws IOException , IllegalArgumentException {
		
		if (first) {
			studentArray = firstReading();
			first = false;
		}
		
		Scanner scanner = new Scanner(System.in);
		
		int ID;
		
		System.out.println("Enter student ID :");
		ID = scanner.nextInt();
		
		if (searchID(ID) == -1)
			throw new IllegalArgumentException("The student ID is not there");
		
		Student[] copyStudentArray = new Student[studentArray.length - 1];
		
		for(int i = 0 ; i < searchID(ID) ; i++) {
			copyStudentArray[i] = new Student(studentArray[i].getStudnetID() , studentArray[i].getName() , studentArray[i].getGrades());
		}
		for(int i = searchID(ID); i < copyStudentArray.length ; i++) {
			copyStudentArray[i] = new Student(studentArray[i + 1].getStudnetID() , studentArray[i + 1].getName() , studentArray[i + 1].getGrades());
		}
		
		studentArray = copyStudentArray;
		pressAnyKeyToContinue();
		
	}
	
	 public static void pressAnyKeyToContinue() {
	  
	        System.out.println("\n\nPress Enter key to continue...");
	        try{
	        
	            System.in.read();
	        }  
	        catch(Exception e) {
	        	
	        }
	          
	 }
	
	public static void Exit() throws IOException {
		
		if (first) {
			studentArray = firstReading();
			first = false;
		}
		
		FileOutputStream fileoutputstream = new FileOutputStream("grades.txt");
		PrintWriter printwriter = new PrintWriter(fileoutputstream);
		
		for(int i = 0 ; i < studentArray.length ; i++)
			printwriter.println(studentArray[i]);
		
		printwriter.close();
		
		System.out.println("Text file have been updated");
		System.exit(1);
		
	}
}