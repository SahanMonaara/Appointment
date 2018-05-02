package com.monaara.sahan.testappointment.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sahan on 4/29/2018.
 */

 public class Lists {
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("synonyms")
    @Expose
    private String synonyms;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }

}
