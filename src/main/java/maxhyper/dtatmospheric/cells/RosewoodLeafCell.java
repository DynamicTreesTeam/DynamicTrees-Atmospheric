package maxhyper.dtatmospheric.cells;

import com.ferreusveritas.dynamictrees.cells.MatrixCell;

public class RosewoodLeafCell extends MatrixCell {

    public RosewoodLeafCell(int value) {
        super(value, valMap);
    }

    static final byte[] valMap = {
            0, 0, 0, 0, 0, 0, 0, 0, //D Maps * -> 0
            0, 0, 2, 2, 0, 0, 0, 0, //U Maps 3 -> 3, 4 -> 3, * -> 0
            0, 1, 2, 3, 0, 0, 0, 0, //N Maps * -> *
            0, 1, 2, 3, 0, 0, 0, 0, //S Maps * -> *
            0, 1, 2, 3, 0, 0, 0, 0, //W Maps * -> *
            0, 1, 2, 3, 0, 0, 0, 0  //E Maps * -> *
    };

}
