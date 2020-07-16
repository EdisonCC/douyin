package com.evan.socket;

import com.evan.socket.netty.protocol.SocketNatMessage;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;

import io.netty.channel.Channel;

public class SocketResponse {
    private SocketRequest request;
    private Channel channel;
    private boolean closed = false;

    public SocketResponse(SocketRequest request, Channel channel) {
        this.request = request;
        this.channel = channel;
    }

    public SocketResponse(Channel channel) {
        this.channel = channel;
    }

    /**
     * 返回数据到服务器
     *
     * @param contentType MIME type，为了支持file，图片等特殊场景才支持
     * @param bytes       返回内容
     */
    public void send(String contentType, byte[] bytes) {
        if (closed) {
            SLog.w("response send already!");
            return;
        }
        SocketNatMessage socketNatMessage = new SocketNatMessage();
        if (request != null) {
            socketNatMessage.setSerialNumber(request.getSerialNo());
        }
        socketNatMessage.setType(SocketNatMessage.TYPE_INVOKE);
        socketNatMessage.setData(bytes);
        socketNatMessage.setExtra(contentType);
        if (channel != null) {
            channel.writeAndFlush(socketNatMessage);
        }
        closed = true;
    }

    public <T> void send(CommonRes<T> commonRes) {
        String stringContent = JsonUtil.toJson(commonRes);
        send("application/json; charset=utf-8", stringContent);
    }

    public void send(String contentType, String string) {
        send(contentType, string.getBytes(StandardCharsets.UTF_8));
        SLog.i("invoke response: " + string);
    }

    public void send(String string) {
        send("text/html; charset=utf-8", string);
    }

    public void sendFile(File file) throws IOException {
        FileInputStream fin = new FileInputStream(file);
        sendStream(getContentType(file.getAbsolutePath()), new BufferedInputStream(fin, 64000));
    }

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    private static final int EOF = -1;

    public void sendStream(String contentType, InputStream inputStream) throws IOException {
        try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            // copy(inputStream, output);
            long count = 0;
            int n;
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            while (EOF != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
                count += n;
            }
            send(contentType, output.toByteArray());
        }
    }

    private static Hashtable<String, String> mContentTypes = new Hashtable<String, String>();

    static {
        mContentTypes.put("js", "application/javascript");
        mContentTypes.put("json", "application/json");
        mContentTypes.put("png", "image/png");
        mContentTypes.put("jpg", "image/jpeg");
        mContentTypes.put("html", "text/html");
        mContentTypes.put("css", "text/css");
        mContentTypes.put("mp4", "video/mp4");
        mContentTypes.put("mov", "video/quicktime");
        mContentTypes.put("wmv", "video/x-ms-wmv");
    }

    public static String getContentType(String path) {
        String type = tryGetContentType(path);
        if (type != null)
            return type;
        return "text/plain";
    }

    public static String tryGetContentType(String path) {
        int index = path.lastIndexOf(".");
        if (index != -1) {
            String e = path.substring(index + 1);
            String ct = mContentTypes.get(e);
            if (ct != null)
                return ct;
        }
        return null;
    }

    public static String getStackTrack(Throwable throwable) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(byteArrayOutputStream));
        throwable.printStackTrace(printWriter);
        printWriter.close();
        try {
            return byteArrayOutputStream.toString(StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    public void failed(int errorCode, Throwable throwable) {
        failed(errorCode, getStackTrack(throwable));
    }

    public void failed(int errorCode, String message) {
        send(CommonRes.failed(errorCode, message));
    }

    public void failed(String message) {
        send(CommonRes.failed(message));
    }

    public <T> void success(T data) {
        send(CommonRes.success(data));
    }
}
