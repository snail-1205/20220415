package content;


import blocks.Snailblock;
import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.world.Block;

import static mindustry.type.ItemStack.with;

public class SNblocks implements ContentList {
    public static Block snailblock;
    @Override
    public void load() {

        snailblock = new Snailblock("snail.block"){{
            requirements(Category.defense, with(Items.titanium, 6));
            health = 150 * 4;
            armor = 3;
        }};

    }
}
