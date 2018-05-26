package deco3850_supreme.uqmakerspace;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


public class StudentSearch extends AppCompatActivity {
    public List<String> workshops = new ArrayList<String>();
    public List<String> equipments = new ArrayList<String>();
    public List<String> materials = new ArrayList<String>();
    //searched by material key is material and value is machine
    private HashMap<String, List<String>> searchedByMaterial = new HashMap<String, List<String>>();

    //workshopEquipmentRelationship key is Equipment and value is workshop
    private HashMap<String, List<String>> workshopEquipmentRelationship = new HashMap<String, List<String>>();
    //searched by workshopsDetails key is workshop and value is description
    private HashMap<String, String> workshopsDetails = new HashMap<String,String>();
    ListView workshopList;
    ArrayAdapter <String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studenthome);
//        loadDataFromDB();
        workshopList = (ListView) findViewById(R.id.workshopList);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(getResources().getStringArray(R.array.listOfResult)));
        adapter = new ArrayAdapter<String>(StudentSearch.this,
                                              android.R.layout.simple_list_item_1,
                                              arrayList);
        workshopList.setAdapter(adapter);
//        loadList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.workshopList);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
         });
        return super.onCreateOptionsMenu(menu);
    }



}
