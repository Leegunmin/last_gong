package nongsa.agoto.loginandregistration.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import nongsa.agoto.R;
import nongsa.agoto.loginandregistration.app.AppConfig;
import nongsa.agoto.loginandregistration.app.AppController;
import nongsa.agoto.loginandregistration.helper.SQLiteHandler;
import nongsa.agoto.loginandregistration.helper.SessionManager;

public class EvalWriteActivity extends Activity {
    private static final String TAG = EvalWriteActivity.class.getSimpleName();
    Button logout_eval_write;
    Button write_eval_write;
    TextView teacher_eval_write;
    TextView writer_eval_write;
    EditText review_eval_write;
    RatingBar rating_eval_write;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eval_write);

        // SqLite database handler
        db = new SQLiteHandler(Board.board);

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        logout_eval_write = (Button)findViewById(R.id.logout_eval_write);
        write_eval_write = (Button)findViewById(R.id.write_eval_write);

        teacher_eval_write = (TextView)findViewById(R.id.teacher_eval_write);
        writer_eval_write = (TextView)findViewById(R.id.writer_eval_write);
        review_eval_write = (EditText)findViewById(R.id.review_eval_write);

        rating_eval_write = (RatingBar)findViewById(R.id.rating_eval_write);

        Intent intent = getIntent();
        final String teacher = intent.getStringExtra("teacher");
        final String teacherName = intent.getStringExtra("teacherName");

        writer_eval_write.setText(db.getUserDetails().get("name").toString().trim());
        teacher_eval_write.setText(teacherName);

        write_eval_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float rating = rating_eval_write.getRating() * 2;
                int ratingTo = (int)rating;
                String ratingStr = Integer.toString(ratingTo);
                writeEval(teacher, teacherName, writer_eval_write.getText().toString().trim(), ratingStr, review_eval_write.getText().toString().trim());
                EvalActivity.evalActivity.finish();
                Intent intent = new Intent(getApplicationContext(), EvalActivity.class);
                intent.putExtra("email", teacher);
                startActivity(intent);
                finish();
            }
        });

        logout_eval_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    private void writeEval(final String teacher, final String teacherName, final String writer, final String star, final String review) {
        // Tag used to cancel the request
        String tag_string_req = "req_eval";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REVIEW_WRITE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Setting update Response: " + response.toString());
                System.out.println(response);


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("teacher", teacher);
                params.put("teacherName", teacherName);
                params.put("writer", writer);
                params.put("star", star);
                params.put("review", review);

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
        Intent intent = new Intent(EvalWriteActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

