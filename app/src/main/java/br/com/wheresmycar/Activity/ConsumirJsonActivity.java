package br.com.wheresmycar.Activity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import br.com.wheresmycar.dto.ImagensDTO;


public class ConsumirJsonActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new DownloadJsonAsyncTask()
				.execute("http://smartparking.somee.com/wcf/MobileService.svc/json/LocalizarCarro?Id_Totem=4&Id_Carro=1190");
	}

//	@Override
//	protected void onListItemClick(ListView l, View v, int position, long id) {
//		super.onListItemClick(l, v, position, id);
//
//		ImagensDTO imagem = (ImagensDTO) l.getAdapter().getItem(position);
//
//		Intent intent = new Intent(this, InformacoesActivity.class);
//		intent.putExtra("imagem", String.valueOf(imagem));
//		startActivity(intent);
//	}

	class DownloadJsonAsyncTask extends AsyncTask<String, Void, List<ImagensDTO>> {
		ProgressDialog dialog;

		//Exibe pop-up indicando que esta sendo feito o download do JSON
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = ProgressDialog.show(ConsumirJsonActivity.this, "Aguarde",
					"Fazendo download do JSON");
		}

		//Acessa o servico do JSON e retorna a lista de imagens
		@Override
		protected List<ImagensDTO> doInBackground(String... params) {
			String urlString = params[0];
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(urlString);
			try {
				HttpResponse response = httpclient.execute(httpget);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream instream = entity.getContent();
					String json = getStringFromInputStream(instream);
					instream.close();
					List<ImagensDTO> imagemList = getImagemList(json);
					return imagemList;
				}
			} catch (Exception e) {
				Log.e("Erro", "Falha ao acessar Web service", e);
			}
			return null;
		}


		//Depois de executada a chamada do servico
		@Override
		protected void onPostExecute(List<ImagensDTO> result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (result.size() > 0) {
				ArrayAdapter<ImagensDTO> adapter = new ArrayAdapter<ImagensDTO>(
						ConsumirJsonActivity.this,
						android.R.layout.simple_list_item_1, result);
				setListAdapter(adapter);
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						ConsumirJsonActivity.this)
						.setTitle("Erro")
						.setMessage("Nao foi possivel acessar as informacoes!!")
						.setPositiveButton("OK", null);
				builder.create().show();
			}
		}
		
		//Retorna uma lista de imagens com as informacoes retornadas do JSON
		private List<ImagensDTO> getImagemList(String jsonString) {
			List<ImagensDTO> imagens = new ArrayList<ImagensDTO>();
			try {
				JSONArray imagemJson = new JSONArray(jsonString);
				JSONObject imagem;

				for (int i = 0; i < imagemJson.length(); i++) {
					imagem = new JSONObject(imagemJson.getString(i));
					Log.i("IMAGEM ENCONTRADA: ",
							"imagem=" + imagem.getString("Imagem"));

					ImagensDTO objetoImagem = new ImagensDTO();
					objetoImagem.setImagem(imagem.getString("Imagem"));
					imagens.add(objetoImagem);
				}

			} catch (JSONException e) {
				Log.e("Erro", "Erro no parsing do JSON", e);
			}
			return imagens;
		}
		

		//Converte objeto InputStream para String
		private String getStringFromInputStream(InputStream is) {

			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();

			String line;
			try {

				br = new BufferedReader(new InputStreamReader(is));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			return sb.toString();

		}

	}
}
