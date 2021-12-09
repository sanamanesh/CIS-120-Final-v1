package org.cis120.twentyfourtyeight;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class GameTest {

    Tile[][] board = new Tile[4][4];

    public void initialize() {
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                board[i][j] = new Tile();
            }
        }
    }

    public void initializeNoMerge() {
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                board[i][j] = new Tile(2 * (i + 1) * (j + 1));
            }
        }
    }

    @Test
    public void testOnlyOneTileUp() {
        initialize();
        board[1][1].updateValue(2);

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.up();
        assertEquals(2, board[0][1].getValue());
    }

    @Test
    public void testUpWithTileInTheWay() {
        initialize();
        board[0][1].updateValue(4);
        board[1][1].updateValue(2);

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.up();
        assertEquals(4, board[0][1].getValue());
        assertEquals(2, board[1][1].getValue());
    }

    @Test
    public void testTwoTilesUpDiffCol() {
        initialize();
        board[2][1].updateValue(4);
        board[1][3].updateValue(2);

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.up();
        assertEquals(4, board[0][1].getValue());
        assertEquals(2, board[0][3].getValue());
    }

    @Test
    public void testTwoTilesUpSameColDiffNum() {
        initialize();
        board[1][1].updateValue(4);
        board[3][1].updateValue(2);

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.up();
        assertEquals(4, board[0][1].getValue());
        assertEquals(2, board[1][1].getValue());
    }

    @Test
    public void testTwoTilesUpSameColSameNum() {
        initialize();
        board[1][1].updateValue(2);
        board[3][1].updateValue(2);

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.up();
        assertEquals(4, board[0][1].getValue());
    }

    @Test
    public void testUpBoardFullYesMerge() {
        initialize();
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                board[i][j].updateValue(2);
            }
        }

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        assertTrue(t.up());
    }

    @Test
    public void testUpBoardFullNoMerge() {
        initializeNoMerge();

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        assertFalse(t.up());
    }

    @Test
    public void testOnlyOneTileDown() {
        initialize();
        board[1][1].updateValue(2);

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.down();
        assertEquals(2, board[3][1].getValue());
    }

    @Test
    public void testDownWithTileInTheWay() {
        initialize();
        board[2][1].updateValue(4);
        board[3][1].updateValue(2);

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.down();
        assertEquals(4, board[2][1].getValue());
        assertEquals(2, board[3][1].getValue());
    }

    @Test
    public void testTwoTilesDownDiffCol() {
        initialize();
        board[2][1].updateValue(4);
        board[1][3].updateValue(2);

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.down();
        assertEquals(4, board[3][1].getValue());
        assertEquals(2, board[3][3].getValue());
    }

    @Test
    public void testTwoTilesDownSameColDiffNum() {
        initialize();
        board[0][1].updateValue(4);
        board[3][1].updateValue(2);

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.down();
        assertEquals(4, board[2][1].getValue());
        assertEquals(2, board[3][1].getValue());
    }

    @Test
    public void testTwoTilesDownSameColSameNum() {
        initialize();
        board[0][1].updateValue(2);
        board[2][1].updateValue(2);

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.down();
        assertEquals(4, board[3][1].getValue());
    }

    @Test
    public void testDownBoardFullYesMerge() {
        initialize();
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                board[i][j].updateValue(2);
            }
        }

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        assertTrue(t.down());
    }

    @Test
    public void testDownBoardFullNoMerge() {
        initializeNoMerge();

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        assertFalse(t.down());
    }

    @Test
    public void testOnlyOneTileRight() {
        initialize();
        board[1][1].updateValue(2);

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.right();
        assertEquals(2, board[1][3].getValue());
    }

    @Test
    public void testRightWithTileInTheWay() {
        initialize();
        board[2][2].updateValue(4);
        board[2][3].updateValue(2);

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.right();
        assertEquals(4, board[2][2].getValue());
        assertEquals(2, board[2][3].getValue());
    }

    @Test
    public void testTwoTilesRightDiffRow() {
        initialize();
        board[2][1].updateValue(4);
        board[1][2].updateValue(2);

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.right();
        assertEquals(4, board[2][3].getValue());
        assertEquals(2, board[1][3].getValue());
    }

    @Test
    public void testTwoTilesRightSameRowDiffNum() {
        initialize();
        board[1][1].updateValue(4);
        board[1][2].updateValue(2);

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.right();
        assertEquals(4, board[1][2].getValue());
        assertEquals(2, board[1][3].getValue());
    }

    @Test
    public void testTwoTilesRightSameRowSameNum() {
        initialize();
        board[1][1].updateValue(2);
        board[1][2].updateValue(2);

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.right();
        assertEquals(4, board[1][3].getValue());
    }

    @Test
    public void testRightBoardFullYesMerge() {
        initialize();
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                board[i][j].updateValue(2);
            }
        }

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        assertTrue(t.right());
    }

    @Test
    public void testRightBoardFullNoMerge() {
        initializeNoMerge();

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        assertFalse(t.right());
    }

    @Test
    public void testOnlyOneTileLeft() {
        initialize();
        board[1][1].updateValue(2);

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.left();
        assertEquals(2, board[1][0].getValue());
    }

    @Test
    public void testLeftWithTileInTheWay() {
        initialize();
        board[2][0].updateValue(4);
        board[2][1].updateValue(2);

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.left();
        assertEquals(4, board[2][0].getValue());
        assertEquals(2, board[2][1].getValue());
    }

    @Test
    public void testTwoTilesLeftDiffRow() {
        initialize();
        board[2][1].updateValue(4);
        board[1][2].updateValue(2);

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.left();
        assertEquals(4, board[2][0].getValue());
        assertEquals(2, board[1][0].getValue());
    }

    @Test
    public void testTwoTilesLeftSameRowDiffNum() {
        initialize();
        board[1][1].updateValue(4);
        board[1][2].updateValue(2);

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.left();
        assertEquals(4, board[1][0].getValue());
        assertEquals(2, board[1][1].getValue());
    }

    @Test
    public void testTwoTilesLeftSameRowSameNum() {
        initialize();
        board[1][1].updateValue(2);
        board[1][2].updateValue(2);

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.left();
        assertEquals(4, board[1][0].getValue());
    }

    @Test
    public void testLeftBoardFullYesMerge() {
        initialize();
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                board[i][j].updateValue(2);
            }
        }

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        assertTrue(t.left());
    }

    @Test
    public void testLeftBoardFullNoMerge() {
        initializeNoMerge();

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        assertFalse(t.left());
    }

    @Test
    public void testResetEmptyBoard() {
        initialize();

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.reset();

        boolean empty = true;
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                if (board[i][j].getValue() != 0) {
                    empty = false;
                }
            }
        }
        assertTrue(empty);
    }

    @Test
    public void testResetZeroTurns() {
        initialize();

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.reset();
        assertEquals(0, t.getNumTurns());
    }

    @Test
    public void testResetGameNumUpdates() {
        initialize();

        TwentyFourtyEight t = new TwentyFourtyEight(this.board);
        t.reset();
        assertEquals(1, t.getGameNumber());
    }

}
