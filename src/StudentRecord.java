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
                    System.exit(0);
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
    private float gpa;
    private StudentInformation head = null;
    private StudentInformation tail = null;

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
        try
        {
            if(redId > 1000000000 || redId < 99999999)
                throw new IndexOutOfBoundsException();
        }
        catch(IndexOutOfBoundsException indexError)
        {
            do
            {
                System.out.println("RedId is not Valid.RedId should be of 9 digits.Enter Again");
                redId = newInput.nextInt();
            }while(redId > 1000000000 || redId < 99999999);
        }
        System.out.println("Enter the Student GPA till 2 decimals");
        gpa = newInput.nextFloat();
        try
        {
            if(gpa < 0.0 || gpa > 4.0)
                throw new IndexOutOfBoundsException();
        }
        catch(IndexOutOfBoundsException indexError)
        {
            do
            {
                System.out.println("Invalid GPA.GPA should be between 0.0 and 4.0.Enter Again");
                gpa = newInput.nextFloat();
            }while(gpa < 0.0 || gpa > 4.0);
        }
        newStudent.storeInformation(studentName, redId, gpa);
        if(head == null)
        {
            newStudent.nextStudent = null;
            newStudent.previousStudent = null;
            head = newStudent;
            tail = newStudent;
        }
        else
        {
            currentStudent = head;
            while((newStudent.studentName.compareTo(currentStudent.studentName
            )>0) && currentStudent.nextStudent != null)
            {
                currentStudent = currentStudent.nextStudent;

            }
            if(currentStudent == head && newStudent.studentName.compareTo(currentStudent.studentName
            )<0)
            {
                newStudent.previousStudent = null;
                newStudent.nextStudent = currentStudent;
                currentStudent.previousStudent = newStudent;
                head = newStudent;
            }
            else if(currentStudent.nextStudent == null && newStudent.studentName.compareTo(currentStudent.studentName
            )>0)
            {
                tail = newStudent;
                newStudent.nextStudent = null;
                newStudent.previousStudent = currentStudent;
                currentStudent.nextStudent = newStudent;
            }
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
        int remainingStudents = studentIndex;
        StudentInformation currentStudent = head;
        if (currentStudent == null)
            System.out.println("The List of Students is Empty");
        else
        {
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
        currentStudent = head;
        System.out.println("The Current Student List:");
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
        int studentCountOnProbation = 0;
        StudentInformation currentStudent;
        currentStudent = head;
        if(currentStudent == null)
            System.out.println("Student Record is Empty");
        else
        {
            System.out.println("Red Id of students on probation:");
            while(currentStudent != null)
            {
                if(currentStudent.studentGPA < 2.85)
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
        int studentCountGPA4 = 0;
        StudentInformation currentStudent = tail;
        if(currentStudent == null)
            System.out.println("Student Record is Empty");
        else
        {
            System.out.println("Name of students with GPA 4.0:");
            while(currentStudent != null)
            {
                if(currentStudent.studentGPA == 4.00)
                {
                    System.out.println(currentStudent.studentName);
                    studentCountGPA4++;
                }
                currentStudent = currentStudent.previousStudent;
            }
            if(studentCountGPA4 == 0)
                System.out.println("No Students have GPA 4.0");
        }
    }
}
