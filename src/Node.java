import java.util.ArrayList;

public final class Node {
	private final byte pos[] = new byte[9];
	private final ArrayList<Node> links = new ArrayList<Node>(2);
	public Node() {
	}
	public Node(byte config[]){
		System.arraycopy(config, 0, pos, 0, 9);
	}
	public byte[] getPos() {
		return pos;
	}
	public ArrayList<Node> getLinks() {
		return links;
	}
	public static final int getId(byte config[]){
		int res = 0;
		for (int i=0; i<9; i+=1){
			res*=10;
			res+=config[i];
		}
		return res;
	}
	public static final String toString(final byte config[]){
		StringBuilder res = new StringBuilder();
		res.append('[');
		for (int i=0; i<9; i+=1){
			res.append(config[i]==0?'-':Character.valueOf((char)('0'+config[i])));
		}
		res.append(']');
		return res.toString();
	}
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append('(');
		res.append(toString(pos));
		if (!links.isEmpty()){
			res.append("=>{");
			int c=0;
			for (Node link : links){
				if (c>0) res.append(',');
				res.append(toString(link.getPos()));
			}
			res.append('}');
		}
		res.append(')');
		return res.toString();
	}
}
