import java.io.IOException;
import java.util.Vector;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;


public class LectureJson {
	
//	private static String[] gareName = {"AVI", "VAL"};
	private static String[] gareName = {"BNV","MBN","CGV","NVS","LCM","PAU","BYE","EVX","CMZ","CTX","DKQ","PPN","SEN","CRL","BGD","CAE","LRY","LIS","SBC","LRT","VER","MXR","BRT","MPW","ANG","BLG","LRE","NRT","LBE","QPR","VAN","LNS","FHS","BGS","BLO","AGN","TDE","CBU","SMP","LAL","VSN","LHA","STE","LAC","BFC","LIB","ORL","TLN","MLH","ATB","CAN","SXA","MTR","BGB","VAL","SRV","ANY","ARR","DOI","VDT","PNO","THL","CPE","CLF","VHY","BZS","NMS","MOS","BSM","RMS","NBN","AGD","CRT","PST","LAB","LIM","TOY","ARY","TGM","TGL","CMR","BFB","HPI","BIZ","DAX","SNA","CNE","LDS","LYD","PMP","MZE","MSE","AVV","RYT","PNO","RES","LEN","PSL","SPC","TRS","ASL","NTS","TSE","PAZ","MSC","RRD","GRE","LLF","BXJ","LEW","PES","PES_TRANS","PBY","MPL","NIC","AMS","MLV","LPR","NCY","PLY","PLY_TRANS","AXV","DJV","STG","PVA","MSF"};
	
	public static void main(String[] args) {

		int cmp = 0;
		
		Gare gare[];
		gare = new Gare[gareName.length];
		for(int i=0;i<gareName.length;i++) {
			gare[i] = new Gare();
			gare[i].setName(gareName[i]);
			if(getObjects(gare[i])==-1) {
				cmp++;
				if(cmp>5) {
					System.out.println("Je suis un robot ...");
					break;
				}
			}
			gare[i].filtreTGV();
			gare[i].filtreRetard30();
			if(gare[i].getTrains().size()!=0)
				System.out.println("\n"+gare[i].toString());
			else
				System.out.print(".");

		}
		System.out.println("TERMINUS");
		
	}

	private static int getObjects(Gare gare) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		Requete requete = new Requete(gare.getName());
		String JSON = requete.getJson();
		
		JSON = JSON.replaceAll("-", "");
		
		try {		
			if(JSON.equals("Error"))
				throw new Exception("robot");
			gare.setJsonFile(JSON);
			gare.setInfoTrains();
			
		} catch (Exception e) {
			System.err.println(" pb lecture JSON de type IOe exception");
			//e.printStackTrace();
			return -1;
		}
		return 0;
	}
}
