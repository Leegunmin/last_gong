package nongsa.agoto.loginandregistration.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nongsa.agoto.R;
import nongsa.agoto.loginandregistration.app.AppConfig;
import nongsa.agoto.loginandregistration.app.AppController;
import nongsa.agoto.loginandregistration.helper.SQLiteHandler;
import nongsa.agoto.loginandregistration.helper.SessionManager;

public class EvalActivity extends Activity {
    public static Activity evalActivity;
    TextView title_eval;
    Button btnLogout_eval;
    Button write_eval;
    ListView list_eval;

    ArrayList<MemberData_eval> datas= new ArrayList<MemberData_eval>();

    private static final String TAG = EvalActivity.class.getSimpleName();

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eval);

        evalActivity = EvalActivity.this;

        btnLogout_eval = (Button) findViewById(R.id.btnLogout_eval);
        write_eval = (Button)findViewById(R.id.write_eval);
        title_eval = (TextView)findViewById(R.id.title_eval);

        Intent intent = getIntent();
        final String teacher = intent.getStringExtra("email");
        final String teacherName = intent.getStringExtra("name");


        // SqLite database handler
        db = new SQLiteHandler(Board.board);

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }


        // Logout button click event
        btnLogout_eval.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        write_eval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EvalWriteActivity.class);
                intent.putExtra("teacher", teacher);
                intent.putExtra("teacherName",teacherName);
                startActivity(intent);
            }
        });
        //선생(히든)  게시자  (레이팅)별점 날짜 후기


        getContent(teacher, teacherName);


        list_eval = (ListView)findViewById(R.id.list_eval);

    }



    private void getContent(final String teacher, final String teacherName) {
        // Tag used to cancel the request
        String tag_string_req = "req_get_content";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REVIEW_READ, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Get Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray jArr = jObj.getJSONArray("result");


                    System.out.println(jArr);
                    System.out.println(jObj);

                    for (int i=0; i<jArr .length(); i++) {
                        boolean error = jArr.getJSONObject(i).getBoolean("error");

                        // Check for error node in json
                        if (!error) {

                            // Now store the user in SQLite
                            int id = jArr.getJSONObject(i).getInt("id");

                            String teacher = jArr.getJSONObject(i).getString("teacher");
                            String teacherName = jArr.getJSONObject(i).getString("teacherName");
                            String writer = jArr.getJSONObject(i).getString("writer");
                            int star = jArr.getJSONObject(i).getInt("star");
                            String dat = jArr.getJSONObject(i).getString("dat");
                            String review = jArr.getJSONObject(i).getString("review");

                            datas.add( new MemberData_eval(teacherName, writer, star, dat, review));


                        } else {
                            // Error in login. Get the error message
                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(getApplicationContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }
                    }

                    //AdapterView의 일종인 ListView에 적용할 Adapter 객체 생성
                    //MemberData 객체의 정보들(이름, 국적, 이미지)를 적절하게 보여줄 뷰로 만들어주는 Adapter클래스 객체생성
                    //이 예제에서는 MemberDataAdapter.java 파일로 클래스를 설계하였음.
                    //첫번재 파라미터로 xml 레이아웃 파일을 객체로 만들어 주는 LayoutInflater 객체 얻어와서 전달..
                    //두번째 파라미터는 우리가 나열한 Data 배열..
                    MemberDataAdapter_eval adapter= new MemberDataAdapter_eval( getLayoutInflater() , datas);
                    //위에 만든 Adapter 객체를 AdapterView의 일종인 ListView에 설정.
                    adapter.notifyDataSetChanged();
                    list_eval.setAdapter(adapter);
                    //list_eval.setOnItemClickListener(itemClickListenerOfLanguageList);


                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Board Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("teacher", teacher);
                params.put("teacherName", teacherName);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void logoutUser() {
        session.setLogin(false);


        db.deleteUsers();

        // Launching the login activity
        Board.board.finish();
        Intent intent = new Intent(EvalActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
