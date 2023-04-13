package ru.antonsibgatulin.informatic.configLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import ru.antonsibgatulin.informatic.InformaticOperationModel;

public class InformaticLoaderConfig {
	
	public HashMap<String,InformaticOperationModel> hashMap = new HashMap<>();
	public ArrayList<InformaticOperationModel> informaticOperationModels = new ArrayList<>();
	
	public InformaticLoaderConfig() {
		String path = "config/informatic";
		File [] files = new File(path).listFiles();
		for(int i =0;i<files.length;i++) {
			File file = files[i];
			BufferedReader bufferedReader = null;
			try {
				bufferedReader = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String all = "";
			String piece = null;
			try {
				while((piece = bufferedReader.readLine())!=null) {
					all = all+ piece;
				}
				JSONObject jsonObject = new JSONObject(all);
				String id = jsonObject.getString("id");
				JSONArray buttons = jsonObject.getJSONArray("buttons");
				JSONArray functions = jsonObject.getJSONArray("functions");
				InformaticOperationModel informaticOperationModel = new InformaticOperationModel(id, buttons,functions);
				this.informaticOperationModels.add(informaticOperationModel);
				this.hashMap.put(id, informaticOperationModel);
				System.out.println(id+" config was loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
