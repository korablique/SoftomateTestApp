package korablique.softomatetestapp.language_idetification;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultLanguage implements Comparable<ResultLanguage> {
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("confidence")
    @Expose
    private Double confidence;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    @Override
    public int compareTo(@NonNull ResultLanguage o) {
        return Double.compare(o.confidence, this.confidence); // desc
    }
}