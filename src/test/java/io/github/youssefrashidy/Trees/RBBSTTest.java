package io.github.youssefrashidy.Trees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RBBSTTest {
    RBBST<Integer, Integer> tree;

    @BeforeEach
    void setup() {
        tree = new RBBST<>();
    }

    // RB structural rules are validated inside RBBST; this test only asserts observable behavior and in-order ordering.
    private void assertBSTInvariants() {
        List<Map.Entry<Integer, Integer>> traversal = tree.inOrder();
        assertEquals(tree.getSize(), traversal.size());

        for (int i = 1; i < traversal.size(); i++) {
            assertTrue(traversal.get(i - 1).getKey() < traversal.get(i).getKey());
        }

        for (Map.Entry<Integer, Integer> entry : traversal) {
            assertTrue(tree.contains(entry.getKey()));
        }
    }

    @Nested
    @DisplayName("Insertion behavior")
    class InsertionTests {
        @Test
        @DisplayName("insert returns true for a new key")
        void insertShouldReturnTrueForNewKey() {
            assertTrue(tree.insert(10, 10));
            assertBSTInvariants();
        }

        @Test
        @DisplayName("insert returns false for a duplicate key")
        void insertShouldReturnFalseForDuplicateKey() {
            tree.insert(10, 10);
            assertFalse(tree.insert(10, 10));
            assertBSTInvariants();
        }

        @Test
        @DisplayName("duplicate insert does not change size")
        void duplicateInsertShouldNotChangeSize() {
            tree.insert(10, 10);
            tree.insert(10, 10);
            assertEquals(1, tree.getSize());
            assertBSTInvariants();
        }

        @Test
        @DisplayName("size increases after each successful insert")
        void sizeShouldIncreaseAfterEachSuccessfulInsert() {
            tree.insert(10, 10);
            tree.insert(11, 11);
            tree.insert(12, 12);
            assertEquals(3, tree.getSize());
            assertBSTInvariants();
        }
    }

    @Nested
    @DisplayName("Deletion behavior")
    class DeletionTests {
        @Test
        @DisplayName("delete returns true for an existing key")
        void deleteShouldReturnTrueForExistingKey() {
            tree.insert(10, 10);
            assertTrue(tree.delete(10));
            assertBSTInvariants();
        }

        @Test
        @DisplayName("delete returns false for an absent key")
        void deleteShouldReturnFalseForAbsentKey() {
            tree.insert(10, 10);
            int sizeBeforeDelete = tree.getSize();
            assertFalse(tree.delete(100));
            assertEquals(sizeBeforeDelete, tree.getSize());
            assertBSTInvariants();
        }

        @Test
        @DisplayName("delete returns false on an empty tree")
        void deleteShouldReturnFalseOnEmptyTree() {
            assertFalse(tree.delete(100));
            assertEquals(0, tree.getSize());
            assertBSTInvariants();
        }

        @Test
        @DisplayName("delete removes a leaf node and preserves other keys")
        void deleteShouldRemoveLeafNode() {
            tree.insert(20, 20);
            tree.insert(10, 10);
            tree.insert(30, 30);

            assertTrue(tree.delete(10));

            assertFalse(tree.contains(10));
            assertTrue(tree.contains(20));
            assertTrue(tree.contains(30));
            assertEquals(2, tree.getSize());
            assertBSTInvariants();
        }

        @Test
        @DisplayName("delete removes a node with one child and reconnects the subtree")
        void deleteShouldRemoveNodeWithOneChild() {
            tree.insert(20, 20);
            tree.insert(10, 10);
            tree.insert(30, 30);
            tree.insert(25, 25);

            assertTrue(tree.delete(30));

            assertFalse(tree.contains(30));
            assertTrue(tree.contains(25));
            assertTrue(tree.contains(20));
            assertTrue(tree.contains(10));
            assertEquals(3, tree.getSize());
            assertBSTInvariants();
        }

        @Test
        @DisplayName("delete removes a node with two children and preserves searchability")
        void deleteShouldRemoveNodeWithTwoChildren() {
            tree.insert(20, 20);
            tree.insert(10, 10);
            tree.insert(30, 30);
            tree.insert(25, 25);
            tree.insert(35, 35);

            assertTrue(tree.delete(30));

            assertFalse(tree.contains(30));
            assertTrue(tree.contains(20));
            assertTrue(tree.contains(10));
            assertTrue(tree.contains(25));
            assertTrue(tree.contains(35));
            assertEquals(4, tree.getSize());
            assertBSTInvariants();
        }

        @Test
        @DisplayName("delete removes the root node and keeps remaining keys accessible")
        void deleteShouldRemoveRootNode() {
            tree.insert(20, 20);
            tree.insert(10, 10);
            tree.insert(30, 30);
            tree.insert(25, 25);

            assertTrue(tree.delete(20));

            assertFalse(tree.contains(20));
            assertTrue(tree.contains(10));
            assertTrue(tree.contains(30));
            assertTrue(tree.contains(25));
            assertEquals(3, tree.getSize());
            assertBSTInvariants();
        }
    }

    @Nested
    @DisplayName("Height behavior")
    class HeightTests {
        @Test
        @DisplayName("height returns zero on an empty tree")
        void getHeightShouldReturnZeroOnEmptyTree() {
            assertEquals(0, tree.getHeight());
        }

        @Test
        @DisplayName("height returns zero for a single-node tree")
        void getHeightShouldReturnZeroForSingleNodeTree() {
            tree.insert(10, 10);
            assertEquals(0, tree.getHeight());
        }

        @Test
        @DisplayName("height stays small for balanced inserts")
        void getHeightShouldStaySmallForBalancedInserts() {
            tree.insert(4, 4);
            tree.insert(2, 2);
            tree.insert(6, 6);
            tree.insert(1, 1);
            tree.insert(3, 3);
            tree.insert(5, 5);
            tree.insert(7, 7);

            assertTrue(tree.getHeight() <= 3);
            assertBSTInvariants();
        }

        @Test
        @DisplayName("height remains logarithmic for sorted inserts")
        void getHeightShouldRemainLogarithmicForSortedInserts() {
            for (int i = 1; i <= 31; i++) {
                tree.insert(i, i);
            }

            int upperBound = 2 * (int) Math.ceil(Math.log(tree.getSize() + 1) / Math.log(2));
            assertTrue(tree.getHeight() <= upperBound);
            assertBSTInvariants();
        }
    }

    @Nested
    @DisplayName("Clear behavior")
    class ClearTests {
        @Test
        @DisplayName("clear empties the tree state")
        void clearShouldResetTreeState() {
            tree.insert(20, 20);
            tree.insert(10, 10);
            tree.insert(30, 30);

            tree.clear();

            assertEquals(0, tree.getSize());
            assertEquals(0, tree.getHeight());
            assertFalse(tree.contains(20));
            assertTrue(tree.inOrder().isEmpty());
            assertBSTInvariants();
        }

        @Test
        @DisplayName("operations still work after clear")
        void operationsShouldStillWorkAfterClear() {
            tree.insert(20, 20);
            tree.insert(10, 10);

            tree.clear();

            assertTrue(tree.insert(40, 40));
            assertTrue(tree.contains(40));
            assertTrue(tree.delete(40));
            assertFalse(tree.contains(40));
            assertEquals(0, tree.getSize());
            assertBSTInvariants();
        }
    }

    @Nested
    @DisplayName("In-order traversal behavior")
    class InOrderTraversalTests {
        @Test
        @DisplayName("inOrder returns entries sorted by key")
        void inOrderShouldReturnEntriesSortedByKey() {
            tree.insert(5, 50);
            tree.insert(1, 10);
            tree.insert(3, 30);
            tree.insert(2, 20);
            tree.insert(4, 40);

            List<Map.Entry<Integer, Integer>> traversal = tree.inOrder();

            assertEquals(5, traversal.size());
            assertEquals(1, traversal.get(0).getKey());
            assertEquals(2, traversal.get(1).getKey());
            assertEquals(3, traversal.get(2).getKey());
            assertEquals(4, traversal.get(3).getKey());
            assertEquals(5, traversal.get(4).getKey());
            assertEquals(10, traversal.get(0).getValue());
            assertEquals(20, traversal.get(1).getValue());
            assertEquals(30, traversal.get(2).getValue());
            assertEquals(40, traversal.get(3).getValue());
            assertEquals(50, traversal.get(4).getValue());
            assertBSTInvariants();
        }
    }

    @Nested
    @DisplayName("Regression behavior")
    class RegressionTests {
        @Test
        @DisplayName("mixed insert and delete operations preserve behavior and ordering")
        void mixedOperationsShouldPreserveBehaviorAndOrdering() {
            assertTrue(tree.insert(8, 8));
            assertTrue(tree.insert(3, 3));
            assertTrue(tree.insert(10, 10));
            assertTrue(tree.insert(1, 1));
            assertTrue(tree.insert(6, 6));
            assertTrue(tree.insert(14, 14));
            assertTrue(tree.insert(4, 4));
            assertTrue(tree.insert(7, 7));
            assertTrue(tree.insert(13, 13));
            assertBSTInvariants();

            assertTrue(tree.delete(1));
            assertBSTInvariants();
            assertTrue(tree.delete(6));
            assertBSTInvariants();
            assertTrue(tree.delete(8));
            assertBSTInvariants();

            assertFalse(tree.delete(42));
            assertBSTInvariants();

            List<Map.Entry<Integer, Integer>> traversal = tree.inOrder();
            assertEquals(List.of(3, 4, 7, 10, 13, 14),
                    traversal.stream().map(Map.Entry::getKey).toList());
        }
    }
}

