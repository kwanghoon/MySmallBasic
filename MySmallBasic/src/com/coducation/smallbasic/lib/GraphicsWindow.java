package com.coducation.smallbasic.lib;

import java.util.ArrayList;

import com.coducation.smallbasic.Value;

public class GraphicsWindow {
	public static void Clear(ArrayList<Value> args) {
		// 그래픽 창에 표시된 모든 것을 지움

	}

	public static void DrawBoundText(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 지정한 길이 범위 안에서 글자를 표시함

	}

	public static void DrawEllipse(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 지정한 펜으로 타원형을 그림

	}

	public static void DrawImage(ArrayList<Value> args) {
		// 지정한 위치의 그림 파일을 불러와 그래픽 창의 지정한 위치에 표시함

	}

	public static void DrawLine(ArrayList<Value> args) {
		// 한 지점에서 다른 지점으로 직선을 그림

	}

	public static void DrawRectangle(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 지정한 펜으로 사각형을 그림

	}

	public static void DrawResizedImage(ArrayList<Value> args) {
		// 지정한 위치의 그림 파일을 불러와 지정한 크기로 바꿔 그래픽 창의 지정한 위치에 표시

	}

	public static void DrawText(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 지정한 길이의 범위 안에서 글자를 표시함

	}

	public static void DrawTriangle(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 지정된 펜으로 삼각형을 그림

	}

	public static void FillEllipse(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 색이 채워진 타원형을 그림

	}

	public static void FillRectangle(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 색이 채워진 사각형을 그림

	}

	public static void FillTriangle(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 색이 채워진 삼각형을 그림

	}

	public static Value GetColorFromRGB(ArrayList<Value> args) {
		// 빨강, 초록, 파랑을 조합해 색을 만듬
		
		return null;
	}

	public static Value GetPixel(ArrayList<Value> args) {
		// 지정한 x와 y 좌표의 픽셀 색상값을 rgb 형식으로 가져옴
		
		return null;
	}

	public static Value GetRandomColor(ArrayList<Value> args) {
		// 표현 가능한 임의의 색을 가져옴
		
		return null;
	}

	public static void Hide(ArrayList<Value> args) {
		// 그래픽 창을 숨김

	}

	public static void SetPixel(ArrayList<Value> args) {
		// 지정한 색을 사용하여 지정한 x와 y 좌표상에 점을 찍음

	}

	public static void Show(ArrayList<Value> args) {
		// 그래픽 창을 표시함

	}

	public static void ShowMessage(ArrayList<Value> args) {
		// 메시지 상자를 표시함

	}
	public static Value Height;
	public static Value Width;
	public static Value BackgroundColor;
	public static Value Title;
	public static Value PenColor;
	public static Value PenWidth;
	public static Value BrushColor;
}
