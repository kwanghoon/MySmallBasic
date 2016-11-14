package com.coducation.smallbasic.lib;

import com.coducation.smallbasic.Value;

public class GraphicsWindow {
	public static void Clear() {
		// 그래픽 창에 표시된 모든 것을 지움

	}

	public static void DrawBoundText(Value x, Value y, Value width, Value height) {
		// 그래픽 창의 지정한 위치에 지정한 길이 범위 안에서 글자를 표시함

	}

	public static void DrawEllipse(Value x, Value y, Value width, Value height) {
		// 그래픽 창의 지정한 위치에 지정한 펜으로 타원형을 그림

	}

	public static void DrawImage(Value imageName, Value x, Value y) {
		// 지정한 위치의 그림 파일을 불러와 그래픽 창의 지정한 위치에 표시함

	}

	public static void DrawLine(Value x1, Value y1, Value x2, Value y2) {
		// 한 지점에서 다른 지점으로 직선을 그림

	}

	public static void DrawRectangle(Value x, Value y, Value width, Value height) {
		// 그래픽 창의 지정한 위치에 지정한 펜으로 사각형을 그림

	}

	public static void DrawResizedImage(Value imageName, Value x, Value y, Value width, Value height) {
		// 지정한 위치의 그림 파일을 불러와 지정한 크기로 바꿔 그래픽 창의 지정한 위치에 표시

	}

	public static void DrawText(Value x, Value y, Value text) {
		// 그래픽 창의 지정한 위치에 지정한 길이의 범위 안에서 글자를 표시함

	}

	public static void DrawTriangle(Value x1, Value y1, Value x2, Value y2, Value x3, Value y3) {
		// 그래픽 창의 지정한 위치에 지정된 펜으로 삼각형을 그림

	}

	public static void FillEllipse(Value x, Value y, Value width, Value height) {
		// 그래픽 창의 지정한 위치에 색이 채워진 타원형을 그림

	}

	public static void FillRectangle(Value x, Value y, Value width, Value height) {
		// 그래픽 창의 지정한 위치에 색이 채워진 사각형을 그림

	}

	public static void FillTriangle(Value x1, Value y1, Value x2, Value y2, Value x3, Value y3) {
		// 그래픽 창의 지정한 위치에 색이 채워진 삼각형을 그림

	}

	public static Value GetColorFromRGB(Value red, Value green, Value blue) {
		// 빨강, 초록, 파랑을 조합해 색을 만듬
		
		return null;
	}

	public static Value GetPixel(Value x, Value y) {
		// 지정한 x와 y 좌표의 픽셀 색상값을 rgb 형식으로 가져옴
		
		return null;
	}

	public static Value GetRandomColor() {
		// 표현 가능한 임의의 색을 가져옴
		
		return null;
	}

	public static void Hide() {
		// 그래픽 창을 숨김

	}

	public static void SetPixel(Value x, Value y, Value color) {
		// 지정한 색을 사용하여 지정한 x와 y 좌표상에 점을 찍음

	}

	public static void Show() {
		// 그래픽 창을 표시함

	}

	public static void ShowMessage(Value text, Value title) {
		// 메시지 상자를 표시함

	}
	public static Value Height;
	public static Value Width;
}
