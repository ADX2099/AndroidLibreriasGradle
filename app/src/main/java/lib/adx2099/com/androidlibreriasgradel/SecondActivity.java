package lib.adx2099.com.androidlibreriasgradel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    private TextView textView;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //Implementar flecha para ir atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView = findViewById(R.id.textViewMain);
        btn = findViewById(R.id.buttonSharing);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });

        //Tomar los datos del intent

        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.getString("greeter" )!= null){
            String greeter = bundle.getString("greeter");
            Toast.makeText(SecondActivity.this, greeter, Toast.LENGTH_LONG).show();
            textView.setText(greeter);
        }else{
            Toast.makeText(SecondActivity.this,"Its empty", Toast.LENGTH_LONG);

        }

    }
}
