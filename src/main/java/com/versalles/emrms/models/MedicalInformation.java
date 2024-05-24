package com.versalles.emrms.models;

import java.io.Serializable;
import com.versalles.emrms.structures.DoublyLinkedList;

/**
 *
 * @author JUANM
 */
public class MedicalInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String patientId;
    private String diagnosis;
    private String treatment;
    private String notes;
    private DoublyLinkedList<String> medicalHistory;
    private DoublyLinkedList<String> currentMedications;

    public MedicalInformation(String patientId, String diagnosis, String treatment, String notes) {
        this.patientId = patientId;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.notes = notes;
        this.medicalHistory = new DoublyLinkedList<>();
        this.currentMedications = new DoublyLinkedList<>();
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public DoublyLinkedList<String> getMedicalHistory() {
        return medicalHistory;
    }

    public void addMedicalHistory(String history) {
        this.medicalHistory.add(history);
    }

    public DoublyLinkedList<String> getCurrentMedications() {
        return currentMedications;
    }

    public void addCurrentMedication(String medication) {
        this.currentMedications.add(medication);
    }
}
