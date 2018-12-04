import java.io.IOException;
import java.util.Vector;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class LectureJson {
	
	private static String[] gareName = {"AVI", "VAL"};
	
	public static void main(String[] args) {

		Gare gare[];
		gare = new Gare[gareName.length];
		for(int i=0;i<gareName.length;i++) {
			gare[i] = new Gare();
			gare[i].setName(gareName[i]);
			getObjects(gare[i]);
			
			gare[i].filtreTGV();
			gare[i].filtreRetard30();
			
			System.out.println("RESULT : " + i);
			System.out.println(gare[i].toString());
		}
	}

	private static void getObjects(Gare gare) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		Requete requete = new Requete(gare.getName());
		String JSON = requete.getJson();
		
		JSON = JSON.replaceAll("-", "");
		
		try {			
			gare.setJsonFile(JSON);
			gare.setInfoTrains();
			
		} catch (Exception e) {
			System.err.println(" pb lecture JSON de type IOe exception");
			e.printStackTrace();
		}
	}
}
