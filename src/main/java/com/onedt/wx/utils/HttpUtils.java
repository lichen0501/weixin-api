package com.onedt.wx.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onedt.wx.constants.WechatConstants;

public class HttpUtils {
    public static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMM");
    /**
     * mime-Type类型
     */
    public static final Map<String, String> contentTypeMap = new HashMap<String, String>();
    static {
        contentTypeMap.put("image/jpeg", "jpg");
        contentTypeMap.put("image/png", "png");
        contentTypeMap.put("image/gif", "gif");
        contentTypeMap.put("audio/mp3", "mp3");
        contentTypeMap.put("video/mpeg4", "mp4");
    }

    /** http请求的报文头信息 */
    public static class WxHeader {
        private String from;// 来自哪个系统，1为web/2为微信
        private String openId;// 微信用户的标识
        private Integer pageIndex;// 页码
        private Integer rowSize = 10;// 每页容量
        private Long total;// 总条数
        private String token;// 调用接口凭证,from为1时必填
        private String type;// 业务操作类型

        public static final String PROPERTY_HEADER_NAME = "header";
        public static final String FROM_WECHAT = "2";
        public static final Integer PAGESIZE_MUCH = 20;

        public WxHeader() {
        }

        public WxHeader(String json) throws Exception {
            HttpUtils.WxHeader header = JacksonUtils.getObjectCamelLower(HttpUtils.WxHeader.class, json);
            this.setFrom(HttpUtils.WxHeader.FROM_WECHAT);
            this.setOpenId(header.getOpenId());
            this.setPageIndex(header.getPageIndex());
            this.setRowSize(header.getRowSize());
        }

        public Long getTotal() {
            return total;
        }

