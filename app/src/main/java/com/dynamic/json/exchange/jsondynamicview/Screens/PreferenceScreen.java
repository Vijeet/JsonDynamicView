package com.dynamic.json.exchange.jsondynamicview.Screens;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dynamic.json.exchange.jsondynamicview.Adapters.BuildViewObject;
import com.dynamic.json.exchange.jsondynamicview.Adapters.RecyclerViewAdapter;
import com.dynamic.json.exchange.jsondynamicview.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vijeet on 08-06-2018.
 */

public class PreferenceScreen extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference_screen);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setVerticalScrollBarEnabled(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        String content = null;
        try {
            content = readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<BuildViewObject> items = new ArrayList<>();
        JSONObject root = null;
        try {
            root = new JSONObject(content);
            JSONArray array= root.getJSONArray("views");
            for(int i=0;i<array.length();i++)
            {
                JSONObject object= array.getJSONObject(i);
                items.add(new BuildViewObject(object.getString("title"), object.getInt("titleType")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // specify an adapter (see also next example)
        mAdapter = new RecyclerViewAdapter(items, this.getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    public String readFile() throws IOException {
        InputStream is = getResources().openRawResource(R.raw.sample);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }
        return writer.toString();
    }

}
