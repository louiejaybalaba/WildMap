package com.example.searchmap;

public class Subject {
    /*Variable Declaration*/
    private String subjNo;
    private String subjName;
    private String[] subjSched = new String[2];
    private String[] subjRoom = new String[2];

    /*Constructor*/
    public Subject(String subjProp) {
        String[] data;

        data = subjProp.split(";");
        subjNo = data[0];
        subjName = data[1];
        subjSched[0] = data[2];
        subjRoom[0] = data[3];
        subjSched[1] = data[4];
        subjRoom[1] = data[5];
    }

    /*Getters*/
    public String getSubjectProperties() {
        String subjectProperties = "";
        subjectProperties = subjNo + ";" + subjName + ";" + subjSched[0] + ";" + subjRoom[0] + ";" + subjSched[1] + ";" + subjRoom[1];
        return subjectProperties;
    }

    public String getSubjectNumber() {
        return subjNo;
    }

    public String getSubjectName() {
        return subjName;
    }

    public String getSubjectSchedule(int element) {
        return subjSched[element];
    }

    public String getSubjectDay(int element) {
        return subjSched[element].split(" ")[0];
    }

    public String getSubjectStartTime(int element) {
        return subjSched[element].split(" ")[1].split("-")[0];
    }

    public String getSubjectEndTime(int element) {
        return subjSched[element].split(" ")[1].split("-")[1];
    }

    public String getSubjectRoom(int element) {
        return subjRoom[element];
    }

    public Boolean secondScheduleExists() {
        if (getSubjectSchedule(1).equals(" "))
            return false;
        return true;
    }
}
