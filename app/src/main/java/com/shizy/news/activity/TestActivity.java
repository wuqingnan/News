package com.shizy.news.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.shizy.news.Cheeses;
import com.shizy.news.R;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class TestActivity extends ActionBarActivity {

    private PtrHandler mPtrHandler = new PtrHandler() {

        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view2) {
            Log.e("", "shizy---checkCanDoRefresh");
            return true;
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {

        }
    };

    private PtrClassicFrameLayout mPtrFrameLayout;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        mPtrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptrLayout);
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Cheeses.sCheeseStrings));
        mPtrFrameLayout.setPtrHandler(mPtrHandler);
        mPtrFrameLayout.disableWhenHorizontalMove(true);
    }

}
