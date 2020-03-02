package com.geekbrains.rpg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

public class Hero {
    private ArrayList<Projectile> projectile_List;
    private TextureRegion texture;
    private TextureRegion texturePointer;
    private TextureRegion textureHp;
    private TextureRegion targetRegion;
    private Target target;
    private Vector2 position;
    private Vector2 dst;
    private Vector2 tmp;
    private float lifetime;
    private float speed;
    private int hp;
    private int hpMax;
    private StringBuilder strBuilder;
    private TextureAtlas atlas;
    Random random = new Random();
    public Hero(TextureAtlas atlas, TextureAtlas targetAtlas) {
        this.atlas = atlas;
        this.target = new Target(targetAtlas);
        this.texture = atlas.findRegion("knight");
        this.targetRegion = targetAtlas.findRegion("ya");
        this.texturePointer = atlas.findRegion("pointer");
        this.textureHp = atlas.findRegion("hp");
        this.position = new Vector2(100, 100);
        this.projectile_List = new ArrayList<>();
        this.dst = new Vector2(position);
        this.tmp = new Vector2(0, 0);
        this.speed = 300.0f;
        this.hpMax = 10;
        this.hp = 10;
        this.strBuilder = new StringBuilder();
    }

    public void render(SpriteBatch batch) {
        batch.draw(texturePointer, dst.x - 30, dst.y - 30, 30, 30, 60, 60, 0.5f, 0.5f, lifetime * 90.0f);
        batch.draw(texture, position.x - 30, position.y - 30, 30, 30, 60, 60, 1, 1, 0);
        batch.draw(textureHp, position.x - 30, position.y + 30, 60 * ((float) hp / hpMax), 12);
        target.render(batch);
        for (int i = 0; i < projectile_List.size(); i++) {
           projectile_List.get(i).render(batch);
        }

    }

    public void renderGUI(SpriteBatch batch, BitmapFont font) {
        strBuilder.setLength(0);
        strBuilder.append("Class: ").append("Knight").append("\n");
        strBuilder.append("HP: ").append(hp).append(" / ").append(hpMax).append("\n");
        font.draw(batch, strBuilder, 10, 710);
    }

    public void update(float dt) {
        for (int i = 0; i < projectile_List.size(); i++) {
            projectile_List.get(i).update(dt);
            target.update(dt, projectile_List.get(i).getPosition());
        }
        lifetime += dt;

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            dst.set(Gdx.input.getX(), 720.0f - Gdx.input.getY());
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            Projectile projectile = new Projectile(atlas);
            projectile.setup(position.x, position.y, Gdx.input.getX(), 720.0f - Gdx.input.getY());
            projectile_List.add(projectile);
        }
        tmp.set(dst).sub(position).nor().scl(speed); // вектор скорости
        if (position.dst(dst) > speed * dt) {
            position.mulAdd(tmp, dt);
        } else {
            position.set(dst);
        }
    }
}