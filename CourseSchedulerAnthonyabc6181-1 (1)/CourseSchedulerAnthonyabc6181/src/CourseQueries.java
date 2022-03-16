
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author anthonycicardo
 */
public class CourseQueries {
    private static Connection connection; 
    
    
    //DBConnection DBObj = new DBConnection();
    
    private static PreparedStatement getAllCoursesPrepared;
    private static PreparedStatement addCoursePrepared; 
    private static PreparedStatement getAllCourseCodesPrepared;
    private static PreparedStatement getAllCourseSeatsPrepared; 
    private static PreparedStatement dropCoursePrepared; 
    private static ResultSet resultSet;
    
    
    public CourseQueries() {

    }
    
    public static ArrayList<CourseEntry>getAllCourses(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> coursesList = new ArrayList<>();
        //ResultSet resultSet = null; 
        try
        { // change star 
            getAllCoursesPrepared = connection.prepareStatement("Select * from app.course where semester = ? order by coursecode");
            getAllCoursesPrepared.setString(1, semester);
            resultSet = getAllCoursesPrepared.executeQuery();
            
            while (resultSet.next()) {
                CourseEntry courses = new CourseEntry(semester, resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                coursesList.add(courses);
            }        
    }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return coursesList;  
}
    public static void addCourse(CourseEntry course) {
        connection = DBConnection.getConnection();
        
        try
        {
            //System.out.println("\n\nIm here in addCourse, SEMESTER IS : ");
            addCoursePrepared = connection.prepareStatement("insert into app.course (semester, courseCode, description, seats) values (?, ?, ?, ?)");
            //System.out.println( course.getSemester() );            
            addCoursePrepared.setString(1, course.getSemester());
            addCoursePrepared.setString(2, course.getCourseCode());
            addCoursePrepared.setString(3, course.getDescription()); 
            addCoursePrepared.setInt(4, course.getSeats());
            
            addCoursePrepared.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    public static ArrayList<String>getAllCourseCodes(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList<>(); 

        
        //ResultSet resultSet = null; 
        try
        {
            getAllCourseCodesPrepared = connection.prepareStatement("Select courseCode from app.course where semester = ?");
            getAllCourseCodesPrepared.setString(1, semester); 
            //System.out.println("In getAllCourseCodes: ");
            //System.out.println(semester);
            resultSet = getAllCourseCodesPrepared.executeQuery();
            
            while (resultSet.next()) {
                courseCodes.add(resultSet.getString(1));
            }        
                   
    }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return courseCodes;
    }
    public static int getCourseSeats(String semester, String courseCode) {
        connection = DBConnection.getConnection(); 
        int count = 0;
        try
        {
            getAllCourseSeatsPrepared = connection.prepareStatement("Select SEATS from app.course where semester = ? and courseCode = ?");
            getAllCourseSeatsPrepared.setString(1, semester);
            getAllCourseSeatsPrepared.setString(2, courseCode);
            resultSet = getAllCourseSeatsPrepared.executeQuery(); 
            
        while(resultSet.next()) {
            count = resultSet.getInt(1); 
        }

           
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace(); 
        }
        return count;
    }
    
    public static void dropCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        
        try
        {
            dropCoursePrepared = connection.prepareStatement("delete from app.course where semester = ? and courseCode = ?");
            dropCoursePrepared.setString(1, semester);
            dropCoursePrepared.setString(2, courseCode); 
            dropCoursePrepared.executeUpdate(); 
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
    }
}
