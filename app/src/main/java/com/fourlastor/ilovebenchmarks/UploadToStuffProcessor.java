package com.fourlastor.ilovebenchmarks;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.TypeAdapters;

import java.io.IOException;

import org.threeten.bp.Instant;

import dk.ilios.spanner.json.AnnotationExclusionStrategy;
import dk.ilios.spanner.json.InstantTypeAdapter;
import dk.ilios.spanner.model.Trial;
import dk.ilios.spanner.output.ResultProcessor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class UploadToStuffProcessor implements ResultProcessor {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final String postUrl;

    public UploadToStuffProcessor(String postUrl) {
        this.postUrl = postUrl;
    }

    @Override
    public void processTrial(Trial trial) {
        GsonBuilder gsonBuilder = new GsonBuilder().setExclusionStrategies(new AnnotationExclusionStrategy());
        gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(Instant.class, new InstantTypeAdapter()));
        Gson gson = gsonBuilder.create();

        String json = gson.toJson(ImmutableList.of(trial));

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);

        Request build = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        try {
            okHttpClient.newCall(build).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {

    }
}
