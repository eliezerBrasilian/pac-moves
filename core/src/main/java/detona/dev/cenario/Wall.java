package detona.dev.cenario;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Wall {
    private Texture wallTexture;
    private Array<Sprite> wallSprites;
    private float size;

    public Wall(float commonSize){
        this.wallTexture = new Texture("wall.jpg");
        this.wallSprites = new Array<>();
        this.size = commonSize;
    }

    public Array<Sprite> createWalls(FitViewport viewport){
            wallSprites.addAll(this.buildFromPositionY(
                1,
                viewport.getWorldHeight(),
                size,true,5));

            wallSprites.addAll(this.buildFromPositionY(
                4,
                0,
                size,false,3));

            wallSprites.addAll(this.buildFromPositionX(
                1,
                0,
                size,true,1));

            wallSprites.addAll(this.buildFromPositionX(
                4,
                2,
                size,true,1));

            wallSprites.addAll(this.buildFromPositionX(
                viewport.getWorldWidth() - 1.2f,
                4,
                size,false,1));

            return wallSprites;

    }

    public Array<Sprite> buildFromPositionY(
        float x, float y, float size, boolean isFromTop, int quantity){

        var wallSprite = new Sprite(this.wallTexture);
        wallSprite.setSize(size,size);
        if(isFromTop){
            wallSprite.setPosition(x, y - wallSprite.getHeight());
        }else{
            wallSprite.setPosition(x, y + wallSprite.getHeight());
        }

        wallSprites.add(wallSprite);

        for(int i = 0; i < quantity; i ++){
            var newWallSprite = new Sprite(wallTexture);
            newWallSprite.setSize(size,size);

            if(isFromTop){
                newWallSprite.setPosition(x, wallSprites.get(wallSprites.size - 1).getY() - newWallSprite.getHeight());
            }else{
                newWallSprite.setPosition(x, wallSprites.get(wallSprites.size - 1).getY() + newWallSprite.getHeight());
            }
            wallSprites.add(newWallSprite);
        }
        return wallSprites;
    }

    public Array<Sprite> buildFromPositionX(
        float x, float y, float size, boolean isFromLeft, int quantity){

        var wallSprite = new Sprite(this.wallTexture);
        wallSprite.setSize(size,size);
        if(isFromLeft){
            wallSprite.setPosition(x + wallSprite.getWidth(), y );
        }else{
            wallSprite.setPosition(x - wallSprite.getWidth(), y  );
        }

        wallSprites.add(wallSprite);

        for(int i = 0; i < quantity; i ++){
            var newWallSprite = new Sprite(wallTexture);
            newWallSprite.setSize(size,size);

            if(isFromLeft){
                newWallSprite.setPosition(wallSprites.get(wallSprites.size - 1).getX() + newWallSprite.getWidth(),y);
            }else{
                newWallSprite.setPosition(wallSprites.get(wallSprites.size - 1).getX() - newWallSprite.getWidth(),y);
            }
            wallSprites.add(newWallSprite);
        }
        return wallSprites;
    }

}
