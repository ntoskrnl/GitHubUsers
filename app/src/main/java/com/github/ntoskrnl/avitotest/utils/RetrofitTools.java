package com.github.ntoskrnl.avitotest.utils;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import retrofit.client.Response;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

/**
 * Created by Anton Danshin on 17/02/15.
 */
public class RetrofitTools {

    public static Object getBodyAs(Response response, Type type) {
        if (response == null) {
            return null;
        }
        TypedInput body = response.getBody();

        if (body == null) {
            return null;
        }
        byte[] bodyBytes = ((TypedByteArray) body).getBytes();
        String bodyMime = body.mimeType();
        String bodyCharset = MimeUtil.parseCharset(bodyMime, "utf-8");
        try {
            String data = new String(bodyBytes, bodyCharset);
            return new Gson().fromJson(data, type);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
