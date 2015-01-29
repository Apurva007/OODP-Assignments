/**
 * Created by Apurva on 1/26/2015.
 */

import java.lang.String;
import java.util.Scanner;

public class StudentRecord
{
    public static void main(String args[])
    {
        Boolean infiniteLoop = true;
        int menuChoice;
        int listNodeNumber;
        Operations newOperations = new Operations();
        Scanner inputChoice = new Scanner(System.in);
        System.out.println("Welcome to Student Record");
        //This Loop provides an Interactive feel to the Program.
        while(infiniteLoop)
        {
            System.out.println("1. Add a new student.");
            System.out.println("2. Enter the List's Node number whose details have to be displayed");
            System.out.println("3. Print the Red Id of students on probation from front to back of list");
            System.out.println("4. Print the names of students with GPA 4.0 from back to front of list");
            System.out.println("5. Exit");
            System.out.println("Enter your Choice");
            menuChoice = inputChoice.nextInt();
            switch(menuChoice)
            {
                case 1:
                    newOperations.addStudent();
                    newOperations.printStudentList();
                    break;
                case 2:
                    System.out.println("Enter the list index of student");
                    listNodeNumber = inputChoice.nextInt();
                    newOperations.printStudentName(listNodeNumber);
                    break;
                case 3:
                    newOperations.printProbationList();
                    break;
                case 4:
                    newOperations.printCentGPAStudents();
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Incorrect Choice. Enter Again");
                    break;
            }
        }
    }
}

class StudentInformation
{
    protected String studentName;
    protected int redId;
    protected float studentGPA;
    protected StudentInformation nextStudent;
    protected StudentInformation previousStudent;

    protected void storeInformation(String studentName,int redId,float studentGPA)
    {
        this.studentName = studentName;
        this.redId = redId;
        this.studentGPA = studentGPA;
        this.nextStudent = null;
        this.previousStudent = null;
    }
}

class Operations
{
    private String studentName;
    private int redId;
    private float studentGPA;
    private int remainingStudents;
    private int studentCountOnProbation;
    private StudentInformation studentListOrigin = null;
    private StudentInformation studentListEnd = null;
    private int redIdStartLimit = 99999999;
    private int redIdEndLimit = 1000000000;
    private int studentCountWithCentGrade;
    private double minimumGPA = 0.0;
    private double maximumGPA = 4.0;
    private double probationGrade = 2.85;

    public void addStudent()
    {
        StudentInformation currentStudent;
        StudentInformation newStudent = new StudentInformation();
        Scanner newInput = new Scanner(System.in);
        System.out.println("Enter the Student name to be stored");
        studentName = newInput.nextLine();
        try
        {
            if(studentName.isEmpty())
                throw new NullPointerException();
        }
        catch(NullPointerException nullError)
        {
            do
            {
                System.out.println("Entered name was not valid.Enter again");
                studentName = newInput.nextLine();
            }while(studentName.isEmpty());
        }
        System.out.println("Enter the Student Red ID");
        redId = newInput.nextInt();
        //Checks if the RedId is 9 digits.
        try
        {
            if(redId > redIdEndLimit || redId < redIdStartLimit)
                throw new IndexOutOfBoundsException();
        }
        catch(IndexOutOfBoundsException indexError)
        {
            do
            {
                System.out.println("RedId is not Valid.RedId should be of 9 digits.Enter Again");
                redId = newInput.nextInt();
            }while(redId > redIdEndLimit || redId < redIdStartLimit);
        }
        System.out.println("Enter the Student GPA till 2 decimals");
        studentGPA = newInput.nextFloat();
        //Checks if GPA is out of 4.0 grade.
        try
        {
            if(studentGPA < minimumGPA || studentGPA > maximumGPA)
                throw new IndexOutOfBoundsException();
        }
        catch(IndexOutOfBoundsException indexError)
        {
            do
            {
                System.out.println("Invalid GPA.GPA should be between 0.0 and 4.0.Enter Again");
                studentGPA = newInput.nextFloat();
            }while(studentGPA < minimumGPA || studentGPA > maximumGPA);
        }
        newStudent.storeInformation(studentName, redId, studentGPA);
        //Condition checks if the List is empty. If true then the student object is added to start of list.
        if(studentListOrigin == null)
        {
            newStudent.nextStudent = null;
            newStudent.previousStudent = null;
            studentListOrigin = newStudent;
            studentListEnd = newStudent;
        }
        else
        {
            currentStudent = studentListOrigin;
            // Loop to traverse the list till a name is found who is lexicographically more or till end of list.
            while((newStudent.studentName.compareTo(currentStudent.studentName
            )>0) && currentStudent.nextStudent != null)
            {
                currentStudent = currentStudent.nextStudent;

            }
            //Checks if new student name lexicographically precedes the student name at start of list.
            if(currentStudent == studentListOrigin && newStudent.studentName.compareTo(currentStudent.studentName
            )<0)
            {
                newStudent.previousStudent = null;
                newStudent.nextStudent = currentStudent;
                currentStudent.previousStudent = newStudent;
                studentListOrigin = newStudent;
            }
            //Checks if new student name is lexicographically more than the student name at end of list.
            else if(currentStudent.nextStudent == null && newStudent.studentName.compareTo(currentStudent.studentName
            )>0)
            {
                studentListEnd = newStudent;
                newStudent.nextStudent = null;
                newStudent.previousStudent = currentStudent;
                currentStudent.nextStudent = newStudent;
            }
            //Checks if the new student name is between two student names in a list.
            else if(newStudent.studentName.compareTo(currentStudent.studentName)<0)
            {
                newStudent.nextStudent = currentStudent;
                newStudent.previousStudent = currentStudent.previousStudent;
                (currentStudent.previousStudent).nextStudent = newStudent;
                currentStudent.previousStudent = newStudent;
            }
        }
    }

