package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfessorController {

    @RequestMapping("/professor")
    public String index() 
    {
        return "Professor";
    }

    @RequestMapping("/professor/verifyAttendance")
    public String verifyAttendance() 
    {
        return "Professor";
    }
    
    @RequestMapping("/professor/startQuiz")
    public String startQuiz() 
    {
        return "Professor";
    }
    
    @RequestMapping("/professor/endQuiz")
    public String endQuiz() 
    {
        return "Professor";
    }
    
    @RequestMapping("/professor/showStatistics")
    public String showStatistics() 
    {
        return "Professor";
    }
    
    @RequestMapping("/professor/showFeedback")
    public String showFeedback() 
    {
        return "Professor";
    }
    @RequestMapping("/professor/administrateLecture")
    public String administrateLecture() 
    {
        return "Professor";
    }
    @RequestMapping("/professor/viewStudentScore")
    public String viewStudentScore() 
    {
        return "Professor";
    }
    @RequestMapping("/professor/viewStudents")
    public String viewStudents() 
    {
        return "Professor";
    }
}
