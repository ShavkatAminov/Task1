package ru.startandroid.task1.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.JsonReader;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import ru.startandroid.task1.R;
import ru.startandroid.task1.adapter.PhotolistAdapter;
import ru.startandroid.task1.http.HttpHandler;
import ru.startandroid.task1.model.Photo;

public class MainActivity extends AppCompatActivity {

    private static LayoutInflater inflate;
    private static LinearLayout parentLayout;
    private int dp;
    private RecyclerView recyclerView;
    public static List<Photo> list = new ArrayList<>();
    private PhotolistAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inflate = getLayoutInflater();
        parentLayout = (LinearLayout) findViewById(R.id.layout_list_photo);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        parentLayout.setLayoutParams(params);
        params.gravity = Gravity.TOP;
        initRecyclerview();
        getlistphoto();
    }
    private void  initRecyclerview() {
        recyclerView = (RecyclerView) findViewById(R.id.list_item_photo);
        int d = 2;
        TelephonyManager manager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        if(manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE)
            d = 3;
        int pixel = this.getWindowManager().getDefaultDisplay().getWidth();
        dp = pixel/(int)getResources().getDisplayMetrics().density;
        dp /= d;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, d);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
    }
    public void getlistphoto() {
        GetTask getTask = new GetTask();
        getTask.execute();
    }
    class GetTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            HttpHandler handler = new HttpHandler();
            String jsonstr = handler.ReadHttpResponse("http://devcandidates.alef.im/list.php");
            if (jsonstr != null) {
                String jsonobjects = "";
                for(int i = 2; i < jsonstr.length(); i ++) {
                    char t = jsonstr.charAt(i);
                    if(t == '"') {
                        Photo photo = new Photo();
                        photo.setUrl(jsonobjects);
                        list.add(photo);
                        i += 2;
                        Log.d("mylog", jsonobjects);
                        jsonobjects = "";
                    }
                    else {
                        jsonobjects += t;
                    }
                }
            }
            if (list == null)
                return false;
            return true;
        }
        @Override
        protected void onPostExecute(Boolean ok) {
            super.onPostExecute(ok);
            if (!ok) {
                Toast.makeText(MainActivity.this, "Отсутствует интернет соединение", Toast.LENGTH_LONG).show();
            }
            else {
                adapter = new PhotolistAdapter(MainActivity.this, list, dp);
                recyclerView.setAdapter(adapter);
            }
        }
    }
}
