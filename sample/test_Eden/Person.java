// Person.java

//{{MODELER_BEFORE_CLASS(Person) - DO NOT DELETE THIS LINE
//OODesigner will reverse the code here.

//Organization: 
//Author: 
//Date: Monday, January 05, 2004
//Super class: 
//Purpose of Person class:
/**
*/

//}}MODELER_BEFORE_CLASS

abstract class Person 
{
// Attributes
    private int _weight;
    protected String _name;
    static String PERSON_CLASS = "���";
    static String MAN_CLASS = "����";
    static String WOMAN_CLASS = "����";
    static int BANANA = 10;
    static int APPLE = 20;
    static int MEAT = 40;
    static int SMALL = 10;
    static int BIG = 30;

// Operations
    Person(String name) {
      _weight = 100;
      _name = name;
    }
    void eat(int food) {
      // ���� ��ŭ �����԰� �´�.
      _weight = _weight + food;
    }
    void urinate(int amount) {
      // �� ���� ��ŭ �����԰� ������.
      _weight = _weight - amount;
    }
    void print() {
      int length = _name.length()*2; // �ѱ� �̸��� ��츦 �����Ѵ�.
      System.out.print("| ");
      int nBlank = 6 - length;
      if (nBlank < 0) nBlank = 0;
      for (int i = 0; i < nBlank; i++)
         System.out.print(" ");
      System.out.print(_name+" | "+whatIsYourClass()+" | ");
      String weightString = "" + _weight;
      length = weightString.length();
      nBlank = 6 - length;
      for (int i = 0; i < nBlank; i++)
         System.out.print(" ");
      System.out.print(_weight+" |");
    }
    void doingX(Person spouse) {
      // �װ��� �� ���� �����԰� ������.
      _weight = _weight - 10;
    }
    abstract void pleaseMakeBabyName();
    String whatIsYourClass() {
      return PERSON_CLASS;
    }
}
