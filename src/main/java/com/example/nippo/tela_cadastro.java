package com.example.nippo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class tela_cadastro extends Activity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telacadastro);

        // Vincular os elementos de interface do usuário com os IDs definidos no layout XML
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonCadastro = findViewById(R.id.buttonLogin);

        // Configurar um ouvinte de clique para o botão de cadastro
        buttonCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Capturar o texto dos campos de texto (usuário e senha)
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Enviar dados para o servidor
                enviarDadosParaServidor(username, password);
            }
        });
    }

    // Método para enviar dados para o servidor
    private void enviarDadosParaServidor(String username, String password) {
        OkHttpClient client = new OkHttpClient();

        // URL do servidor
        String url = "https://w2zffn-3000.csb.app/cadastrar";

        // Construir o corpo da requisição
        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        // Construir a requisição POST
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        // Enviar a requisição assincronamente
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                // Tratar falha na requisição
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(tela_cadastro.this, "Erro ao cadastrar. Verifique sua conexão com a internet.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Tratar resposta do servidor
                    final String resposta = response.body().string();
                    Log.d("Resposta do servidor:", resposta);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Exibir mensagem de sucesso
                            Toast.makeText(tela_cadastro.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Tratar resposta do servidor
                    final String resposta = response.body().string();
                    Log.e("Erro na resposta:", resposta);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Exibir mensagem de erro
                            Toast.makeText(tela_cadastro.this, "Erro ao cadastrar: " + resposta, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}

