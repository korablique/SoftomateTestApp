package korablique.softomatetestapp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;


public class IdentificationLangDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Not supported yet")
                .setNeutralButton(R.string.cancel, (dialog, id) -> {
                    dismiss();
                });
        return builder.create();
    }
}
