// Eden.java

//{{MODELER_BEFORE_CLASS(Eden) - DO NOT DELETE THIS LINE
//OODesigner will reverse the code here.

//Organization: 
//Author: 
//Date: Monday, January 05, 2004
//Super class: 
//Purpose of Eden class:
/**
*/

//}}MODELER_BEFORE_CLASS

public class Eden 
{

// Operations
    public static void main(String[] args) {
      int SOME_DURATION = 10;
      // ���� ������ ������ �ƴ��� â���ϼ̴�.
      Man adam = new Man("�ƴ�");

      int clock;
      // �ƴ��� �� ���� �� �԰� ȭ��� ���� �ϸ��� �ݺ��ߴ�.  
      for (clock = 0; clock < SOME_DURATION; clock++) {
         adam.eat(Person.BANANA); // ��ħ���� �ٳ����� �԰�
         adam.urinate(Person.BIG); // ȭ��ǿ��� ū�� �ذ��� ��
         adam.eat(Person.MEAT); // �������� �Ұ�⸦ ���� ��
         adam.eat(Person.APPLE); // ���ῡ�� ����� �Ծ���.
      }

   /* ���� �� �԰� ȭ��� ���� �ϸ� �ݺ��ϴ� �״� �ſ� �ɽ��ϴٰ� ������.
      �׷��� �ƴ��� �����𿡰� "����"��� �з��Ǵ� �����ڸ� ����� �޶�� ��Ź�Ͽ���.
      ������ ������� �� ��Ź�� ��� �־��� �̺긦 â���Ͽ���. */
      Woman eve = new Woman("�̺�");

      // �ƴ��� �̺�� �Բ� �� �԰� ȭ��� ���� ���� �ݺ��ϸ� ��Ȱ�ߴ�. 
      for (clock = 0; clock < SOME_DURATION; clock++) {
         // ��ħ �Ļ�
         adam.eat(Person.BANANA); // �ƴ��� ��ħ�� �ٳ����� �԰�
         eve.eat(Person.APPLE); // �̺�� ��ħ�� ����� �Ծ���.
         // ȭ��� ����
         adam.urinate(Person.BIG); // �ƴ��� ȭ��ǿ��� ū�� ����
         eve.urinate(Person.SMALL); // �̺�� ���� �� ����.
         // ���� �Ļ�
         adam.eat(Person.MEAT); // �ƴ��� ���ɿ� �Ұ�⸦ �԰�
         eve.eat(Person.APPLE); // �̺�� ���ɿ��� ����� �Ծ���.
         // ���� �Ļ�
         adam.eat(Person.APPLE); // �ƴ��� ���ῡ ����� �԰�
         eve.eat(Person.APPLE); // �̺�� ���ῡ�� ����� �Ծ���.
      }

   /* �Բ� �Ļ��ϰ� �� �Դ� ���� �ݺ��ϸ� ��Ȱ�ϴ� �ƴ�� �̺�� 
      �� �� ��ſ� �ð��� ������ ���� ���̸� �����س´�.
      �� ������ �̸��� "�װ� �Ѵ�"�ε� �Ϸ� �ϰ��� ���� �Ŀ� �ϱ⿡ ������ ���̿���.
      �׷��� �� ���� ���� �㿡 �װ� �ϱ�� �ߴ�. */
      for (clock = 0; clock < SOME_DURATION; clock++) {
         // ��ħ �Ļ�
         adam.eat(Person.BANANA); // �ƴ��� ��ħ�� �ٳ����� �԰�
         eve.eat(Person.APPLE); // �̺�� ��ħ�� ����� �Դ´�.
         // ȭ��� ����
         adam.urinate(Person.BIG); // �ƴ��� ȭ��ǿ��� ū�� ����
         eve.urinate(Person.SMALL); // �̺�� ���� �� ����.
         // ���� �Ļ�
         adam.eat(Person.MEAT); // �ƴ��� ���ɿ� �Ұ�⸦ �԰�
         eve.eat(Person.APPLE); // �̺�� ���ɿ��� ����� �Դ´�.
         // ���� �Ļ�
         adam.eat(Person.APPLE); // �ƴ��� ���ῡ ����� �԰�
         eve.eat(Person.APPLE); // �̺�� ���ῡ�� ����� �Դ´�.
         // ������ �� �� �� ���߿�
         adam.doingX(eve); // �ƴ�� �̺�� �װ� �Ѵ�.
      }
   /* ��Ȯ�� ������ �𸣰����� ������� �̺갡 �ڲ� ����� �Դ� �� ������ ȭ�� ����.
      �׷��� ������� �̵鿡�� ���̸� �ϱ� ���� �ǹ��� ������ �����ߴ�.
      �װ� �ϱ� ���� ������ �ǹ��� ��Ī�� "��ȥ�Ѵ�"�� ���̸� �� �ǹ��� �δ����μ�
      ��ȥ�ϴ� ������� ������ �����ؾ��ϰ� �װ� 365ȸ �� ������ �Ƶ��̳� ���� ����
      �¾�� �Ѵٴ� ���̴�.
      �ƴ�� �̺�� �� �ǹ��� ������� �����ϰ� ���� ��ȥ�Ͽ� �� ������ �����Ͽ���. */
      Family aFamily = adam.marry(eve);
      // �ƴ�� �̺�� ������ �ٸ� �� ���̵��� �����鼭 �׷����� ��ư���.
      for (clock = 0; clock < 200*SOME_DURATION; clock++) {
         aFamily.liveFromHandToMouth();
      }
      // ��, �� ���� �������� ���� ��(����,������,��,�ູ��)���� �˾ƺ���.
      aFamily.print();
    }
}
