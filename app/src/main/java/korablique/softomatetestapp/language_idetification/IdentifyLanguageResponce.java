package korablique.softomatetestapp.language_idetification;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdentifyLanguageResponce {
    @SerializedName("languages")
    @Expose
    private List<ResultLanguage> languages = null;

    public List<ResultLanguage> getLanguages() {
        return languages;
    }

    public void setLanguages(List<ResultLanguage> languages) {
        this.languages = languages;
    }
}
