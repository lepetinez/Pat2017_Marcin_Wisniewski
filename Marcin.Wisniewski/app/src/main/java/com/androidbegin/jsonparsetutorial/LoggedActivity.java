package com.androidbegin.jsonparsetutorial;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LoggedActivity extends Activity {
    public final static String BASE_SERVER_URL = "http://192.168.0.129:8080";
    static final String TITLE = "title";
    static final String DESC = "desc";
    static final String URL = "url";
    private final static String FILE_ONE = "/page_0.json";
    private final static String FILE_TWO = "/page_1.json";
    private final static String FILE_THREE = "/page_2.json";
    private static final String PREFS_NAME = "mypref";
    private final static int THREAD_SLEEP = 500;
    private final static int TOTAL_ELEMENTS_LIMIT=30;
    private int LOADED_PAGE = 0;
    private GridView listview;
    private ListViewAdapter adapter;
    private ProgressDialog mProgressDialog;
    private ArrayList<HashMap<String, String>> arraylist;
    private boolean LOAD_MORE = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_main);
        iniViews();
        new DownloadJSON().execute();
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (totalItemCount < TOTAL_ELEMENTS_LIMIT) {
                    paginate(visibleItemCount, firstVisibleItem, totalItemCount);
                }
            }
        });
    }

    private void iniViews() {
        arraylist = new ArrayList<>();
        listview = (GridView) findViewById(R.id.listview);
    }

    private void paginate(int visibleItemCount, int firstVisibleItem, int totalItemCount) {
        int lastIndexInScreen = visibleItemCount + firstVisibleItem;
        if (lastIndexInScreen >= totalItemCount && totalItemCount == 10 && !LOAD_MORE) {
            LOAD_MORE = true;
            LOADED_PAGE++;
            new DownloadJSON().execute();
        } else if (lastIndexInScreen >= totalItemCount && totalItemCount == 20 && LOAD_MORE) {
            LOAD_MORE = false;
            LOADED_PAGE++;
            new DownloadJSON().execute();
        }
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SharedPreferences sharedPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(LoggedActivity.this.getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(LoggedActivity.this);
            mProgressDialog.setTitle("Zadanie3");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonobject;
            if (LOADED_PAGE == 1) {
                jsonobject = JSONfunctions
                        .getJSONfromURL(BASE_SERVER_URL + FILE_TWO);
            } else if (LOADED_PAGE == 2) {
                jsonobject = JSONfunctions
                        .getJSONfromURL(BASE_SERVER_URL + FILE_THREE);
            } else {
                jsonobject = JSONfunctions
                        .getJSONfromURL(BASE_SERVER_URL + FILE_ONE);
            }
            if (jsonobject == null) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(LoggedActivity.this, "Can't load data. Try again later", Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            }
            try {
                JSONArray jsonarray = jsonobject.getJSONArray("array");

                for (int i = 0; i < jsonarray.length(); i++) {
                    HashMap<String, String> map = new HashMap<>();
                    jsonobject = jsonarray.getJSONObject(i);
                    map.put("title", jsonobject.getString("title"));
                    map.put("desc", jsonobject.getString("desc"));
                    map.put("url", jsonobject.getString("url"));
                    arraylist.add(map);
                }
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (LOADED_PAGE == 0) {
                adapter = new ListViewAdapter(LoggedActivity.this, arraylist);
                listview.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
            try {
                Thread.sleep(THREAD_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mProgressDialog.dismiss();
        }
    }
}