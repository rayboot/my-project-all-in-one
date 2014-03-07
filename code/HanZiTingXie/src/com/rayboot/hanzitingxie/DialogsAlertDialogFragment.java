package com.rayboot.hanzitingxie;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class DialogsAlertDialogFragment extends SherlockDialogFragment {

	public static DialogsAlertDialogFragment newInstance(int title) {
		DialogsAlertDialogFragment frag = new DialogsAlertDialogFragment();
		Bundle args = new Bundle();
		args.putInt("title", title);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		int title = getArguments().getInt("title");

		return new AlertDialog.Builder(getActivity())
				.setTitle(title)
				.setPositiveButton(R.string.alert_dialog_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								((FragmentAlertDialogSupport) getActivity())
										.doPositiveClick();
							}
						})
				.setNegativeButton(R.string.alert_dialog_cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								((FragmentAlertDialogSupport) getActivity())
										.doNegativeClick();
							}
						}).create();
	}
}
