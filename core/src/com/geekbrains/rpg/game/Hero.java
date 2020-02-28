package com.geekbrains.rpg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Hero {
    private Texture texture;
    private Vector2 position;
    private float speed;

    public Hero() {
        this.texture = new Texture("hero.png");
        this.position = new Vector2(100, 100);
        this.speed = 100.0f;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 32, position.y - 32,32,32,64,64,1,1,0,0,0,64,64,false,false);
    }
    public void move(float dt, Vector2 pointerPosition) {
        if ((pointerPosition.x-position.x)>0) {
            position.x +=  speed * dt ;
        }
        else {
            position.x -=  speed * dt ;
        }

        if ((pointerPosition.y-position.y)>0) {
            if ((pointerPosition.x-position.x)>0) {
                position.y += speed * dt * ((pointerPosition.y - position.y) / (pointerPosition.x - position.x));
            }
            else {
                position.y += speed * dt * ((pointerPosition.y - position.y) / (position.x - pointerPosition.x ));
            }
        }
        else {
            // position.y -=  speed * dt * ((position.y -pointerPosition.y)/(pointerPosition.x-position.x));

            if ((pointerPosition.x-position.x)>0) {
                position.y -= speed * dt * ((position.y - pointerPosition.y) / (pointerPosition.x - position.x));
            }
            else {
                position.y -= speed * dt * ((position.y - pointerPosition.y) / (position.x - pointerPosition.x ));
            }
        }

        System.out.println("x="+position.x + " y="+position.y);
    }
    public void update(float dt) {

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            position.x -= speed * dt;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            position.x += speed * dt;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            position.y -= speed * dt;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            position.y += speed * dt;
        }
    }
}