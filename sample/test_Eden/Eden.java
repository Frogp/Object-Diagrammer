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
      // 옛날 옛적에 지오디가 아담을 창조하셨다.
      Man adam = new Man("아담");

      int clock;
      // 아담은 한 동안 밥 먹고 화장실 가는 일만을 반복했다.  
      for (clock = 0; clock < SOME_DURATION; clock++) {
         adam.eat(Person.BANANA); // 아침에는 바나나를 먹고
         adam.urinate(Person.BIG); // 화장실에서 큰걸 해결한 후
         adam.eat(Person.MEAT); // 점심으로 불고기를 먹은 후
         adam.eat(Person.APPLE); // 저녁에는 사과를 먹었다.
      }

   /* 매일 밥 먹고 화장실 가는 일만 반복하던 그는 매우 심심하다고 느꼈다.
      그래서 아담은 지오디에게 "여자"라고 분류되는 동반자를 만들어 달라고 부탁하였다.
      다행히 지오디는 그 부탁을 들어 주었고 이브를 창조하였다. */
      Woman eve = new Woman("이브");

      // 아담은 이브와 함께 밥 먹고 화장실 가는 일을 반복하며 생활했다. 
      for (clock = 0; clock < SOME_DURATION; clock++) {
         // 아침 식사
         adam.eat(Person.BANANA); // 아담은 아침에 바나나를 먹고
         eve.eat(Person.APPLE); // 이브는 아침에 사과를 먹었다.
         // 화장실 가기
         adam.urinate(Person.BIG); // 아담은 화장실에서 큰걸 보고
         eve.urinate(Person.SMALL); // 이브는 작은 걸 본다.
         // 점심 식사
         adam.eat(Person.MEAT); // 아담은 점심에 불고기를 먹고
         eve.eat(Person.APPLE); // 이브는 점심에도 사과를 먹었다.
         // 저녁 식사
         adam.eat(Person.APPLE); // 아담은 저녁에 사과를 먹고
         eve.eat(Person.APPLE); // 이브는 저녁에도 사과를 먹었다.
      }

   /* 함께 식사하고 밥 먹는 일을 반복하며 생활하던 아담과 이브는 
      좀 더 즐거운 시간을 보내기 위한 놀이를 생각해냈다.
      그 놀이의 이름은 "그걸 한다"인데 하루 일과가 끝난 후에 하기에 적당한 놀이였다.
      그래서 그 둘은 매일 밤에 그걸 하기로 했다. */
      for (clock = 0; clock < SOME_DURATION; clock++) {
         // 아침 식사
         adam.eat(Person.BANANA); // 아담은 아침에 바나나를 먹고
         eve.eat(Person.APPLE); // 이브는 아침에 사과를 먹는다.
         // 화장실 가기
         adam.urinate(Person.BIG); // 아담은 화장실에서 큰걸 보고
         eve.urinate(Person.SMALL); // 이브는 작은 걸 본다.
         // 점심 식사
         adam.eat(Person.MEAT); // 아담은 점심에 불고기를 먹고
         eve.eat(Person.APPLE); // 이브는 점심에도 사과를 먹는다.
         // 저녁 식사
         adam.eat(Person.APPLE); // 아담은 저녁에 사과를 먹고
         eve.eat(Person.APPLE); // 이브는 저녁에도 사과를 먹는다.
         // 해지고 난 후 한 밤중에
         adam.doingX(eve); // 아담과 이브는 그걸 한다.
      }
   /* 정확한 이유는 모르겠지만 지오디는 이브가 자꾸 사과만 먹는 것 때문에 화가 났다.
      그래서 지오디는 이들에게 놀이를 하기 위한 의무를 지우기로 결정했다.
      그걸 하기 위한 놀이의 의무의 명칭은 "결혼한다"는 것이며 그 의무의 부담으로서
      결혼하는 사람들은 가족을 구성해야하고 그걸 365회 할 때마다 아들이나 딸이 새로
      태어나야 한다는 것이다.
      아담과 이브는 이 의무를 따르기로 결정하고 둘이 결혼하여 새 가족을 구성하였다. */
      Family aFamily = adam.marry(eve);
      // 아담과 이브는 가정을 꾸린 후 아이들을 낳으면서 그럭저럭 살아간다.
      for (clock = 0; clock < 200*SOME_DURATION; clock++) {
         aFamily.liveFromHandToMouth();
      }
      // 자, 이 가족 구성원의 상태 값(성별,몸무게,힘,행복도)들을 알아보자.
      aFamily.print();
    }
}
