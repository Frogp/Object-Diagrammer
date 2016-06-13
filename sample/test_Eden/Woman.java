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
      // 다소 이상한 계산법에 의해 행복도가 변한다.
      _happiness = _happiness + howMuch * 0.15 + 0.23;
    }
    Woman(String name) {
      super(name);
      _happiness = 0.0;
    }
    void eat(int food) {
      super.eat(food);
      // 먹으면 행복감이 준다.
      increaseHappiness(-food);
    }
    void urinate(int amount) {
      super.urinate(amount);
      // 일을 보면 행복감이 는다.
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
      // 그걸할 때 마다 행복감이 는다.
      _happiness = _happiness + 50.0;
      // 그걸할 때 배우자 쪽의 상태 변화를 야기시킨다.
      if (spouse != null) spouse.doingX(null); 
    }
    void pleaseMakeBabyName() {
      String newName = "";
      System.out.print("안녕하세요 지오디 ! 제 이름(딸 이름) 좀 정해주세요 : ");
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      try {
         newName = in.readLine();
      } catch (IOException e) {
         // 예외 경우 무시
      }
      _name = newName;
    }
    String whatIsYourClass() {
      return WOMAN_CLASS;
    }
}
