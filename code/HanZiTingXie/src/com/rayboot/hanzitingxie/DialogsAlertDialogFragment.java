
package com.rayboot.hanzitingxie;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.DialogFragment;

import android.content.Context;
import android.os.Bundle;

public class DialogsAlertDialogFragment extends DialogFragment {
	
	AlertDialog.Builder builder;
	
    public DialogsAlertDialogFragment() {
        setDialogType(DialogType.AlertDialog);
    }
	

	@Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
		if (builder == null) {
	        builder = new AlertDialog.Builder(getSupportActivity());
		}
        return builder.create();
    }

    
    public AlertDialog.Builder getBuilder(Context context) {
		if (builder == null) {
	        builder = new AlertDialog.Builder(context);
		}
		return builder;
	}
}
