package requestSide;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// La classe JSON permet, a l'aide de Jackson de remplir des objets avec le JSON obtenu avec la classe requete.
//Il remplira des objets de type Artist, Release et Recording qu'il 
//gardera en attribut utilisé ensuite dans la classe recherche entre autre

public class LectureJson {
	private String created;
	private int count;
	private int offset;
	private Artist[] artists;
	private Release[] releases;
	private Recording[] recordings;
	private Artist artist;
	private Release release;
	private Recording recording;

	public Artist[] getArtists() {
		return artists;
	}

	public String getCreated() {
		return created;
	}

	public int getCount() {
		return count;
	}

	public int getOffset() {
		return offset;
	}

	public Release[] getReleases() {
		return releases;
	}

	public Recording[] getRecordings() {
		return recordings;
	}

	// Permet de remplir les attributs de l'objet LectureJson à partir d'un objet
	// requete
	public void getObjects(Requete requete) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		String JSON = requete.getJson();// Recuperation du fichier Json lié à l'objet requete
		JSON = JSON.replaceAll("-", "");
		LectureJson lectureJson;
		try {
			lectureJson = objectMapper.readValue(JSON, LectureJson.class);
			this.artists = lectureJson.getArtists();
			this.recordings = lectureJson.getRecordings();
			this.releases = lectureJson.getReleases();

		} catch (JsonParseException e) {
			System.err.println(" fichier JSON impossible a parser");
			e.printStackTrace();
		} catch (JsonMappingException e) {
			System.err.println(" objet impossible a mapper");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(" pb lecture JSON de type IOe exception");
			e.printStackTrace();
		} // Recupération des infos dans le JSON dans les attributs de r
	}

	protected void getObjectById(String id, String entite) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		Requete requete = new Requete(id, entite);
		String JSON = requete.getJson();
		JSON = JSON.replaceAll("-", "");
		try {
			if (entite == "artist") {
				this.artist = objectMapper.readValue(JSON, Artist.class);
			} else if (entite == "release") {
				this.release = objectMapper.readValue(JSON, Release.class);
			} else if (entite == "recording") {
				this.recording = objectMapper.readValue(JSON, Recording.class);
			}
		} catch (JsonParseException e) {
			System.err.println(" fichier JSON impossible a parser");
			e.printStackTrace();
		} catch (JsonMappingException e) {
			System.err.println(" objet impossible a mapper");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(" pb lecture JSON de type IOe exception");
			e.printStackTrace();
		}
	}

	/*
	 * public static void main(String[] args) { Requete requete = new
	 * Requete("artist","strom",10,false, false); LectureJson r=new LectureJson();
	 * r.getObjects(requete); Artist[] as = r.getArtists(); for (int
	 * i=0;i<requete.getNbResultatsAttendus();i++) {
	 * System.out.println(as[i].toString());}}
	 */

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public Release getRelease() {
		return release;
	}

	public void setRelease(Release release) {
		this.release = release;
	}

	public Recording getRecording() {
		return recording;
	}

	public void setRecording(Recording recording) {
		this.recording = recording;
	}

	public static void main(String[] args) {
		Requete requete = new Requete("release", "racine carree", 10, false, false);
		LectureJson r = new LectureJson();
		r.getObjects(requete);
		Release[] as = r.getReleases();
		Release release = as[0];
		for (int i = 0; i < requete.getNbResultatsAttendus(); i++) {
			System.out.println(as[i].toString());
		}
		ArtistCredit[] ar = release.getArtistcredit();
		Artist are = ar[0].getArtist();
		String artea = are.toString();
		System.out.println(artea);
	}
	
	

}
