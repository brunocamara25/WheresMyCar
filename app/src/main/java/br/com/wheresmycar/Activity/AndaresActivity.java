package br.com.wheresmycar.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
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
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.com.wheresmycar.dto.AndaresDTO;
import wheresmycar.com.br.wheresmycar.R;

public class AndaresActivity extends Activity implements View.OnClickListener{

	ProgressDialog dialog;
    List<AndaresDTO> vagasList;
    List<AndaresDTO> vagasDisponiveis;
    private TextView txtVagas1;
    private TextView txtVagasDisp1;
    private TextView txtVagas2;
    private TextView txtVagasDisp2;
    private TextView txtVagas3;
    private TextView txtVagasDisp3;
    private TextView txtVagas4;
    private TextView txtVagasDisp4;
    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private ImageButton imageButton3;
    private ImageButton imageButton4;

    Intent pageAndar1;
    Intent pageAndar2;
    Intent pageAndar3;
    Intent pageAndar4;

    String idPlacaCarro;

    //Timer Refresh
    private Timer autoUpdate;


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_andares);
		dialog = ProgressDialog.show(AndaresActivity.this, "", "Buscando vagas disponíveis...", true);

        txtVagas1 = (TextView) findViewById(R.id.txtVagas1);
        txtVagasDisp1 = (TextView) findViewById(R.id.txtVagasDisp1);
        txtVagas2 = (TextView) findViewById(R.id.txtVagas2);
        txtVagasDisp2 = (TextView) findViewById(R.id.txtVagasDisp2);
        txtVagas3 = (TextView) findViewById(R.id.txtVagas3);
        txtVagasDisp3 = (TextView) findViewById(R.id.txtVagasDisp3);
        txtVagas4 = (TextView) findViewById(R.id.txtVagas4);
        txtVagasDisp4 = (TextView) findViewById(R.id.txtVagasDisp4);

        imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        imageButton1.setOnClickListener(this);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(this);
        imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(this);
        imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        imageButton4.setOnClickListener(this);

        Intent intent = getIntent();
        idPlacaCarro = intent.getStringExtra("idPlacaCarro");

        ListarAndaresJson();



	}

    private void ListarAndaresJson() {
        new Task().execute();
    }
    private class Task extends AsyncTask<Void, Void, List<AndaresDTO>> {
        private String text;

        @Override
        protected List<AndaresDTO> doInBackground(Void... voids) {

            String url = "http://smartparking.somee.com/wcf/MobileService.svc/json/listarandares";

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

                vagasList = Arrays.asList(gson.fromJson(reader, AndaresDTO[].class));

                content.close();

            } catch (Exception e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(AndaresActivity.this, "Serviço Fora do Ar!", Toast.LENGTH_LONG);
                toast.show();
                Log.e("Error", (String)getText(R.string.msg_erro_servico));

            }
            return vagasList;
        }

        protected void onPostExecute(List<AndaresDTO> vagasList) {
            Log.d("Listando Vagas", "onPostExecute");
            for (AndaresDTO vagas : vagasList) {
				if(vagas.getNome().equalsIgnoreCase("G1")) {
                    txtVagas1.setText(vagas.getQtdVagas());
                    txtVagasDisp1.setText(vagas.getQtdLivre());
                    pageAndar1 = new Intent(AndaresActivity.this,BlocoActivity.class);
                    pageAndar1.putExtra("idAndar", Integer.valueOf(vagas.getId()));
                    pageAndar1.putExtra("idPlacaCarro", idPlacaCarro);

                }else if (vagas.getNome().equalsIgnoreCase("G2")) {
                    txtVagas2.setText(vagas.getQtdVagas());
                    txtVagasDisp2.setText(vagas.getQtdLivre());
                    pageAndar2 = new Intent(AndaresActivity.this,BlocoActivity.class);
                    pageAndar2.putExtra("idAndar", Integer.valueOf(vagas.getId()));
                    pageAndar2.putExtra("idPlacaCarro", idPlacaCarro);

                }else if (vagas.getNome().equalsIgnoreCase("G3")) {
                    txtVagas3.setText(vagas.getQtdVagas());
                    txtVagasDisp3.setText(vagas.getQtdLivre());
                    pageAndar3 = new Intent(AndaresActivity.this,BlocoActivity.class);
                    pageAndar3.putExtra("idAndar", Integer.valueOf(vagas.getId()));
                    pageAndar3.putExtra("idPlacaCarro", idPlacaCarro);

                }else if (vagas.getNome().equalsIgnoreCase("G4")){
                    txtVagas4.setText(vagas.getQtdVagas());
                    txtVagasDisp4.setText(vagas.getQtdLivre());
                    pageAndar4 = new Intent(AndaresActivity.this,BlocoActivity.class);
                    pageAndar4.putExtra("idAndar", Integer.valueOf(vagas.getId()));
                    pageAndar4.putExtra("idPlacaCarro", idPlacaCarro);
                }
            }
            dialog.dismiss();
        }


    }

    @Override
    public void onClick(View v){

        if(v == imageButton1){
            startActivity(pageAndar1);

        }else if(v == imageButton2){
            startActivity(pageAndar2);

        }else if(v == imageButton3){
            startActivity(pageAndar3);

        }else if(v == imageButton4){
            startActivity(pageAndar4);

        }

    }


    @Override
    public void onResume() {
        super.onResume();
        autoUpdate = new Timer();
        autoUpdate.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
//                        Toast refresh = Toast.makeText(AndaresActivity.this, "Refresh!", Toast.LENGTH_SHORT);
//                        refresh.show();
                        ListarAndaresJson();
                    }
                });
            }
        }, 0, 40000); // 40 secs
    }


