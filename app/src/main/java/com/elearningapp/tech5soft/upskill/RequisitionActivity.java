package com.elearningapp.tech5soft.upskill;

import android.R.layout;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.elearningapp.tech5soft.upskill.Models.DomianModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequisitionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private EditText edtTxtTrainingname,editTextObjective;
    private Button btnSend;
    private String outputData;
    ArrayList<DomianModel> domain=new ArrayList<>();
    Map<String,Integer> skillid=new HashMap<>();
    List skillDomain=new ArrayList();
    private String Baseurl="http://27.147.169.230/UpSkillService/UpSkillsService.svc/";
    String DomainName, Train_name,EmpId,OrgId,Train_obj;
    int kid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisition);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        EmpId = prefs.getString("OrgEmpID","");
        OrgId=prefs.getString("UserOrgID","");
        spinner=findViewById(R.id.spinnerDomain);
        spinner.setOnItemSelectedListener(this);
        spinner.setPrompt("Select A Domain");
        edtTxtTrainingname=findViewById(R.id.editText_training_name);
        editTextObjective=findViewById(R.id.editText_Objective);
        btnSend=findViewById(R.id.btnRequisition);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planreq();
            }
        });
        loadSpinnerdata();

    }
    private void planreq()
    {
        Train_name=edtTxtTrainingname.getText().toString().replaceAll(" ","_");
        Train_obj=editTextObjective.getText().toString().replaceAll(" ","_");
        if (Train_name.isEmpty()) {
            edtTxtTrainingname.setError("Required");
            edtTxtTrainingname.requestFocus();
            return;
        }
        if (Train_obj.isEmpty()) {
            editTextObjective.setError("Required");
            editTextObjective.requestFocus();
            return;
        }
        String urlnew=Baseurl+"AddPlanReq_app/"+Train_name+"/"+kid+"/"+Train_obj+"/"+EmpId+"/"+OrgId;
        Log.d("NEW URL",urlnew);

        Ion.with(getApplicationContext())
                .load(urlnew)
                .setBodyParameter("pTrainingName",Train_name)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Log.d("RESULT",result);
                    }
                });
    }

    private void loadSpinnerdata() {
        String url=Baseurl+"Get_SkillDomain";
        Ion.with(getApplicationContext())
                .load("GET",url)
                .setBodyParameter("","")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Log.d("Result",result);
                        try {
                            JSONObject obj = new JSONObject(result);
                            if(obj.isNull("GetSkillDomainResult"))
                            {
                                Log.d("if block","compiled");
                                Toast.makeText(getApplicationContext(), "No Skill Found", Toast.LENGTH_SHORT).show();
                            }
                            else if(!obj.equals(null)){

                                JSONArray jsonArray = obj.getJSONArray("GetSkillDomainResult");
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                  DomianModel model = new DomianModel();
                                    JSONObject skill= jsonArray.getJSONObject(i);
                                    model.setActStatus(skill.getString("ActStatus"));
                                    model.setDomainDesc(skill.getString("DomainDesc"));
                                    model.setDomainName(skill.getString("DomainName"));
                                    model.seteRROR(skill.get("ERROR"));
                                    model.setpKID(skill.getInt("PKID"));
                                    model.setRecStatus(skill.getString("RecStatus"));
                                    model.setSkillName(skill.getString("SkillName"));
                                    DomainName=skill.getString("DomainName");
                                    int pkid=skill.getInt("PKID");
                                    skillDomain.add(DomainName);
                                    skillid.put(DomainName,pkid);

                                    //domain.add(model);
                                }

                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item,skillDomain);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);
                        }
                        catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String label = parent.getItemAtPosition(position).toString();
        kid= skillid.get(label);
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " +label,
                Toast.LENGTH_LONG).show();
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
