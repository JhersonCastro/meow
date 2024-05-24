package com.versalles.emrms.utils;

/**
 *
 * @author JUANM
 */
public class UndoRedoAction {

    private String actionType;
    private Object oldState;
    private Object newState;

    public UndoRedoAction(String actionType, Object oldState, Object newState) {
        this.actionType = actionType;
        this.oldState = oldState;
        this.newState = newState;
    }

    public String getActionType() {
        return actionType;
    }

    public Object getOldState() {
        return oldState;
    }

    public Object getNewState() {
        return newState;
    }

}
