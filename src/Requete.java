//package requestSide;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//Permet de constituer la requete a partir des éléments de recherche et parametre et de recuperer le fichier JSON. 
//L'entite voulu correspond au type d'entree que l'on fournit, l'eventuelle entree au mot recherché,
//le MBID a l'eventuel Id qui permettra de retrouver  les boolean exact et fini definissant si l'entree a une syntaxe exacte que la saisie est complete

public class Requete {
	private String entiteVoulu;
	private String entree;
	private int nbResultatsAttendus;
	private boolean exact = false;
	private boolean complet = false;
	private int offset = 0;
	private String urlToRead;
	private boolean associciated = false;
	private boolean self = false;
	private String associes;
	private String MBID;

	public static void main(String[] args) {
		Requete re = new Requete("release", "racine carree", 1, false, true);
		re.makeRequete();
		System.out.println(re.getUrlToRead());
		System.out.println(re.getJson());
		System.out.println("OK"); 
	}

	public Requete(String entiteVoulu, String entree, int nbResultatsAttendus, boolean exact, boolean complet) {
		super();
		this.entiteVoulu = entiteVoulu;
		this.entree = entree;
		this.nbResultatsAttendus = nbResultatsAttendus;
		this.exact = exact;
		this.complet = complet;

	}

	public Requete(String entiteVoulu, int nbResultatsAttendus, int offset, String associes, String mBID) {
		super();
		this.entiteVoulu = entiteVoulu;
		this.nbResultatsAttendus = nbResultatsAttendus;
		this.offset = offset;
		this.associciated = true;
		this.associes = associes;
		this.MBID = mBID;
	}

	public Requete(String MBID, String entiteVoulu) {
		this.self = true;
		this.MBID = MBID;
		this.entiteVoulu = entiteVoulu;

	}

	public void setUrlToRead(String urlToRead) {
		this.urlToRead = urlToRead;
	}

	public String getUrlToRead() {
		return urlToRead;
	}

	public int getNbResultatsAttendus() {
		return nbResultatsAttendus;
	}

	//Permet de constituer l'url que nous allons rechercher
	public void makeRequete() {
		this.urlToRead = "http://musicbrainz.org/ws/2/";
		this.urlToRead = this.urlToRead + this.entiteVoulu;
		if (this.self) {
			this.urlToRead = this.urlToRead + "/" + this.MBID+"?fmt=json";

		} else {
			if (this.associciated) {

				this.urlToRead = this.urlToRead + "/" + this.MBID + "?inc=" + this.associes;

			} else {
				this.urlToRead = this.urlToRead + "?query=" + this.entree;
				if (!this.exact) {
					this.urlToRead = this.urlToRead + "~";
				} else if (!this.complet) {
					this.urlToRead = this.urlToRead + "*";
				}
				if (this.offset != 0) {
					this.urlToRead = this.urlToRead + "&offhis.set=" + offset;
				}
			}
			this.urlToRead = this.urlToRead + "&limit=" + Integer.toString(this.nbResultatsAttendus) + "&fmt=json";
			this.urlToRead = this.urlToRead.replaceAll(" ", "+");
		}
	}

	//Permet de recuperer le fichier Json lié a l'objet requete

	public String getJson() {
		try {
			makeRequete();
			String urlToRead = this.urlToRead;
			StringBuilder result = new StringBuilder();
			URL url = new URL(urlToRead);
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
			return "";
		}

	}

}
