package com.coducation.smallbasic.lib;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Flickr {

	private static String u;
	private static String photourl= "";

	public static Value GetPictureOfMoment(ArrayList<Value> args) {
		// Flickr 의 현재 사진에 대한 파일 url을 가져옴
		u = "https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=264406bcdc5f0c98741b219cb5726fe7&per_page=1";

		if (args.size() == 0) {
			try {

				DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
				DocumentBuilder parser = f.newDocumentBuilder();
				Document xmlDoc = parser.parse(u);

				Element root = xmlDoc.getDocumentElement(); // 현재 rsp
				String farm_id, server_id, id, secret;
				// farm-id, server-id, photo-id, secret 필요
				Node xmlNode1 = root.getElementsByTagName("photo").item(0);

				farm_id = ((Element) xmlNode1).getAttribute("farm");
				server_id = ((Element) xmlNode1).getAttribute("server");
				id = ((Element) xmlNode1).getAttribute("id");
				secret = ((Element) xmlNode1).getAttribute("secret");

				photourl = "https://farm"+farm_id+".staticflickr.com/"+server_id+"/"+id+"_"+secret+".jpg";

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 

		}
		else throw new InterpretException("ReadContents: Unexpected # of args: " + args.size());

		return new StrV(photourl);
	}

	public static Value GetRandomPicture(ArrayList<Value> args) {
		// 지정된 태그가 있는 무작위 사진의 url 을 가져옴
		u = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=264406bcdc5f0c98741b219cb5726fe7&tags=";

		if (args.size() == 1) {

			if(args.get(0) instanceof StrV || args.get(0) instanceof DoubleV) {
				u += args.get(0).toString(); // tag설정
				String page = (int)(java.lang.Math.random()*500)+"";
				u += "&per_page=1&page="+page; // page설정

				try {

					DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
					DocumentBuilder parser = f.newDocumentBuilder();
					Document xmlDoc = parser.parse(u);

					Element root = xmlDoc.getDocumentElement(); // 현재 rsp
					String farm_id, server_id, id, secret;
					// farm-id, server-id, photo-id, secret 필요
					Node xmlNode1 = root.getElementsByTagName("photo").item(0);

					farm_id = ((Element) xmlNode1).getAttribute("farm");
					server_id = ((Element) xmlNode1).getAttribute("server");
					id = ((Element) xmlNode1).getAttribute("id");
					secret = ((Element) xmlNode1).getAttribute("secret");

					photourl = "https://farm"+farm_id+".staticflickr.com/"+server_id+"/"+id+"_"+secret+".jpg";

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 

			}
			else throw new InterpretException("GetRandomPicture: Unexpected arg(0)");

		}
		else throw new InterpretException("GetRandomPicture: Unexpected # of args: " + args.size());

		return new StrV(photourl);
	}
}
