// Woman.java

//{{MODELER_BEFORE_CLASS(Woman) - DO NOT DELETE THIS LINE
//OODesigner will reverse the code here.

import java.io.*;

//Organization: 
//Author: 
//Date: Monday, January 05, 2004
//Super class: 
//Purpose of Woman class:
/**
*/

//}}MODELER_BEFORE_CLASS

class Woman extends Person 
{
// Attributes
    private double _happiness;

// Operations
    private void increaseHappiness(int howMuch) {
      // �ټ� �̻��� ������ ���� �ູ���� ���Ѵ�.
      _happiness = _happiness + howMuch * 0.15 + 0.23;
    }
    Woman(String name) {
      super(name);
      _happiness = 0.0;
    }
    void eat(int food) {
      super.eat(food);
      // ������ �ູ���� �ش�.
      increaseHappiness(-food);
    }
    void urinate(int amount) {
      super.urinate(amount);
      // ���� ���� �ູ���� �´�.
      increaseHappiness(50*amount);
    }
    void print() {
      super.print();
      System.out.print("        | ");
      StringBuffer buf = new StringBuffer();
      double happiness = java.lang.Math.round(_happiness*100)/100.0;
      buf.append(happiness);
      if (buf.charAt(buf.length()-2) == '.') 
         buf = buf.append('0');
      String happinessString = buf.toString();
      int length = happinessString.length();
      int nBlank = 9 - length;
      for (int i = 0; i < nBlank; i++)
         System.out.print(" ");
      System.out.println(buf+" |");
    }
    void doingX(Person spouse) {
      super.doingX(spouse);
      // �װ��� �� ���� �ູ���� �´�.
      _happiness = _happiness + 50.0;
      // �װ��� �� ����� ���� ���� ��ȭ�� �߱��Ų��.
      if (spouse != null) spouse.doingX(null); 
    }
    void pleaseMakeBabyName() {
      String newName = "";
      System.out.print("�ȳ��ϼ��� ������ ! �� �̸�(�� �̸�) �� �����ּ��� : ");
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      try {
         newName = in.readLine();
      } catch (IOException e) {
         // ���� ��� ����
      }
      _name = newName;
    }
    String whatIsYourClass() {
      return WOMAN_CLASS;
    }
}
