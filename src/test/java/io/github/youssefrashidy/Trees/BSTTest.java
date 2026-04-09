package io.github.youssefrashidy.Trees;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BSTTest {
    BST<Integer,Integer> bst ;

    private void assertBSTInvariants() {
        List<Map.Entry<Integer, Integer>> traversal = bst.inOrder();
        assertEquals(bst.getSize(), traversal.size());

        for (int i = 1; i < traversal.size(); i++) {
            assertTrue(traversal.get(i - 1).getKey() < traversal.get(i).getKey());
        }

        for (Map.Entry<Integer, Integer> entry : traversal) {
            assertTrue(bst.contains(entry.getKey()));
        }
    }

    @BeforeEach
    void setup() {
        bst = new BST<>() ;
    }

    @Nested
    @DisplayName("Insertion behavior")
    class InsertionTest {
        @Test
        @DisplayName("insert returns true for a new key")
        void insertShouldReturnTrueForNewKey() {
            boolean result = bst.insert(10, 10) ;
            assertTrue(result);
            assertBSTInvariants();
        }

        @Test
        @DisplayName("insert returns false for a duplicate key")
        void insertShouldReturnFalseForDuplicateKey() {
            bst.insert(10, 10) ;
            boolean result = bst.insert(10, 10) ;
            assertFalse(result);
            assertBSTInvariants();
        }

        @Test
        @DisplayName("duplicate insert does not change size")
        void duplicateInsertShouldNotChangeSize() {
            bst.insert(10, 10) ;
            bst.insert(10, 10) ;
            int size = bst.getSize();
            assertEquals(1, size);
        }

        @Test
        @DisplayName("size increases after each successful insert")
        void sizeShouldIncreaseAfterEachSuccessfulInsert() {
            bst.insert(10, 10) ;
            bst.insert(11, 10) ;
            bst.insert(12, 10) ;

            int size = bst.getSize();
            assertEquals(3, size);
            assertBSTInvariants();
        }

        @Test
        @DisplayName("contains returns true for an inserted key")
        void containsShouldReturnTrueForInsertedKey() {
            bst.insert(10, 10) ;
            assertTrue(bst.contains(10));
        }

        @Test
        @DisplayName("contains returns false for an absent key")
        void containsShouldReturnFalseForAbsentKey() {
            bst.insert(10, 10) ;
            assertFalse(bst.contains(5));
        }

        @Test
        @DisplayName("contains returns false on an empty tree")
        void containsShouldReturnFalseOnEmptyTree(){
            assertFalse(bst.contains(5));
        }
    }

    @Nested
    @DisplayName("Deletion behavior")
    class DeletionTests {
        @Test
        @DisplayName("delete returns true for an existing key")
        void deleteShouldReturnTrueForExistingKey() {
            bst.insert(10, 10) ;
            assertTrue(bst.delete(10));
            assertBSTInvariants();
        }

        @Test
        @DisplayName("delete returns false for an absent key")
        void deleteShouldReturnFalseForAbsentKey() {
            bst.insert(10, 10) ;
            int sizeBeforeDelete = bst.getSize();
            assertFalse(bst.delete(100));
            assertEquals(sizeBeforeDelete, bst.getSize());
            assertBSTInvariants();
        }

        @Test
        @DisplayName("delete returns false on an empty tree")
        void deleteShouldReturnFalseOnEmptyTree() {
            assertFalse(bst.delete(100));
            assertEquals(0, bst.getSize());
            assertBSTInvariants();
        }

        @Test
        @DisplayName("contains returns false after deleting an existing key")
        void containsShouldReturnFalseAfterDelete(){
            bst.insert(10, 10) ;
            bst.delete(10) ;
            assertFalse(bst.contains(10));
        }

        @Test
        @DisplayName("size decreases after a successful delete")
        void sizeShouldDecreaseAfterSuccessfulDelete(){
            bst.insert(10, 10) ;
            bst.insert(11, 11) ;
            bst.insert(12, 12) ;

            bst.delete(10) ;
            assertEquals(2, bst.getSize());
        }

        @Test
        @DisplayName("delete removes a leaf node and preserves other keys")
        void deleteShouldRemoveLeafNode(){
            bst.insert(20, 10) ;
            bst.insert(10, 11) ;
            bst.insert(30, 12) ;

            bst.delete(10) ;

            assertTrue(bst.contains(20));
            assertTrue(bst.contains(30));
            assertFalse(bst.contains(10));
            assertEquals(2, bst.getSize());
        }

        @Test
        @DisplayName("delete removes a node with one child and reconnects the subtree")
        void deleteShouldRemoveNodeWithOneChild() {
            bst.insert(20, 20) ;
            bst.insert(10, 10) ;
            bst.insert(30, 30) ;
            bst.insert(25, 25) ;

            assertTrue(bst.delete(30));

            assertFalse(bst.contains(30));
            assertTrue(bst.contains(25));
            assertTrue(bst.contains(20));
            assertTrue(bst.contains(10));
            assertEquals(3, bst.getSize());
        }

        @Test
        @DisplayName("delete removes a node with two children and preserves searchability")
        void deleteShouldRemoveNodeWithTwoChildren() {
            bst.insert(20, 20) ;
            bst.insert(10, 10) ;
            bst.insert(30, 30) ;
            bst.insert(25, 25) ;
            bst.insert(35, 35) ;

            assertTrue(bst.delete(30));

            assertFalse(bst.contains(30));
            assertTrue(bst.contains(20));
            assertTrue(bst.contains(10));
            assertTrue(bst.contains(25));
            assertTrue(bst.contains(35));
            assertEquals(4, bst.getSize());
        }

        @Test
        @DisplayName("delete removes the root node and keeps remaining keys accessible")
        void deleteShouldRemoveRootNode() {
            bst.insert(20, 20) ;
            bst.insert(10, 10) ;
            bst.insert(30, 30) ;
            bst.insert(25, 25) ;

            assertTrue(bst.delete(20));

            assertFalse(bst.contains(20));
            assertTrue(bst.contains(10));
            assertTrue(bst.contains(30));
            assertTrue(bst.contains(25));
            assertEquals(3, bst.getSize());
            assertBSTInvariants();
        }
    }

    @Nested
    @DisplayName("Height behavior")
    class HeightTests {
        @Test
        @DisplayName("height returns zero on an empty tree")
        void getHeightShouldReturnZeroOnEmptyTree() {
            assertEquals(0, bst.getHeight());
        }

        @Test
        @DisplayName("height returns zero for a single-node tree")
        void getHeightShouldReturnZeroForSingleNodeTree() {
            bst.insert(10, 10) ;
            assertEquals(0, bst.getHeight());
        }

        @Test
        @DisplayName("height matches expected levels for a balanced tree")
        void getHeightShouldMatchBalancedTreeHeight() {
            bst.insert(4, 4) ;
            bst.insert(2, 2) ;
            bst.insert(6, 6) ;
            bst.insert(1, 1) ;
            bst.insert(3, 3) ;
            bst.insert(5, 5) ;
            bst.insert(7, 7) ;

            assertEquals(2, bst.getHeight());
        }

        @Test
        @DisplayName("height grows linearly for sorted inserts in a degenerate tree")
        void getHeightShouldGrowLinearlyForDegenerateTree() {
            bst.insert(1, 1) ;
            bst.insert(2, 2) ;
            bst.insert(3, 3) ;
            bst.insert(4, 4) ;
            bst.insert(5, 5) ;

            assertEquals(4, bst.getHeight());
        }
    }

    @Nested
    @DisplayName("Clear behavior")
    class ClearTests {
        @Test
        @DisplayName("clear empties the tree state")
        void clearShouldResetTreeState() {
            bst.insert(20, 20) ;
            bst.insert(10, 10) ;
            bst.insert(30, 30) ;

            bst.clear();

            assertEquals(0, bst.getSize());
            assertEquals(0, bst.getHeight());
            assertFalse(bst.contains(20));
            assertTrue(bst.inOrder().isEmpty());
            assertBSTInvariants();
        }

        @Test
        @DisplayName("operations still work after clear")
        void operationsShouldStillWorkAfterClear() {
            bst.insert(20, 20) ;
            bst.insert(10, 10) ;

            bst.clear();

            assertTrue(bst.insert(40, 40));
            assertTrue(bst.contains(40));
            assertTrue(bst.delete(40));
            assertFalse(bst.contains(40));
            assertEquals(0, bst.getSize());
            assertBSTInvariants();
        }
    }

    @Nested
    @DisplayName("In-order traversal behavior")
    class InOrderTraversalTests {
        @Test
        @DisplayName("inOrder returns entries sorted by key")
        void inOrderShouldReturnEntriesSortedByKey() {
            bst.insert(5, 50) ;
            bst.insert(1, 10) ;
            bst.insert(3, 30) ;
            bst.insert(2, 20) ;
            bst.insert(4, 40) ;

            List<Map.Entry<Integer, Integer>> traversal = bst.inOrder();

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
        @DisplayName("mixed insert and delete operations preserve BST invariants")
        void mixedOperationsShouldPreserveBSTInvariants() {
            assertTrue(bst.insert(8, 8));
            assertTrue(bst.insert(3, 3));
            assertTrue(bst.insert(10, 10));
            assertTrue(bst.insert(1, 1));
            assertTrue(bst.insert(6, 6));
            assertTrue(bst.insert(14, 14));
            assertTrue(bst.insert(4, 4));
            assertTrue(bst.insert(7, 7));
            assertTrue(bst.insert(13, 13));
            assertBSTInvariants();

            assertTrue(bst.delete(1));
            assertBSTInvariants();
            assertTrue(bst.delete(6));
            assertBSTInvariants();
            assertTrue(bst.delete(8));
            assertBSTInvariants();

            assertFalse(bst.delete(42));
            assertBSTInvariants();

            List<Map.Entry<Integer, Integer>> traversal = bst.inOrder();
            assertEquals(List.of(3, 4, 7, 10, 13, 14),
                    traversal.stream().map(Map.Entry::getKey).toList());
        }
        @Test
        @DisplayName("bulk insert/contains/delete operations remain correct under large workload")
        void bulkOperationsShouldRemainCorrectUnderLargeWorkload() {
            final int n = 10_000;

            for (int i = 1; i <= n; i++) {
                assertTrue(bst.insert(i, i));
                if (i % 1000 == 0) {
                    assertBSTInvariants();
                }
            }
            assertEquals(n, bst.getSize());

            for (int i = 1; i <= n; i++) {
                assertTrue(bst.contains(i));
            }
            for (int i = n + 1; i <= n + 1000; i++) {
                assertFalse(bst.contains(i));
            }

            for (int i = 2; i <= n; i += 2) {
                assertTrue(bst.delete(i));
                if (i % 1000 == 0) {
                    assertBSTInvariants();
                }
            }
            assertEquals(n / 2, bst.getSize());

            for (int i = 1; i <= n; i++) {
                if (i % 2 == 0) {
                    assertFalse(bst.contains(i));
                } else {
                    assertTrue(bst.contains(i));
                }
            }

            for (int i = 1; i <= n; i += 2) {
                assertTrue(bst.delete(i));
                if (i % 1001 == 1) {
                    assertBSTInvariants();
                }
            }

            assertEquals(0, bst.getSize());
            assertTrue(bst.inOrder().isEmpty());
            assertFalse(bst.delete(1));
            assertFalse(bst.contains(1));
            assertBSTInvariants();
        }
    }



}