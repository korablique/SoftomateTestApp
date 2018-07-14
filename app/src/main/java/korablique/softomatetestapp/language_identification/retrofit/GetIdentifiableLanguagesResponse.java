package korablique.softomatetestapp.language_identification.retrofit;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetIdentifiableLanguagesResponse {
    @SerializedName("languages")
    @Expose
    private List<IdentifiableLanguage> languages = null;

    public List<IdentifiableLanguage> getLanguages() {
        return languages;
    }
}