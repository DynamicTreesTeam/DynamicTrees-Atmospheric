package maxhyper.dtatmospheric.cells;

import com.ferreusveritas.dynamictrees.api.cells.Cell;
import com.ferreusveritas.dynamictrees.api.cells.CellKit;
import com.ferreusveritas.dynamictrees.api.cells.CellNull;
import com.ferreusveritas.dynamictrees.api.cells.CellSolver;
import com.ferreusveritas.dynamictrees.api.registry.Registry;
import com.ferreusveritas.dynamictrees.cells.CellKits;
import com.ferreusveritas.dynamictrees.util.SimpleVoxmap;
import maxhyper.dtatmospheric.DynamicTreesAtmospheric;
import net.minecraft.util.Direction;

public class DTAtmosphericCellKits {

    public static void register(final Registry<CellKit> registry) {
        registry.registerAll(ROSEWOOD);
    }

    public static final CellKit ROSEWOOD = new CellKit(DynamicTreesAtmospheric.resLoc("rosewood")) {

        private final Cell rosewoodBranch = new Cell() {
            @Override
            public int getValue() {
                return 5;
            }

            final int[] map = {0, 2, 4, 4, 4, 4};

            @Override
            public int getValueFromSide(Direction side) {
                return map[side.ordinal()];
            }

        };

        private final Cell[] rosewoodLeafCells = {
                CellNull.NULL_CELL,
                new RosewoodLeafCell(1),
                new RosewoodLeafCell(2),
                new RosewoodLeafCell(3),
                new RosewoodLeafCell(4),
                new RosewoodLeafCell(5),
                new RosewoodLeafCell(6),
                new RosewoodLeafCell(7)
        };

        private final CellKits.BasicSolver rosewoodSolver = new CellKits.BasicSolver(new short[]{0x0413, 0x0322, 0x0311, 0x0211});

        @Override
        public Cell getCellForLeaves(int hydro) {
            return rosewoodLeafCells[hydro];
        }

        @Override
        public Cell getCellForBranch(int radius, int meta) {
            return radius == 1 ? rosewoodBranch : CellNull.NULL_CELL;
        }

        @Override
        public SimpleVoxmap getLeafCluster() {
            return DTAtmosphericLeafClusters.ROSEWOOD;
        }

        @Override
        public CellSolver getCellSolver() {
            return rosewoodSolver;
        }

        @Override
        public int getDefaultHydration() {
            return 3;
        }

    };

}
