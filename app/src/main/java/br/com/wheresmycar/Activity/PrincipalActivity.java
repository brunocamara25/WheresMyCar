package br.com.wheresmycar.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

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
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.wheresmycar.dto.ImagensDTO;
import br.com.wheresmycar.dto.VagasDTO;
import wheresmycar.com.br.wheresmycar.R;

public class PrincipalActivity extends Activity {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final String MEDIA_TYPE_IMAGE = null;
	private Uri fileUri;
	static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
	int idTotem;


	List<ImagensDTO> imagensLocalizarCarro;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
        localizarCarroJson("4");
	}


	public void escolherAndar(View view) {
		Intent pageAndar = new Intent(PrincipalActivity.this, AndaresActivity.class);
		startActivity(pageAndar);
	}

	public void scanQR(View v) {
		try {
			Intent intent = new Intent(ACTION_SCAN);
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);
		} catch (ActivityNotFoundException anfe) {
			showDialog(PrincipalActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
		}
	}

	private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
		AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
		downloadDialog.setTitle(title);
		downloadDialog.setMessage(message);
		downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int i) {
				Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				try {
					act.startActivity(intent);
				} catch (ActivityNotFoundException e) {

				}
			}
		});
		downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int i) {
			}
		});
		return downloadDialog.show();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String valQrcode = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

//                localizarCarroJson(valQrcode);
				  localizarCarroJson("4");

				Toast toast = Toast.makeText(this, "Content:" + valQrcode + " Format:" + format, Toast.LENGTH_LONG);
				toast.show();
			}
		}
	}


	private class localizarCarro extends AsyncTask<Void, Void, List<ImagensDTO>> {
		private String text;

		@Override
		protected List<ImagensDTO> doInBackground(Void... voids) {

			String url = "http://smartparking.somee.com/wcf/MobileService.svc/json/LocalizarCarro?Id_Totem="+ idTotem +"Id_Carro=1190";

			try {

				HttpClient httpclient = new DefaultHttpClient();
				HttpGet request = new HttpGet();
				request.setURI(new URI(url));
				HttpResponse response = httpclient.execute(request);
				InputStream content = response.getEntity().getContent();
				Reader reader = new InputStreamReader(content);
                Gson gson = new Gson();
                imagensLocalizarCarro = Arrays.asList(gson.fromJson(reader, ImagensDTO[].class));
				content.close();

			} catch (Exception e) {
				e.printStackTrace();
				Toast toast = Toast.makeText(PrincipalActivity.this, "Serviço Fora do Ar!", Toast.LENGTH_SHORT);
				toast.show();
				Log.e("Error", (String) getText(R.string.msg_erro_servico));

			}
			return imagensLocalizarCarro;
		}

		protected void onPostExecute(List<VagasDTO> vagasList) {

			Log.d("Listando Vagas", "onPostExecute");
			int varControleVagas = 0;
			for (VagasDTO vagas : vagasList) {
				varControleVagas++;


			}

		}

	}


    private void localizarCarroJson(String valQrcode) {
        idTotem = Integer.parseInt(valQrcode);
        //new localizarCarro().execute();
        new DownloadJsonAsyncTask()
                .execute("http://smartparking.somee.com/wcf/MobileService.svc/json/LocalizarCarro?Id_Totem=4&Id_Carro=1190");
    }

    class DownloadJsonAsyncTask extends AsyncTask<String, Void, List<ImagensDTO>> {
        ProgressDialog dialog;

        //Exibe pop-up indicando que esta sendo feito o download do JSON
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(PrincipalActivity.this, "Aguarde",
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
//                ArrayAdapter<ImagensDTO> adapter = new ArrayAdapter<ImagensDTO>(
//                        PrincipalActivity.this,
//                        android.R.layout.simple_list_item_1, result);
//                setListAdapter(adapter);
				base64ToBitmap(result.get(0).getImagem());
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        PrincipalActivity.this)
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

	private Bitmap base64ToBitmap(String b64) {
		byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
	}
}


//*****************************************************  FUNCIONANDO ****************************************
	     //SALVAR IMAGEM NA PASTA DO APP
	 	/*public void fotoQrCode(View view){
		   
		   Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		   //Arquivo e onde salvar
	       File imagesFolder = new File(Environment.getExternalStorageDirectory(), "WheresMyCar");
	       imagesFolder.mkdirs(); 
	       //File image = new File(imagesFolder, "WhereIsMyCar_"+ Util.getTimeStamp() +".jpg");//Caso queira que salve varias
	       File image = new File(imagesFolder, "WhereIsMyCar_001"+".jpg");
	       fileUri = Uri.fromFile(image);
	       intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
	       intent.putExtra("return-data", true);
		   startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		}*/
		
	//CAPTURA E SALVA	
	/*@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{

	    super.onActivityResult(requestCode, resultCode, data);

	    
	    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	            
	        	handleSmallCameraPhoto(fileUri);     
	            MensagemUtil.addMensage(this, "Imagem Capturada.");
	            Log.i("Sucess", "Imagem Capturada.");
	        } else if (resultCode == RESULT_CANCELED) {
	        	
	        	MensagemUtil.addMensage(this, "Captura da Camera cancelada.");
	        	Log.e("Error", (String)getText(R.string.msg_cancela_camera));
	        } else {
	        	
	        	MensagemUtil.addMensage(this, "Erro na Camera");
	        	Log.e("Error", (String)getText(R.string.msg_erro_camera));
	        }
	    }
	     
	 }

	     private void handleSmallCameraPhoto(Uri uri) 
	        {
	           Bitmap bmp=null;

	              try {
	                bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
	                   } 
	                   catch (FileNotFoundException e) 
	                   {

	                 e.printStackTrace();
	                }


	              Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	              intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
	              

	       }*/
	
//*****************************************************  FUNCIONANDO ****************************************
	
		/*@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
		        if (resultCode == RESULT_OK) {
		            // Image captured and saved to fileUri specified in the Intent
		            Toast.makeText(this, "Image saved to:\n" + data.getData(), Toast.LENGTH_SHORT).show();
		            Log.i("Sucess", "Imagem Capturada.");
		        } else if (resultCode == RESULT_CANCELED) {
		        	Toast.makeText(this, "Captura da Camera cancelada.", Toast.LENGTH_SHORT).show();
		        	Log.e("Error", (String)getText(R.string.msg_cancela_camera));
		        } else {
		        	Toast.makeText(this, "Erro na Camera", Toast.LENGTH_SHORT).show();
		        	Log.e("Error", (String)getText(R.string.msg_erro_camera));
		        }
		    }
		}*/
