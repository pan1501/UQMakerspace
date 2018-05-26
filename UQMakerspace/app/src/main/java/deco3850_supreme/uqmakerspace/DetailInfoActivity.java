package deco3850_supreme.uqmakerspace;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class DetailInfoActivity extends AppCompatActivity {

    private Map<String,String> workshopData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        workshopData=new HashMap<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getExtraData();
        appendDetails();
        this.setTitle(workshopData.get("name"));
    }


    private void getExtraData(){
        workshopData.put("name",this.getIntent().getExtras().getString("name"));
        workshopData.put("id",this.getIntent().getExtras().getString("id"));
        workshopData.put("building",this.getIntent().getExtras().getString("building"));
        workshopData.put("room",this.getIntent().getExtras().getString("room"));
        workshopData.put("des",this.getIntent().getExtras().getString("des"));
        workshopData.put("status",this.getIntent().getExtras().getString("status"));
        Log.e("workshop data",workshopData.toString());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void appendDetails(){
        TextView nameTextView=findViewById(R.id.workshop_name);
        TextView buildingTextView=findViewById(R.id.workshop_building);
        TextView roomTextView=findViewById(R.id.workshop_room);
        TextView statusTextView=findViewById(R.id.workshop_status);
        TextView descriptionTextView=findViewById(R.id.workshop_description);

        nameTextView.append(workshopData.get("name"));
        buildingTextView.append(workshopData.get("building"));
        roomTextView.append(workshopData.get("room"));
        descriptionTextView.append(workshopData.get("des"));
        statusTextView.append(workshopData.get("status"));
    }

}
