import java.util.HashMap;

public class GameGraph {
	
	private final HashMap<Integer, Node> nodes;
	
	public GameGraph() {
		int size=9*8*7*6*5*4*3*2*2;
		nodes = new HashMap<>(size);
	}
	
	public void buildGraph(){
		
		byte config[] = new byte[9];
		buildGraph(config, 0);
		
	}
	
	private void buildGraph(byte[] config, int pos) {
		if (pos==9){
			int nodeId = Node.getId(config);
			Node theNode = nodes.get(nodeId);
			if (theNode==null){
				theNode = new Node(config);
				nodes.put(nodeId, theNode);
			}
			computeLinks(theNode);
			return;
		}
		
		digitLoop: for (byte i=0; i<9; i+=1){
			for (int j=0; j<pos; j+=1)
				if (config[j]==i)
					continue digitLoop;
			config[pos]=i;
			buildGraph(config, pos+1);
		}
	}
	
	private void computeLinks(Node theNode) {
		for (byte[] destConfig : getTransitions(theNode.getPos())){
			int destNodeId = Node.getId(destConfig);
			Node destNode = nodes.get(destNodeId);
			if (destNode==null){
				destNode = new Node(destConfig);
				nodes.put(destNodeId, destNode);
				theNode.getLinks().add(destNode);
			}
			if (!destNode.getLinks().contains(theNode))
				destNode.getLinks().add(theNode);
		}
	}
	
	public static final byte[][] getTransitions(byte[] config){
		int zeroPos = 0;
		for (int i=0; i<9; i+=1){
			if (config[i]==0){
				zeroPos=i;
				break;
			}
		}
		switch(zeroPos){
		case 0:
			return new byte[][]{
					getSwapped(config, 0, 1),
					getSwapped(config, 0, 3)
			};
		case 1:
			return new byte[][]{
					getSwapped(config, 1, 0),
					getSwapped(config, 1, 2),
					getSwapped(config, 1, 4)
			};
		case 2:
			return new byte[][]{
					getSwapped(config, 2, 1),
					getSwapped(config, 2, 5)
			};
		case 3:
			return new byte[][]{
					getSwapped(config, 3, 0),
					getSwapped(config, 3, 4),
					getSwapped(config, 3, 6)
			};
		case 4:
			return new byte[][]{
					getSwapped(config, 4, 3),
					getSwapped(config, 4, 1),
					getSwapped(config, 4, 5),
					getSwapped(config, 4, 7)
			};
		case 5:
			return new byte[][]{
					getSwapped(config, 5, 2),
					getSwapped(config, 5, 4),
					getSwapped(config, 5, 8)
			};
		case 6:
			return new byte[][]{
					getSwapped(config, 6, 7),
					getSwapped(config, 6, 3)
			};
		case 7:
			return new byte[][]{
					getSwapped(config, 7, 6),
					getSwapped(config, 7, 4),
					getSwapped(config, 7, 8)
			};
		case 8:
			return new byte[][]{
					getSwapped(config, 8, 7),
					getSwapped(config, 8, 5)
			};
		default:
			throw new IllegalStateException("posição inválida!");
		}
	}
	
	private static final byte[] getSwapped(byte[] originalConfig, int p1, int p2){
		byte res[] = new byte[9];
		System.arraycopy(originalConfig, 0, res, 0, 9);
		res[p1] = originalConfig[p2];
		res[p2] = originalConfig[p1];
		return res;
	}
	
	public static byte[] getConfig(String numbers){
		byte res[] = new byte[9];
		for (int i=0; i<9; i+=1)
			res[i] = (byte)(numbers.charAt(i)-'0');
		return res;
	}
	
	public static void main(String[] args) {
		
//		for (byte[] destConfig : getTransitions(getConfig("138204567"))){
//			System.out.println(Node.toString(destConfig));
//		}
		
		//if (true) return;
		
		GameGraph g = new GameGraph();
		g.buildGraph();
		for (Node node : g.nodes.values()){
			System.out.println(node);
		}
	}
	
}
