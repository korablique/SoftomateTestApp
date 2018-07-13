package korablique.softomatetestapp.language_identification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdentifiableLanguage {
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("name")
    @Expose
    private String name;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
