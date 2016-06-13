package figure;
/** 이 클래스는 시스템에서 사용되는 중요 상수들을 정의한 클래스이다.
 */
public final class Const extends Object 
{
	/** 문자열의 크기
	 */
	public static int BUFSIZ = 512;
	/** 이 시스템에서 정수의 최대 길이
	 */
	public static int MYDIGITS = 10;
	/** 마우스가 고정되어 있을 때 이 정지 상태를 푸는 마우스 이동 변위
	 */
	public static int FIXTOLERANCE = 20;
	/** 선분간의 연결 상태 : 다른 선분과 만나지 않음
	 */
	public static int ATNOWHERE = 0;
	/** 선분간의 연결 상태 : 이 선분의 앞에서 다른 선분 만남
	 */
	public static int ATBEFORE = 1;
	/** 선분간의 연결 상태 : 이 선분의 뒤에서 다른 선분 만남
	 */
	public static int ATAFTER = 2;
	/** 선분 중간에 새로운 선분을 만들고자 할 때 이 상태를 해제하는 마우스 이동 변위
	 */
	public static int FORKTOLERANCE = 10;
	/** 현재 선분이 방향성이 없음을 나타내는 상수값
	 */
	public static int RESETORIENT = -1;
	/** 화면 상에서 객체를 복사했을 때 원래 객체로 부터 떨어져있어야 하는 변위
	 */
	public static int COPYDELTA = 20;
	/** 스크롤 바가 자동적으로 이동할 때의 변위
	 */
	public static int SCROLLBARDELTA = 30;
	/** 그림 객체의 최소 폭
	 */
	public static int FIGUREMINW = 5;
	/** 그림 객체의 최소 높이
	 */
	public static int FIGUREMINH = 5;
	/** 그림 객체의 최소 길이
	 */
	public static int FIGUREMINL = 5;
	/** 클래스 멤버의 가시성 : public 경우
	 */
	public static int PUBLICID = 1;
	/** 클래스 멤버의 가시성 : protected 경우
	 */
	public static int PROTECTEDID = 2;
	/** 클래스 멤버의 가시성 : private 경우
	 */
	public static int PRIVATEID = 3;
	/** 관계 객체의 이름임을 나타내는 상수
	 */
	public static int ANYTIONNAME = 1;
	/** 관계 객체의 role name임을 나타내는 상수
	 */
	public static int ROLENAME = 2;
	/** 관계 객체의 다중성임을 나타내는 상수
	 */
	public static int MULTIPLICITYNAME = 3;
	/** 그림 객체의 구별 시에 사용되는 상수 : 그림
	 */
	public static int FIGUREPTR = 0;
	/** 그림 객체의 구별 시에 사용되는 상수 : 점
	 */
	public static int POINTPTR = 1;
	/** 그림 객체의 구별 시에 사용되는 상수 : 채워진 점
	 */
	public static int FILLEDPOINTPTR = 2;
	/** 그림 객체의 구별 시에 사용되는 상수 : 사선
	 */
	public static int LINEPTR = 3;
	/** 그림 객체의 구별 시에 사용되는 상수 : 사각형
	 */
	public static int BOXPTR = 4;
	/** 그림 객체의 구별 시에 사용되는 상수 : 둥근 사각형 A
	 */
	public static int ROUNDBOXAPTR = 5;
	/** 그림 객체의 구별 시에 사용되는 상수 : 둥근 사각형 B
	 */
	public static int ROUNDBOXBPTR = 6;
	/** 그림 객체의 구별 시에 사용되는 상수 : 원
	 */
	public static int CIRCLEPTR = 7;
	/** 그림 객체의 구별 시에 사용되는 상수 : 마름모
	 */
	public static int DIAMONDPTR = 8;
	/** 그림 객체의 구별 시에 사용되는 상수 : 삼각형
	 */
	public static int TRIANGLEPTR = 9;
	/** 그림 객체의 구별 시에 사용되는 상수 : 텍스트
	 */
	public static int TEXTPTR = 10;
	/** 그림 객체의 구별 시에 사용되는 상수 : 그룹
	 */
	public static int GROUPPTR = 11;
	/** 그림 객체의 구별 시에 사용되는 상수 : 클래스
	 */
	public static int CLASSTEMPLATEPTR = 12;
	/** 그림 객체의 구별 시에 사용되는 상수 : 선
	 */
	public static int LINESPTR = 13;
	/** 그림 객체의 구별 시에 사용되는 상수 : 직선
	 */
	public static int SLINESPTR = 14;
	/** 그림 객체의 구별 시에 사용되는 상수 : 일반화 관계
	 */
	public static int GENTIONPTR = 15;
	/** 그림 객체의 구별 시에 사용되는 상수 : 결합 관계
	 */
	public static int ASSTIONPTR = 16;
	/** 그림 객체의 구별 시에 사용되는 상수 : 집합 관계
	 */
	public static int AGGTIONPTR = 17;
	/** 그림 객체의 구별 시에 사용되는 상수 : 의존 관계
	 */
	public static int COLTIONPTR = 18;
	/** 그림 객체의 구별 시에 사용되는 상수 : 한 줄 텍스트
	 */
	public static int SINGLETEXTPTR = 19;
	/** 그림 객체의 구별 시에 사용되는 상수 : 점선
	 */
	public static int DASHEDLINEPTR = 20;
	/** 그림 객체의 구별 시에 사용되는 상수 : 기울어진 사선
	 */
	public static int SLASHPTR = 21;
	/** 그림 객체의 구별 시에 사용되는 상수 : 이진 결합 관계
	 */
	public static int ASSTIONPTR2 = 22;
	/** 그림 객체의 구별 시에 사용되는 상수 : 삼진 결합 관계
	 */
	public static int ASSTIONPTR3 = 23;
	/** 그림 객체의 구별 시에 사용되는 상수 : 패키지
	 */
	public static int PACKAGEPTR = 24;
	/** 그림 객체의 구별 시에 사용되는 상수 : 노트
	 */
	public static int NOTEPTR = 24;
	/** 콜백 함수 인자로 사용되는 상수 : 1
	 */
	public static int ONEPTR = 1;
	/** 콜백 함수 인자로 사용되는 상수 : 2
	 */
	public static int TWOPTR = 2;
	/** 콜백 함수 인자로 사용되는 상수 : 3
	 */
	public static int THREEPTR = 3;
	/** 콜백 함수 인자로 사용되는 상수 : 4
	 */
	public static int FOURPTR = 4;
	/** 콜백 함수 인자로 사용되는 상수 : 예외값 (9)
	 */
	public static int SOMEPTR = 9;
	/** 화면 크기 X 값의 최대 값 
	 */
	public static int MAXXVALUE = 99999;
	/** 화면 크기 Y 값의 최대 값 
	 */
	public static int MAXYVALUE = 99999;
	/** 둥근 사각형의 모서리 여분
	 */
	public static int ROUNDBOXBGAB = 30;
	/** 화살표 길이
	 */
	public static int ARROWLENGTH = 20;
	/** 선분 영역의 폭
	 */
	public static int REGIONLENGTH = 10;
	/** 점의 반지름
	 */
	public static int POINTRADIUS = 5;
	/** 직선 상수
	 */
	public static int SOLID = 0;
	/** 점선 상수
	 */
	public static int DASHED = 1;
	/** 선분의 방향성 : 방향성 없음
	 */
	public static int NODIR = 0;
	/** 선분의 방향성 : x1,y1에서 x2,y2로 방향
	 */
	public static int NORMALDIR = 1;
	/** 선분의 방향성 : x2,y2에서 x1,y1로 방향
	 */
	public static int INVERTDIR = 2;
	/** 선분의 방향성 : 양쪽 방향 
	 */
	public static int BIDIR = 3;
	/** 사선 끝에 화살표 없음
	 */
	public static int HEADNONE = 0;
	/** 사선 끝에 화살표 있음
	 */
	public static int HEADARROW1 = 1;
	/** 일반 사선
	 */
	public static int ORDINARY = 1;
	/** 직선
	 */
	public static int STRAIGHT = 2;
	/** 선분의 방향성 : 방향성 없음
	 */
	public static int UNDEFINED = 0;
	/** 선분의 방향성 : 북쪽
	 */
	public static int NORTH = 1;
	/** 선분의 방향성 : 동쪽
	 */
	public static int EAST = 2;
	/** 선분의 방향성 : 남쪽
	*/
	public static int SOUTH = 3;
	/** 선분의 방향성 : 서쪽
	 */
	public static int WEST = 4;
	/** 절대적인가를 나타내는 상수
	 */
	public static int ABSOLUTELY = 1;
	/** 그래픽 모드 상수 : 그림 그리는 중
	 */
	public static int DRAWING = 1;
	/** 그래픽 모드 상수 : lowlight 중
	 */
	public static int LOWLIGHTING = 2;
	/** 그래픽 모드 상수 : highlight 중
	 */
	public static int HIGHLIGHTING = 4;
	/** 그래픽 모드 상수 : rubberbanding 중
	 */
	public static int RUBBERBANDING = 8;
	/** 그림 객체 식별 상수 : 그림
	 */
	public static FigureID IAMFIGURE;
	/** 그림 객체 식별 상수 : 그룹
	 */
	public static FigureID IAMGROUP;
	/** 그림 객체 식별 상수 : 점
	 */
	public static FigureID IAMPOINT;
	/** 그림 객체 식별 상수 : 선분
	 */
	public static FigureID IAMLINE;
	/** 그림 객체 식별 상수 : 사각형
	 */
	public static FigureID IAMBOX;
	/** 그림 객체 식별 상수 : 둥근 사각형 A
	 */
	public static FigureID IAMROUNDBOXA;
	/** 그림 객체 식별 상수 : 둥근 사각형 B
	 */
	public static FigureID IAMROUNDBOXB;
	/** 그림 객체 식별 상수 : 원
	 */
	public static FigureID IAMCIRCLE;
	/** 그림 객체 식별 상수 : 마름모
	 */
	public static FigureID IAMDIAMOND;
	/** 그림 객체 식별 상수 : 삼각형
	 */
	public static FigureID IAMTRIANGLE;
	/** 그림 객체 식별 상수 : 텍스트
	 */
	public static FigureID IAMTEXT;
	/** 그림 객체 식별 상수 : 클래스
	 */
	public static FigureID IAMCLASSTEMPLATE;
	/** 그림 객체 식별 상수 : 클래스 텍스트
	 */
	public static FigureID IAMCLASSTEXT;
	/** 그림 객체 식별 상수 : 선
	 */
	public static FigureID IAMLINES;
	/** 그림 객체 식별 상수 : 한줄 텍스트
	 */
	public static FigureID IAMSINGLELINETEXT;
	/** 그림 객체 식별 상수 : qualification
	 */
	public static FigureID IAMQUALIFICATIONTEXT;
	/** 그림 객체 식별 상수 : 링크 텍스트
	 */
	public static FigureID IAMLINKATTRTEXT;
	/** 그림 객체 식별 상수 : 화일 객체
	 */
	public static FigureID IAMFILEOBJECT;
	/** 그림 객체 식별 상수 : 일반화 관계
	 */
	public static FigureID IAMGENTION;
	/** 그림 객체 식별 상수 : 집합화 관계
	 */
	public static FigureID IAMAGGTION;
	/** 그림 객체 식별 상수 : 의존 관계
	 */
	public static FigureID IAMCOLTION;
	/** 그림 객체 식별 상수 : 결합 관계
	 */
	public static FigureID IAMASSTION;
	/** 그림 객체 식별 상수 : 패키지
	 */
	public static FigureID IAMPACKAGE;
	/** 그림 객체 식별 상수 : 그림
	 */
	public static FigureID IAMPACKAGETEXT;
	/** 그림 객체 식별 상수 : 노트
	 */
	public static FigureID IAMNOTE;
	/** 그림 객체 식별 상수 : 노트 텍스트
	 */
	public static FigureID IAMNOTETEXT;
	/** 그림 객체 식별 상수 : 선 종류들
	 */
	public static FigureID WEARELINES;
	/** 그림 객체 식별 상수 : 클래스 종류들
	 */
	public static FigureID WEARECLASSTEMPLATE;
	/** 그림 객체 식별 상수 : 관계 종류들
	 */
	public static FigureID WEARETION;
	/** 그림 객체 식별 상수 : 편집 가능 객체 종류들
	 */
	public static FigureID WEAREEDITABLECOMPONENT;

