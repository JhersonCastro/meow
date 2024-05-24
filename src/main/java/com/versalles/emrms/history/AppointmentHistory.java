package com.versalles.emrms.history;

import com.versalles.emrms.models.Appointment;
import com.versalles.emrms.structures.DoublyLinkedList;
import java.io.Serializable;

/**
 *
 * @author JUANM
 */
public class AppointmentHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    private DoublyLinkedList<Appointment> pastAppointments;

    public AppointmentHistory() {
        this.pastAppointments = new DoublyLinkedList<>();
    }

    public void addPastAppointment(Appointment appointment) {
        pastAppointments.add(appointment);
    }

    public DoublyLinkedList<Appointment> getPastAppointments() {
        return pastAppointments;
    }

    public boolean remove(Appointment appointment) {
        for (int i = 0; i < pastAppointments.size(); i++) {
            if (pastAppointments.get(i).equals(appointment)) {
                pastAppointments.remove(i);
                return true;
            }
        }
        return false;
    }
}
