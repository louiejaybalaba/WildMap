package com.example.searchmap;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

public class EditSubjectSchedule extends AppCompatActivity {
    String studyLoadTxt;
    StudyLoad studyLoad;
    int element;

    TextView no, name, room1, room2, day1, day2, startingTime1, startingTime2, endingTime1, endingTime2;
    Button setStartTime1, setStartTime2, setEndTime1, setEndTime2, saveBtn, cancelBtn;
    Spinner setDay1, setDay2;

    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int hour, minute;
    String amPm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subject_schedule);

        //--Intitialization--//
        studyLoadTxt = readFile("Subject Schedule.txt");
        studyLoad = new StudyLoad(studyLoadTxt);

        no =  (TextView)  findViewById(R.id.subjectNumberTextView);
            //no.setText(getIntent().getStringExtra("MODE"));
        name = (TextView)  findViewById(R.id.subjectNameTextView);
        room1 = (TextView)  findViewById(R.id.subjectRoom1TextView);
        room2 = (TextView)  findViewById(R.id.subjectRoom2TextView);

        startingTime1 = (TextView)  findViewById(R.id.startTime1TextView);
        endingTime1 = (TextView)  findViewById(R.id.endtTime1TextView);
        startingTime2 = (TextView)  findViewById(R.id.startTime2TextView);
        endingTime2 = (TextView)  findViewById(R.id.endtTime2TextView);

        setStartTime1 = (Button) findViewById(R.id.startTime1Button);
        setEndTime1 = (Button) findViewById(R.id.endTime1Button);
        setStartTime2 = (Button) findViewById(R.id.startTime2Button);
        setEndTime2 = (Button) findViewById(R.id.endTime2Button);
        saveBtn = (Button) findViewById(R.id.saveBtn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.DaysInSchedule, R.layout.day_spinner_layout);
        //adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        setDay1 = (Spinner) findViewById(R.id.dayOnSchedule1Spinner);
        setDay2 = (Spinner) findViewById(R.id.dayOnSchedule2Spinner);
        setDay1.setAdapter(adapter);
        setDay2.setAdapter(adapter);

        if (getIntent().getStringExtra("MODE").equals("edit")) {
            element = getIntent().getIntExtra("ELEMENT", 0);
            no.setText(studyLoad.getSubjectNumber(element));
            name.setText(studyLoad.getSubjectName(element));

            for (int i=0;i<setDay1.getCount();i++){
                if (setDay1.getItemAtPosition(i).toString().equals(studyLoad.getSubjectSchedule1Day(element)))
                    setDay1.setSelection(i);
                if (studyLoad.secondScheduleExists(element))
                    if (setDay2.getItemAtPosition(i).toString().equals(studyLoad.getSubjectSchedule2Day(element)))
                        setDay2.setSelection(i);
            }

            room1.setText(studyLoad.getSubjectRoom1(element));
            startingTime1.setText(studyLoad.getSubjectSchedule1StartTime(element));
            endingTime1.setText(studyLoad.getSubjectSchedule1EndTime(element));

            //day2.setText(studyLoad.getSubjectSchedule2Day(element));
            if (studyLoad.secondScheduleExists(element)) {
                room2.setText(studyLoad.getSubjectRoom2(element));
                startingTime2.setText(studyLoad.getSubjectSchedule2StartTime(element));
                endingTime2.setText(studyLoad.getSubjectSchedule2EndTime(element));
            }
        }

        //--Setting up time--/
        setStartTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(EditSubjectSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12)
                            amPm = "PM";
                        else
                            amPm = "AM";

                        if (hourOfDay == 0)
                            hourOfDay = 12;
                        else if (hourOfDay >= 13)
                            hourOfDay -= 12;

                        startingTime1.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, hour, minute, false);

                timePickerDialog.show();
            }
        });

        setEndTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(EditSubjectSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12)
                            amPm = "PM";
                        else
                            amPm = "AM";

                        if (hourOfDay == 0)
                            hourOfDay = 12;
                        else if (hourOfDay >= 13)
                            hourOfDay -= 12;

                        endingTime1.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);
                    }
                }, hour, minute, false);

                timePickerDialog.show();
            }
        });

        setStartTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(EditSubjectSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12)
                            amPm = "PM";
                        else
                            amPm = "AM";

                        if (hourOfDay == 0)
                            hourOfDay = 12;
                        else if (hourOfDay >= 13)
                            hourOfDay -= 12;

                        startingTime2.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, hour, minute, false);

                timePickerDialog.show();
            }
        });

        setEndTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(EditSubjectSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12)
                            amPm = "PM";
                        else
                            amPm = "AM";

                        if (hourOfDay == 0)
                            hourOfDay = 12;
                        else if (hourOfDay >= 13)
                            hourOfDay -= 12;

                        endingTime2.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);
                    }
                }, hour, minute, false);

                timePickerDialog.show();
            }
        });

        //--Saving Changes--//
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean second_schedule_exists = false;
                if (no.getText().toString().isEmpty() || name.getText().toString().isEmpty()) {
                    Toast.makeText(EditSubjectSchedule.this, "Subject's name and number must be inputed", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (setDay1.getSelectedItemPosition() == 0 || room1.getText().toString().isEmpty() || startingTime1.getText().toString().isEmpty() || endingTime1.getText().toString().isEmpty()) {
                    Toast.makeText(EditSubjectSchedule.this, "First Schedule: name, no, room, start time, and end time must be inputed completely.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ((!(setDay2.getSelectedItemPosition() == 0) || !room2.getText().toString().isEmpty()))
                    if(startingTime2.getText().toString().isEmpty() || endingTime2.getText().toString().isEmpty()) {
                        Toast.makeText(EditSubjectSchedule.this, "Second Schedule: name, no, room, start time, and end time must be inputed completely. If there is none then leave it blank.", Toast.LENGTH_SHORT).show();
                        return;
                }
                else
                    second_schedule_exists = true;

                String subjProp;
                subjProp = no.getText().toString() + ";" + name.getText().toString() + ";"
                                + setDay1.getSelectedItem().toString() + " " + startingTime1.getText().toString() + "-" + endingTime1.getText().toString() + ";" + room1.getText().toString() + ";";
                subjProp += second_schedule_exists? setDay2.getSelectedItem().toString() + " " + startingTime2.getText().toString() + "-" + endingTime2.getText().toString() + ";" + room2.getText().toString() : " ; ";

                if (getIntent().getStringExtra("MODE").equals("add"))
                    studyLoad.addSubject(subjProp);
                else if (getIntent().getStringExtra("MODE").equals("edit"))
                    studyLoad.editSubject(subjProp, getIntent().getIntExtra("ELEMENT", 0));

                saveFile("Subject Schedule.txt", studyLoad.getSubjectLoad());
                Toast.makeText(EditSubjectSchedule.this, "Saved", Toast.LENGTH_SHORT).show();

                finish();
                Intent startIntent = new Intent(getApplicationContext(), ClassSchedule.class);
                startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(startIntent);
            }
        });
    }

    public void saveFile(String file, String text) {
        try {
            FileOutputStream foe = openFileOutput(file, Context.MODE_PRIVATE);
            foe.write(text.getBytes());
            foe.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(EditSubjectSchedule.this, "An error has occured", Toast.LENGTH_SHORT).show();
        }
    }

    public String readFile(String file) {
        String text = "";
        try {
            FileInputStream fis = openFileInput(file);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            text = new String(buffer);
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(EditSubjectSchedule.this, "An error has occured", Toast.LENGTH_SHORT).show();
        }

        return text;
    }
}