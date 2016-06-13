// PersonList.java

//{{MODELER_BEFORE_CLASS(PersonList) - DO NOT DELETE THIS LINE
//OODesigner will reverse the code here.

//Organization: 
//Author: 
//Date: Monday, January 05, 2004
//Super class: List
//Purpose of PersonList class:
/**
*/

//}}MODELER_BEFORE_CLASS

class PersonList extends List 
{

// Operations
    PersonList() {
      super();
    }
    void addTail(Person person) {
      super.addTail((Object)person);
    }
    void addHead(Person person) {
      super.addHead((Object)person);
    }
    Person getFirstPerson() {
      return (Person)super.getFirstObject();
    }
    Person getNextPerson() {
      return (Person)super.getNextObject();
    }
}
