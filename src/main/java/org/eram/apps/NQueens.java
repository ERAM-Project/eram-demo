package org.eram.apps;

import android.util.Log;

import org.eram.core.AppBuilder;
import org.eram.core.app.Application;
import org.eram.core.app.Task;
import org.eram.oc.MainActivity;

public class NQueens {

    private Application app;
    private  String last="";

    public NQueens(int nbQueens){

        NQueensEnter task1 = new NQueensEnter("task1",nbQueens);
        QueensSolver task2 = new QueensSolver("task2",nbQueens);
        NQueensExit task3  = new NQueensExit("task3", nbQueens);

        Task [][] dependencies = new Task[2][2];

        dependencies[0][0]= task1; dependencies[0][1] = task2;
        dependencies[1][0]= task2; dependencies[1][1] = task3;

        this.app = AppBuilder.buildApp();
        this.app.constructApp(dependencies, task1, task2, task3);

        this.last = task3.toString();
    }

    public Application getApp(){
        return app;
    }

    public String lastTask(){
        return  last;
    }
}