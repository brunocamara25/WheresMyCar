package wheresmycar.com.br.util;

import android.app.Activity;
import android.widget.Toast;

public class MensagemUtil {

	public static void addMensage(Activity activity, String msg) {
		Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

	}
}
