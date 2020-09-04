package piotr.michalkiewicz.mealplannerclient.view.login_and_signup;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.mealplannerclient.R;

public class LoginLoadingDialog {

    private Activity activity;
    private AlertDialog alertDialog;

    public LoginLoadingDialog(Activity activity){
        this.activity = activity;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog,null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismissDialog(){
        alertDialog.dismiss();
    }

}
