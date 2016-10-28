package com.example.hongguangjin.myapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongguang.jin on 2016/10/27.
 */
public class DataModel {
    private static DataModel mDataModel = new DataModel();
    private WebServiceHelper mWebServiceHelper;
    private List<String> queryDatas = new ArrayList<String>();
    private List<String> mDatas = new ArrayList<String>();

    public static DataModel getSingleton() {
        return mDataModel;
    }

    public DataModel() {
        initData();
    }

    private void initData() {
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }

    }

    public List<String> searching(String query) {
        queryDatas.clear();
        if (query.isEmpty()) return mDatas;
        else for (String mData : mDatas)
            if (mData.toLowerCase().contains(query.toLowerCase())) queryDatas.add(mData);
        return queryDatas;
    }
}
