package deco3850_supreme.uqmakerspace;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class HamburgerNavActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private String currentLine;
    private String jasonString;
    ListView listView;
    private Map<String,String> currentWorkshopData;
    private List<Map<String,String>> workshopList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        workshopList=new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hamburger_nav);
        super.onCreateDrawer();

        retrieveWorkshopData();

        ListView listView=(ListView)findViewById(R.id.listView);
        CustomAdapter customAdapter=new CustomAdapter();
        listView.setAdapter(customAdapter);

        //viewDetailBtnPressed();
        //setWorkshopName();
        //
    }
/*
    private void setWorkshopName(){
        workshopName=(TextView)this.findViewById(R.id.list_item_workshopName);
        if(workshopNameText==null){
            workshopName.setText("initalName");
        }else{
            workshopName.setText(workshopNameText);
        }
    }
*/
    private void retrieveWorkshopData(){
        //allow network in main thread
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        //String serverURL="http://192.168.1.109/UQ_makerspace/deco3800/php/retrieveData.php";
        String serverURL="https://deco3800-supremeteam.uqcloud.net/php/retrieveWorkshopData.php";
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
            httpURLConnection.disconnect();
            inputStream.close();
            bufferedReader.close();

            jasonString=stringBuilder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //parse JSON DATA
        try{
            JSONArray jsonArray=new JSONArray(jasonString);
            JSONObject jsonObject=null;
            for(int i=0;i<jsonArray.length();i++){
                Map<String,String> data=new HashMap<>();
                jsonObject=jsonArray.getJSONObject(i);
                data.put("id",jsonObject.getString("Wid"));
                data.put("name",jsonObject.getString("Wname"));
                data.put("building",jsonObject.getString("Wbuilding"));
                data.put("des",jsonObject.getString("Wdescription"));
                data.put("status",jsonObject.getString("Wstatus"));
                data.put("room",jsonObject.getString("Wroom"));
                workshopList.add(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //view detail button setup
    private void viewDetailBtnPressed(Button button,int i) {
        //Button viewDetailBtn = (Button) findViewById(R.id.list_item_viewDetailBtn);
            currentWorkshopData = workshopList.get(i);
            Log.e("currentPosition", String.valueOf(i));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent workshopInfoActivity = new Intent(getApplicationContext(), DetailInfoActivity.class);
                    Log.e("id", currentWorkshopData.get("id"));
                    Log.e("currentName", currentWorkshopData.get("name"));
                    workshopInfoActivity.putExtra("id", currentWorkshopData.get("id"));
                    workshopInfoActivity.putExtra("name", currentWorkshopData.get("name"));
                    workshopInfoActivity.putExtra("building", currentWorkshopData.get("building"));
                    workshopInfoActivity.putExtra("des", currentWorkshopData.get("des"));
                    workshopInfoActivity.putExtra("status", currentWorkshopData.get("status"));
                    workshopInfoActivity.putExtra("room", currentWorkshopData.get("room"));
                    startActivity(workshopInfoActivity);
                }
            });
    }

    class CustomAdapter extends BaseAdapter{
        class ViewHolder{
            public TextView name;
            public ImageView imageView;
            public Button button;
        }


        @Override
        public int getCount() {
            return workshopList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder=new ViewHolder();

            View view = getLayoutInflater().inflate(R.layout.relative_listview_layout,null);

            holder.imageView=(ImageView)view.findViewById(R.id.imageView_workshop_image);
            holder.name=(TextView)view.findViewById(R.id.textView_workshop_name);
            holder.button=(Button)view.findViewById(R.id.button_workshop_button);
            holder.button.setTag(position);

            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent workshopInfoActivity = new Intent(getApplicationContext(), DetailInfoActivity.class);
                    currentWorkshopData = workshopList.get((Integer)v.getTag());

                    Log.e("id", currentWorkshopData.get("id"));
                    Log.e("currentName", currentWorkshopData.get("name"));
                    workshopInfoActivity.putExtra("id", currentWorkshopData.get("id"));
                    workshopInfoActivity.putExtra("name", currentWorkshopData.get("name"));
                    workshopInfoActivity.putExtra("building", currentWorkshopData.get("building"));
                    workshopInfoActivity.putExtra("des", currentWorkshopData.get("des"));
                    workshopInfoActivity.putExtra("status", currentWorkshopData.get("status"));
                    workshopInfoActivity.putExtra("room", currentWorkshopData.get("room"));
                    startActivity(workshopInfoActivity);
                }
            });


            holder.imageView.setImageResource(R.drawable.ic_eait_instrumetation);
            holder.name.setText(workshopList.get(position).get("name"));

            return view;
        }
    }
}
