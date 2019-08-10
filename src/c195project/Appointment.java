/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195project;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author chris
 */
public class Appointment {

    private final int appointmentId;

    private final int customerId;
    private final int userId;
    private final String title;
    private final String description;
    private final String location;
    private final String contact;
    private final String type;
    private final String url;
    
    private final Date start;
    private final Date end;
    
    public Appointment(int customerId, int userId, String title, String description, String location, String contact, String type, String url, Date start, Date end) {
        this.appointmentId = -1;
        this.customerId = customerId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;
    }
    
    public Appointment(int appointmentId, int customerId, int userId, String title, String description, String location, String contact, String type, String url, Date start, Date end) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getContact() {
        return contact;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }
    
    public static ArrayList<String> validate(Appointment appointment) {
        ArrayList<String> errors = new ArrayList<>();
        
        if (appointment.getTitle().isEmpty()) {
            errors.add("Title field is required");
        }
        if (appointment.getDescription().isEmpty()) {
            errors.add("Description field is required");
        }
        if (appointment.getLocation().isEmpty()) {
            errors.add("Location field is required");
        }
        if (appointment.getContact().isEmpty()) {
            errors.add("Contact field is required");
        }
        if (appointment.getType().isEmpty()) {
            errors.add("Type field is required");
        }
        if (appointment.getUrl().isEmpty()) {
            errors.add("Url field is required");
        }
        if (hasOverlappingUsersAppointments(appointment)) {
            errors.add("Has overlapping appointments");
        }
        if (appointment.getStart().after(appointment.getEnd())) {
            errors.add("Start time must be before end time");
        }
        if (appointment.getStart().before(Date.from(Instant.now()))) {
            errors.add("Meeting can't be in the past");
        }
        
        return errors;
    }
    
    private static boolean hasOverlappingUsersAppointments(Appointment appointment) {
        ArrayList<Appointment> appointmentsOnDate = new ArrayList<>(SQLQueries.getUsersAppointmentsOnDate(appointment.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), appointment.getUserId()));
        for (Appointment app : appointmentsOnDate) {
            if (appointment.getStart().before(app.getEnd()) && appointment.getEnd().after(app.getStart()) && appointment.getAppointmentId() != app.getAppointmentId()) {
                return true;
            }
        }
        return false;
    }
}
