package org.eram.apps;

import android.util.Log;

import org.eram.core.annotation.InputChangement;
import org.eram.core.annotation.Offloadable;
import org.eram.core.annotation.Remoteable;
import org.eram.core.app.Task;

@Remoteable(isOffloadable = Offloadable.IS_OFFLOADABLE, isInputChangeable = InputChangement.CHANGE_INPUT)
public class QueensSolver extends Task<java.lang.Integer, java.lang.Integer> {

    private static int nbExecutor = 1;

    public QueensSolver(String name,Integer... inputs) {
        super(name, inputs);
    }

    @Override
    public Integer run() {
        return this.localSolveNQueens(inputs.get(0));
    }

    public int localSolveNQueens(int N) {

        int countSolutions = 0;

        byte[][] board = new byte[N][N];

        int start = 0, end = N;

        Log.i(TAG, "This is now running on the VM...");


        // cloneId == 0 if this is the main clone
        // or [1, nbExecutor-1] otherwise
        int cloneId = 0;
        int howManyCols = (N) / nbExecutor; // Integer division, we may
        // loose some columns.
        start = cloneId * howManyCols; // cloneId == 0 if this is the main clone
        end = start + howManyCols;

        // If this is the clone with the highest id let him take care
        // of the columns not considered due to the integer division.
        if (cloneId == nbExecutor - 1) {
            end += N % nbExecutor;
        }


        Log.i(TAG, "Finding solutions for " + N + "-queens puzzle.");
        Log.i(TAG, "Analyzing columns: " + start + "-" + (end - 1));

        for (int i = start; i < end; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    for (int l = 0; l < N; l++) {
                        if (N == 4) {
                            countSolutions += setAndCheckBoard(N, board, i, j, k, l);
                            continue;
                        }
                        for (int m = 0; m < N; m++) {
                            if (N == 5) {
                                countSolutions += setAndCheckBoard(N, board, i, j, k, l, m);
                                continue;
                            }
                            for (int n = 0; n < N; n++) {
                                if (N == 6) {
                                    countSolutions += setAndCheckBoard(N, board, i, j, k, l, m, n);
                                    continue;
                                }
                                for (int o = 0; o < N; o++) {
                                    if (N == 7) {
                                        countSolutions += setAndCheckBoard(N, board, i, j, k, l, m, n, o);
                                        continue;
                                    }
                                    for (int p = 0; p < N; p++) {
                                        countSolutions += setAndCheckBoard(N, board, i, j, k, l, m, n, o, p);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Log.i(TAG, "Found " + countSolutions + " solutions.");

        return countSolutions;
    }

    private int setAndCheckBoard(int N, byte[][] board, int... cols) {

        clearBoard(N, board);

        for (int i = 0; i < N; i++)
            board[i][cols[i]] = 1;

        if (isSolution(N, board))
            return 1;

        return 0;
    }

    private void clearBoard(int N, byte[][] board) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = 0;
            }
        }
    }

    private boolean isSolution(int N, byte[][] board) {

        int rowSum = 0;
        int colSum = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                rowSum += board[i][j];
                colSum += board[j][i];

                if (i == 0 || j == 0)
                    if (!checkDiagonal1(N, board, i, j))
                        return false;

                if (i == 0 || j == N - 1)
                    if (!checkDiagonal2(N, board, i, j))
                        return false;

            }
            if (rowSum > 1 || colSum > 1)
                return false;
            rowSum = 0;
            colSum = 0;
        }

        return true;
    }


    private boolean checkDiagonal1(int N, byte[][] board, int row, int col) {
        int sum = 0;
        int i = row;
        int j = col;
        while (i < N && j < N) {
            sum += board[i][j];
            i++;
            j++;
        }
        return sum <= 1;
    }

    private boolean checkDiagonal2(int N, byte[][] board, int row, int col) {
        int sum = 0;
        int i = row;
        int j = col;
        while (i < N && j >= 0) {
            sum += board[i][j];
            i++;
            j--;
        }
        return sum <= 1;
    }

}
