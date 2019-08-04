/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195project;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;

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
    private final LocalDate date;
    private final String startTime;
    private final String endTime;
    
    public Appointment(int customerId, int userId, String title, String description, String location, String contact, String type, String url, LocalDate date, String startTime, String endTime) {
        this.appointmentId = -1;
        this.customerId = customerId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;   
    }
    
    public Appointment(int appointmentId, int customerId, int userId, String title, String description, String location, String contact, String type, String url, LocalDate date, String startTime, String endTime) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;   
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

    public LocalDate getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
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
        if (appointment.getDate() == null) {
            errors.add("Date field is required");
        }
        if (appointment.getStartTime().isEmpty()) {
            errors.add("Start Time field is required");
        }
        if (appointment.getEndTime().isEmpty()) {
            errors.add("End Time field is required");
        }
        
        String timePattern = "\\d{2}:\\d{2}:\\d{2}";
//        String timePattern = "\\d{2}[0-23]:\\d{2}[0-59]:\\d{2}[0-59]";
//        String timePattern = "\\d[0-2]{2}:\\d{2}:\\d{2}";
//        String timePatter = "^()$";
//        Time startTime = new Time(appointment.getStartTime().split(":")[0]);
//        Time endTime = new Time(1,1,1);
        if (!appointment.getStartTime().matches(timePattern)) {
            errors.add("Start Time format must be '##:##:##'");
        } else if (!appointment.getEndTime().matches(timePattern)) {
            errors.add("End Time format must be '##:##:##'");}
//        } else if (startTime.after(endTime)) {
//            errors.add("Start Time must be before End Time");
//        }
        
        return errors;
    }
}
