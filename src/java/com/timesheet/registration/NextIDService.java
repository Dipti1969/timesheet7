package com.timesheet.registration;

public interface NextIDService {

    public String getNextID(String column);

    public void updateID(String column);
}
