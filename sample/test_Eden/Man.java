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
      // 먹으면 힘이 솟는다.
      increaseXPower(food);
    }
    void urinate(int amount) {
      super.urinate(amount);
      // 일을 봐도 힘이 솟는다.
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
      // 그걸할 때 마다 힘이 빠진다.
      _xPower = _xPower - 10;
      // 그걸할 때 배우자 쪽의 상태 변화를 야기시킨다.
      if (spouse != null) spouse.doingX(null); 
    }
    void pleaseMakeBabyName() {
      String newName = "";
      System.out.print("안녕하세요 지오디 ! 제 이름(아들 이름) 좀 정해주세요 : ");
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      try {
         newName = in.readLine();
      } catch (IOException e) {
         // 예외 경우 무시
      }
      _name = newName;
    }
    String whatIsYourClass() {
      return MAN_CLASS;
    }
    Family marry(Woman bride) {
      // 새로운 가정을 구성한다.
      return new Family(this,bride);
    }
    Person bearBaby(Woman spouse,int day) {
      // 일단 그걸한다.
      doingX(spouse);

      Person baby;
      if (day % 2 == 0) {
         // 날짜가 짝수인 경우에는 아들을 낳는다.
         baby = new Man(null);
      } else {
         // 날짜가 홀수인 경우에는 딸을 낳는다.
         baby = new Woman(null);
      }
      baby.pleaseMakeBabyName();
      return baby;
    }
}
