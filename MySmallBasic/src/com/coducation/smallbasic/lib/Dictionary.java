package com.coducation.smallbasic.lib;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Dictionary { // 옥스퍼드사전 이용

	private static String u;
	private static String app_id = "";
	private static String app_key = "";
	private static StringBuilder def = new StringBuilder(); // 토탈정의

	public static Value GetDefinition(ArrayList<Value> args) {
		// 해당하는 단어의 정의를 가져옴
		u = "https://od-api.oxforddictionaries.com:443/api/v1/entries/en/";

		if (args.size() == 1) {

			if(args.get(0) instanceof StrV || args.get(0) instanceof DoubleV) {
				u += args.get(0).toString().toLowerCase(); // 단어설정
				
				try {
					DataInputStream dis = new DataInputStream(new FileInputStream("resource/Dictionary/api_key.txt"));
					app_id = dis.readLine(); // api_id 설정
					app_key = dis.readLine(); // api_key 설정
					dis.close();

					URL url = new URL(u);
					HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
					urlConnection.setRequestProperty("Accept","application/json");
					urlConnection.setRequestProperty("app_id",app_id);
					urlConnection.setRequestProperty("app_key",app_key);

					// read the output from the server
					InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream(), "UTF-8");

					JSONParser jsonParser = new JSONParser();
					JSONObject object = (JSONObject) jsonParser.parse(reader);

					JSONArray results_array = (JSONArray) object.get("results");

					JSONArray lexicalEntries_array = (JSONArray) ((JSONObject)results_array.get(0)).get("lexicalEntries");

					for(int i=0; i<lexicalEntries_array.size(); i++){
						JSONObject lexicalEntries_object = (JSONObject) lexicalEntries_array.get(i);

						String word = (String) lexicalEntries_object.get("text");
						String lexicalCategory = (String) lexicalEntries_object.get("lexicalCategory");

						JSONArray pronunciations_array = (JSONArray) lexicalEntries_object.get("pronunciations");
						String phoneticSpelling = (String) ((JSONObject)pronunciations_array.get(0)).get("phoneticSpelling");
						def.append(word+" [ "+phoneticSpelling+" ] "+" ( "+lexicalCategory+" )"+"\n\n");

						JSONArray entries_array = (JSONArray) lexicalEntries_object.get("entries");
						JSONArray senses_array = (JSONArray) ((JSONObject)entries_array.get(0)).get("senses");

						for(int j=0; j<senses_array.size(); j++) {
							JSONObject senses_object = (JSONObject) senses_array.get(j);

							JSONArray definitions_array = (JSONArray) senses_object.get("definitions");	
							def.append((j+1)+"."+definitions_array.get(0).toString()+"\n");

							JSONArray examples_array = (JSONArray) senses_object.get("examples");

							if(examples_array!=null) {
								for(int k=0; k<examples_array.size(); k++) {
									JSONObject examples_object = (JSONObject) examples_array.get(k);
									def.append(examples_object.get("text")+"\n");
								}
							}

							JSONArray subsenses_array = (JSONArray) senses_object.get("subsenses"); //부정의

							if(subsenses_array!=null) {
								for(int k=0; k<subsenses_array.size(); k++) {
									JSONObject subsenses_object = (JSONObject) subsenses_array.get(k);

									JSONArray subdefinitions_array = (JSONArray) subsenses_object.get("definitions");	
									def.append((j+1)+"-"+(k+1)+"."+subdefinitions_array.get(0).toString()+"\n");

									JSONArray subexamples_array = (JSONArray) subsenses_object.get("examples");

									if(subexamples_array!=null) {
										for(int h=0; h<subexamples_array.size(); h++) {
											JSONObject subexamples_object = (JSONObject) subexamples_array.get(h);
											def.append(subexamples_object.get("text")+"\n");
										}
									}
									def.append("\n");
								}
							}
							def.append("\n");
						}
						def.append("\n\n");
					}

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} 

			}
			else throw new InterpretException("GetDefinition: Unexpected arg(0)");

		}
		else throw new InterpretException("GetDefinition: Unexpected # of args: " + args.size());

		return new StrV(def.toString());
	}

}
