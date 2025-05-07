package com.potatofarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen extends com.badlogic.gdx.ScreenAdapter {
    private PotatoFarmGame game;
    private Stage stage;
    private FarmPlot[] farmPlots;
    private Texture shovelTexture, hoeTexture, wateringCanTexture;
    private int selectedTool = 0; // 0 = shovel, 1 = hoe, 2 = watering can
    private int potatoes = 0;
    private int money = 10;

    public GameScreen(PotatoFarmGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Initialize farm plots
        farmPlots = new FarmPlot[5]; // 5 plots for simplicity
        for (int i = 0; i < farmPlots.length; i++) {
            farmPlots[i] = new FarmPlot(i * 150, 100); // Positions for plots
        }

        // Load tool textures
        shovelTexture = new Texture("shovel.png");
        hoeTexture = new Texture("hoe.png");
        wateringCanTexture = new Texture("watering_can.png");

        // Create a table for the UI
        Table table = new Table();
        table.top().left();
        table.setFillParent(true);
        stage.addActor(table);

        // Create tool buttons
        TextButton shovelButton = new TextButton("Shovel", game.skin);
        shovelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectedTool = 0; // Shovel selected
            }
        });

        TextButton hoeButton = new TextButton("Hoe", game.skin);
        hoeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectedTool = 1; // Hoe selected
            }
        });

        TextButton waterButton = new TextButton("Water", game.skin);
        waterButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectedTool = 2; // Watering Can selected
            }
        });

        // Add buttons to the table
        table.add(shovelButton).pad(10);
        table.row().pad(10);
        table.add(hoeButton).pad(10);
        table.row().pad(10);
        table.add(waterButton).pad(10);
    }

    @Override
    public void render(float delta) {
        game.batch.begin();

        // Render farm plots
        for (FarmPlot plot : farmPlots) {
            plot.render(game.batch);
        }

        // Render the selected tool (e.g., hover the mouse)
        if (selectedTool == 0) {
            game.batch.draw(shovelTexture, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        } else if (selectedTool == 1) {
            game.batch.draw(hoeTexture, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        } else if (selectedTool == 2) {
            game.batch.draw(wateringCanTexture, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        }

        game.batch.end();

        // Handle mouse interaction
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            for (FarmPlot plot : farmPlots) {
                if (plot.getBounds().contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    plot.handleInteraction(selectedTool);
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        stage.dispose();
    }
}
