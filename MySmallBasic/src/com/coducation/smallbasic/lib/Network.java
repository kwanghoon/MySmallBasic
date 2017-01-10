package com.coducation.smallbasic.lib;

import java.util.ArrayList;

import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Network {
	
	public static Value DownloadFile(ArrayList<Value> args) {
		// 네트워크상의 파일을 로칼상의 임시파일로 다운로드하여 임시파일이름을 반환

		if (args.size() == 1) {
			
				if(args.get(0) instanceof StrV) {
					
				}
				else throw new InterpretException("DownloadFile: Unexpected arg(0)");
			
		}
		else throw new InterpretException("DownloadFile: Unexpected # of args: " + args.size());

		return new StrV("TempFile");
	}

	public static Value GetWebPageContents(ArrayList<Value> args) {
		// 지정된 웹 페이지의 내용을 가져옴

		if (args.size() == 1) {
			
				if(args.get(0) instanceof StrV) {
					
				}
				else throw new InterpretException("GetWebPageContents: Unexpected arg(0)");
			
		}
		else throw new InterpretException("GetWebPageContents: Unexpected # of args: " + args.size());

		return new StrV("FileContents");
	}
}
