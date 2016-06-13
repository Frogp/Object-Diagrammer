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
      _mother.eat(Person.APPLE); // �̺�� �������� ����� �Դ´�.
      Person child = _children.getFirstPerson();
      while(child != null) {
         child.eat(food);
         child = _children.getNextPerson();
      }
    }
    private void urinate() {
      // �ı����� ������ ���� ����.
      _father.urinate(Person.BIG);
      _mother.urinate(Person.SMALL);
      Person child = _children.getFirstPerson();
      while(child != null) {
         if (child.whatIsYourClass().equals(Person.MAN_CLASS)) {
            // �Ƶ���� �׻� ū ���� ����.
            child.urinate(Person.BIG);
         } else if (child.whatIsYourClass().equals(Person.WOMAN_CLASS)) {
            // ������ �׻� ���� ���� ����.
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
      _day++; // ���� ư��.
      eat(Person.BANANA); // �ı����� ��ħ �Ļ縦 �Ѵ�.
      urinate(); // �ı����� ���� ����.
      eat(Person.MEAT); // �ı����� ���� �Ļ縦 �Ѵ�.
      eat(Person.APPLE); // �ı����� ���� �Ļ縦 �Ѵ�.
      if (_day % 365 == 0) {
         // 365�ϸ��� �װ��ϴ� ����� �ƱⰡ �¾� ����.
         Person aBaby = _father.bearBaby(_mother,_day);
         _children.addTail(aBaby);
      } else {
         // �κΰ� �װ��Ѵ�.
         _father.doingX(_mother);
      }
    }
    void print() {
      System.out.println();
      System.out.println("            << ������ ���� �� >>");
      System.out.println("+--------+------+--------+--------+-----------+");
      System.out.println("|  �̸�  | ���� | ������ |   ��   |   �ູ��  |");
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
