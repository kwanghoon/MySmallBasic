package com.coducation.smallbasic.lib;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Network {

	public static Value DownloadFile(ArrayList<Value> args) {
		// 네트워크상의 파일을 로칼상의 임시파일로 다운로드하여 임시파일이름을 반환
		String path = "";
		if (args.size() == 1) {
			try {
				if(args.get(0) instanceof StrV) {


					URL url = new URL(args.get(0).toString());
					InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
					
					java.io.File tmp = java.io.File.createTempFile("tmp", null);
					path = tmp.getCanonicalPath();
					
					OutputStreamWriter osr = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
					
					int a;
					while((a = isr.read())!=-1) {
						osr.write((char)a);
					}
					
				}
				else throw new InterpretException("DownloadFile: Unexpected arg(0)");
			}
			catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else throw new InterpretException("DownloadFile: Unexpected # of args: " + args.size());

		return new StrV(path);
	}

	public static Value GetWebPageContents(ArrayList<Value> args) {
		// 지정된 웹 페이지의 내용을 가져옴

		StringBuilder s = new StringBuilder("");
		
		if (args.size() == 1) {
			try {
				if(args.get(0) instanceof StrV) {

					URL url = new URL(args.get(0).toString());
					InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
					int a;
					while((a = isr.read())!=-1)
						s.append((char)a);
					
				}
				else throw new InterpretException("GetWebPageContents: Unexpected arg(0)");

			}
			catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else throw new InterpretException("GetWebPageContents: Unexpected # of args: " + args.size());

		return new StrV(s.toString());
	}
}
