package com.example.searchmap;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class ClassSchedule extends AppCompatActivity {

    ListView schedListView;
    Button addSubj;

    String[] subjNo;
    String[] subjName;
    String[] subjSched1;
    String[] subjSched2;
    String[] subjRoom1;
    String[] subjRoom2;

    /*String SubjectProperties = "BUS211;General Business with Entrepreneurship;TTH 10:30AM-12:00PM;ST302; ; "
                                + "\n"
                                + "CCS423;Software Systems Engineering;W 5:30PM-7:30PM;ST303;F 5:30PM-8:30PM;ST203"
                                + "\n"
                                + "CPE501;Seminars and Field Trips;WF 7:30AM-10:30AM;FIELD;;"
                                + "\n"
                                + "CPE518;Design Project 1;SAT 2:30PM-8:30PM;FIELD;;\n";*/
    String SubjectProperties = "CPE411 H1;Control Systems;TTH 1:30PM-3:00PM;ST303;W 9:00AM-10:30AM;ST203\n";
                                //+ "ES411 H1;Engineering Economy;TTH 9:00AM-10:30AM;ST302;~;~";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_schedule);

        //saveFile("Subject Schedule.txt", SubjectProperties);
        //saveFile("Subject Schedule.txt", SubjectProperties + "BUS211;General Business with Entrepreneurship;TTH 10:30AM-12:00PM;ST302; ; ");
        //saveFile("Subject Schedule.txt", "");

        schedListView = (ListView) findViewById(R.id.schedListView);
        addSubj = (Button) findViewById(R.id.addSubjectButton);

        ItemAdapter itemAdapter = new ItemAdapter(this);
        schedListView.setAdapter(itemAdapter);

        setAlarm();

        addSubj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getBaseContext(), EditSubjectSchedule.class);
                startIntent.putExtra("MODE", "add");
                startActivity(startIntent);
            }
        });
    }

    public class ItemAdapter extends BaseAdapter {

        String classScheduleTxt = readFile("Subject Schedule.txt");
        StudyLoad subj = new StudyLoad(classScheduleTxt);
        LayoutInflater myInflater;

        public ItemAdapter(Context c) {
            myInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {

            View v =  myInflater.inflate(R.layout.schedule_listview_detail, null);
            TextView subjectNumberTextView = (TextView) v.findViewById(R.id.subjectNumberTextView);
            TextView subjectNameTextView = (TextView) v.findViewById(R.id.subjectNameTextView);
            TextView subjectSchedule1TextView = (TextView) v.findViewById(R.id.subjectSchedule1TextView);
            TextView subjectSchedule2TextView = (TextView) v.findViewById(R.id.subjectSchedule2TextView);
            TextView subjectRoom1TextView = (TextView) v.findViewById(R.id.subjectRoom1TextView);
            TextView subjectRoom2TextView = (TextView) v.findViewById(R.id.subjectRoom2TextView);
            Button editButton = (Button) v.findViewById(R.id.editButton);
            Button deleteButton = (Button) v.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent show_edit_activity = new Intent(getApplicationContext(), EditSubjectSchedule.class);
                    show_edit_activity.putExtra("MODE", "edit");
                    show_edit_activity.putExtra("ELEMENT", i);
                    startActivity(show_edit_activity);
                    //Toast.makeText(ClassSchedule.this, "Subject Edit", Toast.LENGTH_SHORT).show();
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subj.deleteSubj(i);
                    saveFile("Subject Schedule.txt", subj.getSubjectLoad());
                    notifyDataSetInvalidated();
                    Toast.makeText(ClassSchedule.this, "Subject Deleted", Toast.LENGTH_SHORT).show();
                }
            });

            subjectNumberTextView.setText(subj.getSubjectNumber(i));
            subjectNameTextView.setText(subj.getSubjectName(i));
            subjectSchedule1TextView.setText(subj.getSubjectSchedule1(i));
            subjectSchedule2TextView.setText(subj.getSubjectSchedule2(i));
            subjectRoom1TextView.setText(subj.getSubjectRoom1(i));
            subjectRoom2TextView.setText(subj.getSubjectRoom2(i));

            return v;
        }

        @Override
        public int getCount() {
            return subj.getSize();
        }

        @Override
        public Object getItem(int i) {
            return subj.getSubject(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }
    }

    private void saveFile(String file, String text) {
        try {
            FileOutputStream foe = openFileOutput(file, Context.MODE_PRIVATE);
            foe.write(text.getBytes());
            foe.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ClassSchedule.this, "Cannot save file", Toast.LENGTH_SHORT).show();
        }
    }

    private String readFile(String file) {
        String text = "";
        try {
            FileInputStream fis = openFileInput(file);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            text = new String(buffer);
            return text;

        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ClassSchedule.this, file + " not detected. A new " + file + " has been created", Toast.LENGTH_SHORT).show();
            saveFile(file, "");
            return readFile(file);
        }
    }

    private void setAlarm() {
        String classScheduleTxt = readFile("Subject Schedule.txt");
        StudyLoad subj = new StudyLoad(classScheduleTxt);

        Intent intent = new Intent(ClassSchedule.this, AlarmReceiver.class);
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();
        int ctr = 0;
        for (int i = 0; i < subj.getSize(); ++i) {
            // Create time.
            PendingIntent alarmIntent;
            Calendar startTime;
            startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR, Integer.valueOf(subj.getSubjectSchedule1StartTime(0).split(":")[0]));
            startTime.set(Calendar.MINUTE, Integer.valueOf(subj.getSubjectSchedule1StartTime(0).split(":")[1].substring(0,2)));
            startTime.set(Calendar.SECOND, 0);
            startTime.set(Calendar.AM_PM, (subj.getSubjectSchedule1StartTime(0).split(":")[1].substring(2,4).equals("AM"))? Calendar.AM:Calendar.PM);
            startTime.add(Calendar.MINUTE, -5);

            switch (subj.getSubjectSchedule1Day(i)) {
                case "MWF":
                    intent.putExtra("ROOM", subj.getSubjectRoom1(i));
                    alarmIntent = PendingIntent.getBroadcast(ClassSchedule.this, ctr, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    startTime.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                    if(startTime.before(Calendar.getInstance()))
                        startTime.add(Calendar.DATE, 7);

                    //alarm.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                    alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                    ctr++;

                    intent.putExtra("ROOM", subj.getSubjectRoom1(i));
                    alarmIntent = PendingIntent.getBroadcast(ClassSchedule.this, ctr, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    startTime.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                    if(startTime.before(Calendar.getInstance()))
                        startTime.add(Calendar.DATE, 7);

                    //alarm.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                    alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                    ctr++;

                    intent.putExtra("ROOM", subj.getSubjectRoom1(i));
                    alarmIntent = PendingIntent.getBroadcast(ClassSchedule.this, ctr, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    startTime.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                    if(startTime.before(Calendar.getInstance()))
                        startTime.add(Calendar.DATE, 7);

                    //alarm.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                    alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                    ctr++;

                    break;
                case "TTH":
                    intent.putExtra("ROOM", subj.getSubjectRoom1(i));
                    alarmIntent = PendingIntent.getBroadcast(ClassSchedule.this, ctr, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    startTime.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                    if(startTime.before(Calendar.getInstance()))
                        startTime.add(Calendar.DATE, 7);

                    //alarm.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                    alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                    ctr++;

                    intent.putExtra("ROOM", subj.getSubjectRoom1(i));
                    alarmIntent = PendingIntent.getBroadcast(ClassSchedule.this, ctr, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    startTime.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                    if(startTime.before(Calendar.getInstance()))
                        startTime.add(Calendar.DATE, 7);

                    //alarm.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                    alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                    ctr++;

                    break;
                case "M":
                    intent.putExtra("ROOM", subj.getSubjectRoom1(i));
                    alarmIntent = PendingIntent.getBroadcast(ClassSchedule.this, ctr, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    startTime.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                    if(startTime.before(Calendar.getInstance()))
                        startTime.add(Calendar.DATE, 7);

                    //alarm.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                    alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                    ctr++;
                    break;
                case "T":
                    intent.putExtra("ROOM", subj.getSubjectRoom1(i));
                    alarmIntent = PendingIntent.getBroadcast(ClassSchedule.this, ctr, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    startTime.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                    if(startTime.before(Calendar.getInstance()))
                        startTime.add(Calendar.DATE, 7);

                    //alarm.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                    alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                    ctr++;
                    break;
                case "W":
                    intent.putExtra("ROOM", subj.getSubjectRoom1(i));
                    alarmIntent = PendingIntent.getBroadcast(ClassSchedule.this, ctr, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    startTime.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                    if(startTime.before(Calendar.getInstance()))
                        startTime.add(Calendar.DATE, 7);

                    //alarm.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                    alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                    ctr++;
                    break;
                case "Th":
                    intent.putExtra("ROOM", subj.getSubjectRoom1(i));
                    alarmIntent = PendingIntent.getBroadcast(ClassSchedule.this, ctr, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    startTime.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                    if(startTime.before(Calendar.getInstance()))
                        startTime.add(Calendar.DATE, 7);

                    //alarm.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                    alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                    ctr++;
                    break;
                case "F":
                    intent.putExtra("ROOM", subj.getSubjectRoom1(i));
                    alarmIntent = PendingIntent.getBroadcast(ClassSchedule.this, ctr, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    startTime.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                    if(startTime.before(Calendar.getInstance()))
                        startTime.add(Calendar.DATE, 7);

                    //alarm.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                    alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                    ctr++;
                    break;
                case "SAT":
                    intent.putExtra("ROOM", subj.getSubjectRoom1(i));
                    alarmIntent = PendingIntent.getBroadcast(ClassSchedule.this, ctr, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    startTime.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                    if(startTime.before(Calendar.getInstance()))
                        startTime.add(Calendar.DATE, 7);

                    //alarm.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), 7 * 60 * 60 * 10000, alarmIntent);
                    alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                    ctr++;
                    break;
            }

            if(subj.secondScheduleExists(i)) {
                startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR, Integer.valueOf(subj.getSubjectSchedule2StartTime(0).split(":")[0]));
                startTime.set(Calendar.MINUTE, Integer.valueOf(subj.getSubjectSchedule2StartTime(0).split(":")[1].substring(0,2)));
                startTime.set(Calendar.SECOND, 0);
                startTime.set(Calendar.AM_PM, (subj.getSubjectSchedule2StartTime(0).split(":")[1].substring(2,4).equals("AM"))? Calendar.AM:Calendar.PM);
                startTime.add(Calendar.MINUTE, -5);

                switch (subj.getSubjectSchedule2Day(i)) {
                    case "MWF":
                        intent.putExtra("ROOM", subj.getSubjectRoom2(i));
                        alarmIntent = PendingIntent.getBroadcast(ClassSchedule.this, ctr, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        startTime.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                        if(startTime.before(Calendar.getInstance()))
                            startTime.add(Calendar.DATE, 7);

                        //alarm.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                        alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                        ctr++;

                        intent.putExtra("ROOM", subj.getSubjectRoom2(i));
                        alarmIntent = PendingIntent.getBroadcast(ClassSchedule.this, ctr, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        startTime.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                        if(startTime.before(Calendar.getInstance()))
                            startTime.add(Calendar.DATE, 7);

                        //alarm.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                        alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                        ctr++;

                        intent.putExtra("ROOM", subj.getSubjectRoom2(i));
                        alarmIntent = PendingIntent.getBroadcast(ClassSchedule.this, ctr, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        startTime.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                        if(startTime.before(Calendar.getInstance()))
                            startTime.add(Calendar.DATE, 7);

                        //alarm.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                        alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                        ctr++;

                        break;
                    case "TTH":
                        intent.putExtra("ROOM", subj.getSubjectRoom2(i));
                        alarmIntent = PendingIntent.getBroadcast(ClassSchedule.this, ctr, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        startTime.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                        if(startTime.before(Calendar.getInstance()))
                            startTime.add(Calendar.DATE, 7);

                        //alarm.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                        alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                        ctr++;

                        intent.putExtra("ROOM", subj.getSubjectRoom2(i));
                        alarmIntent = PendingIntent.getBroadcast(ClassSchedule.this, ctr, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        startTime.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                        if(startTime.before(Calendar.getInstance()))
                            startTime.add(Calendar.DATE, 7);

                        //alarm.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                        alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                        ctr++;

                        break;
                    case "M":
                        intent.putExtra("ROOM", subj.getSubjectRoom2(i));
                        alarmIntent = PendingIntent.getBroadcast(ClassSchedule.this, ctr, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        startTime.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                        if(startTime.before(Calendar.getInstance()))
                            startTime.add(Calendar.DATE, 7);

                        //alarm.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                        alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                        ctr++;
                        break;
                    case "T":
                        intent.putExtra("ROOM", subj.getSubjectRoom2(i));
                        alarmIntent = PendingIntent.getBroadcast(ClassSchedule.this, ctr, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        startTime.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                        if(startTime.before(Calendar.getInstance()))
                            startTime.add(Calendar.DATE, 7);

                        //alarm.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                        alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                        ctr++;
                        break;
                    case "W":
                        intent.putExtra("ROOM", subj.getSubjectRoom2(i));
                        alarmIntent = PendingIntent.getBroadcast(ClassSchedule.this, ctr, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        startTime.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                        if(startTime.before(Calendar.getInstance()))
                            startTime.add(Calendar.DATE, 7);

                        //alarm.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                        alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                        ctr++;
                        break;
                    case "Th":
                        intent.putExtra("ROOM", subj.getSubjectRoom2(i));
                        alarmIntent = PendingIntent.getBroadcast(ClassSchedule.this, ctr, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        startTime.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                        if(startTime.before(Calendar.getInstance()))
                            startTime.add(Calendar.DATE, 7);

                        //alarm.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                        alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                        ctr++;
                        break;
                    case "F":
                        intent.putExtra("ROOM", subj.getSubjectRoom2(i));
                        alarmIntent = PendingIntent.getBroadcast(ClassSchedule.this, ctr, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        startTime.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                        if(startTime.before(Calendar.getInstance()))
                            startTime.add(Calendar.DATE, 7);

                        //alarm.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                        alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                        ctr++;
                        break;
                    case "SAT":
                        intent.putExtra("ROOM", subj.getSubjectRoom2(i));
                        alarmIntent = PendingIntent.getBroadcast(ClassSchedule.this, ctr, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        startTime.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                        if(startTime.before(Calendar.getInstance()))
                            startTime.add(Calendar.DATE, 7);

                        //alarm.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), 7 * 60 * 60 * 10000, alarmIntent);
                        alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                        ctr++;
                        break;
                }
            }
        }

        /*@Override
    public void onClick(View view) {
        EditText editText = findViewById(R.id.editText);
        TimePicker timePicker = findViewById(R.id.timePicker);

        // Set notificationId & text.
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        intent.putExtra("i", subj);
        intent.putExtra("todo", ""yeah);

        // getBroadcast(context, requestCode, intent, flags)

        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();

        switch (view.getId()) {
            case R.id.setBtn:
                for(int i = 0; i < 10; ++i)
                {
                    PendingIntent alarmIntent = PendingIntent.getBroadcast(MainActivity.this, i,
                            intent, PendingIntent.FLAG_CANCEL_CURRENT);


                    int hour = timePicker.getCurrentHour();
                    int minute = timePicker.getCurrentMinute();

                    // Create time.
                    Calendar startTime = Calendar.getInstance();
                    startTime.set(Calendar.HOUR_OF_DAY, hour);
                    startTime.set(Calendar.MINUTE, minute+i);
                    startTime.set(Calendar.SECOND, 0);
                    long alarmStartTime = startTime.getTimeInMillis();

                    // Set alarm.
                    // set(type, milliseconds, intent)
                    alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);
                }

                Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.cancelBtn:
                //alarm.cancel(alarmIntent);
                Toast.makeText(this, "Canceled.", Toast.LENGTH_SHORT).show();
                break;
        }

    }*/
    }
}
