package com.calftracker.project.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jared on 11/16/2017.
 */

public class Task {

    private Calendar dateLastUpdated;
    private ArrayList<Calf> calvesToObserve;
    private ArrayList<ArrayList<VaccineTask>> vaccinesToAdminister;
    private ArrayList<VaccineTask> overdueVaccinations;

    public Task(Calendar dateLastUpdated, ArrayList<Calf> calvesToObserve,
                ArrayList<ArrayList<VaccineTask>> vaccinesToAdminister, ArrayList<VaccineTask> overdueVaccinations) {
        super();
        this.dateLastUpdated = dateLastUpdated;
        this.calvesToObserve = calvesToObserve;
        this.vaccinesToAdminister = vaccinesToAdminister;
        this.overdueVaccinations = overdueVaccinations;
    }

    public Calendar getDateLastUpdated() {
        return dateLastUpdated;
    }

    public void setDateLastUpdated(Calendar dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }

    public ArrayList<Calf> getCalvesToObserve() {
        return calvesToObserve;
    }

    public void setCalvesToObserve(ArrayList<Calf> calvesToObserve) {
        this.calvesToObserve = calvesToObserve;
    }

    public ArrayList<ArrayList<VaccineTask>> getVaccinesToAdminister() {
        return vaccinesToAdminister;
    }

    public void setVaccinesToAdminister(ArrayList<ArrayList<VaccineTask>> vaccinesToAdminister) {
        this.vaccinesToAdminister = vaccinesToAdminister;
    }

    public ArrayList<VaccineTask> getOverdueVaccinations() {
        return overdueVaccinations;
    }

    public void setOverdueVaccinations(ArrayList<VaccineTask> overdueVaccinations) {
        this.overdueVaccinations = overdueVaccinations;
    }

    public void placeVaccineInTasks(Calendar today, Vaccine vaccine, Calf calf) {
        Calendar vaccStart = Calendar.getInstance();
        vaccStart.setTimeZone(calf.getDateOfBirth().getTimeZone());
        vaccStart.setTimeInMillis(calf.getDateOfBirth().getTimeInMillis());
        vaccStart.add(Calendar.DATE, vaccine.getToBeAdministered().get(0).getSpan()[0]);

        Calendar vaccEnd = Calendar.getInstance();
        vaccEnd.setTimeZone(calf.getDateOfBirth().getTimeZone());
        vaccEnd.setTimeInMillis(calf.getDateOfBirth().getTimeInMillis());
        vaccEnd.add(Calendar.DATE, vaccine.getToBeAdministered().get(0).getSpan()[1]);

        long daysBetweenEnd = calendarDaysBetween(today, vaccEnd);

        if(daysBetweenEnd < 0) {
            this.overdueVaccinations.add(new VaccineTask(vaccine, calf, false));
            return;
        } else {
            this.vaccinesToAdminister.get((int) daysBetweenEnd).add(new VaccineTask(vaccine, calf, false));
        }

        long daysBetweenStart = calendarDaysBetween(today, vaccStart);

        if(daysBetweenStart < 0) {
            this.vaccinesToAdminister.get(0).add(new VaccineTask(vaccine, calf, true));
        } else {
            this.vaccinesToAdminister.get((int) daysBetweenStart).add(new VaccineTask(vaccine, calf, true));
        }
    }

    private static long calendarDaysBetween(Calendar today, Calendar vaccDate) {

        // Create copies so we don't update the original calendars.

        Calendar start = Calendar.getInstance();
        start.setTimeZone(today.getTimeZone());
        start.setTimeInMillis(today.getTimeInMillis());

        Calendar end = Calendar.getInstance();
        end.setTimeZone(vaccDate.getTimeZone());
        end.setTimeInMillis(vaccDate.getTimeInMillis());

        // Set the copies to be at midnight, but keep the day information.

        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);

        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);

        // At this point, each calendar is set to midnight on
        // their respective days. Now use TimeUnit.MILLISECONDS to
        // compute the number of full days between the two of them.

        return TimeUnit.MILLISECONDS.toDays(
                end.getTimeInMillis() - start.getTimeInMillis());
    }
}
