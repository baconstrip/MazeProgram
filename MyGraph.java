import java.util.*;

/**
 * This implements the class Graph. 
 *
 * @author Kyler Witting
 * @author Jeff Spragg
 * 
 * CS2321 Data Structures
 * Fall 2014
 */
public class MyGraph implements Graph {

    ArrayList<Vertex> myVertices;
    ArrayList<Edge> myEdges;

    /**
     * constructor for MyGraph
     */
    public MyGraph() {
        myVertices = new ArrayList<Vertex>();
        myEdges = new ArrayList<Edge>();
    }

    /**
     * @return the vertices of this Graph
     */
    @Override
    public ArrayList<Vertex> vertices() {
        return myVertices;
    }

    /**
     * Creates a new vertex in this MyGraph
     *
     * @param p the pair to create the new vertex from
     * @return the vertex that was created
     */
    @Override
    public Vertex addVertex(Pair p) {
        Vertex vert = new MyVertex();
        vert.setElement(p);
        return vert;
    }

    /**
     * Creates a new vertex in this MyGraph using a Vertex
     *
     * @param v The vertex to add
     * @return the Vertex that was added
     */
    @Override
    public Vertex addVertex(Vertex v) {
        //TODO possibly. Not sure how the adjacent stuff will work
        myVertices.add(v);
        return v;
    }

    /**
     * removes a vertex from MyGraph using a pair
     *
     * @param p the pair of the vertex to remove from MyGraph
     * @return true if the vertex was removed
     */
    @Override
    public boolean removeVertex(Pair p) {
        Vertex found = null;
        for (Vertex vert : myVertices) {
            if (vert.getElement().equals(p)) {
                found = vert;
                break;
            }
        }
        if (found != null) {
            for (int i = 0; i < found.adjacentVertices().size(); i++) {
                Vertex v2 = found.adjacentVertices().get(i);
                v2.adjacentVertices().remove(found);
                v2.incidentEdges().remove(findEdge(found, v2));
                myEdges.remove(findEdge(found, v2));
            }
        } else {
            return false;
        }
        return myVertices.remove(found);
    }

    /**
     * removes a vertex from MyGraph
     *
     * @param v1 the vertex to be removed
     * @return true if the vertex was removed
     */
    @Override
    public boolean removeVertex(Vertex v1) {
        if (myVertices.contains(v1)) {
            for (int i = 0; i < v1.adjacentVertices().size(); i++) {
                Vertex v2 = v1.adjacentVertices().get(i);
                v2.adjacentVertices().remove(v1);
                v2.incidentEdges().remove(findEdge(v1, v2));
                myEdges.remove(findEdge(v1, v2));
            }
        }
        return myVertices.remove(v1);
    }

    /**
     * finds the vertex in the graph by using a pair
     *
     * @param p the pair of the vertex you are trying to find
     * @return the vertex that was found
     */
    @Override
    public Vertex findVertex(Pair p) {
        for (Vertex vert : myVertices) {
            if (vert.getElement().equals(p)) {
                return vert;
            }
        }
        return null;
    }

    /**
     * @return the edges in MyGraph
     */
    @Override
    public ArrayList<Edge> edges() {
        return myEdges;
    }

    /**
     * adds and edge to MyGraph using 2 vertices
     *
     * @param v1 the vertices that the new edge will be created from
     * @return the edge that was created
     */
    @Override
    public Edge addEdge(Vertex v1, Vertex v2) {
        Edge newEdge = new MyEdge();
        newEdge.vertices().add(v1);
        newEdge.vertices().add(v2);
        myEdges.add(newEdge);
        addEdgeToVertex(v1, v2, newEdge);
        return newEdge;
    }

    public void addEdgeToVertex(Vertex v1, Vertex v2, Edge newEdge) {
        v1.adjacentVertices().add(v2);
        v2.adjacentVertices().add(v1);
        v1.incidentEdges().add(newEdge);
        v2.incidentEdges().add(newEdge);
    }

    /**
     * adds an edge to MyGraph using an edge
     *
     * @param e the edge to add
     * @return the edge that was added
     */
    @Override
    public Edge addEdge(Edge e) {
        myEdges.add(e);
        Edge newEdge = e;
        addEdgeToVertex(newEdge.vertices().get(0), newEdge.vertices().get(1), newEdge);
        return e;
    }

    /**
     * removes an edge from the graph
     *
     * @return true if edge was removed
     * @paramv1 the vertices that will indicate the edge to remove
     */
    @Override
    public boolean removeEdge(Vertex v1, Vertex v2) {
        for (int i = 0; i < myEdges.size(); i++) {
            if (myEdges.get(i).vertices().contains(v1) && myEdges.get(i).vertices().contains(v2)) {
                v1.incidentEdges().remove(myEdges.get(i));
                v2.incidentEdges().remove(myEdges.get(i));
                myEdges.remove(i);
                v1.adjacentVertices().remove(v2);
                v2.adjacentVertices().remove(v1);
                return true;
            }
        }
        return false;
    }

