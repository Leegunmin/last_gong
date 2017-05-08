package nongsa.agoto.loginandregistration.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nongsa.agoto.MaintoOther.billing.billingactivity;
import nongsa.agoto.R;
import nongsa.agoto.loginandregistration.activity.setting.ImgUpload;
import nongsa.agoto.loginandregistration.activity.Board;
import nongsa.agoto.loginandregistration.app.AppConfig;
import nongsa.agoto.loginandregistration.app.AppController;
import nongsa.agoto.loginandregistration.helper.SQLiteHandler;
import nongsa.agoto.loginandregistration.helper.SessionManager;

public class SettingActivity extends Activity {
    private static final String TAG = SettingActivity.class.getSimpleName();
    public static Activity setting;

    private Button btnLogout;
    private Button pay;
    String small_code;
    int division;
    private static ImageView img_setting;
    private EditText name_setting;
    private EditText phone_setting;
    private EditText exp_setting;
    private Spinner nation_setting;
    private Spinner subNation_setting;
    private EditText grow_setting;
    private EditText intro_setting;
    private Button revise_setting;
    private Button upload_setting;
    String emailTemp;
    private SQLiteHandler db;
    private SessionManager session;
    final ArrayList<String> EXT_SIDO_NAME = new ArrayList<String>();
    final ArrayList<String> EXT_SIDO_CODE = new ArrayList<String>();
    final ArrayList<String> EXT_GUNGGI_NAME = new ArrayList<String>();
    final ArrayList<String> EXT_GUNGGI_CODE = new ArrayList<String>();
    final ArrayList<String> EXT_GANGWON_NAME = new ArrayList<String>();
    final ArrayList<String> EXT_GANGWON_CODE = new ArrayList<String>();
    final ArrayList<String> EXT_CHUNGBOOK_NAME = new ArrayList<String>();
    final ArrayList<String> EXT_CHUNGBOOK_CODE = new ArrayList<String>();
    final ArrayList<String> EXT_CHUNGNAM_NAME = new ArrayList<String>();
    final ArrayList<String> EXT_CHUNGNAM_CODE = new ArrayList<String>();
    final ArrayList<String> EXT_JUNNAM_NAME = new ArrayList<String>();
    final ArrayList<String> EXT_JUNNAM_CODE = new ArrayList<String>();
    final ArrayList<String> EXT_JUNBOOK_NAME = new ArrayList<String>();
    final ArrayList<String> EXT_JUNBOOK_CODE = new ArrayList<String>();
    final ArrayList<String> EXT_GUNNAM_NAME = new ArrayList<String>();
    final ArrayList<String> EXT_GUNNAM_CODE = new ArrayList<String>();
    final ArrayList<String> EXT_GUNBOOK_NAME = new ArrayList<String>();
    final ArrayList<String> EXT_GUNBOOK_CODE = new ArrayList<String>();
    final ArrayList<String> EXT_JEJU_NAME = new ArrayList<String>();
    final ArrayList<String> EXT_JEJU_CODE = new ArrayList<String>();
    int count2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Init();
        setting = SettingActivity.this;

        btnLogout = (Button) findViewById(R.id.logout_setting);
        pay = (Button) findViewById(R.id.pay_setting);
        img_setting = (ImageView)findViewById(R.id.img_setting);
        name_setting = (EditText)findViewById(R.id.name_setting);
        phone_setting = (EditText)findViewById(R.id.phone_setting);
        exp_setting = (EditText)findViewById(R.id.exp_setting);
        nation_setting = (Spinner)findViewById(R.id.nation_setting);
        subNation_setting = (Spinner)findViewById(R.id.subNation_setting);
        grow_setting = (EditText)findViewById(R.id.grow_setting);
        intro_setting = (EditText)findViewById(R.id.intro_setting);
        revise_setting = (Button)findViewById(R.id.revise_setting);
        upload_setting = (Button)findViewById(R.id.upload_setting);



        // SqLite database handler
        db = new SQLiteHandler(Board.board);

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        setImg();


