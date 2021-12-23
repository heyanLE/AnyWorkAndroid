package com.qgstudio.anyworkc.records.bean;

import java.util.List;

/**
 * 从后台响应的做题记录json数据
 * url:http://ip:port/test/record
 */
public class RecordsData {

    private int state;
    private String stateInfo;
    private List<DataBean> data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private int testpaperId;
        private int status;
        private String endTime;
        private int testpaperType;
        private String testpaperTitle;

        public int getTestpaperId() {
            return testpaperId;
        }

        public void setTestpaperId(int testpaperId) {
            this.testpaperId = testpaperId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getTestpaperType() {
            return testpaperType;
        }

        public void setTestpaperType(int testpaperType) {
            this.testpaperType = testpaperType;
        }

        public String getTestpaperTitle() {
            return testpaperTitle;
        }

        public void setTestpaperTitle(String testpaperTitle) {
            this.testpaperTitle = testpaperTitle;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "testpaperId=" + testpaperId +
                    ", status=" + status +
                    ", endTime='" + endTime + '\'' +
                    ", testpaperType=" + testpaperType +
                    ", testpaperTitle='" + testpaperTitle + '\'' +
                    '}';
        }
    }
}
