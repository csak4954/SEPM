package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController 
{

    @RequestMapping("/student")
    public String index() 
    {
        return "Student";
    }

    @RequestMapping("/student/viewLectures")
    public String viewLectures() 
    {
        return "Student";
    }
    
    @RequestMapping("/student/addLectures")
    public String addLectures() 
    {
        return "Student";
    }
    
    @RequestMapping("/student/removeLectures")
    public String removeLectures() 
    {
        return "Student";
    }
    
    @RequestMapping("/student/sendLectureFeedback")
    public String sendLectureFeedback() 
    {
        return "Student";
    }
    
    @RequestMapping("/student/showQuizResults")
    public String showQuizResults() 
    {
        return "Student";
    }
    
    @RequestMapping("/student/submitAnswers")
    public String submitAnswers() 
    {
        return "Student";
    }
    
    @RequestMapping("/student/confirmAttendance")
    public String confirmAttendance() 
    {
        return "Student";
    }
    
    @RequestMapping("/student/viewStatistics")
    public String viewStatistics() 
    {
        return "Student";
    }
    
    @RequestMapping("/student/viewMap")
    public String viewMap() 
    {
        return "Student";
    }
}
