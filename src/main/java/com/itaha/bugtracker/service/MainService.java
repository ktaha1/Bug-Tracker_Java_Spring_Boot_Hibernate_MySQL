package com.itaha.bugtracker.service;

import com.itaha.bugtracker.entity.*;
import com.itaha.bugtracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class MainService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BugRepository bugRepository;
    @Autowired
    private BugHistoryRepository bugHistoryRepository;
    @Autowired
    private CommentRepository commentRepository;

    public User addUser(User user){
        user.setRole("N/A");
        user.setDateCreation(LocalDateTime.now());
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAllByOrderByDateCreationDesc();
    }

    public void assignRole(Long id, String role){
        User user =userRepository.findById(id).orElse(null);
        user.setRole(role);
        userRepository.save(user);
    }

    public Project addProject(Project project){
        project.setDateCreation(LocalDateTime.now());
        // user details  TODO for later (session part)
        project.setCreatedBy(null);
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects(){
        return projectRepository.findAllByOrderByDateCreationDesc();
    }

    public List<User> getAllManager(){
        return userRepository.findAllByRoleEquals("Manager");
    }

    public void assignManager(Long projectid, Long managerid){
        Project project = projectRepository.findById(projectid).orElse(null);
        User user = userRepository.findById(managerid).orElse(null);
        project.setManagedBy(user);
        project.setDateAssigned(LocalDateTime.now());
        projectRepository.save(project);
    }

    public Project getProject(long id){
        return projectRepository.findById(id).orElse(null);
    }

    public List<User> getAllDevelopers(){
        return userRepository.findAllByRoleEquals("Developer");
    }

    public Bug addBug(Bug bug, Long idproject, Long iddeveloper){
        bug.setDateCreation(LocalDateTime.now());
        // user details  TODO for later (session part)
        User developer=userRepository.findById(iddeveloper).orElse(null);
        Project project = projectRepository.findById(idproject).orElse(null);
        bug.setProject(project);
        bug.setDeveloperAssigned(developer);

        BugHistory history= new BugHistory();
        history.setDate(LocalDateTime.now());
        history.setOldValue(" --- ");
        history.setNewValue(developer.getFirstName()+' '+developer.getLastName());
        history.setProperty("AssignedToDeveloper");
        List<BugHistory> histories = new ArrayList<BugHistory>();
        histories.add(history);
        bug.setBugHistories(histories);
        bugHistoryRepository.save(history);
        bugRepository.save(bug);
        List<Bug> bugs =  project.getBugs();
        bugs.add(bug);
        project.setBugs(bugs);
        project.setDateUpdated(LocalDateTime.now());
        projectRepository.save(project);
        return bug;
    }

    public List<Bug> getAllBugs(){
        return bugRepository.findAll();
    }

    public Bug getBug(Long id){
        return bugRepository.findById(id).orElse(null);
    }

    public Bug addComment(Long idbug, String message){
        Comment comment = new Comment();
        comment.setDateCreation(LocalDateTime.now());
        comment.setMessage(message);
        // todo set createdBy
        commentRepository.save(comment);
        Bug bug = bugRepository.findById(idbug).orElse(null);
        List<Comment> comments = bug.getComments();
        comments.add(comment);
        bug.setComments(comments);
        return bugRepository.save(bug);
    }


    public Project addPersonnel(Long idproject, Long idpersonnel){
        Project project = projectRepository.findById(idproject).orElse(null);
        User personal = userRepository.findById(idpersonnel).orElse(null);
        Set<User> personals =  project.getPersonnelAssigned();
        personals.add(personal);
        project.setPersonnelAssigned(personals);
        project.setDateUpdated(LocalDateTime.now());
        return projectRepository.save(project);
    }

    public Bug editBug(Long idbug, Bug bug, Long iddev){
        Bug bug1 = bugRepository.findById(idbug).orElse(null);
        User dev =userRepository.findById(iddev).orElse(null);
        bug1.setTitle(bug.getTitle());
        bug1.setDescription(bug.getDescription());
        bug1.setPriority(bug.getPriority());
        bug1.setType(bug.getType());
        bug1.setStatus(bug.getStatus());
        if(bug1.getDeveloperAssigned().getId()!=dev.getId()){
            BugHistory history = new BugHistory();
            history.setProperty("AssignedToDeveloper");
            history.setOldValue(bug1.getDeveloperAssigned().getFirstName()+' '+bug1.getDeveloperAssigned().getLastName());
            history.setNewValue(dev.getFirstName()+' '+dev.getLastName());
            history.setDate(LocalDateTime.now());
            bugHistoryRepository.save(history);
            List<BugHistory> histories = bug1.getBugHistories();
            histories.add(history);
            bug1.setBugHistories(histories);
            bug1.setDeveloperAssigned(dev);
        }
        bug1.setDateUpdate(LocalDateTime.now());
        return bugRepository.save(bug1);
    }



    public DashboardElement getDashboardElements(){

        int nbrProjects = projectRepository.findAll().size();
        int nbrBugs = bugRepository.findAll().size();
        int nbrUsers = userRepository.findAll().size();
        int nbrAdmins = userRepository.findAllByRoleEquals("Admin").size();
        HashMap<String,Integer> bugByPriority = new HashMap<String,Integer>();
        bugByPriority.put("Low", bugRepository.findAllByPriorityEquals("Low").size());
        bugByPriority.put("Medium", bugRepository.findAllByPriorityEquals("Medium").size());
        bugByPriority.put("High", bugRepository.findAllByPriorityEquals("High").size());
        bugByPriority.put("Critical", bugRepository.findAllByPriorityEquals("Critical").size());

        HashMap<String,Integer> bugByType = new HashMap<String,Integer>();
        bugByType.put("Functional", bugRepository.findAllByTypeEquals("Functional").size());
        bugByType.put("Content", bugRepository.findAllByTypeEquals("Content").size());
        bugByType.put("Visual", bugRepository.findAllByTypeEquals("Visual").size());
        bugByType.put("Security", bugRepository.findAllByTypeEquals("Security").size());

        HashMap<String,Integer> bugByStatus = new HashMap<String,Integer>();
        bugByStatus.put("Open", bugRepository.findAllByStatusEquals("Open").size());
        bugByStatus.put("Test/Ready", bugRepository.findAllByStatusEquals("Test/Ready").size());
        bugByStatus.put("Verified", bugRepository.findAllByStatusEquals("Verified").size());
        bugByStatus.put("Closed", bugRepository.findAllByStatusEquals("Closed").size());
        bugByStatus.put("Rejected", bugRepository.findAllByStatusEquals("Rejected").size());

        HashMap<String,Integer> userByRole = new HashMap<String,Integer>();
        userByRole.put("Admin", userRepository.findAllByRoleEquals("Admin").size());
        userByRole.put("Manager", userRepository.findAllByRoleEquals("Manager").size());
        userByRole.put("Tester", userRepository.findAllByRoleEquals("Tester").size());
        userByRole.put("Developer", userRepository.findAllByRoleEquals("Developer").size());

        return new DashboardElement(nbrProjects, nbrBugs, nbrUsers, nbrAdmins, bugByPriority, bugByType, bugByStatus, userByRole);

    }

}
