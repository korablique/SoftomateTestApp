package korablique.softomatetestapp.new_text;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import korablique.softomatetestapp.R;


public class IdentificationLangDialog extends DialogFragment {
    public static final String LANGUAGE = "LANGUAGE";
    public static final String ERROR = "ERROR";
    private static final String TAG = IdentificationLangDialog.class.getName();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String result;
        if (getArguments().containsKey(LANGUAGE)) {
            result = getString(R.string.used_language) + getArguments().getString(LANGUAGE);
        } else {
            result = getString(R.string.error) + getArguments().getString(ERROR);
        }
        builder.setMessage(result)
                .setNeutralButton(R.string.cancel, (dialog, id) -> {
                    dismiss();
                });
        return builder.create();
    }

    public static void showDialog(FragmentManager fragmentManager, String languageName) {
        show(fragmentManager, LANGUAGE, languageName);
    }

    public static void showDialogWithError(FragmentManager fragmentManager, String error) {
        show(fragmentManager, ERROR, error);
    }

    private static void show(FragmentManager fragmentManager, String key, String text) {
        Bundle args = new Bundle();
        args.putString(key, text);
        IdentificationLangDialog dialog = new IdentificationLangDialog();
        dialog.setArguments(args);
        dialog.show(fragmentManager, TAG);
    }
}
