package com.viking.myframe.http;

/**
 * 项目名称: MyFrame-master
 * 创建人: 周正一
 * 创建时间：2017/5/11
 * 服务器端返回的数据
 */

public class ResponseDate {
    private String message;
    private Result result;
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public  class Result {
        /**
         * data : "{\"token\":\"90b164495ccae2b46821eee91c8bb79a0d3b184452932d5c2beab80d75e3ecf0\"}"
         * pageCount : 0
         * recordCount : 0
         */
        private int size;
        private int currentPage;
        private String data;

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public String getData() {

            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
