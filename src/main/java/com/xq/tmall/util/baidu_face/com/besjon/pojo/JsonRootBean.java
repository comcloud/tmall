
package com.xq.tmall.util.baidu_face.com.besjon.pojo;

/**
 * Auto-generated: 2020-03-28 15:59:45
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class JsonRootBean {

    private int error_code;
    private String error_msg;
    private long log_id;
    private long timestamp;
    private int cached;
    public Result result;
    public void setError_code(int error_code) {
         this.error_code = error_code;
     }
     public int getError_code() {
         return error_code;
     }

    public void setError_msg(String error_msg) {
         this.error_msg = error_msg;
     }
     public String getError_msg() {
         return error_msg;
     }

    public void setLog_id(long log_id) {
         this.log_id = log_id;
     }
     public long getLog_id() {
         return log_id;
     }

    public void setTimestamp(long timestamp) {
         this.timestamp = timestamp;
     }
     public long getTimestamp() {
         return timestamp;
     }

    public void setCached(int cached) {
         this.cached = cached;
     }
     public int getCached() {
         return cached;
     }

    public void setResult(Result result) {
         this.result = result;
     }
     public Result getResult() {
         return result;
     }

}