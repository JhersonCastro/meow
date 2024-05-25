package com.versalles.emrms.models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author JUANM
 */
public class Appointment implements Serializable, Comparable<Appointment> {

    private static final long serialVersionUID = 1L;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    private String appointmentId;
    private String patientId;
    private String doctorId;
    private String dateTime;
    private String notes;

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId='" + appointmentId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", dateTime=" + dateTime +
                ", notes='" + notes + '\'' +
                '}';
    }

    public Appointment(String appointmentId, String patientId, String doctorId, String dateTime, String notes) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateTime = dateTime;
        this.notes = notes;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }



    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }



    public String getDescription() {
        return notes;
    }

    @Override
    public int compareTo(Appointment other) {
        return this.dateTime.compareTo(other.dateTime);
    }
}
