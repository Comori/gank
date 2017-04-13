package fred.angel.com.mgank.component.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Comori on 2016/11/1.
 * Todo 自定义解析，主要只解析result内容
 */

public class MindGsonConvertFactory extends Converter.Factory  {

    public static MindGsonConvertFactory create() {
        return create(new Gson());
    }

    public static MindGsonConvertFactory create(Gson gson) {
        return new MindGsonConvertFactory(gson);
    }

    private final Gson gson;

    private MindGsonConvertFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CutsomRequestBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CutsomResponseBodyConverter<>(gson, adapter);
    }

    static class CutsomRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private static final Charset UTF_8 = Charset.forName("UTF-8");

        private final Gson gson;
        private final TypeAdapter<T> adapter;

        CutsomRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public RequestBody convert(T value) throws IOException {
            Buffer buffer = new Buffer();
            Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
            JsonWriter jsonWriter = gson.newJsonWriter(writer);
            adapter.write(jsonWriter, value);
            jsonWriter.close();
            return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
        }
    }

    static class CutsomResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private static final Charset UTF_8 = Charset.forName("UTF-8");
        private final Gson mGson;
        private final TypeAdapter<T> adapter;

        public CutsomResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            mGson = gson;
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            String response = value.string();
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
                value.close();
                throw new ApiException(ApiException.ApiErrorEnum.PARSE_ERROR);
            }
            if(jsonObject != null){
                int error = jsonObject.optInt("res");
                if(error != 0){
                    value.close();
                    throw new ApiException(ApiException.ApiErrorEnum.SERVER_ERROR);
                }
                if(jsonObject.has("data")){
                    String result = "";
                    Object resultObj = jsonObject.opt("data");
                    if(resultObj instanceof JSONObject || resultObj instanceof JSONArray){
                        result = resultObj.toString();
                    }else result = jsonObject.optString("data");

                    if(TextUtils.isEmpty(result) || TextUtils.equals(result,"{}") ||
                            TextUtils.equals(result,"[]")) {
                        value.close();
                        return null;
                    }

                    MediaType mediaType = value.contentType();
                    Charset charset = mediaType != null ? mediaType.charset(UTF_8) : UTF_8;
                    ByteArrayInputStream bis = new ByteArrayInputStream(result.getBytes());
                    InputStreamReader reader = new InputStreamReader(bis,charset);
                    JsonReader jsonReader = mGson.newJsonReader(reader);
                    try {
                        return adapter.read(jsonReader);
                    } catch (Exception e){
                       return (T) result;
                    }  finally{
                        value.close();
                    }
                }
            }else {
                MediaType mediaType = value.contentType();
                Charset charset = mediaType != null ? mediaType.charset(UTF_8) : UTF_8;
                ByteArrayInputStream bis = new ByteArrayInputStream(response.getBytes());
                InputStreamReader reader = new InputStreamReader(bis,charset);
                JsonReader jsonReader = mGson.newJsonReader(reader);
                try {
                    return adapter.read(jsonReader);
                } finally {
                    value.close();
                }
            }
            return null;
        }
    }
}
