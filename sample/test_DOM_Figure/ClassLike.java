package figure;
import java.awt.Point;
/** �� �������̽��� Ŭ���� ��ü�� ��Ű�� ��ü�� ������ �η��� ��üó��
 * �̿��ϱ� ���� ���Ǵ� �������̽��̴�. ClassTemplate Ŭ������ PackageTemplate
 * Ŭ������ �� �������̽��� �����Ѵ�.
 */
public interface ClassLike {
	/** ���� ��ü�� ����Ű�� �����͸� ���ο� ������ �����Ѵ�.
	 */
	public boolean replaceAnytionPtr(AnyTion a,AnyTion b);
	/** �� ǥ��� ��ü�� ������ ����� �Լ��̴�.
	 */
	public void makeRegion();
	/** ����Ÿ ��� _region�� access �Լ��̴�.
	 */
	public CRgn region();
	/** ����Ÿ ��� _maxRegion�� access �Լ��̴�.
	 */
	public CRgn maxRegion();
	/** �Է� ������ ������ �簢���� ������� ��ġ�ϵ��� ���̸� �����ϴ� �Լ��̴�.
	 */
	public boolean adjustLine(Line a,boolean b);
	/** �Է� ������ ������ �𼭸��� Ȯ����Ѽ� �� �簢�� ��ü�� �߾ӱ��� ��������ϴ�
	 * �Լ��̴�.
	 */
	public void expandLineToCenter(Line a,boolean b);
	/** ���ڿ� ��õ� ���� ��ü ���۷����� ���� ����Ʈ���� �����ϴ� �Լ��̴�.
	 */
	public void removeFromAnytionList(AnyTion a);
	/** ���ڿ� ��õ� ���� ��ü ���۷����� ���� ����Ʈ�� �߰��ϴ� �Լ��̴�.
	 */
	public void attachToAnytionList(AnyTion a);
	/** ��ü�� identity�� ���� �Լ��̴�.
	 */
	public FigureID whoAreYou();
	/** ����Ÿ ��� controller�� access �Լ��̴�.
	 */
	public GraphicController controller();
	/** �� ��ü�� �𼭸� ��ǥ, x1,y1,x2,y2�� ���� �˾Ƴ��� �Լ��̴�.
	 */
	public void coords(Point p1,Point p2);
	/** �� ��ü�� �߾� ��ǥ���� �˾Ƴ��� �Լ��̴�.
	 */
	public Point center();
	/** ��ǥ x,y �� �� ��ü ���ο� ���ԵǴ°��� �����ϴ� �Լ��̴�.
	 */
	public boolean contains(int a, int b);
	/** �� Ŭ������ ��Ű�� ��ü�� �̸��� �˾ƿ��� �Լ��̴�.
	 */
	public String getName();
	/** ������ Ŭ������ �� ��ü�� ������ ���ԵǴ°��� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean hasAncestor(ClassLike tocheck);
	/** �� ��ü�� ���� �ؽ�Ʈ ���� �۾��� ���۵����ϴ� �Լ��̴�.
	 */
	public void localStartEdit(int a, int b);
}