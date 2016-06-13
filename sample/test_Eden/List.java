// List.java

//{{MODELER_BEFORE_CLASS(List) - DO NOT DELETE THIS LINE
//OODesigner will reverse the code here.

//Organization: 
//Author: 
//Date: Monday, January 05, 2004
//Super class: 
//Purpose of List class:
/**
*/

//}}MODELER_BEFORE_CLASS

class List 
{
// Attributes
    protected ListNode _header;
    protected ListNode _position;

// Operations
    protected List() {
      _header = null;
      _position = null;
    }
    protected void addTail(Object object) {
      ListNode newNode = new ListNode(object,null);
      if (_header == null) {
         _header = newNode;
         return;
      }
      ListNode endPosition;
      ListNode currentPosition;
      currentPosition = endPosition = _header;
      while(currentPosition != null) {
         endPosition = currentPosition;
         currentPosition = currentPosition.nextNode;
      }
      endPosition.nextNode = newNode;
    }
    protected void addHead(Object object) {
      ListNode newNode = new ListNode(object,_header);
      _header = newNode;
    }
    protected Object getFirstObject() {
      _position = _header;
      if (_position == null) return null;
      else return _position.object;
    }
    protected Object getNextObject() {
      _position = _position.nextNode;
      if (_position == null) return null;
      else return _position.object;      
    }
}
