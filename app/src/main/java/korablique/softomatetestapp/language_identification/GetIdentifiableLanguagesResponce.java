package korablique.softomatetestapp.language_identification;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetIdentifiableLanguagesResponce {
    @SerializedName("languages")
    @Expose
    private List<IdentifiableLanguage> languages = null;

    public List<IdentifiableLanguage> getLanguages() {
        return languages;
    }

    public void setLanguages(List<IdentifiableLanguage> languages) {
        this.languages = languages;
    }
}