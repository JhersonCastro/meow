package com.versalles.emrms.models;

import com.versalles.emrms.Exceptions.UserDoesntExist;
import com.versalles.emrms.history.AppointmentHistory;
import java.io.Serializable;
import java.util.Comparator;

import com.versalles.emrms.structures.CircularList;
import com.versalles.emrms.structures.Queue;
import com.versalles.emrms.structures.Stack;
import com.versalles.emrms.utils.Searching;
import com.versalles.emrms.utils.UndoRedoAction;

/**
 *
 * @author JUANM
 */
public class Doctor extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String specialization;
    private CircularList<Patient> patients;
    private Queue<Appointment> appointments;
    private AppointmentHistory appointmentHistory;
    private transient Stack<UndoRedoAction> undoStack;
    private transient Stack<UndoRedoAction> redoStack;

    public Doctor(String id, String name, String password, String address, String phoneNumber, String specialization) {
        super(id, name, password, address, phoneNumber);
        this.specialization = specialization;
        this.patients = new CircularList<>();
        this.appointments = new Queue<>();
        this.appointmentHistory = new AppointmentHistory();
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        String oldSpecialization = this.specialization;
        this.specialization = specialization;
        undoStack.push(new UndoRedoAction("Set specialization", oldSpecialization, this.specialization));
        redoStack.clear();
    }

    public CircularList<Patient> getPatients() {
        return patients;
    }

    public void addPatient(Patient patient) {
        this.patients.add(patient);
        if (undoStack == null)
            undoStack = new Stack<>();
        if(redoStack == null)
            redoStack = new Stack<>();
        undoStack.push(new UndoRedoAction("Add patient", null, patient));
        redoStack.clear();
    }

    public void removePatient(Patient patient) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).equals(patient)) {
                patients.remove(i);
                undoStack.push(new UndoRedoAction("Remove patient", patient, null));
                redoStack.clear();
                break;
            }
        }
    }

    public Queue<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appointment) {
        this.appointments.enqueue(appointment);
        if(undoStack == null)
            undoStack = new Stack<>();
        if(redoStack == null)
            redoStack = new Stack<>();
        undoStack.push(new UndoRedoAction("Add appointment", null, appointment));
        redoStack.clear();
    }

    public void removeAppointment(Appointment appointment) throws UserDoesntExist {
        boolean checker = appointments.remove(appointment);
        if(!checker) throw new UserDoesntExist("appointment");

        undoStack.push(new UndoRedoAction("Remove appointment", appointment, null));
        redoStack.clear();
    }

    public AppointmentHistory getAppointmentHistory() {
        return appointmentHistory;
    }

    public void addPastAppointment(Appointment appointment) {
        appointmentHistory.addPastAppointment(appointment);
        undoStack.push(new UndoRedoAction("Add past appointment", null, appointment));
        redoStack.clear();
    }

    public void undoLastAction() {
        if (!undoStack.isEmpty()) {
            UndoRedoAction lastAction = undoStack.pop();
            redoStack.push(lastAction);
            System.out.println("Undoing action: " + lastAction.getActionType());
            executeUndoRedo(lastAction, true);
        } else {
            System.out.println("No actions to undo.");
        }
    }

    public void redoLastAction() {
        if (!redoStack.isEmpty()) {
            UndoRedoAction lastAction = redoStack.pop();
            undoStack.push(lastAction);
            System.out.println("Redoing action: " + lastAction.getActionType());
            executeUndoRedo(lastAction, false);
        } else {
            System.out.println("No actions to redo.");
        }
    }

    private void executeUndoRedo(UndoRedoAction action, boolean isUndo) {
        switch (action.getActionType()) {
            case "Add patient":
                if (isUndo) {
                    patients.remove((Patient) action.getNewState());
                } else {
                    patients.add((Patient) action.getNewState());
                }
                break;
            case "Remove patient":
                if (isUndo) {
                    patients.add((Patient) action.getOldState());
                } else {
                    patients.remove((Patient) action.getOldState());
                }
                break;
            case "Add appointment":
                if (isUndo) {
                    appointments.dequeue();
                } else {
                    appointments.enqueue((Appointment) action.getNewState());
                }
                break;
            case "Remove appointment":
                if (isUndo) {
                    appointments.enqueue((Appointment) action.getOldState());
                } else {
                    appointments.dequeue();
                }
                break;
            case "Set specialization":
                if (isUndo) {
                    this.specialization = (String) action.getOldState();
                } else {
                    this.specialization = (String) action.getNewState();
                }
                break;
            case "Add past appointment":
                if (isUndo) {
                    appointmentHistory.remove((Appointment) action.getNewState());
                } else {
                    appointmentHistory.addPastAppointment((Appointment) action.getNewState());
                }
                break;
            default:
                System.out.println("Unknown action type: " + action.getActionType());
        }
    }
    public Stack<UndoRedoAction> getUndoStack() {
        if(undoStack ==null)
            undoStack = new Stack<>();
        return undoStack;
    }

    public Stack<UndoRedoAction> getRedoStack() {
        if(redoStack ==null)
            redoStack = new Stack<>();
        return redoStack;
    }
}