    public void printStudentName(int studentIndex)
    {
        remainingStudents = studentIndex;
        StudentInformation currentStudent = studentListOrigin;
        if (currentStudent == null)
            System.out.println("The List of Students is Empty");
        else
        {
            //Checks if the list index specified has any students present in the list or is invalid.
            try
            {
                while (remainingStudents != 0)
                {
                    currentStudent = currentStudent.nextStudent;
                    if(currentStudent == null)
                        throw new IndexOutOfBoundsException();
                    remainingStudents--;
                }
                if (remainingStudents == 0)
                    System.out.println("The Details of the Student at Index " + studentIndex + ": ");
                    System.out.println("Name: "+currentStudent.studentName);
                    System.out.println("Red Id: "+currentStudent.redId);
                    System.out.println("GPA: "+currentStudent.studentGPA);
            }
            catch(IndexOutOfBoundsException error)
            {
                System.out.println("Input is Out of Bound");
            }
        }
    }

    public void printStudentList()
    {
        StudentInformation currentStudent;
        currentStudent = studentListOrigin;
        System.out.println("The Current Student List:");
        //Checks if there is only one student on the list.
        if(currentStudent.nextStudent == null)
            System.out.println(currentStudent.studentName);
        else
        {
            while(currentStudent.nextStudent!=null)
            {
                System.out.println(currentStudent.studentName);
                currentStudent = currentStudent.nextStudent;
            }
            System.out.println(currentStudent.studentName);
        }
    }

    public void printProbationList()
    {
        studentCountOnProbation = 0;
        StudentInformation currentStudent;
        currentStudent = studentListOrigin;
        if(currentStudent == null)
            System.out.println("Student Record is Empty");
        else
        {
            System.out.println("Red Id of students on probation:");
            //This loop helps traverse the list from front to back printing student names with GPA below 2.85.
            while(currentStudent != null)
            {
                if(currentStudent.studentGPA < probationGrade)
                {
                    System.out.println(currentStudent.redId);
                    studentCountOnProbation++;
                }
                currentStudent = currentStudent.nextStudent;
            }
            if(studentCountOnProbation == 0)
                System.out.println("No Students on probation");
        }
    }

    public void printCentGPAStudents()
    {
        studentCountWithCentGrade = 0;
        StudentInformation currentStudent = studentListEnd;
        if(currentStudent == null)
            System.out.println("Student Record is Empty");
        else
        {
            System.out.println("Name of students with GPA 4.0:");
            //This Loop traverses from back to front of the list printing student names with GPA 4.0.
            while(currentStudent != null)
            {
                if(currentStudent.studentGPA == 4.00)
                {
                    System.out.println(currentStudent.studentName);
                    studentCountWithCentGrade++;
                }
                currentStudent = currentStudent.previousStudent;
            }
            if(studentCountWithCentGrade == 0)
                System.out.println("No Students have GPA 4.0");
        }
    }
}
