package deco3850_supreme.uqmakerspace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class EquipmentViewActivity extends AppCompatActivity {

    Map<String,String> equipmentData;
    private int count;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_view);

        equipmentData=new HashMap<>();
        getExtraData();
        appendDetails();
        book();
        viewLocation();
    }

    private void getExtraData(){


        equipmentData.put("name",this.getIntent().getExtras().getString("name"));
        equipmentData.put("id",this.getIntent().getExtras().getString("id"));
        equipmentData.put("des",this.getIntent().getExtras().getString("des"));
        equipmentData.put("status",this.getIntent().getExtras().getString("status"));
        equipmentData.put("count",this.getIntent().getExtras().getString("count"));
        Log.e("Equipment data",equipmentData.toString());
    }
    public void appendDetails(){
        TextView idTextView=findViewById(R.id.equipment_id);
        TextView countTextView=findViewById(R.id.equipment_count);
        TextView nameTextView=findViewById(R.id.equipment_name);
        TextView desTextView=findViewById(R.id.equipment_des);
        TextView statusTextView=findViewById(R.id.equipment_status);


        nameTextView.append(equipmentData.get("name"));
        idTextView.append(equipmentData.get("id"));
        desTextView.append(equipmentData.get("des"));
        countTextView.append(equipmentData.get("count"));
        statusTextView.append(equipmentData.get("status"));

        count= Integer.parseInt(equipmentData.get("count"));
        status= Integer.parseInt(equipmentData.get("status"));
    }

    private void book(){
        Button bookBtn=findViewById(R.id.BookBtn);
        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
                if(status!=0){
                    if(count>0){
                        count=count-1;
                        TextView countTextView=findViewById(R.id.equipment_count);
                        countTextView.setText("Count: "+Integer.toString(count));
                        Toast.makeText(getApplicationContext(), "Item booked successfully", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Not enough item", Toast.LENGTH_SHORT).show();
                        status=0;
                        TextView statusTextView=findViewById(R.id.equipment_status);
                        statusTextView.setText("Status: "+Integer.toString(status));
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Item not available", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void viewLocation(){
        Button mapBtn=findViewById(R.id.MapBtn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        Bundle bundle = getIntent().getExtras();
        String requiredInfo = bundle.getString("Equipment Info");
        Intent intent = new Intent(getApplicationContext(), equipmentMaps.class);
        intent.putExtra("Equipment Info", requiredInfo);
        startActivity(intent);
            }
        });

    }

    private void uploadData(){
        String address="https://deco3800-supremeteam.uqcloud.net/php/insertBookingData.php";
        try {
            URL url = new URL(address);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
            String data = URLEncoder.encode("eid", "UTF-8") + "=" + URLEncoder.encode(equipmentData.get("id"), "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            OS.close();
            InputStream IS = httpURLConnection.getInputStream();
            IS.close();
            //httpURLConnection.connect();
            httpURLConnection.disconnect();
            //return "Registration Success...";
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

