package org.eram.apps;

import org.eram.core.annotation.InputChangement;
import org.eram.core.annotation.Offloadable;
import org.eram.core.annotation.Remoteable;
import org.eram.core.app.Task;

@Remoteable(isOffloadable = Offloadable.IS_NON_OFFLOADABLE, isInputChangeable = InputChangement.DO_NOT_CHANGE_INPUT)
public class NQueensEnter extends Task<Integer, Integer> {

    public NQueensEnter(String name, Integer... inputs) {
        super(name, inputs);
    }

    @Override
    public Integer run() {

        return inputs.get(0);
    }
}