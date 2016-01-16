import java.util.ArrayList;
import java.util.Random;

/**
 * This implements the class Maze. 
 *
 * @author Kyler Witting
 * @author Jeff Spragg
 * 
 * CS2321 Data Structures
 * Fall 2014
 */
public class MyMaze implements Maze {

    Vertex[][] backing = null;
    boolean[][] visited;

    Graph mazeGraph;

    Vertex start;
    Vertex finish;

    /**
     * Constructor for MyMaze
     */
    public MyMaze() {
        mazeGraph = new MyGraph();
    }

    /**
     * Returns a random maze with the specified number of rows and columns
     *
     * @param rows and columns for the size of the new Maze
     */
    @Override
    public void generateMaze(int rows, int columns) {

        if ( rows < 2 || columns < 2) {
            return;
        }
        backing = new Vertex[rows][columns];

        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            for (int colIndex = 0; colIndex < columns; colIndex++) {
                MyVertex myVertex = new MyVertex();
                myVertex.setElement(new MyPair(rowIndex, colIndex));
                backing[rowIndex][colIndex] = myVertex;
                mazeGraph.addVertex(myVertex);
            }
        }

        Random rand = new Random();
        int startRow = rand.nextInt(rows);
        start = backing[startRow][0];
        finish = backing[rand.nextInt(rows)][columns - 1];

        visited = new boolean[rows][columns];
        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            for (int colIndex = 0; colIndex < columns; colIndex++) {
                visited[rowIndex][colIndex] = false;
            }
        }
        generate(startRow, 0);
        vertCheck();
    }

    /**
     * Generates a random Maze, one vertex at a time
     * @param x - the x location of the current vertex
     * @param y - the y location of the current vertex
     */
    private void generate(int x, int y) {
        visited[x][y] = true;
        Random rand = new Random();
        int r = rand.nextInt(4);

        //right
        if (r == 0) {
            if (y == 0) {
                if (!visited[x][y + 1]) {
                    mazeGraph.addEdge(backing[x][y], backing[x][y + 1]);
                    generate(x, y + 1);
                }
            } else if (y == visited[0].length - 1) {
                generate(x, y);
            } else {
                if (!visited[x][y + 1]) {
                    mazeGraph.addEdge(backing[x][y], backing[x][y + 1]);
                    generate(x, y + 1);
                }
                if (!visited[x][y - 1]) {
                    mazeGraph.addEdge(backing[x][y], backing[x][y - 1]);
                    generate(x, y - 1);
                }
            }
        }
        //down
        else if (r == 1) {
            if (x == 0) {
                if (!visited[x + 1][y]) {
                    mazeGraph.addEdge(backing[x][y], backing[x + 1][y]);
                    generate(x + 1, y);
                }
            } else if (x == visited.length - 1) {
                generate(x, y);
            } else {
                if (!visited[x + 1][y]) {
                    mazeGraph.addEdge(backing[x][y], backing[x + 1][y]);
                    generate(x + 1, y);
                }
                if (!visited[x - 1][y]) {
                    mazeGraph.addEdge(backing[x][y], backing[x - 1][y]);
                    generate(x - 1, y);
                }
            }
        }
        //left
        else if (r == 2) {
            if (y == visited[0].length - 1) {
                if (!visited[x][y - 1]) {
                    mazeGraph.addEdge(backing[x][y], backing[x][y - 1]);
                    generate(x, y - 1);
                }
            } else if (y == 0) {
                generate(x, y);
            } else {
                if (!visited[x][y - 1]) {
                    mazeGraph.addEdge(backing[x][y], backing[x][y - 1]);
                    generate(x, y - 1);
                }
                if (!visited[x][y + 1]) {
                    mazeGraph.addEdge(backing[x][y], backing[x][y + 1]);
                    generate(x, y + 1);
                }
            }
        }
        //up
        else if (r == 3) {
            if (x == visited.length - 1) {
                if (!visited[x - 1][y]) {
                    mazeGraph.addEdge(backing[x][y], backing[x - 1][y]);
                    generate(x - 1, y);
                }
            } else if (x == 0) {
                generate(x, y);
            } else {
                if (!visited[x - 1][y]) {
                    mazeGraph.addEdge(backing[x][y], backing[x - 1][y]);
                    generate(x - 1, y);
                }
                if (!visited[x + 1][y]) {
                    mazeGraph.addEdge(backing[x][y], backing[x + 1][y]);
                    generate(x + 1, y);
                }

            }
        }

    }

    /**
     * Checks to make sure that every vertex of the maze is attached to the maze
     * Automatically corrects vertices that aren't attached. 
     */
    private void vertCheck() {
        for (int x = 0; x < visited.length; x++) {
            for (int y = 0; y < visited[0].length; y++) {
                if (!visited[x][y]) {
                    if (x == 1 || x == 2 || x == 3) {
                        if (y != 0)
                            mazeGraph.addEdge(backing[x][y], backing[x][y - 1]);
                        else
                            mazeGraph.addEdge(backing[x][y], backing[x - 1][y]);
                        visited[x][y] = true;
                    } else if (x != 0) {
                        Random rand = new Random();
                        int r = rand.nextInt(3);
                        if (r == 1 && y != 0) {
                            mazeGraph.addEdge(backing[x][y], backing[x][y - 1]);
                        } else if (r == 2 && y != visited[0].length - 1) {
                            mazeGraph.addEdge(backing[x][y], backing[x][y + 1]);
                        } else {
                            mazeGraph.addEdge(backing[x][y], backing[x - 1][y]);
                        }
                        visited[x][y] = true;
                    } else if (x != visited.length - 1) {
                        mazeGraph.addEdge(backing[x][y], backing[x + 1][y]);
                        visited[x][y] = true;
                    } else if (y != 0) {
                        mazeGraph.addEdge(backing[x][y], backing[x][y - 1]);
                        visited[x][y] = true;
                    } else {
                        mazeGraph.addEdge(backing[x][y], backing[x][y + 1]);
                        visited[x][y] = true;
                    }
                }

            }
        }
    }

    /**
     * @return an ordered list representing a path of vertices from the start to the finish
     */
    @Override
    public ArrayList<Vertex> solveMaze() {
        return mazeGraph.shortestPath(start, finish);
    }

    /**
     * @return a graph representing the maze
     */
    @Override
    public Graph toGraph() {
        return mazeGraph;
    }

    /**
     * @return an array of vertices representing the maze
     */
    @Override
    public Vertex[][] toArray() {
        return backing;
    }

    /**
     * @return the start vertex to this maze
     */
    @Override
    public Vertex startVertex() {
        if (backing == null || backing.length < 2)
            return null;
        else
            return start;
    }

    /**
     * @return the finish vertex of this maze
     */
    @Override
    public Vertex finishVertex() {
        if (backing == null || backing.length < 2)
            return null;
        else
            return finish;
    }
}
