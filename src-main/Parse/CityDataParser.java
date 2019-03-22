package Parse;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CityDataParser {

	public List<List<String>> parse(String path){
		List<List<String>> citiesData = new ArrayList<List<String>>();
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			String newline;
			while ((newline = br.readLine())!= null){
				List<String> citydata = new ArrayList<String>();
				for(String s : newline.split(","))
					citydata.add(s);
				citiesData.add(citydata);
			}
			
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("path: " + path, e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("path: " + path +"is empty", e);
		}
		return citiesData;
	}
	
}
