package symTable;

public class SymTable{
	final static int int MAXSIZE = 19937;
	final static int SHIFT = 4;

	public void generate(Object o){

	}

	public void insert(Object o){

	}

	public int lookup(Object o){

	}

	public void delete(Object o){

	}

	public int hash(char * key){
		int temp = 0;
		int i = 0;
		while(key[i] != '\0'){
			temp = ((temp << SHIFT) + key[i]) % MAXSIZE;
			i++;
		}
		return temp;
	}
}