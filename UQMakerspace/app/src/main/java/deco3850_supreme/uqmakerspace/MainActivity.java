package deco3850_supreme.uqmakerspace;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.*;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view){
        EditText StudentId = (EditText)findViewById(R.id.studentNumber);
        EditText password = (EditText)findViewById(R.id.password);
        String sStudentId = StudentId.getText().toString();
        String sPassword = password.getText().toString();
        //Intent startNewActivity = new Intent(this, studentSearch.class);
        Intent startNewActivity = new Intent(this, HomePageActivity.class);
        //Check below will move to userCheck & password Check function after connection to data base is confirmed
        if(sStudentId.matches("")){
            Toast toast = Toast.makeText(getBaseContext() , "Please enter your student number", Toast.LENGTH_LONG);
            toast.show();
        }
        else if(sStudentId.length()!=8){
            Toast toast = Toast.makeText(getBaseContext() , "Student number incorrect", Toast.LENGTH_LONG);
            toast.show();
        }
        else if(sPassword.matches("")){
            Toast toast = Toast.makeText(getBaseContext() , "Please enter your password", Toast.LENGTH_LONG);
            toast.show();
        }
        else if(userCheck(sStudentId)&&passwordCheck(sStudentId, sPassword)){
            startActivity(startNewActivity);
        }
    }
    public boolean userCheck(String userName){

        return true;
    }
    public boolean passwordCheck(String userName, String password){

        return true;
    }


}
