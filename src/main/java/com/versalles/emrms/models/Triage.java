package com.versalles.emrms.models;

import java.io.Serializable;

/**
 *
 * @author JUANM
 */
public class Triage implements Serializable {

    private static final long serialVersionUID = 1L;

    private int priorityLevel;
    private String description;

    public Triage(int priorityLevel, String description) {
        this.priorityLevel = priorityLevel;
        this.description = description;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
