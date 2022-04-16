package blocks;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import content.SNFx;
import mindustry.Vars;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.world.blocks.defense.Wall;

import static mindustry.Vars.minArmorDamage;

public class Snailblock extends Wall {

    public boolean drawShields = true;
    public float armor = 0;
    public float maxShield = health;
    public float regenCooldown = 2 * 60f;
    public float regenAmount = 1;

    public Snailblock(String name) {
        super(name);
        update = true;
        sync = true;
    }
    public class ShieldWallBuild extends WallBuild {
        float shield;
        float shieldAlpha;
        float heat = 0f;

        @Override
        public void created() {
            super.created();
            shield = maxShield = health;
        }

        @Override
        public void draw(){
            super.draw();

            if(shieldAlpha > 0 && drawShields) drawShield();
        }
        public void drawShield(){
            float alpha = shieldAlpha;
            float radius = block.size * Vars.tilesize * 1.3f;
            Draw.z(Layer.blockOver);
            Fill.light(x, y, Lines.circleVertices(radius), radius, Tmp.c1.set(Pal.shield), Tmp.c2.set(Pal.shield).lerp(Color.white, Mathf.clamp(hitTime() / 2f)).a(Pal.shield.a * alpha));
            Draw.reset();
        }

        @Override
        public void damage(float amount){
            rawDamage(Math.max(amount - armor, minArmorDamage * amount));
        }

        @Override
        public void damagePierce(float amount, boolean withEffect){
            float pre = hitTime;

            rawDamage(amount);

            if(!withEffect) hitTime = pre;
        }

        protected void rawDamage(float amount){
            boolean hadShields = shield > 0.0001f;

            if(hadShields) shieldAlpha = 1f;
            heat = regenCooldown;

            float shieldDamage = Math.min(Math.max(shield, 0), amount);
            shield -= shieldDamage;
            hitTime = 1f;
            amount -= shieldDamage;

            if(amount > 0){
                health -= amount;
                if(health <= 0 && !dead()) kill();

                if(hadShields && shield <= 0.0001f) SNFx.blockShieldBreak.at(x, y, 0, this);
            }
        }

        @Override
        public void updateTile(){
            super.updateTile();
            shieldAlpha -= delta() / 15f;
            if(shieldAlpha < 0) shieldAlpha = 0f;

            heat -= delta();
            if(heat <= 0f && shield < maxShield) shield += regenAmount * delta();
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(shield);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            shield = read.f();
        }

    }


}
