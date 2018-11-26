package com.cn.zhbj74.domain;

import java.util.ArrayList;

/**
 * 解析json的Javabean
 */
public class NewsMenu {
    public int retcode;
    public ArrayList<Integer> extend;
    public ArrayList<NewsMenuData> data;

    public class NewsMenuData {
        public ArrayList<NewsTabData> children;
        public int id;
        public String title;
        public int type;

        @Override
        public String toString() {
            return "NewsMenuData{" +
                    "children=" + children +
                    ", title='" + title + '\'' +
                    '}';
        }
    }

    public class NewsTabData {
        public int id;
        public String title;
        public int type;
        public String url;

        @Override
        public String toString() {
            return "NewsTabData{" +
                    "title='" + title + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewsMenu{" +
                "data=" + data +
                '}';
    }
}
