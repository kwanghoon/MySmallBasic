package com.coducation.smallbasic.lib;

import java.util.ArrayList;

import com.coducation.smallbasic.Value;

public class Shapes {
	public static void AddRectangle(ArrayList<Value> args) {
		// width, height
		// GraphicsWiindow에 사각형을 추가한 창을 반환
		
	}
	
	public static void AddEllipse(ArrayList<Value> args) {
		// width, height
		// GraphicsWindow에 원을 추가한 창을 반환
		
	}
	
	public static void AddTriangle(ArrayList<Value> args) {
		// x1, y1, x2, y2, x3, y3
		// GraphicsWindow에 삼각형을 추가한 창을 반환
		
	}
	
	public static void AddLine(ArrayList<Value> args) {
		// x1, y1, x2, y2
		// GraphicsWindow에 선을 추가한 창을 반환
		
	}
	
	public static void AddImage(ArrayList<Value> args) {
		// imageName
		// GraphicsWindow에 이미지를 추가한 창을 반환
		
	}
	
	public static void AddText(ArrayList<Value> args) {
		// text
		// GraphicsWindow에 text를 추가한 창을 반환
		
	}
	
	public static void SetText(ArrayList<Value> args) {
		// shapeName, text
		// shapeName에 해당하는 shape에 text를 설정함
		
	}
	
	public static void Remove(ArrayList<Value> args) {
		// shapeName
		// shapeName에 해당하는 shape를 삭제함
		
	}
	
	public static void Move(ArrayList<Value> args) {
		// shapeName, x, y
		// shapeName에 해당하는 shape를 새로운 x, y로 이동
		
	}
	
	public static void Rotate(ArrayList<Value> args) {
		// shapeName, angle
		// shapeName에 해당하는 shape를 angle만큼 회전
		
	}
	
	public static void Zoom(ArrayList<Value> args) {
		// shapeName, scaleX, scaleY
		// shapeName에 해당하는 shape에 줌 레벨을 설정
		
	}
	
	public static void Animate(ArrayList<Value> args) {
		// shapeName, x, y, duration
		// shapeName에 해당하는 shape를 새로운 x, y로 움직임
		
	}
	
	public static Value GetLeft(ArrayList<Value> args) {
		// shapeName
		// shapeName에 해당하는 shape의 left를 가져옴
		
		return null;
	}
	
	public static Value GetTop(ArrayList<Value> args) {
		// shapeName
		// shapeName에 해당하는 shape의 top을 가져옴
		
		return null;
	}
	
	public static Value GetOpacity(ArrayList<Value> args) {
		// shapeName
		// shapeName에 해당하는 shape의 opaque를 반환(0과 100사이)
		
		return null;
	}
	
	public static void SetOpacity(ArrayList<Value> args) {
		// shapeName, level
		// shapeName에 해당하는 shape의 opaque 레벨 설정(0과 100 사이)
		
	}
	
	public static void HideShape(ArrayList<Value> args) {
		// shapeName
		// shapeName의 shape를 숨김
		
	}
	
	public static void ShowShape(ArrayList<Value> args) {
		// shapeName
		// shapeName의 shape를 보여줌
		
	}
}