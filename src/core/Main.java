package core;

import arc.Events;
import content.ModLoader;
import mindustry.game.EventType.*;
import mindustry.mod.Mod;


public class Main extends Mod {
    public Main(){
        Events.on(ClientLoadEvent.class, e -> {
        });

        Events.on(WorldLoadEvent.class, e -> {
        });
    }

    @Override
    public void init(){
    }

    @Override
    public void loadContent(){
        new ModLoader().load();
    }


}
