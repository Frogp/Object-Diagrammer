package figure;

import java.io.Serializable;
/** 이 클래스는 텍스트 클래스 객체의 내용 중에 한 라인의 문자열을 관리하는 클래스이다.
 */
public class TextLine extends Object 
							implements Serializable 
{
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = 6832423691852257849L;
	/** 멤버 파싱을 위한 상태값 상수 : 에러 상태
	 */
	final static int ERRORSTATE = 0; 
	/** 멤버 파싱을 위한 상태값 상수 : 초기화 상태
	 */
	final static int INITIALSTATE = 1; 
	/** 멤버 파싱을 위한 상태값 상수 : 가시성 인식 상태
	 */
	final static int VISIBILITYACCEPTEDSTATE = 2;
	/** 멤버 파싱을 위한 상태값 상수 : 이름 수집 상태
	 */
	final static int NAMECOLLECTINGSTATE = 3;
	/** 멤버 파싱을 위한 상태값 상수 : 타입 인식 준비 상태
	 */
	final static int READYTOACCEPTTYPESTATE = 4;
	/** 멤버 파싱을 위한 상태값 상수 : 타입 문자열 수집 상태
	 */
	final static int TYPECOLLECTINGSTATE = 5;
	/** 멤버 파싱을 위한 상태값 상수 : 디폴트 값 수집 상태
	 */
	final static int DEFAULTVALUECOLLECTINGSTATE = 6;
	/** 멤버 파싱을 위한 상태값 상수 : 프로퍼티 값 수집 상태
	 */
	final static int PROPERTYCOLLECTINGSTATE = 7;
	/** 멤버 파싱을 위한 상태값 상수 : 인자 수집 상태
	 */
	final static int ARGUMENTCOLLECTINGSTATE = 8;
	/** 멤버 파싱을 위한 상태값 상수 : 인자 수집 종료 상태
	 */
	final static int ARGUMENTCOLLECTFINISHSTATE = 9;
	/** 라인 버퍼의 크기
	 */
	protected static int LINEBUFSIZ = 256;
	/** 바이트 문자열 버퍼: 멤버의 파싱때만 사용
	 */
	private char []lineBuffer;
	/** 라인 내용을 저장하는 스트링 버퍼
	 */
	public String theLine;
	/** 가시성을 나타내는 스트링 
	 */
	public int visibility;
	/** name 필드는 클래스 이름이거나 변수 이름 혹은 함수 이름이다. */
	public String name;
	/** 데이타 멤버인가 멤버 함수인가를 나타내는 flag
	 */
	public boolean dataMemberFlag;
	/** 타입 명을 나타내는 스트링
	 */
	public String type;
	/** 디폴트 값을 나타내는 스트링
	 */
	public String defaultValue;
	/** 인자들을 나타내는 스트링
	 */
	public String argument;
	/** 프로퍼티를 나타내는 스트링
	 */
	public String property;
	/** 현 라인의 내용을 스트링으로 변환해주는 함수이다.
	 */
	public String toString() {
		if (dataMemberFlag) {
			return new String(name);
		} else {
			return new String(name+"("+argument+")");
		}
	}
	/** destructor의 역할을 하는 함수이다. dangling reference의 가능성을 줄이기 위해
	 * 사용된다.
	 */
	public void delete() {
		lineBuffer = null;
		name = null;
		type = null;
		defaultValue = null;
		property = null;
	}
	/** 생성자이다.
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
	/** 입력 인자가 알파벳 문자인가 검사하는 함수이다.
	 */
	private boolean alphaOr_(char c) {
		if (Character.isJavaIdentifierStart(c)) return true;
//		if (c == '_') return true;
//		if (c >= 'a' && c <= 'z') return true;
//		if (c >= 'A' && c <= 'Z') return true;
		return false;
	}
	/** 입력 인자가 알파벳 문자나 숫자인가 검사하는 함수이다
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
	/** 라인의 내용을 C++ 인자 형식으로 바꿔주는 함수이다.
	 */
	private String convertAsCArgument(char aLine[]) {
		String cArg = new String();
		if (aLine[0] == '\0') return cArg;
		int stateNo = INITIALSTATE;
		int ptr = 0;
		/* buffer 변수 이름을 잠시 저장하기위해서만 사용 */
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
	/** 멤버 함수인 경우의 파싱 함수이다.
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
					/* 인수들의 마지막 괄호가 없음 */
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
	/** 데이타 멤버인 경우의 파싱 함수이다.
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
	/** 라인의 내용을 멤버 함수처럼 출력하는 함수이다.
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
	/** 라인의 내용을 데이타 멤버처럼 출력하는 함수이다.
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
	/** 라인 버퍼의 내용을 비우는 함수이다.
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
	/** 라인에 내용이 있는가를 검사하는 함수이다.
	 */
	public boolean hasContent() {
		if (theLine.length() == 0) return false;
		else return true;
	}
	/** 데이타 멤버 theLine의 길이에 대한 읽기 함수이다.
	 */
	public int getLength() {
		return theLine.length(); 
	}
	/** 라인 버퍼의 i-번째 문자를 읽는 함수이다.
	 */
	public char valueAt(int i) {
		return theLine.charAt(i);
	}
	/** 현재 텍스트 라인을 복사하는 함수이다.
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
	/** 인자에서 명시된 텍스트 라인의 내용을 복사하는 함수이다.
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
	/** 버퍼의 내용에 인자에서 명시된 스트링을 저장하는 함수이다.
	 */
	public void setString(String s) {
		theLine = s;
	}
	public String getString() {
		return theLine;
	}
	/** 현 라인이 이름을 위한 라인인 경우 파싱하는 함수이다.
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