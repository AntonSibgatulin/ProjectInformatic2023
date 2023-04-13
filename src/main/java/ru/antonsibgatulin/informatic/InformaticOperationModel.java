package ru.antonsibgatulin.informatic;

import java.util.ArrayList;

import org.json.JSONArray;

public class InformaticOperationModel {
	public ArrayList<String> operationList = new ArrayList<>();
	public String language = null;
	public ELanguage eLanguage = null;
	public JSONArray buttons = null;
	public JSONArray functions = null;

	public InformaticOperationModel(String language,JSONArray buttons,JSONArray functions) {
		this.eLanguage = ELanguage.valueOf(language);
		this.language = language;
		this.buttons = buttons;
		this.functions = functions;

	/*	for(int i =0;i<jsonArray.length();i++) {
			operationList.add(jsonArray.getString(i));
		}

	 */
		
		
	}
	
	
	
}