	/**********************************/
	/** 정제기 디렉토리 이름
	 */
	public static String RefinerDiretory;
	/** Viewer 디렉토리 이름
	 */
	public static String ViewerDirectory;
	/** 상수값 초기화 함수
	 */
	public static void initialize() 
	{
		
		String path = System.getProperty("file.separator");
		RefinerDiretory = new String("d:" + path + "Models" + path + "Model");
		ViewerDirectory = new String("d:" + path + "Models" + path + "DOModel" );
				
		
		IAMFIGURE = new FigureID(0);
		IAMGROUP = new FigureID(1);
		IAMPOINT = new FigureID(2);
		IAMLINE = new FigureID(3);
		IAMBOX = new FigureID(4);
		IAMROUNDBOXA = new FigureID(5);
		IAMROUNDBOXB = new FigureID(6);
		IAMCIRCLE = new FigureID(7);
		IAMDIAMOND = new FigureID(8);
		IAMTRIANGLE = new FigureID(9);
		IAMTEXT = new FigureID(10);
		IAMCLASSTEMPLATE = new FigureID(11);
		IAMCLASSTEXT = new FigureID(12);
		IAMLINES = new FigureID(13);
		IAMSINGLELINETEXT = new FigureID(14);
		IAMQUALIFICATIONTEXT = new FigureID(15);
		IAMLINKATTRTEXT = new FigureID(16);
		IAMFILEOBJECT = new FigureID(17);
		IAMGENTION = new FigureID(18);
		IAMAGGTION = new FigureID(19);
		IAMCOLTION = new FigureID(20);
		IAMASSTION = new FigureID(21);
		IAMPACKAGE = new FigureID(22);
		IAMPACKAGETEXT = new FigureID(23);
		IAMNOTE = new FigureID(24);
		IAMNOTETEXT = new FigureID(25);
		WEARELINES =
					IAMLINE.
							oring(IAMLINES).
											oring(IAMGENTION).
															  oring(IAMAGGTION).
																				oring(IAMCOLTION).
																								  oring(IAMASSTION);
		WEARECLASSTEMPLATE = IAMCLASSTEMPLATE.
											  oring(IAMPACKAGE);
		WEARETION =
				   IAMGENTION.
							  oring(IAMAGGTION).
												oring(IAMCOLTION).
																  oring(IAMASSTION);
		WEAREEDITABLECOMPONENT  =
								 IAMSINGLELINETEXT.
												   oring(IAMQUALIFICATIONTEXT).
																			   oring(IAMLINKATTRTEXT);
	}
}