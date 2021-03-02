package minhfx03283.funix.prm391_asm_1.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import minhfx03283.funix.prm391_asm_1.R;

public class InputNameFragment extends DialogFragment {
    NoticeDialogListener listener;
    private String mUserName;

    public InputNameFragment() {
    }

    public NoticeDialogListener getListener() {
        return listener;
    }

    public void setListener(NoticeDialogListener listener) {
        this.listener = listener;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use Builder class to build dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get Layout Inflater & EditText reference
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_name_input, null);
        EditText editText = (EditText) dialogLayout.findViewById(R.id.edt_name_input);

        // Get activity_main inflater
        View mainLayout = inflater.inflate(R.layout.activity_main, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialogLayout)
                // Add action buttons
                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        InputNameFragment.this.setUserName(editText.getText().toString());
                        listener.onDialogPositiveClick(InputNameFragment.this);
                    }
                })
                .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        InputNameFragment.this.setUserName("User");
                        listener.onDialogNegativeClick(InputNameFragment.this);
                        InputNameFragment.this.getDialog().cancel();
                    }
                });

        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        void onDialogPositiveClick(InputNameFragment dialog);

        void onDialogNegativeClick(InputNameFragment dialog);


    }
}
