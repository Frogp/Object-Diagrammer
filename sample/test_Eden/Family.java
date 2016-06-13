// Family.java

//{{MODELER_BEFORE_CLASS(Family) - DO NOT DELETE THIS LINE
//OODesigner will reverse the code here.

//Organization: 
//Author: 
//Date: Monday, January 05, 2004
//Super class: 
//Purpose of Family class:
/**
*/

//}}MODELER_BEFORE_CLASS

class Family 
{
// Attributes
    private Man _father;
    private Woman _mother;
    private PersonList _children;
    private int _day;

// Operations
    private void eat(int food) {
      _father.eat(food);
      _mother.eat(Person.APPLE); // 이브는 악착같이 사과만 먹는다.
      Person child = _children.getFirstPerson();
      while(child != null) {
         child.eat(food);
         child = _children.getNextPerson();
      }
    }
    private void urinate() {
      // 식구들이 나란히 일을 본다.
      _father.urinate(Person.BIG);
      _mother.urinate(Person.SMALL);
      Person child = _children.getFirstPerson();
      while(child != null) {
         if (child.whatIsYourClass().equals(Person.MAN_CLASS)) {
            // 아들들은 항상 큰 일을 본다.
            child.urinate(Person.BIG);
         } else if (child.whatIsYourClass().equals(Person.WOMAN_CLASS)) {
            // 딸들은 항상 작은 일을 본다.
            child.urinate(Person.SMALL);
         }
         child = _children.getNextPerson();
      }
    }
    Family(Man father,Woman mother) {
      _father = father;
      _mother = mother;
      _children = new PersonList();
      _day = 1;
    }
    void liveFromHandToMouth() {
      _day++; // 동이 튼다.
      eat(Person.BANANA); // 식구들이 아침 식사를 한다.
      urinate(); // 식구들이 일을 본다.
      eat(Person.MEAT); // 식구들이 점심 식사를 한다.
      eat(Person.APPLE); // 식구들이 저녁 식사를 한다.
      if (_day % 365 == 0) {
         // 365일마다 그걸하는 결과로 아기가 태어 난다.
         Person aBaby = _father.bearBaby(_mother,_day);
         _children.addTail(aBaby);
      } else {
         // 부부가 그걸한다.
         _father.doingX(_mother);
      }
    }
    void print() {
      System.out.println();
      System.out.println("            << 가족의 상태 값 >>");
      System.out.println("+--------+------+--------+--------+-----------+");
      System.out.println("|  이름  | 성별 | 몸무게 |   힘   |   행복도  |");
      System.out.println("+--------+------+--------+--------+-----------+");
      _father.print();
      _mother.print(); 
      Person child = _children.getFirstPerson();
      while(child != null) {
         child.print();
         child = _children.getNextPerson();
      }
      System.out.println("+--------+------+--------+--------+-----------+");
    }
}
