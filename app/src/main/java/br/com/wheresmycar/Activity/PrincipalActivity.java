package br.com.wheresmycar.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import wheresmycar.com.br.wheresmycar.R;

public class PrincipalActivity extends Activity {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final String MEDIA_TYPE_IMAGE = null;
	private Uri fileUri;
	static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
	}
	

	public void escolherAndar(View view) {
		 Intent pageAndar = new Intent(PrincipalActivity.this,AndaresActivity.class);
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
				} catch (ActivityNotFoundException anfe) {

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
				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

				Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
				toast.show();
			}
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
	        	
	        	MensagemUtil.addMensage(this, "Captura da C�mera cancelada.");
	        	Log.e("Error", (String)getText(R.string.msg_cancela_camera));
	        } else {
	        	
	        	MensagemUtil.addMensage(this, "Erro na C�mera");
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
		        	Toast.makeText(this, "Captura da C�mera cancelada.", Toast.LENGTH_SHORT).show();
		        	Log.e("Error", (String)getText(R.string.msg_cancela_camera));
		        } else {
		        	Toast.makeText(this, "Erro na C�mera", Toast.LENGTH_SHORT).show();
		        	Log.e("Error", (String)getText(R.string.msg_erro_camera));
		        }
		    }
		}*/


	 
}
