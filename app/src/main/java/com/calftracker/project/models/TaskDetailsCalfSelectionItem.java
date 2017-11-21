package com.calftracker.project.models;

/**
 * Created by Jared on 11/20/2017.
 */

public class TaskDetailsCalfSelectionItem {
    private String id;
    private boolean checked;

    public TaskDetailsCalfSelectionItem(String id, boolean checked){
        this.id = id;
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
