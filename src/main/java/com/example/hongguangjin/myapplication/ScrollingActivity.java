package com.example.hongguangjin.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, RecyclerDataAdapter.OnItemClickListener{
    private static final String TAG = "ScrollingActivity";
    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private RecyclerDataAdapter mRecyclerViewAdapter;
    private FrameLayout mFrameLayout; //query|all data list context.
    private DataModel mDataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initRecyclerDataView();
        initFloatingAction();
    }

    private void initFloatingAction() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    private void initRecyclerDataView() {
        mDataModel = DataModel.getSingleton();
        mFrameLayout = (FrameLayout) findViewById(R.id.search_results_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecylerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRecyclerViewAdapter = new RecyclerDataAdapter(this, mDataModel.searching("")));
        mRecyclerViewAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, ((TextView) view.findViewById(R.id.id_num)).getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        createSearchView(menu);
        return true;
    }

    private void createSearchView(Menu menu) {
        mSearchView = (SearchView) menu.findItem(R.id.search_menu).getActionView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.search_menu), new  MenuItemCompat.OnActionExpandListener(){

                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    Log.i(TAG, "onMenuItemActionExpand");
                    mFrameLayout.setVisibility(View.VISIBLE);
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    Log.i(TAG, "onMenuItemActionCollapse");
                    mFrameLayout.setVisibility(View.GONE);
                    return true;
                }
        });
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setQueryHint("输入您感兴趣的...");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public boolean onQueryTextChange(String query) {
        mRecyclerViewAdapter.setData(mDataModel.searching(query));
        mRecyclerViewAdapter.notifyDataSetChanged();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public void onStop() { super.onStop(); }
}
