package rga.contact.json;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import rga.contact.R;


public class JsonDownloader extends AsyncTask<String, String, String> {

	private final String URL =
    "https://s3-sa-east-1.amazonaws.com/rgasp-mobile-test/v1/content.json";

	private OnTaskCompleted listener;
	private ProgressDialog progressDialog;
	
	public JsonDownloader(Context context, OnTaskCompleted listener ){
		this.listener = listener;
		this.progressDialog = new ProgressDialog(context);
		progressDialog.setIndeterminate(false);
		progressDialog.setCancelable(false);
		String msg = context.getResources().getString(R.string.dialog_message);
		progressDialog.setMessage(msg);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
        showProgressDialog();
	}

    private void showProgressDialog() {
        this.progressDialog.show();
    }

    @Override
	protected String doInBackground(String... params) {
		String json = "";
		try {
			HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(URL);
            HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			if ( statusLine.getStatusCode() == HttpStatus.SC_OK ){
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				Scanner scanner = new Scanner(content);
				json = scanner.useDelimiter("\\A").next().replaceAll("\"", "\'");
                content.close();
				scanner.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	@Override
	protected void onPostExecute(String json) {
		super.onPostExecute(json);
        dismissProgressDialog();
		this.listener.onJsonDownloaded(json);
	}

    private void dismissProgressDialog() {
        this.progressDialog.dismiss();
    }

    public interface OnTaskCompleted {
		public void onJsonDownloaded(String json);
	}
}
