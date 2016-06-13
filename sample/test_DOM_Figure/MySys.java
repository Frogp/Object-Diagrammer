package figure;

import java.io.*;
import java.lang.*;
import java.awt.*;
/** �� Ŭ������ ���� ���̺귯�� ����� �ϴ� ���� �Լ����� ��Ƴ��� Ŭ�����̴�.
 */
public final class MySys {
	/** �� ���� �߿��� �ִ밪�� ã�� �Լ��̴�.
	 */
	public static int max(int a,int b,int c) {
		int max = a;
		if (max < b) max = b;
		if (max < c) max = c;
		return max;
	}
	/** �𼭸��� �簢���� �׸��� �Լ��̴�.
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
	/** ������ �׸��� �Լ��̴�.
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
	/** ���� �׸��� �Լ��̴�.
	 */
	public static void myDrawLine(Graphics g,int x1,int y1,int x2,int y2) {
		g.drawLine(x1,y1,x2,y2);
	}
	/** �簢���� �׸��� �Լ��̴�.
	 */
	public static void myDrawRectangle(Graphics g,int x,int y,int w,int h) {
		g.drawRect(x,y,w,h);
	}
	/** ���� �׸��� �Լ��̴�.
	 */
	public static void myDrawOval(Graphics g,int x,int y,int w,int h) {
		g.drawOval(x,y,w,h);
	}
	/** �ձ� �簢���� �׸��� �Լ��̴�.
	 */
	public static void myDrawRoundbox(Graphics g,int x,int y,int w,int h,int arcWidth, int arcHeight) {
		g.drawRoundRect(x,y,w,h,arcWidth,arcHeight);
	}
	/** ä���� �ձ� �簢���� �׸��� �Լ��̴�.
	 */
	public static void myFillRoundbox(Graphics g,int x,int y,int w,int h,int arcWidth, int arcHeight) {
		g.fillRoundRect(x,y,w,h,arcWidth,arcHeight);
	}
	/** ä���� ���� �׸��� �Լ��̴�.
	 */
	public static void myFillOval(Graphics g,int x,int y,int w,int h) {
		g.fillOval(x,y,w,h);
	}
	/** ä���� �ٰ����� �׸��� �Լ��̴�.
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
	/** ǥ�� ��¹��̴�.
	 */
	public static void printf(String s) {
		System.out.println(s);
	}
	/** ���� ��¹��̴�.
	 */
	public static void myprinterr(String s) {
		System.out.println(s);
	}
	/** �� ���� �Է� �Լ��̴�.
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
	/** �� ���� ��� �Լ��̴�.
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
	/** �� ������ �Ÿ��� ���ϴ� �Լ��̴�. 
	 */
	public static int distance(int x1,int y1,int x2,int y2) {
		int len1 = x2 - x1;
		int len2 = y2 - y1;
		int len = (int)Math.sqrt((double)(len1*len1+len2*len2));
		return len;
	}
	/** �������� ���ϴ� �Լ��̴�
	 */
	public static double calculateRadius(double theta, double w, double h) {
		double r = (sqr(h)*sqr(w))/(sqr(h)*sqr(Math.sin(theta))*sqr(w)*sqr(Math.cos(theta)));
		return ((double)Math.sqrt(r));
	}
	/** �ڽ� ���� ���ϴ� �Լ��̴�.
	 */
	public static double sqr(double x) {
		return (x*x);
	}
	/** ������ ��ȣ ���� ���ϴ� �Լ��̴�.
	 */
	public static int sign(int x) {
		if(x<0) return (-1);
		else return(1);
	}
	/** �Ǽ��� ��ȣ ���� ���ϴ� �Լ��̴�.
	 */
	public static double sign(double x) {
		if(x<0) return (-1);
		else return(1);
	}
	/** ����Ʈ ���ڿ��� ���̸� ���ϴ� �Լ��̴�.
	 */
	public static int strlen(char tmp[], int index) {
		int count=0;
		while(tmp[index] != '\0'){
			index++;
			count++;
		}
		return count;
	}
	/** ����Ʈ ���ڿ��� �����ϴ� �Լ��̴�.
	 */
	public static void strcpy(char s[],char t[]) {
		int i = 0;
		while ((s[i] = t[i]) != '\0') i++;
	}
	/** ����Ʈ ���ڿ��� �����ϴ� �Լ��̴�.
	 */
	public static void strcat(char s[],char t[]) {
		int i = 0;
		int j = 0;
		while (s[i] != '\0') i++;
		while ((s[i++] = t[j++]) != '\0');
	}
	/** ����Ʈ ���ڿ��� ���ϴ� �Լ��̴�.
	 */
	public static int strcmp(char s[],char t[]) {
		int i = 0;
		while (s[i] == t[i])
			if (s[i++] == '\0')
				return 0;
		return(s[i] - t[i]);
	}
	/** ����Ʈ ���ڿ��� �־��� ��ġ���� ���ϴ� �Լ��̴�.
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
	/** ��Ʈ���� ����Ʈ ���ڿ��� ��ȯ�ϴ� �Լ��̴�.
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
	/** ����ִ� ���ڿ��ΰ� �˻��ϴ� �Լ��̴�.
	 */
	public static boolean emptyString(char s[]) {
		int len = strlen(s,0);
		for (int i = 0; i < len; i++) {
			if (s[i] != ' ') return false;
		}
		return true;
	}
	/** ����Ʈ ���ڿ��� ��� ��Ҹ� �Ҵ��Ͽ� �����ϴ� �Լ��̴�.
	 */
	public static char[] mystrdup(char s[]) {
		char p[] = new char[strlen(s,0) + 1];
		strcpy(p,s);
		return p;
	}
}