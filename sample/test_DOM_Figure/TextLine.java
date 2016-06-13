package figure;

import java.io.Serializable;
/** �� Ŭ������ �ؽ�Ʈ Ŭ���� ��ü�� ���� �߿� �� ������ ���ڿ��� �����ϴ� Ŭ�����̴�.
 */
public class TextLine extends Object 
							implements Serializable 
{
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = 6832423691852257849L;
	/** ��� �Ľ��� ���� ���°� ��� : ���� ����
	 */
	final static int ERRORSTATE = 0; 
	/** ��� �Ľ��� ���� ���°� ��� : �ʱ�ȭ ����
	 */
	final static int INITIALSTATE = 1; 
	/** ��� �Ľ��� ���� ���°� ��� : ���ü� �ν� ����
	 */
	final static int VISIBILITYACCEPTEDSTATE = 2;
	/** ��� �Ľ��� ���� ���°� ��� : �̸� ���� ����
	 */
	final static int NAMECOLLECTINGSTATE = 3;
	/** ��� �Ľ��� ���� ���°� ��� : Ÿ�� �ν� �غ� ����
	 */
	final static int READYTOACCEPTTYPESTATE = 4;
	/** ��� �Ľ��� ���� ���°� ��� : Ÿ�� ���ڿ� ���� ����
	 */
	final static int TYPECOLLECTINGSTATE = 5;
	/** ��� �Ľ��� ���� ���°� ��� : ����Ʈ �� ���� ����
	 */
	final static int DEFAULTVALUECOLLECTINGSTATE = 6;
	/** ��� �Ľ��� ���� ���°� ��� : ������Ƽ �� ���� ����
	 */
	final static int PROPERTYCOLLECTINGSTATE = 7;
	/** ��� �Ľ��� ���� ���°� ��� : ���� ���� ����
	 */
	final static int ARGUMENTCOLLECTINGSTATE = 8;
	/** ��� �Ľ��� ���� ���°� ��� : ���� ���� ���� ����
	 */
	final static int ARGUMENTCOLLECTFINISHSTATE = 9;
	/** ���� ������ ũ��
	 */
	protected static int LINEBUFSIZ = 256;
	/** ����Ʈ ���ڿ� ����: ����� �Ľ̶��� ���
	 */
	private char []lineBuffer;
	/** ���� ������ �����ϴ� ��Ʈ�� ����
	 */
	public String theLine;
	/** ���ü��� ��Ÿ���� ��Ʈ�� 
	 */
	public int visibility;
	/** name �ʵ�� Ŭ���� �̸��̰ų� ���� �̸� Ȥ�� �Լ� �̸��̴�. */
	public String name;
	/** ����Ÿ ����ΰ� ��� �Լ��ΰ��� ��Ÿ���� flag
	 */
	public boolean dataMemberFlag;
	/** Ÿ�� ���� ��Ÿ���� ��Ʈ��
	 */
	public String type;
	/** ����Ʈ ���� ��Ÿ���� ��Ʈ��
	 */
	public String defaultValue;
	/** ���ڵ��� ��Ÿ���� ��Ʈ��
	 */
	public String argument;
	/** ������Ƽ�� ��Ÿ���� ��Ʈ��
	 */
	public String property;
	/** �� ������ ������ ��Ʈ������ ��ȯ���ִ� �Լ��̴�.
	 */
	public String toString() {
		if (dataMemberFlag) {
			return new String(name);
		} else {
			return new String(name+"("+argument+")");
		}
	}
	/** destructor�� ������ �ϴ� �Լ��̴�. dangling reference�� ���ɼ��� ���̱� ����
	 * ���ȴ�.
	 */
	public void delete() {
		lineBuffer = null;
		name = null;
		type = null;
		defaultValue = null;
		property = null;
	}
	/** �������̴�.
	 */
	public TextLine() {
		name = new String("");
		dataMemberFlag = false;
		type = new String("void");
		defaultValue = new String("");
		property = new String("");
		argument = new String("");
		visibility = Const.PUBLICID;
		lineBuffer = new char[LINEBUFSIZ];
		theLine = new String("");
		clear();
	}
	/** �Է� ���ڰ� ���ĺ� �����ΰ� �˻��ϴ� �Լ��̴�.
	 */
	private boolean alphaOr_(char c) {
		if (Character.isJavaIdentifierStart(c)) return true;
//		if (c == '_') return true;
//		if (c >= 'a' && c <= 'z') return true;
//		if (c >= 'A' && c <= 'Z') return true;
		return false;
	}
	/** �Է� ���ڰ� ���ĺ� ���ڳ� �����ΰ� �˻��ϴ� �Լ��̴�
	 */
	private boolean alphaOrNum(char c) {
		if (Character.isJavaIdentifierPart(c)) return true;
//		if (c == '_') return true;
//		if (c == '-') return true;
//		if (c >= '0' && c <= '9') return true;
//		if (c >= 'a' && c <= 'z') return true;
//		if (c >= 'A' && c <= 'Z') return true;
		return false;
	}
	/** ������ ������ C++ ���� �������� �ٲ��ִ� �Լ��̴�.
	 */
	private String convertAsCArgument(char aLine[]) {
		String cArg = new String();
		if (aLine[0] == '\0') return cArg;
		int stateNo = INITIALSTATE;
		int ptr = 0;
		/* buffer ���� �̸��� ��� �����ϱ����ؼ��� ��� */
		char []buffer = new char[LINEBUFSIZ];
		int bufPtr = 0;
		while (true) {
			char c = aLine[ptr];
			switch(stateNo) {
			case ERRORSTATE:
				// error state
				return cArg;
			case INITIALSTATE:
				if (c == ' ') {
					stateNo = INITIALSTATE;
				} else if (c == '\0') {
					return cArg;
				} else if (alphaOr_(c)) {
					buffer[bufPtr] = c;
					bufPtr++;
					stateNo = NAMECOLLECTINGSTATE;
				} else if (c == ':') {
					buffer[bufPtr] = '\0';
					stateNo = READYTOACCEPTTYPESTATE;
				} else {
					stateNo = ERRORSTATE;
				}
				break;
			case NAMECOLLECTINGSTATE:
				if (c == ' ') {
					stateNo = NAMECOLLECTINGSTATE;
				} else if (c == '\0') {
					buffer[bufPtr] = '\0';
					if (bufPtr > 0) {
						String argName = new String(buffer,0,MySys.strlen(buffer,0));
						cArg = cArg + argName;
					}
					return cArg;
				} else if (c == ':') {
					buffer[bufPtr] = '\0';
					stateNo = READYTOACCEPTTYPESTATE;
				} else if (c == '=') {
					buffer[bufPtr] = '\0';
					if (bufPtr > 0) {
						String argName = new String(buffer,0,MySys.strlen(buffer,0));
						cArg = cArg + argName;
					}
					buffer[0] = '\0';
					bufPtr = 0;
					cArg = cArg + " = ";
					stateNo = DEFAULTVALUECOLLECTINGSTATE;
				} else if (c == ',') {
					buffer[bufPtr] = '\0';
					if (bufPtr > 0) {
						String argName = new String(buffer,0,MySys.strlen(buffer,0));
						cArg = cArg + argName;
					}
					buffer[0] = '\0';
					bufPtr = 0;
					cArg = cArg + ",";
					stateNo = INITIALSTATE;
				} else if (alphaOrNum(c)) {
					buffer[bufPtr] = c;
					bufPtr++;
					stateNo = NAMECOLLECTINGSTATE;
				} else {
					stateNo = ERRORSTATE;
				}
				break;
			case READYTOACCEPTTYPESTATE:
				if (c == ' ') {
					stateNo = READYTOACCEPTTYPESTATE;
				} else if (c == '\0') {
					buffer[bufPtr] = '\0';
					if (bufPtr > 0) {
						String argName = new String(buffer,0,MySys.strlen(buffer,0));
						cArg = cArg + argName;
					}
					return cArg;
				} else if (alphaOr_(c)) {
					cArg = cArg + c;
					stateNo = TYPECOLLECTINGSTATE;
				} else {
					stateNo = ERRORSTATE;
				}
				break;
			case TYPECOLLECTINGSTATE:
				if (c == ' ') {
					stateNo = TYPECOLLECTINGSTATE;
				} else if (c == '\0') {
					buffer[bufPtr] = '\0';
					if (bufPtr > 0) {
						String argName = new String(buffer,0,MySys.strlen(buffer,0));
						cArg = cArg + " " + argName;
					}
					return cArg;
				} else if (c == '=') {
					buffer[bufPtr] = '\0';
					if (bufPtr > 0) {
						String argName = new String(buffer,0,MySys.strlen(buffer,0));
						cArg = cArg + " " + argName;
					}
					buffer[0] = '\0';
					bufPtr = 0;
					cArg = cArg + " = ";
					stateNo = DEFAULTVALUECOLLECTINGSTATE;	
				} else if (c == ',') {
					buffer[bufPtr] = '\0';
					if (bufPtr > 0) {
						String argName = new String(buffer,0,MySys.strlen(buffer,0));
						cArg = cArg + " " + argName;
					}
					buffer[0] = '\0';
					bufPtr = 0;
					cArg = cArg + ",";
					stateNo = INITIALSTATE;
				} else if (alphaOrNum(c) || c == '&' || c == '*') {
					cArg = cArg + c;
					stateNo = TYPECOLLECTINGSTATE;
				} else {
					stateNo = ERRORSTATE;
				}
				break;
			case DEFAULTVALUECOLLECTINGSTATE:
				if (c == '\0') {
					return cArg;
				} else if (c == ',') {
					cArg = cArg + c;
					stateNo = INITIALSTATE;
				} else {
					cArg = cArg + c;
					stateNo = DEFAULTVALUECOLLECTINGSTATE;
				}
				break;
			}
			ptr++;
		}
	}
	private void setLineBufferForParse() {
		int len = theLine.length();
		int i = 0;
		for(i = 0; i < len; i++) {
			lineBuffer[i] = theLine.charAt(i);
		}		
		lineBuffer[i] = '\0';
	}
	/** ��� �Լ��� ����� �Ľ� �Լ��̴�.
	 */
	public void doParseForMethod() {
		setLineBufferForParse();
		if (lineBuffer[0] == '\0') return;
		int stateNo = INITIALSTATE;
		int ptr = 0;
		char []buffer = new char[LINEBUFSIZ];
		int bufPtr = 0;
		while (true) {
			char c = lineBuffer[ptr];
			switch(stateNo) {
			case ERRORSTATE:
				// error state
				return;
			case INITIALSTATE:
				if (c == ' ') {
					stateNo = INITIALSTATE;
				} else if (c == '\0') {
					return;
				} else if (alphaOr_(c)) {
					buffer[bufPtr] = c;
					bufPtr++;
					stateNo = NAMECOLLECTINGSTATE;
				} else if (c == '+') {
					visibility = Const.PUBLICID;
					stateNo = VISIBILITYACCEPTEDSTATE;
				} else if (c == '-') {
					visibility = Const.PRIVATEID;
					stateNo = VISIBILITYACCEPTEDSTATE;
				} else if (c == '#') {
					visibility = Const.PROTECTEDID;
					stateNo = VISIBILITYACCEPTEDSTATE;
				} else {
					stateNo = ERRORSTATE;
				}
				break;
			case VISIBILITYACCEPTEDSTATE:
				if (c == ' ') {
					stateNo = VISIBILITYACCEPTEDSTATE;
				} else if (c == '\0') {
					return;
				} else if (alphaOr_(c)) {
					buffer[bufPtr] = c;
					bufPtr++;
					stateNo = NAMECOLLECTINGSTATE;
				} else {
					stateNo = ERRORSTATE;
				}
				break;
			case NAMECOLLECTINGSTATE:
				if (c == ' ') {
					stateNo = NAMECOLLECTINGSTATE;
				} else if (c == '\0') {
					buffer[bufPtr] = '\0';
					if (bufPtr == 0) return;
					name = new String(buffer,0,MySys.strlen(buffer,0));
					return;
				} else if (c == ':') {
					buffer[bufPtr] = '\0';
					if (bufPtr > 0) {
						name = new String(buffer,0,MySys.strlen(buffer,0));
					}
					buffer[0] = '\0';
					bufPtr = 0;
					stateNo = READYTOACCEPTTYPESTATE;
				} else if (c == '(') {
					buffer[bufPtr] = '\0';
					if (bufPtr > 0) {
						name = new String(buffer,0,MySys.strlen(buffer,0));
					}
					buffer[0] = '\0';
					bufPtr = 0;
					stateNo = ARGUMENTCOLLECTINGSTATE;
				} else if (c == '{') {
					buffer[bufPtr] = '\0';
					if (bufPtr > 0) {
						name = new String(buffer,0,MySys.strlen(buffer,0));
					}
					buffer[0] = '\0';
					bufPtr = 0;
					stateNo = PROPERTYCOLLECTINGSTATE;
				} else if (alphaOrNum(c)) {
					buffer[bufPtr] = c;
					bufPtr++;
					stateNo = NAMECOLLECTINGSTATE;
				} else {
					stateNo = ERRORSTATE;
				}
				break;
			case READYTOACCEPTTYPESTATE:
				if (c == ' ') {
					stateNo = READYTOACCEPTTYPESTATE;
				} else if (c == '\0') {
					return;
				} else if (alphaOr_(c)) {
					buffer[bufPtr] = c;
					bufPtr++;
					stateNo = TYPECOLLECTINGSTATE;
				} else {
					stateNo = ERRORSTATE;
				}
				break;
			case TYPECOLLECTINGSTATE:
				if (c == ' ') {
					stateNo = TYPECOLLECTINGSTATE;
				} else if (c == '\0') {
					buffer[bufPtr] = '\0';
					if (bufPtr == 0) return;
					type = new String(buffer,0,MySys.strlen(buffer,0));
					return;
				} else if (c == '{') {
					buffer[bufPtr] = '\0';
					if (bufPtr > 0) {
						type = new String(buffer,0,MySys.strlen(buffer,0));
					}
					buffer[0] = '\0';
					bufPtr = 0;
					stateNo = PROPERTYCOLLECTINGSTATE;
				} else if (alphaOrNum(c) || c == '&' || c == '*') {
					buffer[bufPtr] = c;
					bufPtr++;
					stateNo = TYPECOLLECTINGSTATE;
				} else {
					stateNo = ERRORSTATE;
				}
				break;
			case ARGUMENTCOLLECTINGSTATE:
				if (c == '\0') {
					/* �μ����� ������ ��ȣ�� ���� */
					buffer[bufPtr] = '\0';
					stateNo = ERRORSTATE;
					ptr--;
				} else if (c == ' ' && bufPtr == 0) {
					stateNo = ARGUMENTCOLLECTINGSTATE;
				} else if (c == ')') {
					buffer[bufPtr] = '\0';
					if (bufPtr > 0) {
						argument = convertAsCArgument(buffer);
					}
					buffer[0] = '\0';
					bufPtr = 0;
					stateNo = ARGUMENTCOLLECTFINISHSTATE;	
				} else {
					buffer[bufPtr] = c;
					bufPtr++;
					stateNo = ARGUMENTCOLLECTINGSTATE;
				}
				break;
			case ARGUMENTCOLLECTFINISHSTATE:
				if (c == ' ') {
					stateNo = ARGUMENTCOLLECTFINISHSTATE;
				} else if (c == '\0') {
					return;
				} else if (c == ':') {
					stateNo = TYPECOLLECTINGSTATE;
				} else if (c == '{') {
					stateNo = PROPERTYCOLLECTINGSTATE;
				} else {
					stateNo = ERRORSTATE;
				}
				break;
			case PROPERTYCOLLECTINGSTATE:
				if (c == '\0' || c == '}') {
					buffer[bufPtr] = '\0';
					if (bufPtr == 0) return;
					property = new String(buffer,0,MySys.strlen(buffer,0));
					return;
				} else if (c == ' ' && bufPtr == 0) {
					stateNo = PROPERTYCOLLECTINGSTATE;
				} else {
					buffer[bufPtr] = c;
					bufPtr++;
					stateNo = PROPERTYCOLLECTINGSTATE;
				}
				break;
			}
			ptr++;
		}
	}
	/** ����Ÿ ����� ����� �Ľ� �Լ��̴�.
	 */
	public void doParseForVar() {
		setLineBufferForParse();
		if (lineBuffer[0] == '\0') return;
		int stateNo = INITIALSTATE;
		int ptr = 0;
		char []buffer = new char[LINEBUFSIZ];
		int bufPtr = 0;
		while (true) {
			char c = lineBuffer[ptr];
			switch(stateNo) {
			case ERRORSTATE:
				// error state
				return;
			case INITIALSTATE:
				if (c == ' ') {
					stateNo = INITIALSTATE;
				} else if (c == '\0') {
					return;
				} else if (alphaOr_(c) || c == '/') {
					buffer[bufPtr] = c;
					bufPtr++;
					stateNo = NAMECOLLECTINGSTATE;
				} else if (c == '+') {
					visibility = Const.PUBLICID;
					stateNo = VISIBILITYACCEPTEDSTATE;
				} else if (c == '-') {
					visibility = Const.PRIVATEID;
					stateNo = VISIBILITYACCEPTEDSTATE;
				} else if (c == '#') {
					visibility = Const.PROTECTEDID;
					stateNo = VISIBILITYACCEPTEDSTATE;
				} else {
					stateNo = ERRORSTATE;
				}
				break;
			case VISIBILITYACCEPTEDSTATE:
				if (c == ' ') {
					stateNo = VISIBILITYACCEPTEDSTATE;
				} else if (c == '\0') {
					return;
				} else if (alphaOr_(c) || c == '/') {
					buffer[bufPtr] = c;
					bufPtr++;
					stateNo = NAMECOLLECTINGSTATE;
				} else {
					stateNo = ERRORSTATE;
				}
				break;
			case NAMECOLLECTINGSTATE:
				if (c == ' ') {
					stateNo = NAMECOLLECTINGSTATE;
				} else if (c == '\0') {
					buffer[bufPtr] = '\0';
					if (bufPtr == 0) return;
					name = new String(buffer,0,MySys.strlen(buffer,0));
					return;
				} else if (c == ':') {
					buffer[bufPtr] = '\0';
					if (bufPtr > 0) {
						name = new String(buffer,0,MySys.strlen(buffer,0));
					}
					buffer[0] = '\0';
					bufPtr = 0;
					stateNo = READYTOACCEPTTYPESTATE;
				} else if (c == '=') {
					buffer[bufPtr] = '\0';
					if (bufPtr > 0) {
						name = new String(buffer,0,MySys.strlen(buffer,0));
					}
					buffer[0] = '\0';
					bufPtr = 0;
					stateNo = DEFAULTVALUECOLLECTINGSTATE;
				} else if (c == '{') {
					buffer[bufPtr] = '\0';
					if (bufPtr > 0) {
						name = new String(buffer,0,MySys.strlen(buffer,0));
					}
					buffer[0] = '\0';
					bufPtr = 0;
					stateNo = PROPERTYCOLLECTINGSTATE;
				} else if (alphaOrNum(c)) {
					buffer[bufPtr] = c;
					bufPtr++;
					stateNo = NAMECOLLECTINGSTATE;
				} else {
					stateNo = ERRORSTATE;
				}
				break;
			case READYTOACCEPTTYPESTATE:
				if (c == ' ') {
					stateNo = READYTOACCEPTTYPESTATE;
				} else if (c == '\0') {
					return;
				} else if (alphaOr_(c)) {
					buffer[bufPtr] = c;
					bufPtr++;
					stateNo = TYPECOLLECTINGSTATE;
				} else {
					stateNo = ERRORSTATE;
				}
				break;
			case TYPECOLLECTINGSTATE:
				if (c == ' ') {
					stateNo = TYPECOLLECTINGSTATE;
				} else if (c == '\0') {
					buffer[bufPtr] = '\0';
					if (bufPtr == 0) return;
					type = new String(buffer,0,MySys.strlen(buffer,0));
					return;
				} else if (c == '=') {
					buffer[bufPtr] = '\0';
					if (bufPtr > 0) {
						type = new String(buffer,0,MySys.strlen(buffer,0));
					}
					buffer[0] = '\0';
					bufPtr = 0;
					stateNo = DEFAULTVALUECOLLECTINGSTATE;	
				} else if (c == '{') {
					buffer[bufPtr] = '\0';
					if (bufPtr > 0) {
						type = new String(buffer,0,MySys.strlen(buffer,0));
					}
					buffer[0] = '\0';
					bufPtr = 0;
					stateNo = PROPERTYCOLLECTINGSTATE;
				} else if (alphaOrNum(c) || c == '&' || c == '*') {
					buffer[bufPtr] = c;
					bufPtr++;
					stateNo = TYPECOLLECTINGSTATE;
				} else {
					stateNo = ERRORSTATE;
				}
				break;
			case DEFAULTVALUECOLLECTINGSTATE:
				if (c == '\0') {
					buffer[bufPtr] = '\0';
					if (bufPtr == 0) return;
					defaultValue = new String(buffer,0,MySys.strlen(buffer,0));
					return;
				} else if (c == '{') {
					buffer[bufPtr] = '\0';
					if (bufPtr > 0) {
						defaultValue = new String(buffer,0,MySys.strlen(buffer,0));
					}
					buffer[0] = '\0';
					bufPtr = 0;
					stateNo = PROPERTYCOLLECTINGSTATE;	
				} else if (c == ' ' && bufPtr == 0) {
					stateNo = DEFAULTVALUECOLLECTINGSTATE;
				} else {
					buffer[bufPtr] = c;
					bufPtr++;
					stateNo = DEFAULTVALUECOLLECTINGSTATE;
				}
				break;
			case PROPERTYCOLLECTINGSTATE:
				if (c == '\0' || c == '}') {
					buffer[bufPtr] = '\0';
					if (bufPtr == 0) return;
					property = new String(buffer,0,MySys.strlen(buffer,0));
					return;
				} else if (c == ' ' && bufPtr == 0) {
					stateNo = PROPERTYCOLLECTINGSTATE;
				} else {
					buffer[bufPtr] = c;
					bufPtr++;
					stateNo = PROPERTYCOLLECTINGSTATE;
				}
				break;
			}
			ptr++;
		}
	}
	/** ������ ������ ��� �Լ�ó�� ����ϴ� �Լ��̴�.
	 */
	public void printAsMethod() {
		String v;
		if (visibility == Const.PUBLICID) {
			v = new String("public ");
		} else if (visibility == Const.PRIVATEID) {
			v = new String("private ");
		} else if (visibility == Const.PROTECTEDID) {
			v = new String("protected ");
		} else {
			v = "";
		}
		if (property.length() < 1) {
			System.out.println(v+type+" "+name+"("+argument+")");
		} else {
			System.out.println(v+property+" "+type+" "+name+"("+argument+")");
		}
	}
	/** ������ ������ ����Ÿ ���ó�� ����ϴ� �Լ��̴�.
	 */
	public void printAsVar() {
		String v;
		if (visibility == Const.PUBLICID) {
			v = new String("public ");
		} else if (visibility == Const.PRIVATEID) {
			v = new String("private ");
		} else if (visibility == Const.PROTECTEDID) {
			v = new String("protected ");
		} else {
			v = "";
		}
		String dValue;
		if (defaultValue.length() < 1) {
			dValue = new String("");
		} else {
			dValue = " = " + defaultValue;
		}
		if (property.length() < 1) {
			System.out.println(v+type+" "+name+dValue);
		} else {
			System.out.println(v+property+" "+type+" "+name+dValue);
		}
	}
	/** ���� ������ ������ ���� �Լ��̴�.
	 */
	public void clear() {
		name = new String("");
		theLine = new String("");
		dataMemberFlag = false;
		type = new String("void");
		defaultValue = new String("");
		property = new String("");
		argument = new String("");
		visibility = Const.PUBLICID;
		for(int i = 0; i < LINEBUFSIZ; i++) {
			lineBuffer[i] = '\0';
		}
	}
	/** ���ο� ������ �ִ°��� �˻��ϴ� �Լ��̴�.
	 */
	public boolean hasContent() {
		if (theLine.length() == 0) return false;
		else return true;
	}
	/** ����Ÿ ��� theLine�� ���̿� ���� �б� �Լ��̴�.
	 */
	public int getLength() {
		return theLine.length(); 
	}
	/** ���� ������ i-��° ���ڸ� �д� �Լ��̴�.
	 */
	public char valueAt(int i) {
		return theLine.charAt(i);
	}
	/** ���� �ؽ�Ʈ ������ �����ϴ� �Լ��̴�.
	 */
	public TextLine born() {
		TextLine copied = new TextLine();
		copied.copy(this);
		return copied;
	}
	public void copySubstring(TextLine fromLine,int from,int to) {
		theLine = new String(fromLine.theLine.substring(from,to));
	}
	public void truncateStringFrom(int i) {
		theLine = new String(theLine.substring(0,i));
	}
	/** ���ڿ��� ��õ� �ؽ�Ʈ ������ ������ �����ϴ� �Լ��̴�.
	 */
	public void copy(TextLine from) {
		clear();
		for(int i = 0; i < LINEBUFSIZ; i++) {
			lineBuffer[i] = from.lineBuffer[i];
		}
		theLine = new String(from.theLine);
		visibility = from.visibility;
		dataMemberFlag = from.dataMemberFlag;
		if (from.name == null) {
			name = null;
		} else {
			name = new String(from.name);
		}
		if (from.type == null) {
			type = null;
		} else {
			type = new String(from.type);
		}
		if (defaultValue == null) {
			defaultValue = null;
		} else {
			defaultValue = new String(from.defaultValue);
		}
		if (argument == null) {
			argument = null;
		} else {
			argument = new String(from.argument);
		}
		if (property == null) {
			property = null;
		} else {
			property = new String(from.property);
		}
	}
	/** ������ ���뿡 ���ڿ��� ��õ� ��Ʈ���� �����ϴ� �Լ��̴�.
	 */
	public void setString(String s) {
		theLine = s;
	}
	public String getString() {
		return theLine;
	}
	/** �� ������ �̸��� ���� ������ ��� �Ľ��ϴ� �Լ��̴�.
	 */
	public void doParseForName()
	{				
		setLineBufferForParse();
		if (lineBuffer[0] == '\0') return;
		int stateNo = INITIALSTATE;
		int ptr = 0;
		char []buffer = new char[LINEBUFSIZ];
		int bufPtr = 0;
		while (true) 
		{
			char c = lineBuffer[ptr];
			switch(stateNo) 
			{
				case ERRORSTATE:
					// error state
					return;
				case INITIALSTATE:
					if (c == ' ') {
						stateNo = INITIALSTATE;
					} else if (c == '\0') {
						return;
					} else if(alphaOr_(c) || c =='/' || c =='<' || c == '>') {
						buffer[bufPtr] = c;
						bufPtr++;
						stateNo = NAMECOLLECTINGSTATE;
					} else if (c == '+') {
						visibility = Const.PUBLICID;
						stateNo = VISIBILITYACCEPTEDSTATE;
					} else if (c == '-') {
						visibility = Const.PRIVATEID;
						stateNo = VISIBILITYACCEPTEDSTATE;
					} else if (c == '#') {
						visibility = Const.PROTECTEDID;
						stateNo = VISIBILITYACCEPTEDSTATE;
					} else {
						stateNo = ERRORSTATE;
					}
					break;
				case VISIBILITYACCEPTEDSTATE:
					if (c == ' ') {
						stateNo = VISIBILITYACCEPTEDSTATE;
					} else if (c == '\0') {
						return;
					} // else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
					else if(alphaOr_(c) || c =='/' || c =='<' || c == '>') {
						buffer[bufPtr] = c;
						bufPtr++;
						stateNo = NAMECOLLECTINGSTATE;
					} else {
						stateNo = ERRORSTATE;
					}
					break;
				case NAMECOLLECTINGSTATE:
					if (c == ' ') {
						stateNo = NAMECOLLECTINGSTATE;
					} else if (c == '\0') {
						buffer[bufPtr] = '\0';
						if (bufPtr == 0) return;
						name = new String(buffer,0,MySys.strlen(buffer,0));
						return;
					}  else if (alphaOrNum(c) || c =='<' || c == '>') {
						buffer[bufPtr] = c;
						bufPtr++;
						stateNo = NAMECOLLECTINGSTATE;
					} else {
						stateNo = ERRORSTATE;
					}
					
				break;
			}
			ptr++;
		}
	}
	
}