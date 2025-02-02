package dominio.customer.enums;

public enum Type {
	DEFAULT(1), SUBSCRIBER(2);
	
	private final int value;
	
	Type(int type) {
		this.value = type;
	}
	
	public int getType() {
		return this.value;
	}
}
