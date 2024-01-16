/**
 * This is the test class that tests the functionality for the AVL tree.
 * Known bugs: None
 * 
 * @author Sulaiman Lateef
 * sulaimanlateef@brandeis.edu
 * November 15, 2023
 * COSI 21A PA2
 */
package test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.AVLPlayerNode;
import main.Player;


public class AVLUnitTesting {
	@Test
    void testInsert() {
        AVLPlayerNode root = new AVLPlayerNode(new Player("Player1", 1, 1500), 50.0);
        root = root.insert(new Player("Player2", 2, 1600), 60.0);
        root = root.insert(new Player("Player3", 3, 1400), 40.0);

    }

    @Test
    void testDelete() {
        AVLPlayerNode root = new AVLPlayerNode(new Player("Player1", 1, 1500), 50.0);
        root = root.insert(new Player("Player2", 2, 1600), 60.0);
        root = root.insert(new Player("Player3", 3, 1400), 40.0);

        root = root.delete(60.0);

        assertEquals("((Player3)Player1)", root.treeString());
    }

    @Test
    void testRotateRight() {
        AVLPlayerNode root = new AVLPlayerNode(new Player("Player1", 1, 1500), 50.0);
        root = root.insert(new Player("Player2", 2, 1600), 60.0);
        root = root.insert(new Player("Player3", 3, 1400), 40.0);

        root.rotateRight();

    }

    @Test
    void testRotateLeft() {
        AVLPlayerNode root = new AVLPlayerNode(new Player("Player1", 1, 1500), 50.0);
        root = root.insert(new Player("Player2", 2, 1600), 60.0);
        root = root.insert(new Player("Player3", 3, 1400), 40.0);

        root.rotateLeft();

    }

    @Test
    void testGetPlayer() {
        AVLPlayerNode root = new AVLPlayerNode(new Player("Player1", 1, 1500), 50.0);
        root = root.insert(new Player("Player2", 2, 1600), 60.0);
        root = root.insert(new Player("Player3", 3, 1400), 40.0);

        Player player = root.getPlayer(60.0);

    }

    @Test
    void testGetRank() {
        AVLPlayerNode root = new AVLPlayerNode(new Player("Player1", 1, 1500), 50.0);
        root = root.insert(new Player("Player2", 2, 1600), 60.0);
        root = root.insert(new Player("Player3", 3, 1400), 40.0);

        int rank = root.getRank(60.0);

    }

    @Test
    void testScoreboard() {
        AVLPlayerNode root = new AVLPlayerNode(new Player("Player1", 1, 1500), 50.0);
        root = root.insert(new Player("Player2", 2, 1600), 60.0);
        root = root.insert(new Player("Player3", 3, 1400), 40.0);

        String scoreboard = root.scoreboard();

    }
}
