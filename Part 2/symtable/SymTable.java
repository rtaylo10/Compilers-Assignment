package symtable;

public class SymTable{
	final static int MAXSIZE = 19937;
	final static int SHIFT = 4;

	public SymTable(){

	}

	public void generate(Object o){

	}

	public void insert(Object o){

	}

	public int lookup(Object o){
		return 0;
	}

	public void delete(Object o){

	}

	public int hash(char key[]){
		int temp = 0;
		int i = 0;
		while(key[i] != '\0'){
			temp = ((temp << SHIFT) + key[i]) % MAXSIZE;
			i++;
		}
		return temp;
	}
}