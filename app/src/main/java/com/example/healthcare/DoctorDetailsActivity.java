package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoctorDetailsActivity extends AppCompatActivity {

    Button locationBtn;

    private String[][] doctor_details1 =
            {
                    {"Doctor Name : Roshan Fernando","Hospital Address : Care Zone","Exp : 5yrs","Mobile No : 071556678","600"},
                    {"Doctor Name : Kanishka Dias","Hospital Address : Seth Sevana","Exp : 4yrs","Mobile No : 077349870","900"},
                    {"Doctor Name : Ajith Wasala","Hospital Address : Hemas","Exp : 7yrs","Mobile No : 078546730","500"},
                    {"Doctor Name : Kasun Weerakoon","Hospital Address : Asiri","Exp : 9yrs","Mobile No : 071545980","300"},
                    {"Doctor Name : Sameera Silva","Hospital Address : Navinna","Exp : 2yrs","Mobile No : 071539018","200"}
            };

    private String[][] doctor_details2 =
            {
                    {"Doctor Name : Nimmi Fernando","Hospital Address : Care Zone","Exp : 5yrs","Mobile No : 071556678","600"},
                    {"Doctor Name : Kanish Dias","Hospital Address : Seth Sevana","Exp : 4yrs","Mobile No : 077349870","900"},
                    {"Doctor Name : Amila Wasala","Hospital Address : Hemas","Exp : 7yrs","Mobile No : 078546730","500"},
                    {"Doctor Name : Kasuni Weerakoon","Hospital Address : Asiri","Exp : 9yrs","Mobile No : 071545980","300"},
                    {"Doctor Name : Sanju Silva","Hospital Address : Navinna","Exp : 2yrs","Mobile No : 071539018","200"}
            };

    private String[][] doctor_details3 =
            {
                    {"Doctor Name : Ramesh Fernando","Hospital Address : Care Zone","Exp : 5yrs","Mobile No : 071556678","600"},
                    {"Doctor Name : malmi Dias","Hospital Address : Seth Sevana","Exp : 4yrs","Mobile No : 077349870","900"},
                    {"Doctor Name : Anoma Wasala","Hospital Address : Hemas","Exp : 7yrs","Mobile No : 078546730","500"},
                    {"Doctor Name : Kapila Weerakoon","Hospital Address : Asiri","Exp : 9yrs","Mobile No : 071545980","300"},
                    {"Doctor Name : Sithumi Silva","Hospital Address : Navinna","Exp : 2yrs","Mobile No : 071539018","200"}
            };

    private String[][] doctor_details4 =
            {
                    {"Doctor Name : Rasika Fernando","Hospital Address : Care Zone","Exp : 5yrs","Mobile No : 071556678","600"},
                    {"Doctor Name : Kanthi Dias","Hospital Address : Seth Sevana","Exp : 4yrs","Mobile No : 077349870","900"},
                    {"Doctor Name : Pasindu Wasala","Hospital Address : Hemas","Exp : 7yrs","Mobile No : 078546730","500"},
                    {"Doctor Name : Parami Weerakoon","Hospital Address : Asiri","Exp : 9yrs","Mobile No : 071545980","300"},
                    {"Doctor Name : Hansi Silva","Hospital Address : Navinna","Exp : 2yrs","Mobile No : 071539018","200"}
            };

    private String[][] doctor_details5 =
            {
                    {"Doctor Name : Nayani Fernando","Hospital Address : Care Zone","Exp : 5yrs","Mobile No : 071556678","600"},
                    {"Doctor Name : Manju Dias","Hospital Address : Seth Sevana","Exp : 4yrs","Mobile No : 077349870","900"},
                    {"Doctor Name : pathum Wasala","Hospital Address : Hemas","Exp : 7yrs","Mobile No : 078546730","500"},
                    {"Doctor Name : Meena Weerakoon","Hospital Address : Asiri","Exp : 9yrs","Mobile No : 071545980","300"},
                    {"Doctor Name : Chamara Silva","Hospital Address : Navinna","Exp : 2yrs","Mobile No : 071539018","200"}
            };
    TextView tv;
    Button btn;
    String[][] getDoctor_details = {};
    HashMap<String,String> item;
    ArrayList List;
    SimpleAdapter sa;
    private String[][] doctor_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        tv = findViewById(R.id.textViewBMCartLogo6);
        btn = findViewById(R.id.buttonMODBack);
        locationBtn = findViewById(R.id.loacation_details_btn);

        Intent it = getIntent();
        String title = it.getStringExtra("title");
        tv.setText(title);

        if(title.compareTo("Family Physicians")==0)
            doctor_details = doctor_details1;
        else
        if(title.compareTo("Dietician")==0)
            doctor_details = doctor_details2;
        else
        if(title.compareTo("Dentist")==0)
            doctor_details = doctor_details3;
        else
        if(title.compareTo("Surgeon")==0)
            doctor_details = doctor_details4;
        else
            doctor_details = doctor_details5;

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorDetailsActivity.this, DoctorLocationDetailsActivity.class);
                startActivity(intent);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity((new Intent(DoctorDetailsActivity.this,FindDoctorActivity.class)));
            }
        });

        List = new ArrayList();
        for(int i=0;i<doctor_details.length;i++){
            item = new HashMap<>();
            item.put("line1", doctor_details[i][0]);
            item.put("line2", doctor_details[i][1]);
            item.put("line3", doctor_details[i][2]);
            item.put("line4", doctor_details[i][3]);
            item.put("line5", "Cons Fees:"+doctor_details[i][4]+"/-");
            List.add((item));
        }

        java.util.List<? extends Map<String, ?>> list = null;
        sa = new SimpleAdapter(this,list,
                R.layout.multi_lines,
                new String[]{"line1","line2","line3","line4","line5"},
                new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e}
                );
        ListView lst = findViewById(R.id.listViewMO);
        lst.setAdapter(sa);
        
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(DoctorDetailsActivity.this,BookAppointmentActivity.class);
                it.putExtra("text1",title);
                int i =0;
                it.putExtra("text2",doctor_details[i][0]);
                it.putExtra("text3",doctor_details[i][1]);
                it.putExtra("text4",doctor_details[i][3]);
                it.putExtra("text5",doctor_details[i][4]);
                startActivity(it);
            }
        });
    }
}