package symboltable;

public abstract class SymbolInfo{
	private String name;

	public SymbolInfo(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public abstract boolean LValue();
	public abstract boolean RValue();
	public abstract String getSymbolType();
}