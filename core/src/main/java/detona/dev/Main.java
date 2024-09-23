package detona.dev;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import detona.dev.players.Ball;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
     ShapeRenderer shape;
     Ball ball;
     Texture backgroundTexture;
     Texture bucketTexture;
     Texture wallTexture;
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

        createWalls();
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

        // Store the bucket size for brevity
        float bucketHeight = bucketSprite.getHeight();
        float bucketWidth = bucketSprite.getWidth();

        // Subtract the bucket width
        bucketSprite.setX(MathUtils.clamp(bucketSprite.getX(), 0, worldWidth - bucketWidth));

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
                wallSprites.removeIndex(i);
            }
        }
    }

    private void input() {
        float speed = 10f;
        float delta = Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            bucketSprite.translateY(speed * delta);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
           bucketSprite.translateX(speed * delta);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            bucketSprite.translateY(-speed * delta);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            bucketSprite.translateX(-speed * delta);
        }
    }


    void createWalls(){
        var wallTexture = new Texture("wall.png");

        var alturaDoViewPort = viewport.getWorldHeight();

          /*
            BLOCOS DE PRIMEIRA PAREDE
         */

        var firstWall = new Sprite(wallTexture);
        firstWall.setSize(commonSize,commonSize);
        firstWall.setPosition(2,alturaDoViewPort - firstWall.getHeight());//traz a parede para frente
        wallSprites.add(firstWall);
        //a ideia é renderizar as proximas paredes após esse primeiro que está no topo

        for (int i = 1; i < 7; i++) {
            var newWall = new Sprite(wallTexture);
            newWall.setSize(commonSize, commonSize);

            float posicaoYDaParedeAnterior = wallSprites.get(i - 1).getY();
            float alturaDaParedeAnterior = wallSprites.get(i - 1).getHeight();

            newWall.setPosition(2, (posicaoYDaParedeAnterior - alturaDaParedeAnterior) + 0.07f);
            wallSprites.add(newWall);
        }

         /*
            BLOCOS DE SEGUNDA PAREDE
         */

        var secondWall = new Sprite(wallTexture);
        firstWall.setSize(commonSize,commonSize);
        firstWall.setPosition(3,0.6f);

        var secondWall2 = new Sprite(wallTexture);
        secondWall2.setSize(commonSize,commonSize);
        secondWall2.setPosition(3,(secondWall.getY() + secondWall2.getHeight()) + 0.5f);

        var secondWall3 = new Sprite(wallTexture);
        secondWall3.setSize(commonSize,commonSize);
        secondWall3.setPosition(3,(secondWall2.getY() + secondWall3.getHeight()) - 0.1f);

        var secondWall4 = new Sprite(wallTexture);
        secondWall4.setSize(commonSize,commonSize);
        secondWall4.setPosition(3,(secondWall3.getY() + secondWall4.getHeight()) - 0.1f);

        var secondWall5 = new Sprite(wallTexture);
        secondWall5.setSize(commonSize,commonSize);
        secondWall5.setPosition(3,(secondWall4.getY() + secondWall5.getHeight()) - 0.1f);

        wallSprites.add(secondWall,secondWall2,secondWall3,secondWall4);
        wallSprites.add(secondWall5);
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
