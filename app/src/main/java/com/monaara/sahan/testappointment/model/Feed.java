package com.monaara.sahan.testappointment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.monaara.sahan.testappointment.model.Response.Response;

import java.util.ArrayList;

/**
 * Created by Sahan on 4/29/2018.
 */

public class Feed {
    //intializing variable according to json file
    @SerializedName("response")
    @Expose
    private ArrayList<Response> response;

    public ArrayList<Response> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<Response> response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "response=" + response +
                '}';
    }
}
