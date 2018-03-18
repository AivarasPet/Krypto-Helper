package meh.kitastest;

/**
 * Created by Aivaras on 2018-03-18.
 */
/**
public class data_download extends AsyncTask<Void, Void, Void> {

    String data="";
    String webSite = "";
    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url = new URL(webSite);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while(line != null) {
                line = bufferedReader.readLine();
                data += data + line;
            }

        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.Data.setText(this.data); //this.data tai cia atsiustas stringas
    }
}
**/