// Man.java

//{{MODELER_BEFORE_CLASS(Man) - DO NOT DELETE THIS LINE
//OODesigner will reverse the code here.

import java.io.*;

//Organization: 
//Author: 
//Date: Monday, January 05, 2004
//Super class: 
//Purpose of Man class:
/**
*/

//}}MODELER_BEFORE_CLASS

class Man extends Person 
{
// Attributes
    private int _xPower;

// Operations
    private void increaseXPower(int howMuch) {
      _xPower = _xPower + howMuch/10;
    }
    Man(String name) {
      super(name);
      _xPower = 0;
    }
    void eat(int food) {
      super.eat(food);
      // ������ ���� �ڴ´�.
      increaseXPower(food);
    }
    void urinate(int amount) {
      super.urinate(amount);
      // ���� ���� ���� �ڴ´�.
      increaseXPower(amount);
    }
    void print() {
      super.print();
      System.out.print(" ");
      String xPowerString = "" + _xPower;
      int length = xPowerString.length();
      int nBlank = 6 - length;
      for (int i = 0; i < nBlank; i++)
         System.out.print(" ");
      System.out.println(_xPower+" |           |");
    }
    void doingX(Person spouse) {
      super.doingX(spouse);
      // �װ��� �� ���� ���� ������.
      _xPower = _xPower - 10;
      // �װ��� �� ����� ���� ���� ��ȭ�� �߱��Ų��.
      if (spouse != null) spouse.doingX(null); 
    }
    void pleaseMakeBabyName() {
      String newName = "";
      System.out.print("�ȳ��ϼ��� ������ ! �� �̸�(�Ƶ� �̸�) �� �����ּ��� : ");
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      try {
         newName = in.readLine();
      } catch (IOException e) {
         // ���� ��� ����
      }
      _name = newName;
    }
    String whatIsYourClass() {
      return MAN_CLASS;
    }
    Family marry(Woman bride) {
      // ���ο� ������ �����Ѵ�.
      return new Family(this,bride);
    }
    Person bearBaby(Woman spouse,int day) {
      // �ϴ� �װ��Ѵ�.
      doingX(spouse);

      Person baby;
      if (day % 2 == 0) {
         // ��¥�� ¦���� ��쿡�� �Ƶ��� ���´�.
         baby = new Man(null);
      } else {
         // ��¥�� Ȧ���� ��쿡�� ���� ���´�.
         baby = new Woman(null);
      }
      baby.pleaseMakeBabyName();
      return baby;
    }
}
