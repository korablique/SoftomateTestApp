package korablique.softomatetestapp.language_identification.retrofit;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdentifyLanguageResponse {
    @SerializedName("languages")
    @Expose
    private List<ResultLanguage> languages = null;

    public List<ResultLanguage> getLanguages() {
        return languages;
    }
}
