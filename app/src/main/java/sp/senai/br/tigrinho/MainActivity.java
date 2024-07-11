package sp.senai.br.tigrinho;

import static sp.senai.br.tigrinho.Creditos.REQUEST_CODE_CREDITOS;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView ivSlote1, ivSlote2, ivSlote3;
    MediaPlayer mpBotao, mpMusicaMenu, mpRoleta, mpDuasCasas, mpTresCasas;
    AnimationDrawable animacaoslote2, animacaoslote3, animacaoslote1;

    float saldoAtual = 0;
    float saldoAposta = 0;
    float retorno = 0;
    int valorAumentado, tentativa = 0;
    int[] arImagens = {
            R.drawable.sete, R.drawable.coracao, R.drawable.cereja, R.drawable.diamante, R.drawable.espadilha
    };

    TextView txtSaldoAposta, txtSaldoAtual, txtRetorno, txtTentativas;
    Button btMais, btMenos, btAposta, btSom;

    boolean ligado = false;
    boolean divisao = false;
    boolean tudoLigado = false;
    @Override
    protected void onPause() {
        super.onPause();
        pausePlaying();
    }
    @Override
    protected void onStop() {
        super.onStop();
        pausePlaying(); // Parar o som quando a Activity está em parada
    }

    @Override
    protected void onResume() {
        super.onResume();
        startPlaying(); // Continuar o som quando a Activity é retomada
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mpBotao != null) {
            mpBotao.release();
            mpBotao = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
            getSupportActionBar().setTitle("Fortune Tiger Bet");
        }

        ivSlote1 = findViewById(R.id.ivSlote1);
        ivSlote2 = findViewById(R.id.ivSlote2);
        ivSlote3 = findViewById(R.id.ivSlote3);

        ivSlote1.setImageResource(0);
        ivSlote2.setImageResource(0);
        ivSlote3.setImageResource(0);

        ivSlote2.setBackgroundResource(R.drawable.animacao2);
        ivSlote3.setBackgroundResource(R.drawable.animacao3);
        ivSlote1.setBackgroundResource(R.drawable.animacao);

        animacaoslote2 = (AnimationDrawable) ivSlote2.getBackground();
        animacaoslote3 = (AnimationDrawable) ivSlote3.getBackground();
        animacaoslote1 = (AnimationDrawable) ivSlote1.getBackground();

        txtSaldoAposta = findViewById(R.id.txtSaldoAposta);
        txtSaldoAtual = findViewById(R.id.txtSaldoAtual);
        txtRetorno = findViewById(R.id.txtRetorno);
        txtTentativas = findViewById(R.id.txtTentativas);
        txtSaldoAtual.setText("R$: " + saldoAtual);


        btMais = findViewById(R.id.btMais);
        btMenos = findViewById(R.id.btMenos);
        btAposta = findViewById(R.id.btAposta);
        btSom = findViewById(R.id.btSom);

        btMenos.setEnabled(false);
        btMais.setEnabled(true);
        btAposta.setEnabled(false);

        mpMusicaMenu = MediaPlayer.create(this, R.raw.memoir);
        mpMusicaMenu.setLooping(false);
        mpMusicaMenu.setVolume(1.00f, 1.00f);
        mpMusicaMenu.start();

        if (mpMusicaMenu != null) {
            if (mpMusicaMenu.isPlaying()) {
                mpMusicaMenu.seekTo(0);
            }
            mpMusicaMenu.start();
        }

        mpRoleta = MediaPlayer.create(this, R.raw.slot);
        mpRoleta.setLooping(true);
        mpRoleta.setVolume(1.00f, 1.00f);

        mpBotao = MediaPlayer.create(this, R.raw.click);
        mpBotao.setLooping(false);
        mpBotao.setVolume(1.00f, 1.00f);

        mpDuasCasas = MediaPlayer.create(this, R.raw.lvlup2);
        mpDuasCasas.setLooping(true);
        mpDuasCasas.setVolume(1.00f, 1.00f);

        mpTresCasas = MediaPlayer.create(this, R.raw.lvlup3);
        mpDuasCasas.setLooping(true);
        mpDuasCasas.setVolume(1.00f, 1.00f);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CREDITOS) {
            if (resultCode == RESULT_OK && data != null && data.hasExtra("Credito")) {
                float fCreditos = data.getFloatExtra("Credito", 0.0f);
                saldoAtual += fCreditos;
                atualizarTextos();
                verifs();
            }
        }
    }


    private void atualizarTextos() {
        txtSaldoAposta.setText("R$: " + saldoAposta);
        txtRetorno.setText("R$: " + retorno);
        txtSaldoAtual.setText("R$: " + saldoAtual);
        txtTentativas.setText("" + tentativa);
    }
    public void desligar(View v) {
        if (ligado = !ligado) {
            mpMusicaMenu.pause();
        } else {
            mpMusicaMenu.start();
        }
    }

    public void desligarTudo(View v) {
        if (tudoLigado = !tudoLigado) {
            mpMusicaMenu.pause();
            mpBotao.setVolume(0.00f, 0.00f);
            mpRoleta.setVolume(0.00f, 0.00f);
        } else {
            mpMusicaMenu.start();
            mpBotao.setVolume(1.00f, 1.00f);
            mpRoleta.setVolume(1.00f, 1.00f);
        }
    }

    private void startPlaying() {
        if (mpMusicaMenu != null) {
            mpMusicaMenu.start();
        }
    }
    private void pausePlaying() {
        if (mpMusicaMenu != null && mpMusicaMenu.isPlaying()) {
            mpMusicaMenu.pause();
        }
    }
    public void novo(View v) {
        Intent ins = new Intent(MainActivity.this, splash.class);
        startActivity(ins);
        finish();
    }

    public void creditos(View v) {
        Intent cred = new Intent(MainActivity.this, Creditos.class);
        startActivityForResult(cred, REQUEST_CODE_CREDITOS);
    }
    public void instrucoes(View v) {
        Intent ins = new Intent(MainActivity.this, instrucoes.class);
        startActivity(ins);
    }

    public void aposta(View v) {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(50);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mpBotao.start();
                            mpRoleta.start();
                            btAposta.setEnabled(false);
                            btMais.setEnabled(false);
                            btMenos.setEnabled(false);
                        }
                    });
                    Thread.sleep(2025);
                    if (mpRoleta.isPlaying()) {
                        mpRoleta.seekTo(0);
                    }
                    mpRoleta.pause();
                    verifs();
                    aposta_bt();
                } catch (Exception e) {
                }
            }
        }.start();


        btAposta.setEnabled(true);
        Random rand = new Random();
        int iRandom  = rand.nextInt(5);
        int iRandom2 = rand.nextInt(5);
        int iRandom3 = rand.nextInt(5);

        ivSlote1.setImageResource(0);
        ivSlote2.setImageResource(0);
        ivSlote3.setImageResource(0);

        new Thread() {
            public void run() {
                try {
                    animacaoslote1.start();
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            animacaoslote1.stop();
                            ivSlote1.setImageResource(arImagens[iRandom]);
                        }
                    });
                } catch (Exception e) {
                }
            }
        }.start();
        new Thread() {
            public void run() {
                try {
                    animacaoslote2.start();
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            animacaoslote2.stop();
                            ivSlote2.setImageResource(arImagens[iRandom2]);
                        }
                    });
                } catch (Exception e) {
                }
            }
        }.start();
        new Thread() {
            public void run() {
                try {
                    animacaoslote3.start();
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            animacaoslote3.stop();
                            ivSlote3.setImageResource(arImagens[iRandom3]);
                        }
                    });
                } catch (Exception e) {
                }
            }
        }.start();

        float saldoAntes = saldoAtual;
        txtRetorno.setText("R$ 0.0");
        if (iRandom == 0 && iRandom2 == 0 & iRandom3 == 0) {
            valorAumentado = 17;
            saldoAtual += (saldoAposta * valorAumentado);
        } else if (iRandom == 1 && iRandom2 == 1 & iRandom3 == 1) {
            valorAumentado = 13;
            saldoAtual += (saldoAposta * valorAumentado);
        } else if (iRandom == 2 && iRandom2 == 2 & iRandom3 == 2) {
            valorAumentado = 10;
            saldoAtual += (saldoAposta * valorAumentado);
        } else if (iRandom == 3 && iRandom2 == 3 & iRandom3 == 3) {
            valorAumentado = 8;
            saldoAtual += (saldoAposta * valorAumentado);
        } else if (iRandom == 4 && iRandom2 == 4 & iRandom3 == 4) {
            valorAumentado = 6;
            saldoAtual += (saldoAposta * valorAumentado);
        } else if (iRandom != iRandom2 && iRandom2 != iRandom3 && iRandom3 != iRandom) {
            saldoAtual -= saldoAposta;
        } else {
            saldoAtual -= (saldoAposta / 2);
        }
        retorno = saldoAtual - saldoAntes;

        saldoAtual += saldoAposta;
        saldoAposta = 0.00f;
        tentativa++;
        verifs();
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            atualizarTextos();
                        }
                    });

                } catch (Exception e) {
                }
            }
        }.start();
        btMenos.setEnabled(false);

    }

    public void mais(View v) {
        if (mpBotao != null) {
            if (mpBotao.isPlaying()) {
                mpBotao.seekTo(0);
            }
            mpBotao.start();
        }

        if (saldoAtual >= 10) {
            saldoAposta += 10;
            saldoAtual -= 10;
            atualizarTextos();
            btMenos.setEnabled(true);
        }
        verifs();
        aposta_bt();
    }

    public void menos(View v) {
        if (mpBotao != null) {
            if (mpBotao.isPlaying()) {
                mpBotao.seekTo(0);
            }
            mpBotao.start();
        }
        if (saldoAposta >= 10) {
            saldoAtual += 10;
            saldoAposta -= 10;
            atualizarTextos();
        }

        if (saldoAposta < 10) {
            btMenos.setEnabled(false);
            atualizarTextos();
        } else {
            btMenos.setEnabled(true);
            atualizarTextos();
        }
        verifs();
        aposta_bt();
    }
    public void max(View v) {
        if (mpBotao != null) {
            if (mpBotao.isPlaying()) {
                mpBotao.seekTo(0);
            }
            mpBotao.start();
        }
        if (saldoAtual > 0) {
            float saldoTemp = saldoAtual;
            saldoAtual = 0;
            saldoAposta += saldoTemp;
            atualizarTextos();
        }

        if (saldoAposta < 10) {
            btMenos.setEnabled(false);
            atualizarTextos();
        } else {
            btMenos.setEnabled(true);
            atualizarTextos();
        }
        verifs();
        aposta_bt();
    }

    public void aposta_bt() {
        if (saldoAposta < 1) {
            btAposta.setEnabled(false);
        } else {
            btAposta.setEnabled(true);
        }
    }

    public void verifs() {
        if (saldoAtual == 0 && saldoAposta > 0) {
            btMais.setEnabled(false);
        } else {
            btMais.setEnabled(true);
        }

        if (saldoAtual > 1) {
            btMais.setEnabled(true);
        } else {
            btMais.setEnabled(false);
        }

    }
}
