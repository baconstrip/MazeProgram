import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.fail;

/**
 * This tests the class MyMaze. 
 *
 * @author Kyler Witting
 * @author Jeff Spragg
 * 
 * CS2321 Data Structures
 * Fall 2014
 */

public class MyMazeTest {


    /**
     * Tests to make sure the maze generated is a correct maze and that it is not null
     */
    @Test
    public void testGenerateMaze() {
        Maze myMaze = new MyMaze();

        myMaze.generateMaze(10, 10);

        ArrayList<Vertex> traversal = myMaze.solveMaze();

        if (!traversal.get(0).equals(myMaze.startVertex())) {
            fail("maze did not have a start");
        }
        if (!traversal.get(traversal.size() - 1).equals(myMaze.finishVertex())) {
            fail("maze did not have an end");
        }

        Graph graph = myMaze.toGraph();

        if (graph.vertices().size() != 100) {
            fail("maze did not have the correct number of vertices, found: " + graph.vertices().size());
        }
    }

    /**
     * Tests the solve maze by making sure the start and end vertices are correct and that a path does exist between them
     */
    @Test
    public void testSolveMaze() {
        Maze myMaze = new MyMaze();

        myMaze.generateMaze(10, 10);

        ArrayList<Vertex> traversal = myMaze.solveMaze();

        if (!traversal.get(0).equals(myMaze.startVertex())) {
            fail("solve did not start at the correct location");
        }

        for (Vertex vert : traversal) {
            if (traversal.indexOf(vert) == traversal.size() - 1) {
                if (!vert.equals(myMaze.finishVertex())) {
                    fail("did not reach end of maze");
                }
                break;
            }
            if (!vert.adjacentVertices().contains(traversal.get(traversal.indexOf(vert) + 1))) {
                fail("Graph made an illegal jump");
            }
        }

    }

    /**
     * Tests toGraph by making sure it isn't null and contains all the vertices of the maze
     */
    @Test
    public void testToGraph() {
        Maze myMaze = new MyMaze();

        myMaze.generateMaze(10, 10);
        if (myMaze.toGraph() == null) {
            fail("tograph returned null");
        }

        Graph graph = myMaze.toGraph();
        if (graph.vertices().size() != 100) {
            fail("graph didn't have all of it's vertices");
        }

        /*int size = graph.minimumSpanningTree().vertices().size();
        if(size != 100) {
            fail("graph didn't have all of it's vertices after minimum spanning tree, got:"  + size);
        }*/
    }

    /**
     * Tests toArray by making sure each element in the array is the correct one and isn't null
     */
    @Test
    public void testToArray() {

        Maze myMaze = new MyMaze();
        myMaze.generateMaze(10, 10);
        Vertex[][] doubleArray = myMaze.toArray();

        for (int x = 0; x < doubleArray.length; x++) {
            for (int y = 0; y < doubleArray[x].length; y++) {
                if (doubleArray[x][y] == null) {
                    fail("array was nulled at " + x + " " + y);
                }
                Pair pair = doubleArray[x][y].getElement();
                if (pair.getX() != x) {
                    fail("disorganized the maze when generating");
                }
                if (pair.getY() != y) {
                    fail("disorganized the maze when generating");
                }
            }
        }
    }

    /**
     * Tests the starting vertex by making sure it isn't null and is the beginning of the traversal
     */
    @Test
    public void testStartVertex() {
        Maze myMaze = new MyMaze();

        myMaze.generateMaze(10, 10);

        ArrayList<Vertex> traversal = myMaze.solveMaze();

        if (myMaze.startVertex() == null) {
            fail("did not generate a start vertex");

        }
        if (myMaze.startVertex() == myMaze.finishVertex()) {
            fail("chose starting and ending vertex to be the same value");
        }

        if (!traversal.get(0).equals(myMaze.startVertex())) {
            fail("solve did not start at the correct location");
        }


    }

    /**
     * Tests the ending vertex by making sure it isn't null and is the ending of the traversal
     */
    @Test
    public void testFinishVertex() {
        Maze myMaze = new MyMaze();

        myMaze.generateMaze(10, 10);

        ArrayList<Vertex> traversal = myMaze.solveMaze();

        if (myMaze.finishVertex() == null) {
            fail("did not generate a start vertex");

        }
        if (myMaze.startVertex() == myMaze.finishVertex()) {
            fail("chose starting and ending vertex to be the same value");
        }

        Vertex vertex = traversal.get(traversal.size() - 1);
        if (!vertex.equals(myMaze.finishVertex())) {
            fail("solve did not end at the correct location got:" + vertex + " should've been " + myMaze.finishVertex());
        }
    }
}
