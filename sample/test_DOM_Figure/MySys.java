package figure;

import java.io.*;
import java.lang.*;
import java.awt.*;
/** 이 클래스는 범용 라이브러리 기능을 하는 정적 함수들을 모아놓은 클래스이다.
 */
public final class MySys {
	/** 세 정수 중에서 최대값을 찾는 함수이다.
	 */
	public static int max(int a,int b,int c) {
		int max = a;
		if (max < b) max = b;
		if (max < c) max = c;
		return max;
	}
	/** 모서리에 사각점을 그리는 함수이다.
	 */
	public static void myDrawDots(Graphics g,CPoint pt[],int n) {
		int gap = 3;
		for (int i = 0; i < n; i++) {
			int x = pt[i].x - gap;
			int y = pt[i].y - gap;
			int w = 2 * gap;
			int h = 2 * gap;	
			g.fillRect(x,y,w,h);
		}
	}
	/** 점선을 그리는 함수이다.
	 */
	public static void myDrawDashedLine(Graphics g,int x1,int y1,int x2,int y2) {
		int dashedLength = 5;
		int w = x2 - x1;
		int h = y2 - y1;
		int length = (int)(Math.sqrt((double)(w*w + h*h)));
		int count = length / 5;
		if (count == 0) return;
		int remainder = length - 5 * count;
		int startX = x1;
		int startY = y1;
		int wStep = w / count;
		int hStep = h / count;
		int increX,increY;
		if (w >= 0) increX = 1;
		else increX = -1;
		if (h >= 0) increY = 1;
		else increY = -1;
		for (int i = 0; i < count; i++) {
			if (i%2 == 0) {
				g.drawLine(startX,startY,startX+wStep,startY+hStep);
			}
			startX = startX + wStep;
			startY = startY + hStep;
		}
	}
	/** 선을 그리는 함수이다.
	 */
	public static void myDrawLine(Graphics g,int x1,int y1,int x2,int y2) {
		g.drawLine(x1,y1,x2,y2);
	}
	/** 사각형을 그리는 함수이다.
	 */
	public static void myDrawRectangle(Graphics g,int x,int y,int w,int h) {
		g.drawRect(x,y,w,h);
	}
	/** 원을 그리는 함수이다.
	 */
	public static void myDrawOval(Graphics g,int x,int y,int w,int h) {
		g.drawOval(x,y,w,h);
	}
	/** 둥근 사각형을 그리는 함수이다.
	 */
	public static void myDrawRoundbox(Graphics g,int x,int y,int w,int h,int arcWidth, int arcHeight) {
		g.drawRoundRect(x,y,w,h,arcWidth,arcHeight);
	}
	/** 채워진 둥근 사각형을 그리는 함수이다.
	 */
	public static void myFillRoundbox(Graphics g,int x,int y,int w,int h,int arcWidth, int arcHeight) {
		g.fillRoundRect(x,y,w,h,arcWidth,arcHeight);
	}
	/** 채워진 원을 그리는 함수이다.
	 */
	public static void myFillOval(Graphics g,int x,int y,int w,int h) {
		g.fillOval(x,y,w,h);
	}
	/** 채워진 다각형을 그리는 함수이다.
	 */
	public static void myFillPolygon(Graphics g,CPoint pt[],int n) {
		int x[] = new int[n];
		int y[] = new int[n];
		for (int i = 0; i < n; i++) {
			x[i] = pt[i].x;
			y[i] = pt[i].y;
		}
		g.fillPolygon(x,y,n);
	}
	/** 표준 출력문이다.
	 */
	public static void printf(String s) {
		System.out.println(s);
	}
	/** 에러 출력문이다.
	 */
	public static void myprinterr(String s) {
		System.out.println(s);
	}
	/** 한 문자 입력 함수이다.
	 */
	public static char getc(FileReader fp) {
		char[] data = new char[1];
		data[0] = ' ';
		try {
			int nread = 0;
			int trycount = 0;
			while((nread = fp.read(data,0,1)) < 1) {
				trycount++;
				if (trycount > 10) {
					return ' ';
				}
			}
			if (nread > 1) {
				System.out.println("Something wrong is Mysys.getc()");
			}
		} catch (IOException e) {
		}
		return data[0];
	}
	/** 한 문자 출력 함수이다.
	 */
	public static void putc(char c,FileWriter fp) {
		char[] data = new char[1];
		data[0] = c;	
		try {
			fp.write(data,0,1);
			fp.flush();
		} catch (IOException e) {
		}
	}
	/** 두 점간의 거리를 구하는 함수이다. 
	 */
	public static int distance(int x1,int y1,int x2,int y2) {
		int len1 = x2 - x1;
		int len2 = y2 - y1;
		int len = (int)Math.sqrt((double)(len1*len1+len2*len2));
		return len;
	}
	/** 반지름을 구하는 함수이다
	 */
	public static double calculateRadius(double theta, double w, double h) {
		double r = (sqr(h)*sqr(w))/(sqr(h)*sqr(Math.sin(theta))*sqr(w)*sqr(Math.cos(theta)));
		return ((double)Math.sqrt(r));
	}
	/** 자승 값을 구하는 함수이다.
	 */
	public static double sqr(double x) {
		return (x*x);
	}
	/** 정수의 부호 값을 구하는 함수이다.
	 */
	public static int sign(int x) {
		if(x<0) return (-1);
		else return(1);
	}
	/** 실수의 부호 값을 구하는 함수이다.
	 */
	public static double sign(double x) {
		if(x<0) return (-1);
		else return(1);
	}
	/** 바이트 문자열의 길이를 구하는 함수이다.
	 */
	public static int strlen(char tmp[], int index) {
		int count=0;
		while(tmp[index] != '\0'){
			index++;
			count++;
		}
		return count;
	}
	/** 바이트 문자열을 복사하는 함수이다.
	 */
	public static void strcpy(char s[],char t[]) {
		int i = 0;
		while ((s[i] = t[i]) != '\0') i++;
	}
	/** 바이트 문자열을 연결하는 함수이다.
	 */
	public static void strcat(char s[],char t[]) {
		int i = 0;
		int j = 0;
		while (s[i] != '\0') i++;
		while ((s[i++] = t[j++]) != '\0');
	}
	/** 바이트 문자열을 비교하는 함수이다.
	 */
	public static int strcmp(char s[],char t[]) {
		int i = 0;
		while (s[i] == t[i])
			if (s[i++] == '\0')
				return 0;
		return(s[i] - t[i]);
	}
	/** 바이트 문자열을 주어진 위치부터 비교하는 함수이다.
	 */
	public static int strcmp(char s[],char t[],int i,int j) {
		while (s[i] == t[j])
			if (s[i] == '\0')
				return 0;
			else {
				i++; j++;
			}
		return(s[i] - t[j]);
	}
	/** 스트링을 바이트 문자열로 변환하는 함수이다.
	 */
	public static char[] toCharArray(String s) {
		int len = s.length();
		char p[] = new char[len+1];
		for (int i = 0; i < len; i++) {
			p[i] = s.charAt(i);
		}	
		p[len] = '\0';
		return p;
	}
	/** 비어있는 문자열인가 검사하는 함수이다.
	 */
	public static boolean emptyString(char s[]) {
		int len = strlen(s,0);
		for (int i = 0; i < len; i++) {
			if (s[i] != ' ') return false;
		}
		return true;
	}
	/** 바이트 문자열을 기억 장소를 할당하여 복사하는 함수이다.
	 */
	public static char[] mystrdup(char s[]) {
		char p[] = new char[strlen(s,0) + 1];
		strcpy(p,s);
		return p;
	}
}