        public void setTotal(Long total) {
            this.total = total;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public Integer getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(Integer pageIndex) {
            this.pageIndex = pageIndex;
        }

        public Integer getRowSize() {
            return rowSize;
        }

        public void setRowSize(Integer rowSize) {
            this.rowSize = rowSize;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    /**
     * 项目本机发送get方式普通请求web服务
     * 
     * @param url地址
     * @return result
     * @throws Exception
     */
    public static String httpGet(String url) throws Exception {
        String result = null;
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            if (url == null || "".equals(url.trim())) {
                // LoggerUtil.error("GET连接出现了异常，连接地址："+url,HttpUtils.class);
                return null;
            }
            URL urlGet = new URL(url);
            connection = (HttpURLConnection) urlGet.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuffer sb = new StringBuffer();
            while ((result = reader.readLine()) != null) {
                sb.append(result);
            }
            result = sb.toString();
        } catch (Exception e) {
            LOGGER.error("GET连接出现了异常，连接地址：" + url + "。异常信息：" + e.getMessage());
            throw e;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    /**
     * 项目本机发送 post方式普通web请求
     * 
     * @param url请求地址
     * @param params参数数据
     * @param json是否是Json数据格式
     * @return result
     * @throws Exception
     */
    public static String httpPost(String url, String params) throws Exception {
        return httpPost(url, params, false);
    }

    /**
     * 项目本机发送 post json方式请求
     * 
     * @param url请求地址
     * @param params参数数据
     * @param json是否是Json数据格式,适应Spring的@RequestBody要求
     * @return result
     * @throws Exception
     */
    public static String httpPost(String url, String params, boolean json) throws Exception {
        return httpPost(url, params, json, null);
    }

    /**
     * 项目本机发送 post json方式请求
     * 
     * @param url请求地址
     * @param params参数数据
     * @param json是否是Json数据格式,适应Spring的@RequestBody要求
     * @param header 自定义添加的报头信息
     * @return result
     * @throws Exception
     */
    public static String httpPost(String url, String params, boolean json, WxHeader header) throws Exception {
        String result = null;
        HttpURLConnection connection = null;
        try {
            if (url == null || "".equals(url.trim())) {
                // LoggerUtil.error("POST连接出现了异常，连接地址："+url,HttpUtils.class);
                return null;
            }
            URL urlGet = new URL(url);
            connection = (HttpURLConnection) urlGet.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            if (json) {// 设置http报文头
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            } else {
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            }
            if (header != null) {// 添加http报文头信息
                connection.setRequestProperty(WxHeader.PROPERTY_HEADER_NAME, JacksonUtils.getJsonCamelLower(header));
            }
            connection.connect();
            if (params != null && !"".equals(params.trim())) {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "utf-8"));
                out.write(params);
                out.flush();
                out.close();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuffer sb = new StringBuffer();
            while ((result = reader.readLine()) != null) {
                sb.append(result);
            }
            if (header != null) {// 将查询统计的总条数数据返回给header对象
                Map<String, List<String>> map = connection.getHeaderFields();// 获取响应返回的header信息
                if (map != null && map.size() > 0) {
                    List<String> headerList = map.get(WxHeader.PROPERTY_HEADER_NAME);
                    if (headerList != null && headerList.size() > 0) {
                        WxHeader headerResult = JacksonUtils.getObjectCamelLower(WxHeader.class, headerList.get(0));
                        if (headerResult != null) {
                            header.setTotal(headerResult.getTotal());
                        }
                    }
                }
            }
            reader.close();
            result = sb.toString();
        } catch (Exception e) {
            LOGGER.error("POST连接出现了异常，连接地址：" + url + ",参数：" + params + "。异常信息：" + e.getMessage());
            throw e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    /**
     * 项目本机post方式请求并带有文件上传
     * 
     * @param Map<String,String> params
     * @param File file文件可以为空
     * @throws Exception
     */
    public static String postFile(String url, File file, Map<String, String> params) throws Exception {
        String result = null;
        HttpURLConnection connection = null;
        DataInputStream in = null;
        OutputStream out = null;
        try {
            if (url == null || "".equals(url.trim())) {
                // LoggerUtil.error("POST连接(可带文件)出现了异常，连接地址："+url,HttpUtils.class);
                return null;
            }
            URL urlGet = new URL(url);
            connection = (HttpURLConnection) urlGet.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);// post请求此处必为false
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            // 设置边界,发送文件必须配置边界线
            String BOUNDARY = "----------" + System.currentTimeMillis();
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            // 请求正文信息,参数字段 部分
            StringBuffer sb = new StringBuffer();
            if (params != null && params.size() > 0) {
                Set<String> keys = params.keySet();
                Iterator<String> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String value = params.get(key);
                    sb.append("--" + BOUNDARY + "\r\n");
                    sb = sb.append("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n");
                    sb = sb.append(URLEncoder.encode(value, "utf-8"));
                    sb = sb.append("\r\n");
                }
            }
            out = new DataOutputStream(connection.getOutputStream());
            if (file != null && file.exists() && file.length() > 0) {
                // 请求正文信息,发送文件参数部分
                sb = sb.append("--" + BOUNDARY + "\r\n");
                sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
                sb = sb.append("Content-Type: application/octet-stream\r\n\r\n");
                byte[] head = sb.toString().getBytes("utf-8");
                out.write(head);
                // 文件本身
                in = new DataInputStream(new FileInputStream(file));
                int bytes = 0;
                byte[] bufferOut = new byte[1024 * 10];
                while ((bytes = in.read(bufferOut)) != -1) {
                    out.write(bufferOut, 0, bytes);
                }
                in.close();
                in = null;
            } else {
                byte[] head = sb.toString().getBytes("utf-8");
                out.write(head);
            }
            out.write(("\r\n--" + BOUNDARY + "\r\n").getBytes("utf-8"));
            out.flush();
            out.close();
            out = null;
            // 读取服务器的响应数据
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuffer back = new StringBuffer();
            while ((result = reader.readLine()) != null) {
                back.append(result);
            }
            reader.close();
            connection.disconnect();
            result = back.toString();
            return result;
        } catch (Exception e) {
            LOGGER.error("POST连接(可带文件)出现了异常，连接地址：" + url + "。异常信息：" + e.getMessage());
            throw e;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e2) {
                LOGGER.error("关闭http连接的IO流出现异常,异常：" + e2.getMessage());
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * 项目本机post方式请求并带有文件上传
     * 
     * @param String url上传的到哪里
     * @param String fileUrl上传文件的路径
     * @throws Exception
     */
    public static String postFile(String url, String fileUrl, String fileName) throws Exception {
        String result = null;
        HttpURLConnection connection = null;
        DataInputStream in = null;
        OutputStream out = null;
        try {
            if (url == null || "".equals(url.trim())) {
                LOGGER.error("POST连接(可带文件)出现了异常，连接地址：" + url);
                return null;
            }
            URL urlGet = new URL(url);
            connection = (HttpURLConnection) urlGet.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);// post请求此处必为false
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            // 设置边界,发送文件必须配置边界线
            String BOUNDARY = "----------" + System.currentTimeMillis();
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            // 请求正文信息,参数字段 部分
            StringBuffer sb = new StringBuffer();
            out = new DataOutputStream(connection.getOutputStream());
            if (fileUrl != null && fileUrl.length() > 0) {
                // 请求正文信息,发送文件参数部分
                sb = sb.append("--" + BOUNDARY + "\r\n");
                sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + fileName + "\"\r\n");
                sb = sb.append("Content-Type: application/octet-stream\r\n\r\n");
                byte[] head = sb.toString().getBytes("utf-8");
                out.write(head);
                // 文件本身
                URL fileURL = new URL(fileUrl);
                in = new DataInputStream(fileURL.openStream());
                int bytes = 0;
                byte[] bufferOut = new byte[1024 * 10];
                while ((bytes = in.read(bufferOut)) != -1) {
                    out.write(bufferOut, 0, bytes);
                }
                in.close();
                in = null;
            } else {
                byte[] head = sb.toString().getBytes("utf-8");
                out.write(head);
            }
            out.write(("\r\n--" + BOUNDARY + "\r\n").getBytes("utf-8"));
            out.flush();
            out.close();
            out = null;
            // 读取服务器的响应数据
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuffer back = new StringBuffer();
            while ((result = reader.readLine()) != null) {
                back.append(result);
            }
            reader.close();
            connection.disconnect();
            result = back.toString();
            return result;
        } catch (Exception e) {
            LOGGER.error("POST连接(可带文件)出现了异常，连接地址：" + url + "。异常信息：" + e.getMessage());
            throw e;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e2) {
                LOGGER.error("关闭http连接的IO流出现异常,异常：" + e2.getMessage());
                // LoggerUtil.print(e2, HttpUtils.class);
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * 通过文件的url下载此文件，比如下载网络资源（文件/html等） 方式：java.net.URL 支持请求的方式：http、https、ftp、telnet、file、mailto等等
     * 
     * @throws Exception
     */
    public static String downLoadWeb(String fileUrl) throws Exception {
        if (fileUrl == null || "".equals(fileUrl.trim())) {
            return null;
        }
        String type = "";// 资源类型
        if (fileUrl.indexOf(".") > -1) {
            type = fileUrl.substring(fileUrl.lastIndexOf(".") + 1);
        } else {
            type = "file";
        }
        DataInputStream dis = null;
        FileOutputStream fos = null;
        try {
            URL url = new URL(fileUrl);
            File tempPath = new File(WechatConstants.MEDIA_TEMP_PATH);
            if (!tempPath.exists()) {
                tempPath.mkdirs();
            }
            dis = new DataInputStream(url.openStream());
            String tempFile = tempPath.getPath() + File.separator + type + "_" + System.currentTimeMillis() + "." + type;  // 在本项目建立一个临时文件
            fos = new FileOutputStream(new File(tempFile));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            dis.close();
            fos.close();
            return tempFile;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        } finally {
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e) {
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * HttpClient下载web资源，比如下载网络资源（文件/html等） 方式：java.net.URL 支持请求的方式：http、https、ftp、telnet、file、mailto等等
     * 
     * @throws Exception
     */
    public static String downLoad(String fileUrl) throws Exception {
        if (fileUrl == null || "".equals(fileUrl.trim())) {
            return null;
        }
        String fileType = null;// 资源类型
        InputStream is = null;
        FileOutputStream fos = null;
        File tempPath = null;
        String tempFilePath = null;
        String result = null;
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet get = new HttpGet(fileUrl);
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {// 读取http报头,200说明请求成功,比如HTTP/1.1 200 OK
                HttpEntity entity = response.getEntity();
                is = entity.getContent();// 报文主要内容
                fileType = getFileType(getFileName(response));
                if (StringUtils.isEmpty(fileType)) {
                    LOGGER.info("[downLoad]获取响应的消息头里上传的文件类型为空，根据文件后缀名");
                    fileType = getFileType(response);
                    if (StringUtils.isEmpty(fileType)) {
                        LOGGER.error("[downLoad]获取响应的消息头里上传的文件类型为空，根据响应数据类型");
                    }
                }
                String newName = UUID.randomUUID().toString() + "." + fileType;
                String filePath = FORMAT.format(new Date());
                tempPath = new File(WechatConstants.MEDIA_TEMP_PATH + File.separator + filePath);
                tempFilePath = tempPath.getPath() + File.separator + newName;  // 在本项目建立一个目录
                if (!tempPath.exists()) {
                    tempPath.mkdirs();
                }
                fos = new FileOutputStream(new File(tempFilePath));
                byte[] buffer = new byte[1024 * 10];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
                result = WechatConstants.NGINX_HOST + "/" + filePath + "/" + newName;
            }
            return result;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 通过HttpResponse报头获取文件名字
     * 
     * @param response
     * @return
     */
    public static String getFileName(HttpResponse response) {
        // 从http报头里获取Content-Disposition信息,比如Content-disposition: attachment; filename="MEDIA_ID.jpg"
        // Header header = response.getFirstHeader("Content-Disposition");
        Header[] headers = response.getAllHeaders();
        String filename = null;
        if (headers != null && headers.length > 0) {
            for (Header header : headers) {
                String headerName = header.getName();
                if ("Content-Disposition".equalsIgnoreCase(headerName)) {
                    String headerValue = header.getValue();
                    if (StringUtils.isNotEmpty(headerValue) && headerValue.length() > 2) {
                        if (headerValue.startsWith("\"")) {
                            headerValue = headerValue.substring(1);
                        }
                        if (headerValue.endsWith("\"")) {
                            headerValue = headerValue.substring(0, headerValue.length() - 1);
                        }
                    }
                    return headerValue;
                }
            }
        } else {
            LOGGER.error("[getFileName]从HttpResponse里获取Header为空");
        }
        return filename;
    }

    /**
     * 从HttpServletRequest获取http报文头信息
     * 
     * @return
     */
    public static Map<String, String> getHeadersInfo(HttpServletRequest request) {
        if (request == null)
            return null;
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    /**
     * 根据响应报文头获取响应数据类型（文件后缀类型）
     * 
     * @param response
     * @return
     */
    public static String getFileType(HttpResponse response) {
        String type = null;
        Header[] headers = response.getAllHeaders();
        if (headers != null && headers.length > 0) {
            for (Header header : headers) {
                String headerName = header.getName();
                if ("Content-Type".equalsIgnoreCase(headerName)) {
                    String headerValue = header.getValue();
                    if (StringUtils.isNotEmpty(headerValue)) {
                        return contentTypeMap.get(headerValue.toLowerCase());
                    }
                }
            }
        } else {
            LOGGER.error("[getFileType]从HttpResponse里获取Header为空");
        }
        return type;
    }

    /**
     * 根据文件名获取文件后缀类型
     * 
     * @param fileName
     * @return
     */
    public static String getFileType(String fileName) {
        String type = null;
        if (fileName != null && fileName.indexOf(".") > -1) {
            type = fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            LOGGER.error("[getFileType]根据文件名获取文件类型失败，filename=" + fileName);
        }
        return type;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
    }

}
