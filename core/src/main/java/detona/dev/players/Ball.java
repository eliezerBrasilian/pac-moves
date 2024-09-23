package detona.dev.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ball {
    private int x;
    private int y;
    private int size;
    private int xSpeed;

    public Ball(int x,int y,int size, int xSpeed){
        this.x = x;
        this.y = y;
        this.size = size;
        this.xSpeed = xSpeed;
    }

    public void update(){
        x+=xSpeed;

        if(x > Gdx.graphics.getWidth()){
            xSpeed = -5;
        }

        if (x < 0) {
            xSpeed = 5;
        }
    }

    public void draw(ShapeRenderer shape){
        shape.circle(x,y,size);
    }
}
