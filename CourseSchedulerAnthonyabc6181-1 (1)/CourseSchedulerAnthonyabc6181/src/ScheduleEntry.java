
import java.sql.Timestamp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author anthonycicardo
 */
public class ScheduleEntry {
    private String semester;
    private String courseCode;
    private String studentID; 
    private String status; 
    private Timestamp timestamp;

    public ScheduleEntry(String semester, String courseCode, String studentID, String status, Timestamp timestamp) {
        //super(semester, courseCode, description, seats);
        this.semester = semester; 
        this.courseCode = courseCode; 
        this.status = status;
        this.studentID = studentID; 
        this.timestamp = timestamp;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSemester() {
        return semester;
    }

    public String getCourseCode() {
        return courseCode;
    }
    

    public String getStudentID() {
        return studentID;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
    
    

    }
    
    
    
    
    

