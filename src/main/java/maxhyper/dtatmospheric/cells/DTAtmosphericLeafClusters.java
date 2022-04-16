package maxhyper.dtatmospheric.cells;

import com.ferreusveritas.dynamictrees.util.SimpleVoxmap;
import net.minecraft.util.math.BlockPos;

public class DTAtmosphericLeafClusters {

    public static final SimpleVoxmap ROSEWOOD = new SimpleVoxmap(5, 2, 5, new byte[]{

            //Layer 0(Bottom)
            0, 1, 1, 1, 0,
            1, 2, 3, 2, 1,
            1, 3, 0, 3, 1,
            1, 2, 3, 2, 1,
            0, 1, 1, 1, 0,

            //Layer 1 (Top)
            0, 0, 0, 0, 0,
            0, 1, 1, 1, 0,
            0, 1, 1, 1, 0,
            0, 1, 1, 1, 0,
            0, 0, 0, 0, 0

    }).setCenter(new BlockPos(3, 0, 3));

}
