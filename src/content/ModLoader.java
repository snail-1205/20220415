package content;

import mindustry.ctype.ContentList;


public class ModLoader implements ContentList{
    private final ContentList[] contents = {
            new SNblocks();
    };

    public void load(){
        for(ContentList list : contents){
            list.load();
        }
    }
}
