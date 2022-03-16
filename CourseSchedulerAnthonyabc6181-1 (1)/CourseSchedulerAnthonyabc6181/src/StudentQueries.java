
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author anthonycicardo
 */
public class StudentQueries {
    
    private static Connection connection;
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudentsPrepared; 
    private static PreparedStatement getStudentPrepared; 
    private static PreparedStatement dropStudentPrepared; 
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student) {
        connection = DBConnection.getConnection();
        
        try
        {
        addStudent = connection.prepareStatement("insert into app.student (studentID, firstName, lastName) values (?,?,?)");
        addStudent.setString(1, student.getStudentID());
        addStudent.setString(2, student.getFirstName());
        addStudent.setString(3, student.getLastName());
        addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        
    }
    
    public static ArrayList<StudentEntry> getAllStudents() {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<>(); 
        
        try
        {
            getAllStudentsPrepared = connection.prepareStatement("select * from app.student"); 
            //getAllStudentsPrepared.setString(1, semester);
            //getAllStudentsPrepared.setString(2, studentID); 
            
            resultSet = getAllStudentsPrepared.executeQuery(); 
            
            while (resultSet.next()) {
                StudentEntry student = new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)); 
                       
                students.add(student);
                
            }
        }
        catch(SQLException sqlException) {
                sqlException.printStackTrace(); 
                }
            
        return students;      
    }
    public static StudentEntry getStudent(String studentID) {
        connection = DBConnection.getConnection();
        // get entire student entry?
        ArrayList<StudentEntry> studentResults = new ArrayList<>();
        //StudentEntry student = null;
        
        try
        {
            getStudentPrepared = connection.prepareStatement("select * from app.student where studentID = ?");
            getStudentPrepared.setString(1, studentID); 
            
            resultSet = getStudentPrepared.executeQuery(); 
            
            while(resultSet.next()) {
                StudentEntry student = new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                studentResults.add(student);
            }
        }
        catch(SQLException sqlException) {
                sqlException.printStackTrace();
                }
        
        return studentResults.get(0); 
        }
    
    public static void dropStudent(String studentID) { 
        connection = DBConnection.getConnection(); 
        
        try
        {
            dropStudentPrepared = connection.prepareStatement("delete from app.student where studentID = ?"); 
            dropStudentPrepared.setString(1, studentID); 
            
            dropStudentPrepared.executeUpdate(); 
            
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace(); 
        }
    }
    }
    
    

