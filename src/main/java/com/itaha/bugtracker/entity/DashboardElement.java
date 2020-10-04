package com.itaha.bugtracker.entity;

import lombok.Data;

import java.util.HashMap;

@Data
public class DashboardElement {

    private int nbrProjects;
    private int nbrBugs;
    private int nbrUsers;
    private int nbrAdmins;
    private HashMap<String, Integer> bugByPriority;
    private HashMap<String, Integer> bugByType;
    private HashMap<String, Integer> bugByStatus;
    private HashMap<String, Integer> userByRole;

    public DashboardElement(int nbrProjects, int nbrBugs, int nbrUsers, int nbrAdmins, HashMap<String, Integer> bugByPriority, HashMap<String, Integer> bugByType, HashMap<String, Integer> bugByStatus, HashMap<String, Integer> userByRole) {
        this.nbrProjects = nbrProjects;
        this.nbrBugs = nbrBugs;
        this.nbrUsers = nbrUsers;
        this.nbrAdmins = nbrAdmins;
        this.bugByPriority = bugByPriority;
        this.bugByType = bugByType;
        this.bugByStatus = bugByStatus;
        this.userByRole = userByRole;
    }



}
