package playSound;

public class Sample {
	private short[] buffer;
	private int size;
	
	public Sample(short[] buf, int s) {
		buffer = buf.clone();
		size = s;
	}
	
	public short[] GetBuffer() {
		return buffer;
	}
	
	public int GetSize() {
		return size;
	}
}
