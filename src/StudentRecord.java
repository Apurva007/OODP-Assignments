/**
 * Created by Apurva on 1/26/2015.
 */
import java.lang.String;
import java.util.Scanner;

public class StudentRecord
{
    public static void main(String args[])
    {
        Operations newOperations = new Operations();
        newOperations.addStudent();
        newOperations.addStudent();
        newOperations.addStudent();
        newOperations.addStudent();
        newOperations.printStudentlist();
    }

}

class StudentInformation
{
    String studentName;
    int redId;
    float gpa;
    StudentInformation nextStudent;
    StudentInformation previousStudent;
    public void storeInformation(String studentName,int redId,float gpa)
    {
        this.studentName = studentName;
        this.redId = redId;
        this.gpa = gpa;
        this.nextStudent = null;
    }
}

class Operations
{
    public String studentName;
    int redId;
    float gpa;
    StudentInformation head = null;
    public void addStudent()
    {
        StudentInformation currentStudent;
        StudentInformation newStudent = new StudentInformation();
        Scanner newInput = new Scanner(System.in);
        System.out.println("Enter the Student name to be stored");
        studentName = newInput.nextLine();
        System.out.println("Enter the Student Red ID");
        redId = newInput.nextInt();
        System.out.println("Enter the Student GPA till 2 decimals");
        gpa = newInput.nextFloat();
        newStudent.storeInformation(studentName, redId, gpa);
        if(head == null)
        {
            newStudent.nextStudent = null;
            newStudent.previousStudent = null;
            head = newStudent;
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
                head =newStudent;
            }
            else if(newStudent.studentName.compareTo(currentStudent.studentName
            )>0 && currentStudent == head)
            {
                newStudent.nextStudent = null;
                newStudent.previousStudent = currentStudent;
                currentStudent.nextStudent = newStudent;
            }
            else if(currentStudent.nextStudent == null && newStudent.studentName.compareTo(currentStudent.studentName
            )<0)
            {
                newStudent.nextStudent = currentStudent;
                newStudent.previousStudent = currentStudent.previousStudent;
                (currentStudent.previousStudent).nextStudent = newStudent;
            }
            else
            {
                newStudent.nextStudent = null;
                newStudent.previousStudent = currentStudent;
                currentStudent.nextStudent = newStudent;
            }
        }
    }

    public void printStudentlist()
    {
        StudentInformation currentStudent;
        currentStudent = head;
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
}
