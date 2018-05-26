package deco3850_supreme.uqmakerspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class HomePageActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Intent newActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        super.onCreateDrawer();

        final Button workshopButton=(Button)findViewById(R.id.imageButton_workshop);
        final Button equipmentButton=(Button)findViewById(R.id.imageButton_equipment);
        final Button blankButton=(Button)findViewById(R.id.imageButton_blank);
        workshopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPressed(workshopButton);
            }
        });
        equipmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPressed(equipmentButton);
            }
        });
        blankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPressed(blankButton);
            }
        });
    }



    private void buttonPressed(Button button){
        if(button.getId()==R.id.imageButton_workshop||button.getId()==R.id.imageButton_blank){
            newActivity=new Intent(getApplicationContext(),HamburgerNavActivity.class);
        }if(button.getId()==R.id.imageButton_equipment){
            newActivity=new Intent(getApplicationContext(),Equipments.class);
        }
        startActivity(newActivity);
    }
}
