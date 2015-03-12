package com.shizy.news.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.shizy.news.Cheeses;
import com.shizy.news.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

public class StoreHouseFragment extends Fragment {
    private PtrHandler mPtrHandler = new PtrHandler() {

        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view2) {
            Log.e("", "shizy---checkCanDoRefresh-->");
            return !canScrollUp(view);
        }

        @Override
        public void onRefreshBegin(final PtrFrameLayout ptrFrameLayout) {
            ptrFrameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrFrameLayout.refreshComplete();
                }
            }, 2000);
        }
    };

    private PtrFrameLayout mPtrFrameLayout;
    private ListView mListView;

    private int mPosition;

    public static StoreHouseFragment newInstance(int position) {
        StoreHouseFragment fragment = new StoreHouseFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt("position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_house, container, false);
        initView(view);
        return view;
    }

    private void initView(View root) {
        mPtrFrameLayout = (PtrFrameLayout) root;
        mListView = (ListView) root.findViewById(R.id.listView);
        mListView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Cheeses.sCheeseStrings));
        mPtrFrameLayout.setPtrHandler(mPtrHandler);
        mPtrFrameLayout.disableWhenHorizontalMove(true);

        final StoreHouseHeader header = new StoreHouseHeader(getActivity());
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        header.setPadding(0, padding, 0, padding);
        header.initWithString("Shizy");
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);
    }

    public boolean canScrollUp(View view) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (view instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) view;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return view.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(view, -1);
        }
    }
}
