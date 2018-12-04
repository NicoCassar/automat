
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//Permet de constituer la requete a partir des éléments de recherche et parametre et de recuperer le fichier JSON. 
//L'entite voulu correspond au type d'entree que l'on fournit, l'eventuelle entree au mot recherché,
//le MBID a l'eventuel Id qui permettra de retrouver  les boolean exact et fini definissant si l'entree a une syntaxe exacte que la saisie est complete

public class Requete {
	private String gare;
	private String urlToRead;

	public Requete(String gare) {
		this.gare = gare;
	}

	private void makeURL() {
		this.urlToRead = "https://www.gares-sncf.com/fr/train-times/" + gare + "/departure";
	}

	public String getJson() {
		makeURL();
		StringBuilder result = new StringBuilder();

		try {
			URL url = new URL(this.urlToRead);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			return result.toString();
		} catch (Exception e) {
			System.err.println("Fichier Json impossible a recuperer");
			return "Error";
		}

	}

}
