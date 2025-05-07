package com.potatofarm;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FarmPlot {
    private int x, y;
    private Sprite plotSprite, potatoSprite;
    private Rectangle bounds;
    private boolean isPlanted = false;
    private boolean isWatered = false;

    public FarmPlot(int x, int y) {
        this.x = x;
        this.y = y;
        this.plotSprite = new Sprite(new Texture("plot.png"));
        this.potatoSprite = new Sprite(new Texture("potato.png"));
        this.bounds = new Rectangle(x, y, plotSprite.getWidth(), plotSprite.getHeight());
    }

    public void render(SpriteBatch batch) {
        plotSprite.setPosition(x, y);
        plotSprite.draw(batch);

        if (isPlanted) {
            potatoSprite.setPosition(x + 30, y + 30); // Adjust potato position
            potatoSprite.draw(batch);
        }
    }

    public void handleInteraction(int toolType) {
        if (!isPlanted && toolType == 0) {
            // Shovel to plant
            isPlanted = true;
            System.out.println("Potato planted!");
        } else if (isPlanted && toolType == 2 && !isWatered) {
            // Watering can to water
            isWatered = true;
            System.out.println("Potato watered!");
        } else if (isPlanted && toolType == 1) {
            // Hoe to harvest
            System.out.println("Potato harvested!");
            isPlanted = false;
            isWatered = false;
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
