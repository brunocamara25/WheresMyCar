package br.com.wheresmycar.Activity;


        import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.wheresmycar.dao.DatabaseHelper;
import br.com.wheresmycar.dto.CarrosDTO;
import br.com.wheresmycar.dto.UsuarioDTO;
        import wheresmycar.com.br.wheresmycar.R;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText edtLogin;
    //private EditText edtSenha;
    private Button btnLogar;
    private static final String TAG = "ExampleActivity";
    private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
    String CPF;
    ProgressDialog dialog;
    List<CarrosDTO> carrosList;

    private SQLiteDatabase db;

    private ProgressDialog mConnectionProgressDialog;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //findViewById(R.id.sign_in_button).setOnClickListener((OnClickListener) this);
        edtLogin = (EditText) findViewById(R.id.edtLogin);
        //edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnLogar = (Button) findViewById(R.id.btnLogar);

        //Criando Banco
        dbHelper = new DatabaseHelper(getApplicationContext());
        dbHelper.createTable();

        ListarCarrosJson();

    }


    public void logar(View view){

        String login = edtLogin.getText().toString();
       // String senha = edtSenha.getText().toString();

        List<UsuarioDTO> listaUsuarios = new ArrayList<UsuarioDTO>();
        listaUsuarios = dbHelper.getUsuario(login);



//        boolean validaDadosLogin = true;
//        if (login == null || login.equals("")) {
//            edtLogin.setError(getText(R.string.msg_erro_login));
//            Log.e("Error", (String) getText(R.string.msg_erro_login));
//            validaDadosLogin = false;
//        }
//        if(senha == null || senha.equals("")){
//            edtSenha.setError(getText(R.string.msg_erro_senha));
//            Log.e("Error", (String)getText(R.string.msg_erro_senha));
//            validaDadosLogin = false;
//        }
//
//        if(!listaUsuarios.isEmpty()) {
//            for (UsuarioDTO listaUsuario : listaUsuarios) {
//                if (listaUsuario.getLogin().equalsIgnoreCase(login.toString()) && listaUsuario.getSenha().equalsIgnoreCase(senha.toString()) ){
//                    MensagemUtil.addMensage(LoginActivity.this,(String) getText(R.string.logado));
                    Log.i("Sucesso", "Logado");
                    Intent i = new Intent(LoginActivity.this,PrincipalActivity.class);
                    startActivity(i);
                    finish();
//                }else{
//                    Toast toast = Toast.makeText(this, "Usuário não Cadastrado!", Toast.LENGTH_LONG);
//                    toast.show();
//                    Log.e("Error", (String)getText(R.string.msg_usuario_sem_cadastro));
//                    validaDadosLogin = false;
//                }
//            }
//
//      }

    }

    private void ListarCarrosJson() {
        new Task().execute();
    }
    private class Task extends AsyncTask<Void, Void, List<CarrosDTO>> {
        private String text;



        @Override
        protected List<CarrosDTO> doInBackground(Void... voids) {

            String url = "http://smartparking.somee.com/wcf/MobileService.svc/json/ListarCarros?CPF=32380172501";

            try {
                // Setamos o cliente http e o nosso request, que será do tipo GET
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                // Setamos nossa URI
                request.setURI(new URI(url));
                // Executamos nossa transação HTTP
                HttpResponse response = httpclient.execute(request);
                // Pegamos o conteúdo como resposta e inserimos em um InputStream
                InputStream content = response.getEntity().getContent();
                // Instanciamos o nosso Reader com o InputStream
                Reader reader = new InputStreamReader(content);
                // Aqui vamos utilizar a Biblioteca Gson para transformar o Json recebido em Objeto JAVA
                /* Instanciamos o objeto Gson e em seguida utilizamos o método fromJson() passando como parâmetro o Reader instanciado e o tipo do Objeto que será retornado. */
                Gson gson = new Gson();

                carrosList = Arrays.asList(gson.fromJson(reader, CarrosDTO[].class));

                content.close();

            } catch (Exception e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(LoginActivity.this, "Serviço Fora do Ar!", Toast.LENGTH_LONG);
                toast.show();
                Log.e("Error", (String) getText(R.string.msg_erro_servico));

            }
            return carrosList;
        }

        protected void onPostExecute(List<CarrosDTO> vagasList) {
             /* Log.d("Listando Vagas", "onPostExecute");
            for (CarrosDTO carros : carrosList) {
                  txtVagas1.setText(vagas.getQtdVagas());
                    txtVagasDisp1.setText(vagas.getQtdLivre());
                    pageAndar1 = new Intent(AndaresActivity.this,BlocoActivity.class);
                    pageAndar1.putExtra("idAndar", Integer.valueOf(vagas.getId()));
            }
            dialog.dismiss();*/
        }


    }


    @Override
    public void onClick(View v) {

    }



}

