package com.coducation.smallbasic.lib;

import java.util.ArrayList;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Flickr {
	
	public static Value GetPictureOfMoment() {
		// Flickr 의 현재 사진에 대한 파일 url을 가져옴

		return new StrV("URL");
	}

	public static Value GetRandomPicture(ArrayList<Value> args) {
		// 지정된 태그가 있는 무작위 사진의 url 을 가져옴

		if (args.size() == 1) {
			
				if(args.get(0) instanceof StrV || args.get(0) instanceof DoubleV) {
					
				}
				else throw new InterpretException("GetRandomPicture: Unexpected arg(0)");
			
		}
		else throw new InterpretException("GetRandomPicture: Unexpected # of args: " + args.size());

		return new StrV("URL");
	}
}
