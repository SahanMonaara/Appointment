package com.monaara.sahan.testappointment.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sahan on 4/29/2018.
 */

public class Response {
    @SerializedName("list")
    @Expose
    private Lists list;

    public Lists getList() {
        return list;
    }

    public void setList(Lists list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Response{" +
                "list=" + list +
                '}';
    }
}
