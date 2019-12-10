public class Student {
	
	private int StudentID;
	private String name;
	private double[] grade;
	private int numberOfQuiz;
	
	public static int num;
	
	public Student(int StudentID , String name , double[] grade) {
		int n = 0;
		for (int i = 0 ; i < grade.length ; i++) {
			if(grade[i] != 0)
				n++;
		}
		numberOfQuiz = n;
		
		this.grade = new double[4];
		this.StudentID = StudentID;
		this.name = name;
		for (int i = 0 ; i < numberOfQuiz ; i++)
			this.grade[i] = grade[i];

		num++;
	}
	
	public int getStudnetID() {
		return StudentID;
	}
	
	public String getName() {
		return name;
	}
	
	public double[] getGrades() {
		return grade;
	}
	
	public void addGrade(double grade) throws IllegalArgumentException {
		if (grade < 0 || grade > 100)
			throw new IllegalArgumentException("Error: Invalid quiz grade");
		if (numberOfQuiz > 4)
			throw new IllegalArgumentException("Error: Maximum number of quizzes is 4");
		this.grade[numberOfQuiz] = grade;
		numberOfQuiz++;
	}
	
	public void modifyGrade(double grade , int quizNum) throws IllegalArgumentException {
		if (grade < 0 || grade > 100)
			throw new IllegalArgumentException("Error: Invalid quiz grade");
		if (quizNum > numberOfQuiz || quizNum < 1)
			throw new IllegalArgumentException("Error: Invalid quiz number");
		this.grade[quizNum - 1] = grade;
	}
	
	public String toString() {
		String printString =  String.format("    %d       %s" , StudentID , name );
		for (int i = 0 ; i < numberOfQuiz ; i++)
			printString += "    " + this.grade[i];
		return printString;
	}
	
	public boolean equals(Object obj) {
		  if(obj == null)
				return false;
			   else if(getClass() != obj.getClass())
		           return false;
		        else {
				  Student student = (Student)obj;
				  return this.StudentID == student.StudentID;	
			   }
	}
	
	public double getAverage() {
		
		double sum = 0;
		
		for (int i = 0 ; i < numberOfQuiz ; i++)
			sum += grade[i];
		
		return sum/numberOfQuiz;
	}
	
	public int getNumberOfQuizzes() {
		return numberOfQuiz;
	}

}