        ArrayAdapter<String> sAdapter = new ArrayAdapter<String>(SettingActivity.this, R.layout.simple_spinner_item_custom_setting, EXT_SIDO_NAME);
        sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom_setting);
        nation_setting.setAdapter(sAdapter);

        int count = 0;
        for (int i = 0; i < EXT_SIDO_NAME.size(); i++) {
            if (EXT_SIDO_NAME.get(i).equals(db.getUserDetails().get("subNationA"))) {
                count = i;
            }
        }

        count2= 0;
        System.out.println("hihihi "+db.getUserDetails().get("subNationB"));
        for (int j = 0; j < EXT_GUNGGI_NAME.size(); j++) {
            if (EXT_GUNGGI_NAME.get(j).equals(db.getUserDetails().get("subNationB"))) {
                count2 = j;
                System.out.println("hihihi here1 "+j);
            }
        }

        for (int j = 0; j < EXT_CHUNGBOOK_NAME.size(); j++) {
            if (EXT_CHUNGBOOK_NAME.get(j).equals(db.getUserDetails().get("subNationB"))) {
                count2 = j;
                System.out.println("hihihi here2 "+j);
            }
        }

        for (int j = 0; j < EXT_CHUNGNAM_NAME.size(); j++) {
            if (EXT_CHUNGNAM_NAME.get(j).equals(db.getUserDetails().get("subNationB"))) {
                count2 = j;
                System.out.println("hihihi here3 "+j);
            }
        }

        for (int j = 0; j < EXT_GUNBOOK_NAME.size(); j++) {
            if (EXT_GUNBOOK_NAME.get(j).equals(db.getUserDetails().get("subNationB"))) {
                count2 = j;
                System.out.println("hihihi here4 "+j);
            }
        }

        for (int j = 0; j < EXT_GUNNAM_NAME.size(); j++) {
            if (EXT_GUNNAM_NAME.get(j).equals(db.getUserDetails().get("subNationB"))) {
                count2 = j;
                System.out.println("hihihi here5 "+j);
            }
        }

        for (int j = 0; j <EXT_CHUNGBOOK_NAME.size(); j++) {
            if (EXT_CHUNGBOOK_NAME.get(j).equals(db.getUserDetails().get("subNationB"))) {
                count2 = j;
                System.out.println("hihihi here6 "+j);
            }
        }

        for (int j = 0; j < EXT_GANGWON_NAME.size(); j++) {
            if (EXT_GANGWON_NAME.get(j).equals(db.getUserDetails().get("subNationB"))) {
                count2 = j;
                System.out.println("hihihi here7 "+j);
            }
        }

        for (int j = 0; j < EXT_GUNNAM_NAME.size(); j++) {
            if (EXT_GUNNAM_NAME.get(j).equals(db.getUserDetails().get("subNationB"))) {
                count2 = j;
                System.out.println("hihihi here8 "+j);
            }
        }

        for (int j = 0; j < EXT_GUNBOOK_NAME.size(); j++) {
            if (EXT_GUNBOOK_NAME.get(j).equals(db.getUserDetails().get("subNationB"))) {
                count2 = j;
                System.out.println("hihihi here9 "+j);
            }
        }



        nation_setting.setSelection(count);


        name_setting.setText(db.getUserDetails().get("name"));
        phone_setting.setText(db.getUserDetails().get("phone"));
        exp_setting.setText(db.getUserDetails().get("exp"));
        grow_setting.setText(db.getUserDetails().get("grow"));
        intro_setting.setText(db.getUserDetails().get("intro"));
        // Logout button click event


        // Logout button click event
        nation_setting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                if(EXT_SIDO_NAME.get(position).equals("경기도")){
                    ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(SettingActivity.this,R.layout.simple_spinner_item_custom_setting,EXT_GUNGGI_NAME);
                    sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom_setting);
                    division = 1;
                    subNation_setting.setAdapter(sAdapter);
                    subNation_setting.setSelection(count2);
                }
                else if(EXT_SIDO_NAME.get(position).equals("강원도")){
                    division = 2;
                    ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(SettingActivity.this,R.layout.simple_spinner_item_custom_setting,EXT_GANGWON_NAME);
                    sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom_setting);
                    subNation_setting.setAdapter(sAdapter);
                    subNation_setting.setSelection(count2);
                }
                else if(EXT_SIDO_NAME.get(position).equals("충청북도")){
                    division = 3;
                    ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(SettingActivity.this,R.layout.simple_spinner_item_custom_setting,EXT_CHUNGBOOK_NAME);
                    sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom_setting);
                    subNation_setting.setAdapter(sAdapter);
                    subNation_setting.setSelection(count2);
                }
                else if(EXT_SIDO_NAME.get(position).equals("충청남도")){
                    division = 4;
                    ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(SettingActivity.this,R.layout.simple_spinner_item_custom_setting,EXT_CHUNGNAM_NAME);
                    sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom_setting);
                    subNation_setting.setAdapter(sAdapter);
                    subNation_setting.setSelection(count2);
                }
                else if(EXT_SIDO_NAME.get(position).equals("전라북도")){
                    division = 5;
                    ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(SettingActivity.this,R.layout.simple_spinner_item_custom_setting,EXT_JUNBOOK_NAME);
                    sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom_setting);
                    subNation_setting.setAdapter(sAdapter);
                    subNation_setting.setSelection(count2);
                }
                else if(EXT_SIDO_NAME.get(position).equals("전라남도")){
                    division = 6;
                    ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(SettingActivity.this,R.layout.simple_spinner_item_custom_setting,EXT_JUNNAM_NAME);
                    sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom_setting);
                    subNation_setting.setAdapter(sAdapter);
                    subNation_setting.setSelection(count2);
                }
                else if(EXT_SIDO_NAME.get(position).equals("경상북도")){
                    division = 7;
                    ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(SettingActivity.this,R.layout.simple_spinner_item_custom_setting,EXT_GUNBOOK_NAME);
                    sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom_setting);
                    subNation_setting.setAdapter(sAdapter);
                    subNation_setting.setSelection(count2);
                }
                else if(EXT_SIDO_NAME.get(position).equals("경상남도")){
                    division = 8;
                    ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(SettingActivity.this,R.layout.simple_spinner_item_custom_setting,EXT_GUNNAM_NAME);
                    sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom_setting);
                    subNation_setting.setAdapter(sAdapter);
                    subNation_setting.setSelection(count2);
                }
                else if(EXT_SIDO_NAME.get(position).equals("제주도")){
                    division = 9;
                    ArrayAdapter<String> sAdapter= new ArrayAdapter<String>(SettingActivity.this,R.layout.simple_spinner_item_custom_setting,EXT_JEJU_NAME);
                    sAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom_setting);
                    subNation_setting.setAdapter(sAdapter);
                    subNation_setting.setSelection(count2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        subNation_setting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                if(division == 1){
                    small_code= EXT_GUNGGI_CODE.get(position);
                }
                else if(division == 2){
                    small_code = EXT_GANGWON_CODE.get(position);
                }
                else if(division ==3){
                    small_code = EXT_CHUNGBOOK_CODE.get(position);
                }
                else if(division == 4){
                    small_code = EXT_CHUNGNAM_CODE.get(position);
                }
                else if(division ==5){
                    small_code = EXT_JUNBOOK_CODE.get(position);
                }
                else if(division == 6){
                    small_code = EXT_JUNNAM_CODE.get(position);
                }
                else if(division ==7){
                    small_code = EXT_GUNBOOK_CODE.get(position);
                }
                else if(division == 8){
                    small_code = EXT_GUNNAM_CODE.get(position);
                }
                else if(division ==3){
                    small_code = EXT_CHUNGBOOK_CODE.get(position);
                }
                else if(division ==9){
                    small_code = EXT_JEJU_CODE.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), billingactivity.class);
                startActivity(intent);

            }
        });
        revise_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("why not : "+db.getUserDetails().get("email"));
                 emailTemp = db.getUserDetails().get("email").toString().trim();
                updateInfo(emailTemp, name_setting.getText().toString().trim(), phone_setting.getText().toString().trim(), exp_setting.getText().toString().trim(), grow_setting.getText().toString().trim(), intro_setting.getText().toString().trim(),
                        nation_setting.getSelectedItem().toString().trim(), subNation_setting.getSelectedItem().toString().trim());


            }
        });
        upload_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ImgUpload.class);
                startActivity(intent);
            }
        });



    }

    private void reLogin(final String email) {
        // Tag used to cancel the request
        System.out.println("hohoho1");
        String tag_string_req = "req_login";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_RELOAD, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println("hohoho9");
                System.out.println("hohoho9"+ response.toString());
                Log.d(TAG, "Login Response: " + response.toString());
                System.out.println(response);

                try {
                    System.out.println("hohoho3");
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        int exp = user.getInt("exp");
                        String nation = user.getString("nation");
                        String grow = user.getString("grow");
                        String phone = user.getString("phone");

                        int auth = user.getInt("auth");
                        String intro = user.getString("intro");

                        String subNationA = user.getString("subNationA");
                        String subNationB = user.getString("subNationB");
                        System.out.println("hohoho "+ exp);
                        System.out.println("hohoho "+ auth);
                        System.out.println(name);
                        System.out.println(email);
                        System.out.println(phone);

                        // Inserting row in users table
                        db.addUser(name, email, uid, exp, nation, grow, phone, auth, intro,subNationA ,subNationB);

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                "gunmingunmingjun1"+errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "gunmingunmingjun0 Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                Board.board.finish();//모름모름
                Intent intent = new Intent(getApplicationContext(), Board.class);
                startActivity(intent);
                finish();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "gunmingunmingjun2" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void updateInfo(final String email, final String name, final String phone, final String exp, final String grow, final String intro
            ,final String subNationA, final String subNationB) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPDATE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Setting update Response: " + response.toString());
                logoutUserB();
                System.out.println("hohoho2");
                reLogin(emailTemp);



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "gunmingunmingjun3" +error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("name", name);
                params.put("phone", phone);
                params.put("exp", exp);
                params.put("grow", grow);
                params.put("intro", intro);
                params.put("subNationA", nation_setting.getSelectedItem().toString());
                params.put("subNationB", subNation_setting.getSelectedItem().toString());
                params.put("nation", subNationA + " " + subNationB);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Board.board.finish();
        Intent intent = new Intent(getApplicationContext(), Board.class);
        startActivity(intent);
        finish();

    }


    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void logoutUserB() {
        session.setLogin(false);

        db.deleteUsers();
    }

    private void setImg(){
        final Handler handler = new Handler();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {    // 오래 거릴 작업을 구현한다
                // TODO Auto-generated method stub
                try{
                    // 걍 외우는게 좋다 -_-;
                    URL url = new URL("http://52.79.61.227/file/uploads/" + db.getUserDetails().get("email") + ".jpg");
                    InputStream is = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    handler.post(new Runnable() {

                        @Override
                        public void run() {  // 화면에 그려줄 작업
                            img_setting.setImageBitmap(bm);
                        }
                    });
                } catch(Exception e){

                }

            }
        });
        t.start();
    }

    public void Init(){
        EXT_SIDO_NAME.add("경기도"); EXT_SIDO_CODE.add("6410000");EXT_SIDO_NAME.add("강원도");EXT_SIDO_CODE.add("6420000");EXT_SIDO_NAME.add("충청북도");
        EXT_SIDO_CODE.add("6430000");EXT_SIDO_NAME.add("충청남도");EXT_SIDO_CODE.add("6440000");EXT_SIDO_NAME.add("전라북도");EXT_SIDO_CODE.add("6450000");EXT_SIDO_NAME.add("전라남도");
        EXT_SIDO_CODE.add("6460000");EXT_SIDO_NAME.add("경상북도");EXT_SIDO_CODE.add("6470000");EXT_SIDO_NAME.add("경상남도");EXT_SIDO_CODE.add("6480000");
        EXT_SIDO_NAME.add("제주도");EXT_SIDO_CODE.add("6500000");
        //////////////

        EXT_JEJU_NAME.add("제주시");EXT_JEJU_CODE.add("5490000");EXT_JEJU_NAME.add("서귀포시");EXT_JEJU_CODE.add("5500000");

        EXT_GUNNAM_NAME.add("거제시");
        EXT_GUNNAM_CODE.add("5370000");
        EXT_GUNNAM_NAME.add("통영시");
        EXT_GUNNAM_CODE.add("5330000");
        EXT_GUNNAM_NAME.add("고성군");
        EXT_GUNNAM_CODE.add("5420000");
        EXT_GUNNAM_NAME.add("남해시");
        EXT_GUNNAM_CODE.add("5430000");
        EXT_GUNNAM_NAME.add("사천시");
        EXT_GUNNAM_CODE.add("5340000");
        EXT_GUNNAM_NAME.add("하동군");
        EXT_GUNNAM_CODE.add("5440000");
        EXT_GUNNAM_NAME.add("산청군");
        EXT_GUNNAM_CODE.add("5450000");
        EXT_GUNNAM_NAME.add("함양군");
        EXT_GUNNAM_CODE.add("5460000");
        EXT_GUNNAM_NAME.add("거창군");
        EXT_GUNNAM_CODE.add("5470000");
        EXT_GUNNAM_NAME.add("진주시");
        EXT_GUNNAM_CODE.add("5310000");
        EXT_GUNNAM_NAME.add("마산시");
        EXT_GUNNAM_CODE.add("5280000");
        EXT_GUNNAM_NAME.add("진해시");
        EXT_GUNNAM_CODE.add("5320000");
        EXT_GUNNAM_NAME.add("합천군");
        EXT_GUNNAM_CODE.add("5480000");
        EXT_GUNNAM_NAME.add("의령군");
        EXT_GUNNAM_CODE.add("5390000");
        EXT_GUNNAM_NAME.add("함안군");
        EXT_GUNNAM_CODE.add("5400000");
        EXT_GUNNAM_NAME.add("창원시");
        EXT_GUNNAM_CODE.add("5670000");
        EXT_GUNNAM_NAME.add("김해시");
        EXT_GUNNAM_CODE.add("5350000");
        EXT_GUNNAM_NAME.add("창녕군");
        EXT_GUNNAM_CODE.add("5410000");
        EXT_GUNNAM_NAME.add("밀양시");
        EXT_GUNNAM_CODE.add("5360000");
        EXT_GUNNAM_NAME.add("양산시");
        EXT_GUNNAM_CODE.add("5380000");
///////지금까지 경상남도

        EXT_GUNBOOK_NAME.add("청도군");
        EXT_GUNBOOK_CODE.add("5190000");
        EXT_GUNBOOK_NAME.add("고령군");
        EXT_GUNBOOK_CODE.add("5200000");
        EXT_GUNBOOK_NAME.add("성주군");
        EXT_GUNBOOK_CODE.add("5210000");
        EXT_GUNBOOK_NAME.add("칠곡군");
        EXT_GUNBOOK_CODE.add("5220000");
        EXT_GUNBOOK_NAME.add("김천시");
        EXT_GUNBOOK_CODE.add("5060000");
        EXT_GUNBOOK_NAME.add("경산시");
        EXT_GUNBOOK_CODE.add("5130000");
        EXT_GUNBOOK_NAME.add("경주시");
        EXT_GUNBOOK_CODE.add("5050000");
        EXT_GUNBOOK_NAME.add("영천시");
        EXT_GUNBOOK_CODE.add("5100000");
        EXT_GUNBOOK_NAME.add("구미시");
        EXT_GUNBOOK_CODE.add("5080000");
        EXT_GUNBOOK_NAME.add("군위군");
        EXT_GUNBOOK_CODE.add("5140000");
        EXT_GUNBOOK_NAME.add("포항시");
        EXT_GUNBOOK_CODE.add("5020000");
        EXT_GUNBOOK_NAME.add("상주시");
        EXT_GUNBOOK_CODE.add("5110000");
        EXT_GUNBOOK_NAME.add("의성군");
        EXT_GUNBOOK_CODE.add("5150000");
        EXT_GUNBOOK_NAME.add("청송군");
        EXT_GUNBOOK_CODE.add("5160000");
        EXT_GUNBOOK_NAME.add("영덕군");
        EXT_GUNBOOK_CODE.add("5160000");
        EXT_GUNBOOK_NAME.add("문경시");
        EXT_GUNBOOK_CODE.add("5120000");
        EXT_GUNBOOK_NAME.add("예천군");
        EXT_GUNBOOK_CODE.add("5230000");
        EXT_GUNBOOK_NAME.add("안동시");
        EXT_GUNBOOK_CODE.add("5070000");
        EXT_GUNBOOK_NAME.add("영양군");
        EXT_GUNBOOK_CODE.add("5170000");
        EXT_GUNBOOK_NAME.add("울진군");
        EXT_GUNBOOK_CODE.add("5250000");
        EXT_GUNBOOK_NAME.add("봉화군");
        EXT_GUNBOOK_CODE.add("5240000");
        EXT_GUNBOOK_NAME.add("영주시");
        EXT_GUNBOOK_CODE.add("5090000");
        EXT_GUNBOOK_NAME.add("울릉군");
        EXT_GUNBOOK_CODE.add("5260000");

        /////지금까지 경상북도

        EXT_JUNNAM_NAME.add("진도군");
        EXT_JUNNAM_CODE.add("5000000");
        EXT_JUNNAM_NAME.add("해남군");
        EXT_JUNNAM_CODE.add("4930000");
        EXT_JUNNAM_NAME.add("강진군");
        EXT_JUNNAM_CODE.add("4920000");
        EXT_JUNNAM_NAME.add("신안군");
        EXT_JUNNAM_CODE.add("5010000");
        EXT_JUNNAM_NAME.add("목포시");
        EXT_JUNNAM_CODE.add("4800000");
        EXT_JUNNAM_NAME.add("영암군");
        EXT_JUNNAM_CODE.add("4940000");
        EXT_JUNNAM_NAME.add("장흥군");
        EXT_JUNNAM_CODE.add("4910000");
        EXT_JUNNAM_NAME.add("고흥군");
        EXT_JUNNAM_CODE.add("4880000");
        EXT_JUNNAM_NAME.add("보성군");
        EXT_JUNNAM_CODE.add("4890000");
        EXT_JUNNAM_NAME.add("무안군");
        EXT_JUNNAM_CODE.add("4950000");
        EXT_JUNNAM_NAME.add("나주시");
        EXT_JUNNAM_CODE.add("4830000");
        EXT_JUNNAM_NAME.add("화순군");
        EXT_JUNNAM_CODE.add("4900000");
        EXT_JUNNAM_NAME.add("순천시");
        EXT_JUNNAM_CODE.add("4820000");
        EXT_JUNNAM_NAME.add("여수시");
        EXT_JUNNAM_CODE.add("4810000");
        EXT_JUNNAM_NAME.add("광양시");
        EXT_JUNNAM_CODE.add("4840000");
        EXT_JUNNAM_NAME.add("구례군");
        EXT_JUNNAM_CODE.add("4870000");
        EXT_JUNNAM_NAME.add("곡성군");
        EXT_JUNNAM_CODE.add("4860000");
        EXT_JUNNAM_NAME.add("담양군");
        EXT_JUNNAM_CODE.add("4850000");
        EXT_JUNNAM_NAME.add("장성군");
        EXT_JUNNAM_CODE.add("4980000");
        EXT_JUNNAM_NAME.add("영광군");
        EXT_JUNNAM_CODE.add("4970000");
        EXT_JUNNAM_NAME.add("함평군");
        EXT_JUNNAM_CODE.add("4960000");

        ///전라남도


        EXT_JUNBOOK_NAME.add("고창군");
        EXT_JUNBOOK_CODE.add("4780000");
        EXT_JUNBOOK_NAME.add("정읍시");
        EXT_JUNBOOK_CODE.add("4690000");
        EXT_JUNBOOK_NAME.add("순창군");
        EXT_JUNBOOK_CODE.add("4770000");
        EXT_JUNBOOK_NAME.add("남원시");
        EXT_JUNBOOK_CODE.add("4700000");
        EXT_JUNBOOK_NAME.add("임실군");
        EXT_JUNBOOK_CODE.add("4760000");
        EXT_JUNBOOK_NAME.add("부안군");
        EXT_JUNBOOK_CODE.add("4790000");
        EXT_JUNBOOK_NAME.add("김제시");
        EXT_JUNBOOK_CODE.add("4710000");
        EXT_JUNBOOK_NAME.add("전주시");
        EXT_JUNBOOK_CODE.add("4640000");
        EXT_JUNBOOK_NAME.add("장수군");
        EXT_JUNBOOK_CODE.add("4750000");
        EXT_JUNBOOK_NAME.add("진안군");
        EXT_JUNBOOK_CODE.add("4730000");
        EXT_JUNBOOK_NAME.add("무주군");
        EXT_JUNBOOK_CODE.add("4740000");
        EXT_JUNBOOK_NAME.add("완주군");
        EXT_JUNBOOK_CODE.add("4720000");
        EXT_JUNBOOK_NAME.add("익산시");
        EXT_JUNBOOK_CODE.add("4680000");
        EXT_JUNBOOK_NAME.add("군산시");
        EXT_JUNBOOK_CODE.add("4670000");

        /////지금까지 전라북도


        EXT_CHUNGNAM_NAME.add("금산군");
        EXT_CHUNGNAM_CODE.add("4550000");
        EXT_CHUNGNAM_NAME.add("논산시");
        EXT_CHUNGNAM_CODE.add("4540000");
        EXT_CHUNGNAM_NAME.add("부여군");
        EXT_CHUNGNAM_CODE.add("4570000");
        EXT_CHUNGNAM_NAME.add("서천군");
        EXT_CHUNGNAM_CODE.add("4580000");
        EXT_CHUNGNAM_NAME.add("보령시");
        EXT_CHUNGNAM_CODE.add("4510000");
        EXT_CHUNGNAM_NAME.add("청양군");
        EXT_CHUNGNAM_CODE.add("4590000");
        EXT_CHUNGNAM_NAME.add("공주시");
        EXT_CHUNGNAM_CODE.add("4500000");
        EXT_CHUNGNAM_NAME.add("예산군");
        EXT_CHUNGNAM_CODE.add("4610000");
        EXT_CHUNGNAM_NAME.add("홍성군");
        EXT_CHUNGNAM_CODE.add("4600000");
        EXT_CHUNGNAM_NAME.add("서산시");
        EXT_CHUNGNAM_CODE.add("4530000");
        EXT_CHUNGNAM_NAME.add("태안군");
        EXT_CHUNGNAM_CODE.add("4620000");
        EXT_CHUNGNAM_NAME.add("당진시");
        EXT_CHUNGNAM_CODE.add("5680000");
        EXT_CHUNGNAM_NAME.add("아산시");
        EXT_CHUNGNAM_CODE.add("4520000");
        EXT_CHUNGNAM_NAME.add("천안시");
        EXT_CHUNGNAM_CODE.add("4490000");

        ///여기 까지 충천남도


        EXT_CHUNGBOOK_NAME.add("영동군");
        EXT_CHUNGBOOK_CODE.add("4440000");
        EXT_CHUNGBOOK_NAME.add("옥천군");
        EXT_CHUNGBOOK_CODE.add("4430000");
        EXT_CHUNGBOOK_NAME.add("보은군");
        EXT_CHUNGBOOK_CODE.add("4420000");
        EXT_CHUNGBOOK_NAME.add("청원군");
        EXT_CHUNGBOOK_CODE.add("4410000");
        EXT_CHUNGBOOK_NAME.add("청주시");
        EXT_CHUNGBOOK_CODE.add("4360000");
        EXT_CHUNGBOOK_NAME.add("괴산군");
        EXT_CHUNGBOOK_CODE.add("4460000");
        EXT_CHUNGBOOK_NAME.add("증평군");
        EXT_CHUNGBOOK_CODE.add("5570000");
        EXT_CHUNGBOOK_NAME.add("진천군");
        EXT_CHUNGBOOK_CODE.add("4450000");
        EXT_CHUNGBOOK_NAME.add("음성군");
        EXT_CHUNGBOOK_CODE.add("4470000");
        EXT_CHUNGBOOK_NAME.add("충주시");
        EXT_CHUNGBOOK_CODE.add("4390000");
        EXT_CHUNGBOOK_NAME.add("제천시");
        EXT_CHUNGBOOK_CODE.add("4400000");
        EXT_CHUNGBOOK_NAME.add("단양군");
        EXT_CHUNGBOOK_CODE.add("4480000");

        //충청북도

        EXT_GANGWON_NAME.add("철원군");
        EXT_GANGWON_CODE.add("4300000");
        EXT_GANGWON_NAME.add("화천군");
        EXT_GANGWON_CODE.add("4310000");
        EXT_GANGWON_NAME.add("양구군");
        EXT_GANGWON_CODE.add("4320000");
        EXT_GANGWON_NAME.add("고성군");
        EXT_GANGWON_CODE.add("4340000");
        EXT_GANGWON_NAME.add("춘천시");
        EXT_GANGWON_CODE.add("4180000");
        EXT_GANGWON_NAME.add("인제군");
        EXT_GANGWON_CODE.add("4330000");
        EXT_GANGWON_NAME.add("속초시");
        EXT_GANGWON_CODE.add("4230000");
        EXT_GANGWON_NAME.add("홍청군");
        EXT_GANGWON_CODE.add("4250000");
        EXT_GANGWON_NAME.add("양양군");
        EXT_GANGWON_CODE.add("4350000");
        EXT_GANGWON_NAME.add("횡성군");
        EXT_GANGWON_CODE.add("4260000");
        EXT_GANGWON_NAME.add("평창군");
        EXT_GANGWON_CODE.add("4280000");
        EXT_GANGWON_NAME.add("강릉시");
        EXT_GANGWON_CODE.add("4200000");
        EXT_GANGWON_NAME.add("원주시");
        EXT_GANGWON_CODE.add("4190000");
        EXT_GANGWON_NAME.add("영월군");
        EXT_GANGWON_CODE.add("4270000");
        EXT_GANGWON_NAME.add("정선군");
        EXT_GANGWON_CODE.add("4290000");
        EXT_GANGWON_NAME.add("동해시");
        EXT_GANGWON_CODE.add("4210000");
        EXT_GANGWON_NAME.add("삼척시");
        EXT_GANGWON_CODE.add("4240000");
        EXT_GANGWON_NAME.add("태백시");
        EXT_GANGWON_CODE.add("4220000");

        ///강원도

        EXT_GUNGGI_NAME.add("안성시");
        EXT_GUNGGI_CODE.add("4080000");
        EXT_GUNGGI_NAME.add("오산시");
        EXT_GUNGGI_CODE.add("4000000");
        EXT_GUNGGI_NAME.add("이천시");
        EXT_GUNGGI_CODE.add("4070000");
        EXT_GUNGGI_NAME.add("수원시");
        EXT_GUNGGI_CODE.add("3740000");
        EXT_GUNGGI_NAME.add("광주시");
        EXT_GUNGGI_CODE.add("5540000");
        EXT_GUNGGI_NAME.add("의왕시");
        EXT_GUNGGI_CODE.add("4030000");
        EXT_GUNGGI_NAME.add("성남시");
        EXT_GUNGGI_CODE.add("3780000");
        EXT_GUNGGI_NAME.add("군포시");
        EXT_GUNGGI_CODE.add("4020000");
        EXT_GUNGGI_NAME.add("안산시");
        EXT_GUNGGI_CODE.add("3930000");
        EXT_GUNGGI_NAME.add("시흥시");
        EXT_GUNGGI_CODE.add("4010000");
        EXT_GUNGGI_NAME.add("안양시");
        EXT_GUNGGI_CODE.add("3830000");
        EXT_GUNGGI_NAME.add("부천시");
        EXT_GUNGGI_CODE.add("3860000");
        EXT_GUNGGI_NAME.add("광명시");
        EXT_GUNGGI_CODE.add("3900000");
        EXT_GUNGGI_NAME.add("과천시");
        EXT_GUNGGI_CODE.add("3970000");
        EXT_GUNGGI_NAME.add("하남시");
        EXT_GUNGGI_CODE.add("4040000");
        EXT_GUNGGI_NAME.add("구리시");
        EXT_GUNGGI_CODE.add("3980000");
        EXT_GUNGGI_NAME.add("의정부시");
        EXT_GUNGGI_CODE.add("3820000");
        EXT_GUNGGI_NAME.add("고양시");
        EXT_GUNGGI_CODE.add("3940000");
        EXT_GUNGGI_NAME.add("김포시");
        EXT_GUNGGI_CODE.add("4090000");
        EXT_GUNGGI_NAME.add("양주시");
        EXT_GUNGGI_CODE.add("5590000");
        EXT_GUNGGI_NAME.add("동두천시");
        EXT_GUNGGI_CODE.add("3920000");
        EXT_GUNGGI_NAME.add("포천시");
        EXT_GUNGGI_CODE.add("5600000");

    }


}