    /**
     * removes an edge from MyGraph
     *
     * @param e the edge to remove
     * @return true if the edge was removed
     */
    @Override
    public boolean removeEdge(Edge e) {
        Vertex v1 = e.vertices().get(0);
        Vertex v2 = e.vertices().get(0);
        v1.incidentEdges().remove(e);
        v2.incidentEdges().remove(e);
        v1.adjacentVertices().remove(v2);
        v2.adjacentVertices().remove(v1);
        return myEdges.remove(e);
    }

    /**
     * finds an edge in MyGraph
     *
     * @param v1 the vertices that make up the edge to find
     * @return the edge that was found
     */
    @Override
    public Edge findEdge(Vertex v1, Vertex v2) {
        for (int i = 0; i < myEdges.size(); i++) {
            if (myEdges.get(i).vertices().contains(v1) && myEdges.get(i).vertices().contains(v2))
                return myEdges.get(i);
        }
        return null;
    }

    /**
     * checks to see if the two vertices are connected by an edge
     *
     * @param v1 the 2 vertices to be checked
     * @return true if the vertices are connected
     */
    @Override
    public boolean areConnected(Vertex v1, Vertex v2) {
        for (int i = 0; i < myEdges.size(); i++) {
            if (myEdges.get(i).vertices().contains(v1) && myEdges.get(i).vertices().contains(v2))
                return true;
        }
        return false;
    }

    /**
     * @return the adjacent vertices of the given vertex
     */
    @Override
    public ArrayList<Vertex> adjacentVertices(Vertex v1) {
        return v1.adjacentVertices();
    }

    /**
     * @return the incident edges of the given vertex
     */
    @Override
    public ArrayList<Edge> incidentEdges(Vertex v1) {
        return v1.incidentEdges();
    }

    /**
     * finds the shortest path between 2 vertices
     *
     * @param v1 the vertices to find the path between
     * @return ArrayList of vertices that are the shortest path between 2 vertices
     */
    @Override
    public ArrayList<Vertex> shortestPath(Vertex v1, Vertex v2) {
        Map<Vertex, Integer> distance = new HashMap<Vertex, Integer>();
        Map<Vertex, Vertex> previous = new HashMap<Vertex, Vertex>();

        Queue<Vertex> queue = new ArrayDeque<Vertex>();

        for (Vertex vert : myVertices) {
            if (!vert.equals(v1)) {
                distance.put(vert, Integer.MAX_VALUE);
                previous.put(vert, null);
            }
        }
        distance.put(v1, 0);
        queue.add(v1);

        while (!queue.isEmpty()) {
            Vertex current = queue.remove();
            if (current.equals(v2)) {
                ArrayList<Vertex> retVal = new ArrayList<Vertex>();
                while (previous.get(current) != null) {
                    retVal.add(0, current);
                    current = previous.get(current);
                }
                retVal.add(0, v1);
                return retVal;
            }

            for (Vertex adjacent : current.adjacentVertices()) {
                if (distance.get(adjacent) == Integer.MAX_VALUE) {
                    distance.put(adjacent, distance.get(current) + 1);
                    previous.put(adjacent, current);
                    queue.add(adjacent);
                }
            }
        }

        return null;
    }

    /**
     * @return the minimum spanning tree of MyGraph
     */
    @Override
    public Graph minimumSpanningTree() {

        Graph retVal = new MyGraph();

        // a new entry key is the vertex in question, value is its connection to the rest of the tree
        Map.Entry<Vertex, Vertex> current = new AbstractMap.SimpleEntry<Vertex, Vertex>(myVertices.get(0), null);

        HashSet<Vertex> remaining = new HashSet<Vertex>(myVertices);

        Stack<Map.Entry<Vertex, Vertex>> stack = new Stack<Map.Entry<Vertex, Vertex>>();
        stack.push(current);

        while (!stack.isEmpty()) {
            current = stack.pop();
            if (remaining.contains(current.getKey())) {
                Vertex newVertex = new MyVertex();
                newVertex.setElement(current.getKey().getElement());
                remaining.remove(current.getKey());
                retVal.addVertex(newVertex);
                if ( current.getValue() != null ) {
                    retVal.addEdge(newVertex, current.getValue());
                }
                for (Vertex vert : current.getKey().adjacentVertices()) {
                    stack.push(new AbstractMap.SimpleEntry<Vertex, Vertex>(vert, newVertex));
                }
            }
        }
        return retVal;
    }

    /**
     * inherits toString
     */
    @Override
    public String toString( ) {
        return myVertices + "    " + myEdges;
    }
}
