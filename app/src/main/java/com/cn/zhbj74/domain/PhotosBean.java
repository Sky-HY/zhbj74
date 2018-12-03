package com.cn.zhbj74.domain;

import java.util.ArrayList;

/**
 * 组图解析json数据的Javabean
 */
public class PhotosBean {

    public PhotosData data;

    public class PhotosData {
        public ArrayList<PhotoNews> news;
    }

    public class PhotoNews {
        public int id;
        public String listimage;
        public String title;
    }
}
