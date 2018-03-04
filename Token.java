class Token {

  public final static int ERROR = 0;
  public final static int ELSE = 1;
  public final static int IF = 2;
  public final static int INT = 3;
  public final static int RETURN = 4;
  public final static int VOID = 5;
  public final static int WHILE = 6;
  public final static int SPECIAL = 7;
  public final static int ID = 8;
  public final static int NUM = 9;
  public final static int UNKNOWN = 10;

  public int m_type;
  public String m_value;
  public int m_line;
  public int m_column;
  
  Token (int type, String value, int line, int column) {
    m_type = type;
    m_value = value;
    m_line = line;
    m_column = column;
  }

  public String toString() {
    switch (m_type) {
      case ELSE:
        return "ELSE";
      case IF:
        return "IF";
      case INT:
        return "INT";
      case RETURN:
        return "RETURN";
      case VOID:
        return "VOID";
      case WHILE:
        return "WHILE";
      case SPECIAL:
        return "SPECIAL(" + m_value + ")";
      case ID:
        return "ID(" + m_value + ")";
      case NUM:
        return "NUM(" + m_value + ")";
      case UNKNOWN:
        return "UNKNOWN(" + m_value + ")";
      default:
        return "UNKNOWN(" + m_value + ")";
    }
  }
}

