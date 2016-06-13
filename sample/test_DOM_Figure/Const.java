package figure;
/** �� Ŭ������ �ý��ۿ��� ���Ǵ� �߿� ������� ������ Ŭ�����̴�.
 */
public final class Const extends Object 
{
	/** ���ڿ��� ũ��
	 */
	public static int BUFSIZ = 512;
	/** �� �ý��ۿ��� ������ �ִ� ����
	 */
	public static int MYDIGITS = 10;
	/** ���콺�� �����Ǿ� ���� �� �� ���� ���¸� Ǫ�� ���콺 �̵� ����
	 */
	public static int FIXTOLERANCE = 20;
	/** ���а��� ���� ���� : �ٸ� ���а� ������ ����
	 */
	public static int ATNOWHERE = 0;
	/** ���а��� ���� ���� : �� ������ �տ��� �ٸ� ���� ����
	 */
	public static int ATBEFORE = 1;
	/** ���а��� ���� ���� : �� ������ �ڿ��� �ٸ� ���� ����
	 */
	public static int ATAFTER = 2;
	/** ���� �߰��� ���ο� ������ ������� �� �� �� ���¸� �����ϴ� ���콺 �̵� ����
	 */
	public static int FORKTOLERANCE = 10;
	/** ���� ������ ���⼺�� ������ ��Ÿ���� �����
	 */
	public static int RESETORIENT = -1;
	/** ȭ�� �󿡼� ��ü�� �������� �� ���� ��ü�� ���� �������־�� �ϴ� ����
	 */
	public static int COPYDELTA = 20;
	/** ��ũ�� �ٰ� �ڵ������� �̵��� ���� ����
	 */
	public static int SCROLLBARDELTA = 30;
	/** �׸� ��ü�� �ּ� ��
	 */
	public static int FIGUREMINW = 5;
	/** �׸� ��ü�� �ּ� ����
	 */
	public static int FIGUREMINH = 5;
	/** �׸� ��ü�� �ּ� ����
	 */
	public static int FIGUREMINL = 5;
	/** Ŭ���� ����� ���ü� : public ���
	 */
	public static int PUBLICID = 1;
	/** Ŭ���� ����� ���ü� : protected ���
	 */
	public static int PROTECTEDID = 2;
	/** Ŭ���� ����� ���ü� : private ���
	 */
	public static int PRIVATEID = 3;
	/** ���� ��ü�� �̸����� ��Ÿ���� ���
	 */
	public static int ANYTIONNAME = 1;
	/** ���� ��ü�� role name���� ��Ÿ���� ���
	 */
	public static int ROLENAME = 2;
	/** ���� ��ü�� ���߼����� ��Ÿ���� ���
	 */
	public static int MULTIPLICITYNAME = 3;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : �׸�
	 */
	public static int FIGUREPTR = 0;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : ��
	 */
	public static int POINTPTR = 1;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : ä���� ��
	 */
	public static int FILLEDPOINTPTR = 2;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : �缱
	 */
	public static int LINEPTR = 3;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : �簢��
	 */
	public static int BOXPTR = 4;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : �ձ� �簢�� A
	 */
	public static int ROUNDBOXAPTR = 5;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : �ձ� �簢�� B
	 */
	public static int ROUNDBOXBPTR = 6;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : ��
	 */
	public static int CIRCLEPTR = 7;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : ������
	 */
	public static int DIAMONDPTR = 8;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : �ﰢ��
	 */
	public static int TRIANGLEPTR = 9;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : �ؽ�Ʈ
	 */
	public static int TEXTPTR = 10;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : �׷�
	 */
	public static int GROUPPTR = 11;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : Ŭ����
	 */
	public static int CLASSTEMPLATEPTR = 12;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : ��
	 */
	public static int LINESPTR = 13;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : ����
	 */
	public static int SLINESPTR = 14;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : �Ϲ�ȭ ����
	 */
	public static int GENTIONPTR = 15;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : ���� ����
	 */
	public static int ASSTIONPTR = 16;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : ���� ����
	 */
	public static int AGGTIONPTR = 17;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : ���� ����
	 */
	public static int COLTIONPTR = 18;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : �� �� �ؽ�Ʈ
	 */
	public static int SINGLETEXTPTR = 19;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : ����
	 */
	public static int DASHEDLINEPTR = 20;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : ������ �缱
	 */
	public static int SLASHPTR = 21;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : ���� ���� ����
	 */
	public static int ASSTIONPTR2 = 22;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : ���� ���� ����
	 */
	public static int ASSTIONPTR3 = 23;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : ��Ű��
	 */
	public static int PACKAGEPTR = 24;
	/** �׸� ��ü�� ���� �ÿ� ���Ǵ� ��� : ��Ʈ
	 */
	public static int NOTEPTR = 24;
	/** �ݹ� �Լ� ���ڷ� ���Ǵ� ��� : 1
	 */
	public static int ONEPTR = 1;
	/** �ݹ� �Լ� ���ڷ� ���Ǵ� ��� : 2
	 */
	public static int TWOPTR = 2;
	/** �ݹ� �Լ� ���ڷ� ���Ǵ� ��� : 3
	 */
	public static int THREEPTR = 3;
	/** �ݹ� �Լ� ���ڷ� ���Ǵ� ��� : 4
	 */
	public static int FOURPTR = 4;
	/** �ݹ� �Լ� ���ڷ� ���Ǵ� ��� : ���ܰ� (9)
	 */
	public static int SOMEPTR = 9;
	/** ȭ�� ũ�� X ���� �ִ� �� 
	 */
	public static int MAXXVALUE = 99999;
	/** ȭ�� ũ�� Y ���� �ִ� �� 
	 */
	public static int MAXYVALUE = 99999;
	/** �ձ� �簢���� �𼭸� ����
	 */
	public static int ROUNDBOXBGAB = 30;
	/** ȭ��ǥ ����
	 */
	public static int ARROWLENGTH = 20;
	/** ���� ������ ��
	 */
	public static int REGIONLENGTH = 10;
	/** ���� ������
	 */
	public static int POINTRADIUS = 5;
	/** ���� ���
	 */
	public static int SOLID = 0;
	/** ���� ���
	 */
	public static int DASHED = 1;
	/** ������ ���⼺ : ���⼺ ����
	 */
	public static int NODIR = 0;
	/** ������ ���⼺ : x1,y1���� x2,y2�� ����
	 */
	public static int NORMALDIR = 1;
	/** ������ ���⼺ : x2,y2���� x1,y1�� ����
	 */
	public static int INVERTDIR = 2;
	/** ������ ���⼺ : ���� ���� 
	 */
	public static int BIDIR = 3;
	/** �缱 ���� ȭ��ǥ ����
	 */
	public static int HEADNONE = 0;
	/** �缱 ���� ȭ��ǥ ����
	 */
	public static int HEADARROW1 = 1;
	/** �Ϲ� �缱
	 */
	public static int ORDINARY = 1;
	/** ����
	 */
	public static int STRAIGHT = 2;
	/** ������ ���⼺ : ���⼺ ����
	 */
	public static int UNDEFINED = 0;
	/** ������ ���⼺ : ����
	 */
	public static int NORTH = 1;
	/** ������ ���⼺ : ����
	 */
	public static int EAST = 2;
	/** ������ ���⼺ : ����
	*/
	public static int SOUTH = 3;
	/** ������ ���⼺ : ����
	 */
	public static int WEST = 4;
	/** �������ΰ��� ��Ÿ���� ���
	 */
	public static int ABSOLUTELY = 1;
	/** �׷��� ��� ��� : �׸� �׸��� ��
	 */
	public static int DRAWING = 1;
	/** �׷��� ��� ��� : lowlight ��
	 */
	public static int LOWLIGHTING = 2;
	/** �׷��� ��� ��� : highlight ��
	 */
	public static int HIGHLIGHTING = 4;
	/** �׷��� ��� ��� : rubberbanding ��
	 */
	public static int RUBBERBANDING = 8;
	/** �׸� ��ü �ĺ� ��� : �׸�
	 */
	public static FigureID IAMFIGURE;
	/** �׸� ��ü �ĺ� ��� : �׷�
	 */
	public static FigureID IAMGROUP;
	/** �׸� ��ü �ĺ� ��� : ��
	 */
	public static FigureID IAMPOINT;
	/** �׸� ��ü �ĺ� ��� : ����
	 */
	public static FigureID IAMLINE;
	/** �׸� ��ü �ĺ� ��� : �簢��
	 */
	public static FigureID IAMBOX;
	/** �׸� ��ü �ĺ� ��� : �ձ� �簢�� A
	 */
	public static FigureID IAMROUNDBOXA;
	/** �׸� ��ü �ĺ� ��� : �ձ� �簢�� B
	 */
	public static FigureID IAMROUNDBOXB;
	/** �׸� ��ü �ĺ� ��� : ��
	 */
	public static FigureID IAMCIRCLE;
	/** �׸� ��ü �ĺ� ��� : ������
	 */
	public static FigureID IAMDIAMOND;
	/** �׸� ��ü �ĺ� ��� : �ﰢ��
	 */
	public static FigureID IAMTRIANGLE;
	/** �׸� ��ü �ĺ� ��� : �ؽ�Ʈ
	 */
	public static FigureID IAMTEXT;
	/** �׸� ��ü �ĺ� ��� : Ŭ����
	 */
	public static FigureID IAMCLASSTEMPLATE;
	/** �׸� ��ü �ĺ� ��� : Ŭ���� �ؽ�Ʈ
	 */
	public static FigureID IAMCLASSTEXT;
	/** �׸� ��ü �ĺ� ��� : ��
	 */
	public static FigureID IAMLINES;
	/** �׸� ��ü �ĺ� ��� : ���� �ؽ�Ʈ
	 */
	public static FigureID IAMSINGLELINETEXT;
	/** �׸� ��ü �ĺ� ��� : qualification
	 */
	public static FigureID IAMQUALIFICATIONTEXT;
	/** �׸� ��ü �ĺ� ��� : ��ũ �ؽ�Ʈ
	 */
	public static FigureID IAMLINKATTRTEXT;
	/** �׸� ��ü �ĺ� ��� : ȭ�� ��ü
	 */
	public static FigureID IAMFILEOBJECT;
	/** �׸� ��ü �ĺ� ��� : �Ϲ�ȭ ����
	 */
	public static FigureID IAMGENTION;
	/** �׸� ��ü �ĺ� ��� : ����ȭ ����
	 */
	public static FigureID IAMAGGTION;
	/** �׸� ��ü �ĺ� ��� : ���� ����
	 */
	public static FigureID IAMCOLTION;
	/** �׸� ��ü �ĺ� ��� : ���� ����
	 */
	public static FigureID IAMASSTION;
	/** �׸� ��ü �ĺ� ��� : ��Ű��
	 */
	public static FigureID IAMPACKAGE;
	/** �׸� ��ü �ĺ� ��� : �׸�
	 */
	public static FigureID IAMPACKAGETEXT;
	/** �׸� ��ü �ĺ� ��� : ��Ʈ
	 */
	public static FigureID IAMNOTE;
	/** �׸� ��ü �ĺ� ��� : ��Ʈ �ؽ�Ʈ
	 */
	public static FigureID IAMNOTETEXT;
	/** �׸� ��ü �ĺ� ��� : �� ������
	 */
	public static FigureID WEARELINES;
	/** �׸� ��ü �ĺ� ��� : Ŭ���� ������
	 */
	public static FigureID WEARECLASSTEMPLATE;
	/** �׸� ��ü �ĺ� ��� : ���� ������
	 */
	public static FigureID WEARETION;
	/** �׸� ��ü �ĺ� ��� : ���� ���� ��ü ������
	 */
	public static FigureID WEAREEDITABLECOMPONENT;

	/**********************************/
	/** ������ ���丮 �̸�
	 */
	public static String RefinerDiretory;
	/** Viewer ���丮 �̸�
	 */
	public static String ViewerDirectory;
	/** ����� �ʱ�ȭ �Լ�
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