//	private void ListarAndaresJson(final String url) {
//		// Toda chamada externa necessita rodar em background, então utilizamos thread
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//
//				// Criamos nosso objeto de retorno que poderia ser uma entidade (Exemplo: Aluno, Usuário, etc.), nesse caso utilizamos algo genérico.
//
//				Object retorno = null;
//
//				// Há a necessidade de tratarmos excessão tendo em vista que estamos realizando requisições em nossa aplicação
//
//				try {
//
//					// Setamos o cliente http e o nosso request, que será do tipo GET (O POST veremos em outros artigos)
//
//					HttpClient httpclient = new DefaultHttpClient();
//
//					HttpGet request = new HttpGet();
//
//					// Setamos nossa URI
//
//					request.setURI(new URI(url));
//
//					// Executamos nossa transação HTTP
//
//					HttpResponse response = httpclient.execute(request);
//
//					// Pegamos o conteúdo advindo como resposta e inserimos em um InputStream
//
//					InputStream content = response.getEntity().getContent();
//
//					// Instanciamos o nosso Reader com o InputStream
//
//					Reader reader = new InputStreamReader(content);
//
//
//
//					// Aqui vamos utilizar a Biblioteca Gson para transformar o Json recebido em Objeto JAVA
//
//    				/* Instanciamos o objeto Gson e em seguida utilizamos o método fromJson() passando como parâmetro o Reader instanciado e o tipo do Objeto que será retornado. */
//
//					Gson gson = new Gson();
//
//					vagasList = Arrays.asList(gson.fromJson(reader, AndaresDTO[].class));
//
//
//					//Post post = gson.fromJson(reader, Post.class);
//
//					//Para unico elemento
//					//retorno = gson.fromJson(reader, HashMap.class);
////					for (AndaresDTO vagas : vagasList) {
////						//Toast toast = Toast.makeText(AndaresActivity.this, vagas.getQtdLivre() , Toast.LENGTH_LONG);
////						//toast.show();
////                        edtVagas.setText(vagas.getQtdLivre());
////					}
//
//					content.close();
//
//				} catch (Exception e) {
//
//					e.printStackTrace();
//
//				}
//
//			}
//
//		}).start();
//
//
//	}



}
//	ProgressDialog dialog;
//	final String SOAP_ACTION = "http://tempuri.org/IServico/ListarAndares";
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_andares);
//		dialog = ProgressDialog.show(AndaresActivity.this, "", "Buscando vagas disponíveis...", true);
//		new Thread(this).start();
//	}
//
//	// Metodo da classe Runnable, execulta ao iniciar a classe.
//		public void run() {
//			listarVagasDisponiveis();
//		}
//
//		    //Teremos tres constantes, sendo:
//			//NAMESPACE: O endereco do site que contem o Web Service.
//			//URL: O endereço do servico Web Service.
//			//SOAP_ACTION: O endereco do site + metodo utilizado.
//		public void listarVagasDisponiveis() {
//			//Objeto composto pelo NameSpace e pelo metodo que queremos chamar
//			SoapObject soap = new SoapObject("http://smartparking.somee.com/Servico.svc", "ListarAndares");
//
//			//Requisição do web service, solicitação do numero do CEP
//			//soap.addProperty("cep", "50740110");
//			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11);
//			envelope.dotNet = true;
//			envelope.setOutputSoapObject(soap);
//
//			Log.i("Wheres my car", "Chamando WebService para consulta de Vagas Disponíveis");
//			String url = "http://smartparking.somee.com";
//			HttpTransportSE httpTransport = new HttpTransportSE(url);
//
//			try {
//				Log.d("WhereIsMycar", "VOU ENTRAR");
//				httpTransport.call(SOAP_ACTION, envelope);
//
//				//Object msg = envelope.getResponse();
//
//
//				//androidHttpTransport.call(SOAP_ACTION, envelope);
//	            SoapObject result = (SoapObject) envelope.getResponse();
//	            Log.d("WhereIsMycar", "ENTREI");
//	            result.getPropertyAsString(0).split(",");
//	            Log.d("WhereIsMycar", "Endereço: " + result);
//
//				dialog.dismiss();
//				System.out.println(result);
//				Log.w("VAGAS", result.toString());
//				Toast toast = Toast.makeText(this, "Content:" + result.toString(), Toast.LENGTH_LONG);
//				toast.show();
//			}
//			catch (Exception e) {
//				Log.e("GB", "CATH DE BUSCA Vagas!");
//				finish();
//	        }
//		}