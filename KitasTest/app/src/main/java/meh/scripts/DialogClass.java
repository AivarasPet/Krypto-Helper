package meh.scripts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

public class DialogClass {
    public Runnable ans_true = null;
   // public Runnable ans_false = null;

    // Dialog. --------------------------------------------------------------

    public void Confirm(Activity act, String Title, String ConfirmText,
                           String CancelBtn, String OkBtn, Runnable aProcedure) {
        ans_true = aProcedure;
        //ans_false= bProcedure;
        AlertDialog dialog = new AlertDialog.Builder(act).create();
        dialog.setTitle(Title);
        dialog.setMessage(ConfirmText);
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, OkBtn,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int buttonId) {
                        ans_true.run();
                    }
                });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, CancelBtn,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int buttonId) {
                       // ans_false.run();
                    }
                });
        Log.d("NERODO ", "NERODO");
        dialog.setIcon(android.R.drawable.ic_dialog_alert);
        dialog.show();
    }

    public void AddDialog() {

    }
}
