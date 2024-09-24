package detona.dev;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import detona.dev.cenario.Wall;
import detona.dev.players.Ball;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
     ShapeRenderer shape;
     Ball ball;
     Texture backgroundTexture;
     Texture bucketTexture;
     SpriteBatch spriteBatch;
     FitViewport viewport;
     Sprite bucketSprite;
     Array<Sprite> wallSprites;
     Rectangle bucketRectangle;
     Rectangle dropRectangle;

     float commonSize = 0.5f;

    @Override
    public void create() {
       shape = new ShapeRenderer();
       ball = new Ball(50,50,50,5);
       backgroundTexture = new Texture("background.png");
       bucketTexture = new Texture("bucket.png");

       spriteBatch = new SpriteBatch();
       viewport = new FitViewport(8,5);

       bucketSprite = new Sprite(bucketTexture);
       bucketSprite.setSize(commonSize - 0.13f,commonSize - 0.13f);

       wallSprites = new Array<>();

       bucketRectangle = new Rectangle();
       dropRectangle = new Rectangle();

        wallSprites.addAll(new Wall(commonSize).createWalls(viewport));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height,true);
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    private void logic() {
        float worldWidth = viewport.getWorldWidth();
        float wordHeigh = viewport.getWorldHeight();

        float bucketHeight = bucketSprite.getHeight();
        float bucketWidth = bucketSprite.getWidth();

        /*
            COLIDINDO COM A VIEWPORT
         */
        bucketSprite.setX(MathUtils.clamp(bucketSprite.getX(), 0, worldWidth - bucketWidth));
        bucketSprite.setY(MathUtils.clamp(bucketSprite.getY(), 0, wordHeigh - bucketHeight));

        /*
            USANDO OS DADOS DO BALDE PARA CRIAR SEU COLISOR
        */
        bucketRectangle.set(bucketSprite.getX(), bucketSprite.getY(), bucketWidth, bucketHeight);

        for (int i = wallSprites.size - 1; i >= 0; i--) {
            Sprite parede = wallSprites.get(i);
            float paredeWidth = parede.getWidth();
            float paredeHeight = parede.getHeight();
            /*
                USANDO OS DADOS DA PAREDE PARA CRIAR SEU COLISOR
            */
            dropRectangle.set(parede.getX(), parede.getY(), paredeWidth, paredeHeight);

            /*
                AQUI ABAIXO ACONTECE A COLISÃO
             */

            if (bucketRectangle.overlaps(dropRectangle)) {

               // wallSprites.removeIndex(i);
            }
        }
    }

    private void input() {
        float speed = 0.9f;
        float delta = Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            bucketSprite.translateY(speed * delta);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
           bucketSprite.translateX(speed * delta);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            bucketSprite.translateY(-speed * delta);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            bucketSprite.translateX(-speed * delta);
        }
    }

    private void draw(){
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        var worldWidth = viewport.getWorldWidth();
        var worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture,0,0,worldWidth,worldHeight);

        bucketSprite.draw(spriteBatch);

        for (Sprite wallSprite : wallSprites) {
            wallSprite.draw(spriteBatch);
        }

        spriteBatch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
