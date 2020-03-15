package com.geekbrains.rpg.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.geekbrains.rpg.game.logic.GameController;
import com.geekbrains.rpg.game.logic.WorldRenderer;
import com.geekbrains.rpg.game.screens.utils.Assets;

public class GameScreen extends AbstractScreen {
    private GameController gc;
    private WorldRenderer worldRenderer;
    private Stage stage;
    BitmapFont font32;
    public GameScreen(SpriteBatch batch) {
        super(batch);
    }

    @Override
    public void show() {
        gc = new GameController();
        worldRenderer = new WorldRenderer(gc, batch);
        createGui();
    }

    @Override
    public void render(float delta) {
        gc.update(delta);
        worldRenderer.render();
        stage.draw();
    }
    public void createGui() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin();
        skin.addRegions(Assets.getInstance().getAtlas());

        font32 = Assets.getInstance().getAssetManager().get("fonts/font32.ttf");

        TextButton.TextButtonStyle menuBtnStyle = new TextButton.TextButtonStyle(
                skin.getDrawable("simpleButton"), null, null, font32);

        TextButton btnPauseGame = new TextButton("Pause", menuBtnStyle);
        btnPauseGame.setPosition(550, 600);
        TextButton btnMenuGame = new TextButton("Menu", menuBtnStyle);
        btnMenuGame.setPosition(900, 600);

        btnMenuGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.MENU);
            }
        });

        btnPauseGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gc.setPause();
            }
        });

        stage.addActor(btnPauseGame);
        stage.addActor(btnMenuGame);
        skin.dispose();
    }
    public void setPause() {
    }
    public void update(float dt) {
        stage.act(dt);
    }
}