package com.geekbrains.rpg.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Target {
    private TextureRegion textureRegion;
    private Vector2 tposition;
    private boolean active;
    Random random = new Random();

    public Target(TextureAtlas targetAtlas) {
        this.textureRegion = targetAtlas.findRegion("ya");
        this.active = false;
        setup();
    }
    public void setup() {
        this.tposition = new Vector2(0, 0);
        tposition.set(random.nextInt(1200), random.nextInt(720));
        active = true;
    }
    public void recreate() {
        setup();
    }
    public void render(SpriteBatch batch) {
        if (active) {
            batch.draw(textureRegion, tposition.x, tposition.y, 30, 30);
        }
    }
    public void update(float dt, Vector2 projPosition) {
        if (Math.abs(tposition.x - projPosition.x) <30  & Math.abs(tposition.y - projPosition.y) < 30) {
            active = false;
            recreate();
        }

    }
}
