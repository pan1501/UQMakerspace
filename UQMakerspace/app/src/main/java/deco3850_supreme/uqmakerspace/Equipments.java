package deco3850_supreme.uqmakerspace;
import  static deco3850_supreme.uqmakerspace.Constants.FIRST_COLUMN;
import  static deco3850_supreme.uqmakerspace.Constants.SECOND_COLUMN;
import  static deco3850_supreme.uqmakerspace.Constants.THIRD_COLUMN;
import  static deco3850_supreme.uqmakerspace.Constants.FOURTH_COLUMN;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Equipments extends AppCompatActivity {
    public List<String> equipments = new ArrayList<String>();
    ListView equipmentList;
    ArrayAdapter <String> adapter;

    private String currentLine;
    private String jasonString;
    private List<Map<String,String>> equipmentDataList;


    @Override
    protected void onCreate(Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        setContentView(R.layout.equipments);
        equipmentList = (ListView) findViewById(R.id.equipmentList);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(getResources().getStringArray(R.array.listOfEquipmentResult)));
        adapter = new ArrayAdapter<String>(Equipments.this,
                android.R.layout.simple_list_item_1,
                arrayList);
        equipmentList.setAdapter(adapter);
        equipmentDataList=new ArrayList<>();
        retrieveEquipmentData();

        equipmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String equipmentId=equipmentDataList.get(position).get("id");
                    String equipmentName=equipmentDataList.get(position).get("name");
                    String equipmentDes=equipmentDataList.get(position).get("des");
                    String equipmentStatus=equipmentDataList.get(position).get("status");
                    String equipmentCount=equipmentDataList.get(position).get("count");

                    Intent intent = new Intent(getApplicationContext(),EquipmentViewActivity.class);
                    intent.putExtra("id",equipmentId);
                    intent.putExtra("name",equipmentName);
                    intent.putExtra("des",equipmentDes);
                    intent.putExtra("status",equipmentStatus);
                    intent.putExtra("count",equipmentCount);
                    intent.putExtra("Equipment Info", equipmentList.getItemAtPosition(position).toString());
                    startActivity(intent);
//                Intent intent = new Intent(Equipments.this, equipmentMaps.class);
//                intent.putExtra("Equipment Info", equipmentList.getItemAtPosition(position).toString());
//                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_equipment, menu);
        MenuItem item = menu.findItem(R.id.equipmentList);
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


    private void retrieveEquipmentData(){
        //allow network in main thread
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        //String serverURL="http://192.168.1.109/UQ_makerspace/deco3800/php/retrieveData.php";
        String serverURL="https://deco3800-supremeteam.uqcloud.net/php/retrieveEquipmentData.php";
        try {
            URL url=new URL(serverURL);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            InputStream inputStream= new BufferedInputStream(httpURLConnection.getInputStream());

            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder=new StringBuilder();

            while((currentLine = bufferedReader.readLine())!=null){
                stringBuilder.append(currentLine +"\n");
            }
            inputStream.close();
            bufferedReader.close();
            httpURLConnection.disconnect();

            jasonString=stringBuilder.toString();
            Log.e("JsonString",jasonString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //parse JSON DATA
        try{
            JSONArray jsonArray=new JSONArray(jasonString);
            //Currently just test on 6 different data sets
            for(int i=0;i<6;i++){
                Map<String,String> data=new HashMap<>();
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                data.put("id",jsonObject.getString("Eid"));
                data.put("name",jsonObject.getString("Ename"));
                data.put("des",jsonObject.getString("Edescription"));
                data.put("status",jsonObject.getString("Estatus"));
                data.put("count",jsonObject.getString("Ecount"));
                equipmentDataList.add(data);
                Log.e("EquipmentDataList",data.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




}
