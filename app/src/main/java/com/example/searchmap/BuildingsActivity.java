package com.example.searchmap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BuildingsActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    List<String> listGroup;
    HashMap<String, List<String>> listItem;
    MainAdapter1 adapter;
    int flag = 0;
    String st;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buildings);

        expandableListView = findViewById(R.id.expandableList);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new MainAdapter1(this, listGroup, listItem);
        expandableListView.setAdapter(adapter);
        initListData();


        String[] rooms = getResources().getStringArray(R.array.Rooms);

        final AutoCompleteTextView editText = (AutoCompleteTextView) findViewById(R.id.searchRoom);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rooms);
        editText.setAdapter(adapter);

        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String index = parent.getItemAtPosition(position).toString();

                String[] superMain = getResources().getStringArray(R.array.superMain);
                String[] superAllied = getResources().getStringArray(R.array.superAllied);
                String[] superST = getResources().getStringArray(R.array.superST);
                String[] superEng = getResources().getStringArray(R.array.superEng);

                if(Arrays.asList(superMain).contains(index)) {
                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    intent.putExtra("room", Integer.parseInt(index));
                    startActivityForResult(intent, 0);
                }
                if(Arrays.asList(superAllied).contains(index))
                {
                    Intent intent = new Intent(getApplicationContext(), DetailActivityA.class);
                    intent.putExtra("room", Integer.parseInt(index));
                    startActivityForResult(intent, 0);
                }
                if(Arrays.asList(superST).contains(index))
                {
                    Intent intent = new Intent(getApplicationContext(), DetailActivityST.class);
                    intent.putExtra("room", index);
                    startActivityForResult(intent, 0);
                }
                if(Arrays.asList(superEng).contains(index))
                {
                    Intent intent = new Intent(getApplicationContext(), DetailActivityE_3.class);
                    intent.putExtra("room", index);
                    startActivityForResult(intent, 0);
                }
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Toast.makeText(BuildingsActivity.this, listGroup.get(groupPosition) + " : " + listItem.get(listGroup.get(groupPosition)).get(childPosition), Toast.LENGTH_LONG).show();

                if(groupPosition == 0)
                {
                    if(childPosition == 0)
                    {
                        Intent intent = new Intent(getApplicationContext(), DetailActivityST.class);
                        intent.putExtra("room", "mground");
                        startActivityForResult(intent, 0);
                    }
                    if(childPosition == 1)
                    {
                        Intent intent = new Intent(getApplicationContext(), DetailActivityST.class);
                        intent.putExtra("room", "msecond");
                        startActivityForResult(intent, 0);
                    }
                    if(childPosition == 2)
                    {
                        Intent intent = new Intent(getApplicationContext(), DetailActivityST.class);
                        intent.putExtra("room", "mthird");
                        startActivityForResult(intent, 0);
                    }
                    if(childPosition == 3)
                    {
                        Intent intent = new Intent(getApplicationContext(), DetailActivityST.class);
                        intent.putExtra("room", "mfourth");
                        startActivityForResult(intent, 0);
                    }
                }
                if(groupPosition == 1)
                {
                    if(childPosition == 0)
                    {
                        Intent intent = new Intent(getApplicationContext(), DetailActivityST.class);
                        intent.putExtra("room", "stground");
                        startActivityForResult(intent, 0);
                    }
                    if(childPosition == 1)
                    {
                        Intent intent = new Intent(getApplicationContext(), DetailActivityST.class);
                        intent.putExtra("room", "stsecond");
                        startActivityForResult(intent, 0);
                    }
                    if(childPosition == 2)
                    {
                        Intent intent = new Intent(getApplicationContext(), DetailActivityST.class);
                        intent.putExtra("room", "stthird");
                        startActivityForResult(intent, 0);
                    }
                    if(childPosition == 3)
                    {
                        Intent intent = new Intent(getApplicationContext(), DetailActivityST.class);
                        intent.putExtra("room", "stfourth");
                        startActivityForResult(intent, 0);
                    }
                }
                if(groupPosition == 2)
                {
                    if(childPosition == 0)
                    {
                        Intent intent = new Intent(getApplicationContext(), DetailActivityST.class);
                        intent.putExtra("room", "background");
                        startActivityForResult(intent, 0);
                    }
                    if(childPosition == 1)
                    {
                        Intent intent = new Intent(getApplicationContext(), DetailActivityST.class);
                        intent.putExtra("room", "backsecond");
                        startActivityForResult(intent, 0);
                    }
                }
                if(groupPosition == 3)
                {
                    if(childPosition == 0)
                    {
                        Intent intent = new Intent(getApplicationContext(), DetailActivityST.class);
                        intent.putExtra("room", "engground");
                        startActivityForResult(intent, 0);
                    }
                    if(childPosition == 1)
                    {
                        Intent intent = new Intent(getApplicationContext(), DetailActivityST.class);
                        intent.putExtra("room", "engsecond");
                        startActivityForResult(intent, 0);
                    }
                    if(childPosition == 2)
                    {
                        Intent intent = new Intent(getApplicationContext(), DetailActivityST.class);
                        intent.putExtra("room", "engthird");
                        startActivityForResult(intent, 0);
                    }
                    if(childPosition == 3)
                    {
                        Intent intent = new Intent(getApplicationContext(), DetailActivityST.class);
                        intent.putExtra("room", "engfourth");
                        startActivityForResult(intent, 0);
                    }
                }

                return false;
            }
        });
    }

    private void initListData()
    {
        listGroup.add(getString(R.string.mainBuilding));
        listGroup.add(getString(R.string.stBuilding));
        listGroup.add(getString(R.string.alliedBuilding));
        listGroup.add(getString(R.string.engBuilding));

        String[] array;

        List<String> list1 = new ArrayList<>();
        array = getResources().getStringArray(R.array.mainBuilding);
        for(String item:array)
        {
            list1.add(item);
        }

        List<String> list2 = new ArrayList<>();
        array = getResources().getStringArray(R.array.stBuilding);
        for(String item:array)
        {
            list2.add(item);
        }

        List<String> list3 = new ArrayList<>();
        array = getResources().getStringArray(R.array.alliedBuilding);
        for(String item:array)
        {
            list3.add(item);
        }

        List<String> list4 = new ArrayList<>();
        array = getResources().getStringArray(R.array.engBuilding);
        for(String item:array)
        {
            list4.add(item);
        }

        listItem.put(listGroup.get(0), list1);
        listItem.put(listGroup.get(1), list2);
        listItem.put(listGroup.get(2), list3);
        listItem.put(listGroup.get(3), list4);
        adapter.notifyDataSetChanged();
    }

}
