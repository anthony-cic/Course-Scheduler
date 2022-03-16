import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class ScheduleQueries {
    
    private static Connection connection; 
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addScheduleEntryPrepared;
    private static PreparedStatement getSchedulePrepared; 
    private static PreparedStatement getScheduleCountPrepared; 
    private static PreparedStatement getScheduledStudentsByCoursePrepared;
    private static PreparedStatement getWaitlistedStudentsByCoursePrepared;
    private static PreparedStatement dropStudentScheduleByCoursePrepared; 
    private static PreparedStatement dropScheduleByCoursePrepared; 
    private static PreparedStatement updateScheduleEntryPrepared; 
    private static ResultSet resultSet;
    
    
    
    public static void addScheduleEntry(ScheduleEntry entry) {
        connection = DBConnection.getConnection();
        try
        {
            addScheduleEntryPrepared = connection.prepareStatement("insert into app.schedule (semester, coursecode, studentid, status, timestamp) values (?, ?, ?, ?, ?)");
            addScheduleEntryPrepared.setString(1, entry.getSemester());
            addScheduleEntryPrepared.setString(2, entry.getCourseCode());
            addScheduleEntryPrepared.setString(3, entry.getStudentID());
            addScheduleEntryPrepared.setString(4, entry.getStatus());
            addScheduleEntryPrepared.setTimestamp(5, entry.getTimestamp());
            
            addScheduleEntryPrepared.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedules = new ArrayList<>(); 
    try
    {
        
        getSchedulePrepared = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule where semester = ? and studentid = ? order by studentID");
        getSchedulePrepared.setString(1,semester);
        getSchedulePrepared.setString(2,studentID);
    
        resultSet = getSchedulePrepared.executeQuery(); 
    
    while (resultSet.next()) {
        ScheduleEntry schedule = new ScheduleEntry(semester,resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5));
        schedules.add(schedule); 
    }
    }
    catch(SQLException sqlException)
    {
        sqlException.printStackTrace();
        }
    return schedules;
    }
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode) {
        connection = DBConnection.getConnection(); 
        int count = 0; 
    try
    {
        getScheduleCountPrepared = connection.prepareStatement("select count(studentID) from app.schedule where semester = ? and courseCode = ? and status = ?");
        getScheduleCountPrepared.setString(1, currentSemester);
        getScheduleCountPrepared.setString(2, courseCode); 
        getScheduleCountPrepared.setString(3, "S"); 
        
        resultSet = getScheduleCountPrepared.executeQuery(); 
        
        while(resultSet.next()) {
            count = resultSet.getInt(1); 
        }
    }
    catch(SQLException sqlException) {
        sqlException.printStackTrace();
        
    }
    return count;
    }
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode) { 
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> students = new ArrayList<>(); 
        
        try 
        {
            getScheduledStudentsByCoursePrepared = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule where semester = ? and coursecode = ? and status = ?");
            getScheduledStudentsByCoursePrepared.setString(1, semester);
            getScheduledStudentsByCoursePrepared.setString(2, courseCode);
            getScheduledStudentsByCoursePrepared.setString(3, "S");
            
            resultSet = getScheduledStudentsByCoursePrepared.executeQuery(); 
            
            
            
            while(resultSet.next())  {
                ScheduleEntry schedule = new ScheduleEntry(semester,resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5));
                students.add(schedule); 
                
                
            }
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace(); 
        }
         
        return students; 
               
    }
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode) { 
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> students = new ArrayList<>(); 
        
        try 
        {
            getWaitlistedStudentsByCoursePrepared = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule where semester = ? and coursecode = ? and status = ? order by timestamp");
            getWaitlistedStudentsByCoursePrepared.setString(1, semester);
            getWaitlistedStudentsByCoursePrepared.setString(2, courseCode);
            getWaitlistedStudentsByCoursePrepared.setString(3, "W");
            
            resultSet = getWaitlistedStudentsByCoursePrepared.executeQuery(); 
            
            while(resultSet.next())  {
                ScheduleEntry schedule = new ScheduleEntry(semester,resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5));
                students.add(schedule); 
                
                
            }
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace(); 
        }
        
        return students; 
               
    }
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode) {
        connection = DBConnection.getConnection();
        
        try
        {
           dropStudentScheduleByCoursePrepared = connection.prepareStatement("delete from app.schedule where semester = ? and studentid = ? and coursecode = ?");
           dropStudentScheduleByCoursePrepared.setString(1, semester); 
           dropStudentScheduleByCoursePrepared.setString(2, studentID);
           dropStudentScheduleByCoursePrepared.setString(3, courseCode);
           
           dropStudentScheduleByCoursePrepared.executeUpdate();
              
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    public static void dropScheduleByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection(); 
        
        try {
            dropScheduleByCoursePrepared = connection.prepareStatement("delete from app.schedule where semester = ? and coursecode = ?");
            dropScheduleByCoursePrepared.setString(1, semester);
            dropScheduleByCoursePrepared.setString(2, courseCode); 
            
            dropScheduleByCoursePrepared.executeUpdate(); 
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace(); 
        }
    }
    public static void updateScheduleEntry(String semester, ScheduleEntry entry) {
        connection = DBConnection.getConnection(); 
        
        try {
            updateScheduleEntryPrepared = connection.prepareStatement("update app.schedule set semester = ?, studentid = ?, coursecode = ?, status = ?, timestamp = ? where semester = ? "); 
            updateScheduleEntryPrepared.setString(1, semester); 
            updateScheduleEntryPrepared.setString(2, entry.getStudentID()); 
            updateScheduleEntryPrepared.setString(3, entry.getCourseCode()); 
            updateScheduleEntryPrepared.setString(4, entry.getStatus()); 
            updateScheduleEntryPrepared.setTimestamp(5, entry.getTimestamp());
            updateScheduleEntryPrepared.setString(6, semester); 
            
            updateScheduleEntryPrepared.executeUpdate(); 
                    
            
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
             
    }
    
}

