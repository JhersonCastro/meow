package com.versalles.emrms.models;

import com.versalles.emrms.history.AppointmentHistory;
import java.io.Serializable;
import com.versalles.emrms.structures.DoublyLinkedList;
import com.versalles.emrms.structures.Stack;
import com.versalles.emrms.utils.UndoRedoAction;

/**
 *
 * @author JUANM
 */
public class Patient extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int age;
    private DoublyLinkedList<Appointment> appointments;
    private MedicalInformation medicalInformation;
    private Triage triage;
    private AppointmentHistory appointmentHistory;
    private transient Stack<UndoRedoAction> undoStack;
    private transient Stack<UndoRedoAction> redoStack;

    public Patient(String id, String name, String password, String address, String phoneNumber, int age) {
        super(id, name, password, address, phoneNumber);
        this.age = age;
        this.appointments = new DoublyLinkedList<>();
        this.medicalInformation = null;
        this.triage = null;
        this.appointmentHistory = new AppointmentHistory();
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        int oldAge = this.age;
        this.age = age;
        undoStack.push(new UndoRedoAction("Set age", oldAge, this.age));
        redoStack.clear();
    }

    public DoublyLinkedList<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
        undoStack.push(new UndoRedoAction("Add appointment", null, appointment));
        redoStack.clear();
    }

    public void removeAppointment(Appointment appointment) {
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).equals(appointment)) {
                appointments.remove(i);
                undoStack.push(new UndoRedoAction("Remove appointment", appointment, null));
                redoStack.clear();
                break;
            }
        }
    }

    public MedicalInformation getMedicalInformation() {
        return medicalInformation;
    }

    public void setMedicalInformation(MedicalInformation medicalInformation) {
        MedicalInformation oldMedicalInformation = this.medicalInformation;
        this.medicalInformation = medicalInformation;
        undoStack.push(new UndoRedoAction("Set medical information", oldMedicalInformation, this.medicalInformation));
        redoStack.clear();
    }

    public Triage getTriage() {
        return triage;
    }

    public void setTriage(Triage triage) {
        Triage oldTriage = this.triage;
        this.triage = triage;
        undoStack.push(new UndoRedoAction("Set triage", oldTriage, this.triage));
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
            case "Set age":
                if (isUndo) {
                    this.age = (int) action.getOldState();
                } else {
                    this.age = (int) action.getNewState();
                }
                break;
            case "Add appointment":
                if (isUndo) {
                    appointments.remove((Appointment) action.getNewState());
                } else {
                    appointments.add((Appointment) action.getNewState());
                }
                break;
            case "Remove appointment":
                if (isUndo) {
                    appointments.add((Appointment) action.getOldState());
                } else {
                    appointments.remove((Appointment) action.getOldState());
                }
                break;
            case "Set medical information":
                if (isUndo) {
                    this.medicalInformation = (MedicalInformation) action.getOldState();
                } else {
                    this.medicalInformation = (MedicalInformation) action.getNewState();
                }
                break;
            case "Set triage":
                if (isUndo) {
                    this.triage = (Triage) action.getOldState();
                } else {
                    this.triage = (Triage) action.getNewState();
                }
                break;
            case "Add past appointment":
                if (isUndo) {
                    appointmentHistory.getPastAppointments().remove((Appointment) action.getNewState());
                } else {
                    appointmentHistory.addPastAppointment((Appointment) action.getNewState());
                }
                break;
            default:
                System.out.println("Unknown action type: " + action.getActionType());
        }
    }

    @Override
    public void displayMenu() {
        System.out.println("Patient Menu:");
        System.out.println("1. View Personal Information");
        System.out.println("2. Request Appointment");
        System.out.println("3. Cancel Appointment");
        System.out.println("4. View Upcoming Appointments");
        System.out.println("5. View Medical History");
        System.out.println("6. Update Contact Information");
        System.out.println("7. Undo Last Action");
        System.out.println("8. Redo Last Action");
        System.out.println("9. Exit");
    }
}
