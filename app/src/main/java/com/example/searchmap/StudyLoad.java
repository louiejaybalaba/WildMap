package com.example.searchmap;

import java.util.ArrayList;

public class StudyLoad {
    private ArrayList<Subject> subj = new ArrayList<Subject>();

    /*Constructor*/
    public StudyLoad(String classScheduleTxt) {
        String subjPropLine[] = classScheduleTxt.split("\n");

        if(classScheduleTxt.equals(""))
            return;

        for(int l = 0; l < subjPropLine.length; l++)
            subj.add(new Subject(subjPropLine[l]));
    }

    /*Getters for Specified Element*/
    public Subject getSubject(int element) {
        return subj.get(element);
    }

    public String getSubjectNumber(int element) {
        if(element >= 0 && element < subj.size())
            return subj.get(element).getSubjectNumber();
        return "";
    }

    public String getSubjectName(int element) {
        if(element >= 0 && element < subj.size())
            return subj.get(element).getSubjectName();
        return "";
    }

    public String getSubjectSchedule1(int element) {
        if(element >= 0 && element < subj.size())
            return subj.get(element).getSubjectSchedule(0);
        return "";
    }

    public String getSubjectSchedule2(int element) {
        if(element >= 0 && element < subj.size())
            return subj.get(element).getSubjectSchedule(1);
        return "";
    }

    public String getSubjectRoom1(int element) {
        if(element >= 0 && element < subj.size())
            return subj.get(element).getSubjectRoom(0);
        return "";
    }

    public String getSubjectRoom2(int element) {
        if(element >= 0 && element < subj.size())
            return subj.get(element).getSubjectRoom(1);
        return "";
    }

    public String getSubjectSchedule1Day(int element) {
        if(element >= 0 && element < subj.size())
            return subj.get(element).getSubjectDay(0);
        return "";
    }

    public String getSubjectSchedule2Day(int element) {
        if(element >= 0 && element < subj.size())
            return subj.get(element).getSubjectDay(1);
        return "";
    }

    public String getSubjectSchedule1StartTime(int element) {
        if(element >= 0 && element < subj.size())
            return subj.get(element).getSubjectStartTime(0);
        return "";
    }

    public String getSubjectSchedule2StartTime(int element) {
        if(element >= 0 && element < subj.size())
            return subj.get(element).getSubjectStartTime(1);
        return "";
    }

    public String getSubjectSchedule1EndTime(int element) {
        if(element >= 0 && element < subj.size())
            return subj.get(element).getSubjectEndTime(0);
        return "";
    }

    public String getSubjectSchedule2EndTime(int element) {
        if(element >= 0 && element < subj.size())
            return subj.get(element).getSubjectEndTime(1);
        return "";
    }



    /*Getters for Arrays
    public String[] getSubjectNumberArray() {
        String[] getSubjectNumberArray = new String[subj.size()];

        for(int e = 0; e < getSubjectNumberArray.length; e++)
            getSubjectNumberArray[e] = subj.get(e).getSubjectNumber();

        return getSubjectNumberArray;
    }

    public String[] getSubjectNameArray() {
        String[] subjNameArray = new String[subj.size()];

        for(int e = 0; e < subjNameArray.length; e++)
            subjNameArray[e] = subj.get(e).getSubjectName();

        return subjNameArray;
    }

    public String[] getSubjectSchedule1() {
        String[] subjSchedArray = new String[subj.size()];

        for(int e = 0; e < subjSchedArray.length; e++)
            subjSchedArray[e] = subj.get(e).getSubjectSchedule(0);

        return subjSchedArray;
    }

    public String[] getSubjectSchedule2() {
        String[] subjSchedArray = new String[subj.size()];

        for(int e = 0; e < subjSchedArray.length; e++)
            subjSchedArray[e] = subj.get(e).getSubjectSchedule(1);

        return subjSchedArray;
    }

    public String[] getSubjectRoom1() {
        String[] subjRoomArray = new String[subj.size()];

        for(int e = 0; e < subjRoomArray.length; e++)
            subjRoomArray[e] = subj.get(e).getSubjectRoom(0);

        return subjRoomArray;
    }

    public String[] getSubjectRoom2() {
        String[] subjRoomArray = new String[subj.size()];

        for(int e = 0; e < subjRoomArray.length; e++)
            subjRoomArray[e] = subj.get(e).getSubjectRoom(1);

        return subjRoomArray;
    }
    */

    /*Others*/
    public int getSize() {
        return subj.size();
    }

    public String getSubjectLoad() {
        String subjectSchedule = "";
            for (int l = 0; l < subj.size(); l++) {
                subjectSchedule += subj.get(l).getSubjectProperties();
                subjectSchedule += (l == subj.size() - 1)? "": "\n";
        }
        return subjectSchedule;
    }

    public void addSubject(String subjProp) {
        subj.add(new Subject(subjProp));
    }

    public void editSubject(String subjProp, int element) {
        subj.set(element, new Subject(subjProp));
    }

    public void deleteSubj(int index) {
        subj.remove(index);
    }

    public Boolean secondScheduleExists(int element) {
        return subj.get(element).secondScheduleExists();
    }

}
