package com.itaha.bugtracker.controller;


import com.itaha.bugtracker.entity.Project;
import com.itaha.bugtracker.entity.User;
import com.itaha.bugtracker.entity.Bug;
import com.itaha.bugtracker.service.MainService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private MainService service;

    @RequestMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @RequestMapping("/register")
    public String getRegisterPage(){
        return "register";
    }

    @PostMapping("/doregister")
    public String doRegister(@ModelAttribute User user){
        service.addUser(user);
        return "redirect:/login";
    }

    @RequestMapping("/role")
    public ModelAndView getRolePage(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("users",service.getAllUsers());
        mv.setViewName("role");
        return mv;
    }

    @RequestMapping(value="/dorole", method=RequestMethod.POST)
    public String doAssignRole(@RequestParam("id") String id, @RequestParam("newrole") String newrole){
        service.assignRole(Long.parseLong(id),newrole);
        return "redirect:/role";
    }

    @RequestMapping("/newproject")
    public String getNewProjectPage(){
        return "project-new";
    }

    @RequestMapping("/projects")
    public ModelAndView projectsPage(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("projects", service.getAllProjects());
        mv.setViewName("projects");
        return mv;
    }

    @RequestMapping(value = "/addproject", method = RequestMethod.POST)
    public String addProject(@ModelAttribute Project project){
        Project p=service.addProject(project);
        return "redirect:/project/"+p.getId();
    }

    @RequestMapping("/projectassign")
    public ModelAndView getProjectAssignPage(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("managers", service.getAllManager());
        mv.addObject("projects", service.getAllProjects());
        mv.setViewName("project-user");
        return mv;
    }

    @RequestMapping(value = "/assignproject", method = RequestMethod.POST)
    public String assignProjectToManager(@RequestParam String projectid, @RequestParam String managerid){
        service.assignManager(Long.parseLong(projectid), Long.parseLong(managerid));
        return "redirect:/projectassign";
    }


    @RequestMapping("/newbug")
    public ModelAndView getNewBugPage(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("projects", service.getAllProjects());
        mv.addObject("developers", service.getAllDevelopers());
        mv.setViewName("bug-new");
        return mv;
    }


    @RequestMapping(value = "/addbug", method = RequestMethod.POST)
    public String addNewBug(@ModelAttribute Bug bug, @RequestParam String idproject, @RequestParam String iddeveloper ){
        Bug res =service.addBug(bug,Long.parseLong(idproject), Long.parseLong(iddeveloper));
        return "redirect:/bug/"+res.getId();
    }

    @RequestMapping("/bugs")
    public ModelAndView getAllBugsPage(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("bugs", service.getAllBugs());
        mv.setViewName("bugs");
        return mv;
    }

    @RequestMapping("/bug/{id}")
    public ModelAndView getBugPage(@PathVariable Long id){
        ModelAndView mv = new ModelAndView();
        mv.addObject("bug", service.getBug(id));
        mv.setViewName("bug-detail");
        return mv;
    }


    @RequestMapping(value = "/project/{id}", method = RequestMethod.GET)
    public ModelAndView getProjectPage(@PathVariable Long id){
        ModelAndView mv = new ModelAndView();
        mv.addObject("project", service.getProject(id));
        mv.addObject("users", service.getAllUsers());
        mv.setViewName("project");
        return mv;
    }

    @RequestMapping("/addcomment")
    public String addComment(@RequestParam String message, @RequestParam Long idbug ){
        service.addComment(idbug,message);
        return "redirect:/bug/"+idbug;
    }

    @RequestMapping("/addpersonnel")
    public String addPersonneltoProject(@RequestParam Long idproject, @RequestParam Long idpersonnel){
        service.addPersonnel(idproject, idpersonnel);
        return "redirect:/project/"+idproject;
    }


    @RequestMapping("/bugeditform/{id}")
    public ModelAndView getEditFormPage(@PathVariable Long id){
        ModelAndView mv= new ModelAndView();
        mv.addObject("bug", service.getBug(id));
        mv.addObject("developers", service.getAllDevelopers());
        mv.setViewName("bug-update");
        return mv;
    }

    @RequestMapping("/editbug")
    public String editBug(@RequestParam Long idbug, @ModelAttribute Bug bug, @RequestParam Long iddev){
        service.editBug(idbug,bug,iddev);
        return "redirect:/bug/"+idbug;
    }


    @RequestMapping("/dashboard")
    public ModelAndView getDashboard(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("elements", service.getDashboardElements());
        mv.setViewName("dashboard");
        return mv;
    }






}

