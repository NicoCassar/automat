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
	private static String[] gareFullName = {"Besançon Viotte","Montauban Ville Bourbon","Champagne-Ardenne TGV","Nevers","Le Creusot - Montceau-les-Mines - Montchanin TGV","Pau","Bayonne","Évreux Normandie","Charleville-Mézières","Châteauroux","Dunkerque","Perpignan","Saint-Étienne Châteaucreux","Chambéry - Challes-les-Eaux","Bellegarde","Caen","La Roche-sur-Yon","Lisieux","Saint-Brieuc","Lorient Bretagne Sud","Vierzon","Morlaix","Brest","Massy TGV","Angoulême","Brive-la-Gaillarde","La Rochelle","Niort","La Baule-Escoublac","Quimper","Vannes","Lens","Calais - Fréthun","Bourges","Blois - Chambord","Agen","Trouville - Deauville","Cherbourg","Saint-Malo","Laval","Valenciennes","Le Havre","Sète","Les Arcs - Draguignan","Besançon Franche-Comté TGV","Libourne","Orléans","Toulon","Mâcon Loché TGV","Antibes","Cannes","Lyon Saint-Exupéry TGV","Montélimar","Bourg-en-Bresse","Valence TGV Rhône-Alpes Sud","Saint-Raphaël Valescure","Annecy","Arras","Douai","Vendôme - Villiers-sur-Loir","Paris Gare du Nord Surface Banlieue","Thionville","Compiègne","Clermont-Ferrand","Vichy","Béziers","Nîmes","Moûtiers - Salins - Brides-les-Bains","Bourg-Saint-Maurice","Reims","Narbonne","Agde","Châtellerault","Poitiers","Les Aubrais","Limoges Bénédictins","Troyes","Auray","Meuse TGV","Lorraine TGV","Colmar","Belfort - Montbéliard TGV","TGV Haute Picardie","Biarritz","Dax","Saint-Nazaire","Carcassonne","Lourdes","Lyon Part Dieu","Paris Montparnasse Hall 1","Paris Montparnasse Hall 2","Metz Ville","Mulhouse","Avignon TGV","Aéroport Charles de Gaulle 2 TGV","Paris Gare du Nord","Rennes","Le Mans","Paris Saint-Lazare","Paris Saint-Lazare","Saint-Pierre-des-Corps","Tours","Angers Saint-Laud","Nantes","Toulouse Matabiau","Paris Austerlitz","Marseille Saint-Charles","Rouen Rive Droite","Grenoble","Lille Flandres","Bordeaux Saint-Jean","Lille Europe","Paris Est","Paris Est","Paris Bercy Bourgogne - Pays d'Auvergne","Montpellier Saint-Roch","Nice","Amiens","Marne-la-Vallée Chessy","Lyon Perrache","Nancy","Paris Gare de Lyon Hall 1","Paris Gare de Lyon Hall 2","Aix-en-Provence TGV","Dijon","Strasbourg","Paris Montparnasse Vaugirard","Paris Gare du Nord transmanche","Montpellier Sud de France"};
	
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
			if(gare[i].getTrains().size()!=0) {

				System.out.print("\n"+gareFullName[i] +"\t");
				System.out.println(gare[i].toString());
			}
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
