package Others;

public class Procesadores{
	String label;
	int value;
	public Procesadores(String label, int value) {
		this.label = label;
		this.value = value;
	}
	@Override
	public String toString() {
		return label;
	}
	public int getValue() {
		return value;
	}
}