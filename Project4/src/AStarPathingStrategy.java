import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.HashMap;

class AStarPathingStrategy
        implements PathingStrategy
{
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        List<Point> path = new LinkedList<>();
        Comparator<PathNode> comp = (p1, p2) -> p1.getF()-p2.getF();
    	HashMap<Point, PathNode> openHash = new HashMap<>();
    	PriorityQueue<PathNode> openPQ = new PriorityQueue<>(5, comp);
    	HashMap<Point, PathNode> closedHash = new HashMap<>();
    	
    	int endStartDist = Math.abs(start.x-end.x) + Math.abs(start.y-end.y);
        PathNode curr = new PathNode(start, 0, endStartDist, null);
        openPQ.add(curr);
        openHash.put(start, curr);
        
        while(!withinReach.test(curr.getPoint(), end)) {
        	List<Point> neighbors = potentialNeighbors.apply(curr.getPoint())
        			.filter(canPassThrough)
        			.filter(p -> !closedHash.containsKey(p))
        			.collect(Collectors.toList());
        	
        	for(int i=0; i<neighbors.size(); i++) {
        		Point pt = neighbors.get(i);
        		int addG = 10;
        		if(pt.x!=curr.getPoint().x && pt.y!=curr.getPoint().y)
        			addG = 14;
        		if(!openHash.containsKey(pt)) {
        			int dist = Math.abs(pt.x-end.x) + 
        					Math.abs(pt.y-end.y);
        			PathNode neighborNode = new PathNode(pt, curr.getG()+addG, 
        					dist, curr);
        			openHash.put(pt, neighborNode);
        			openPQ.add(neighborNode);
        		}
        		else {
        			PathNode node = openHash.get(pt);
        			if(node.getG()>curr.getG()+addG) {
        				node.setG(curr.getG()+addG);
        				node.setPrev(curr);
        			}
        		}
        	}
        	
        	closedHash.put(curr.getPoint(), curr);
        	openPQ.remove();
        	openHash.remove(curr.getPoint());
        	
        	if(openPQ.size()==0) {
        		return path;
        	}
        	
        	curr = openPQ.peek();
        }
        
        while(curr.getPrev()!=null) {
        	path.add(0, curr.getPoint());
        	curr = curr.getPrev();
        }
        
        return path;
    }
}
