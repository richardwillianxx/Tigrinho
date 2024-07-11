package sp.senai.br.tigrinho;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Creditos extends AppCompatActivity {
    EditText etCredito;
    public static final int REQUEST_CODE_CREDITOS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);
        etCredito = findViewById(R.id.etCredito);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    public void creditos(View v) {
        String creditoText = etCredito.getText().toString();
        if (!creditoText.isEmpty()) {
            try {
                float fCreditos = Float.parseFloat(creditoText);
                Intent intent = new Intent();
                intent.putExtra("Credito", fCreditos);
                setResult(RESULT_OK, intent);

                finish();
            } catch (NumberFormatException e) {
                Toast.makeText(Creditos.this, "Por favor, insira um número válido", Toast.LENGTH_SHORT).show();
            }
        } else {
            etCredito.setError("Por favor, insira um valor");
        }
    }

    public void fechar(View v){
        finish();
    }
}
