package lib.adx2099.com.androidlibreriasgradel;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {
    private EditText editTextPhone;
    private EditText editTextWeb;
    private EditText editTextContact;
    private ImageButton imgBtnPhone;
    private ImageButton imageButtonWeb;
    private ImageButton imageButtonCamera;
    private ImageButton imageButtonContact;
    private final int PHONE_CALL_CODE = 100;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        editTextPhone = findViewById(R.id.editTextPhone);
        editTextWeb = findViewById(R.id.editTextWeb);
        editTextContact = findViewById(R.id.editTextContact);
        imgBtnPhone = findViewById(R.id.imageButtonPhone);
        imageButtonWeb = findViewById(R.id.imageButtonWeb);
        imageButtonCamera = findViewById(R.id.imageButtonCamera);
        imageButtonContact = findViewById(R.id.imageButtonContact);

        imgBtnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = editTextPhone.getText().toString();
                if (phoneNumber != null && !phoneNumber.isEmpty()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if(checkPermission(Manifest.permission.CALL_PHONE)) {
                            Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                            if (ActivityCompat.checkSelfPermission(ThirdActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                                return;

                            startActivity(intentCall);

                        }else{
                            if(!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                                NewerVersions();
                            }else{
                                Toast.makeText(ThirdActivity.this,"Please enable the request permission", Toast.LENGTH_SHORT).show();
                                Intent settIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                settIntent.addCategory(Intent.CATEGORY_DEFAULT);
                                settIntent.setData(Uri.parse("package:"+getPackageName()));
                                settIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                settIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                settIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                startActivity(settIntent);
                            }

                        }


                    } else {
                        OlderVersions(phoneNumber);
                    }
                }else{
                    Toast.makeText(context, "Insert a phone number", Toast.LENGTH_SHORT).show();
                }
            }

            private void OlderVersions(String phoneNumber) {
                Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                if (checkPermission(Manifest.permission.CALL_PHONE)) {
                    startActivity(intentCall);
                } else {
                    Toast.makeText(ThirdActivity.this, "You declined the access", Toast.LENGTH_SHORT);
                }

            }

            private void NewerVersions() {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
            }
        });

        //Boton WEB
        imageButtonWeb.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                String url = editTextWeb.getText().toString();
                if(url != null && !url.isEmpty()){
                    Intent intentWeb = new Intent();
                    intentWeb.setAction(Intent.ACTION_VIEW);
                    intentWeb.setData(Uri.parse("http://"+url));
                    startActivity(intentWeb);
                }
            }
        });

        imageButtonContact.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String url = editTextContact.getText().toString();
                if(url != null && !url.isEmpty()){
                    Intent intentContact = new Intent();
                    intentContact.setAction(Intent.ACTION_VIEW);
                    intentContact.setData(Uri.parse("content://contacts/people"));
                    startActivity(intentContact);
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //CASO TELEFONIO
        switch (requestCode) {
            case PHONE_CALL_CODE:

                String permission = permissions[0];
                int result = grantResults[0];
                if (permission.equals(Manifest.permission.CALL_PHONE)) {

                    //Comprueba si ha sido aceptado o dengeado la peticion
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        //CONCEDIO PERMISO
                        String phoneNumber = editTextPhone.getText().toString();
                        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)return;

                        startActivity(intentCall);

                    }
                    else{
                        //No concedio permiso
                        Toast.makeText(context, "You decliend the access", Toast.LENGTH_SHORT).show();
                    }
                }


                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;

        }
    }

    private boolean checkPermission(String permission){
        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }


}
