package com.geekbrains.rpg.game.logic;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class GameController {
    private ProjectilesController projectilesController;
    private Map map;
    private Hero hero;
    private ArrayList <Monster> monsters;
    private Vector2 tmp, tmp2;

    public Hero getHero() {
        return hero;
    }

    public ArrayList<Monster> getMonsters() {
        return this.monsters;
    }

    public Map getMap() {
        return map;
    }

    public ProjectilesController getProjectilesController() {
        return projectilesController;
    }

    public GameController() {
        this.projectilesController = new ProjectilesController();
        this.hero = new Hero(this);

        this.monsters = new ArrayList<Monster>();
        Monster m1 = new Monster(this);
        Monster m2 = new Monster(this);
        Monster m3 = new Monster(this);
        this.monsters.add(m1);
        this.monsters.add(m2);
        this.monsters.add(m3);

        this.map = new Map();
        this.tmp = new Vector2(0, 0);
        this.tmp2 = new Vector2(0, 0);
    }

    public void update(float dt) {
        hero.update(dt);
        for (Monster m: this.monsters) {
            m.update(dt);
            checkCollisions(m);
            collideUnits(hero, m);
        }

        projectilesController.update(dt);
    }

    public void collideUnits(GameCharacter u1, GameCharacter u2) {
        if (u1.getArea().overlaps(u2.getArea())) {
            tmp.set(u1.getArea().x, u1.getArea().y);
            tmp.sub(u2.getArea().x, u2.getArea().y);
            float halfInterLen = ((u1.getArea().radius + u2.getArea().radius) - tmp.len()) / 2.0f;
            tmp.nor();

            tmp2.set(u1.getPosition()).mulAdd(tmp, halfInterLen);
            if (map.isGroundPassable(tmp2)) {
                u1.changePosition(tmp2);
            }

            tmp2.set(u2.getPosition()).mulAdd(tmp, -halfInterLen);
            if (map.isGroundPassable(tmp2)) {
                u2.changePosition(tmp2);
            }
        }
    }

    public void checkCollisions(Monster monster) {
        for (int i = 0; i < projectilesController.getActiveList().size(); i++) {
            Projectile p = projectilesController.getActiveList().get(i);
            if (!map.isAirPassable(p.getCellX(), p.getCellY())) {
                p.deactivate();
                continue;
            }
            if (p.getPosition().dst(monster.getPosition()) < 24) {
                p.deactivate();
                if (monster.takeDamage(1)) {
                    hero.addCoins(MathUtils.random(1, 10));
                }
            }
        }
    }
}
