package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;


import java.util.ArrayList;
import java.util.Random;

public class CoinMan extends ApplicationAdapter {
	SpriteBatch batch;
	//Our Background image
	Texture background;
	//man images
	Texture[] man;

	Rectangle manRectangle;
	//get the man state on the screen
	int manState = 0;
	int pause = 0;

	//physics
	float gravity = 0.6f;
	float velocity = 0;
	int manY=0;

    Random random;
	//coin objects
	ArrayList<Integer> coinXs = new ArrayList<Integer>();
	ArrayList<Integer> coinYs = new ArrayList<Integer>();
	ArrayList<Rectangle> coinRectangles = new ArrayList<Rectangle>();
	Texture coin;
	int coinCount;


	//bombs objects
    ArrayList<Integer> bombXs = new ArrayList<Integer>();
    ArrayList<Integer> bombYs = new ArrayList<Integer>();
	ArrayList<Rectangle> bombRectangles = new ArrayList<Rectangle>();
    Texture bomb;
    int bombCount;


	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		man = new Texture[4];
		man[0] = new Texture("frame-1.png");
		man[1] = new Texture("frame-2.png");
		man[2] = new Texture("frame-3.png");
		man[3] = new Texture("frame-4.png");

		//get the manY inside the create() because we can't instantiate this value at the top of the class
		manY = Gdx.graphics.getHeight()/2;

		coin = new Texture("coin.png");
        random = new Random();

        //bomb init
        bomb = new Texture("bomb.png");
	}

	public void makeCoin(){
		//randomizing the coin height
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		coinYs.add((int)height);
		coinXs.add(Gdx.graphics.getWidth());
	}

	public void placeCoins(){
        if(coinCount<100){
            coinCount++;
        }else{
            coinCount=0;
            makeCoin();
        }

        coinRectangles.clear();
        for(int i=0;i<coinXs.size();i++){
            batch.draw(coin,coinXs.get(i),coinYs.get(i));
            //move the coinX
            coinXs.set(i,coinXs.get(i)-4);
            coinRectangles.add(new Rectangle(coinXs.get(i),coinYs.get(i),coin.getWidth(),coin.getHeight()));
        }

    }
	public void makeBomb(){
        //randomizing the bomb height
        float height = random.nextFloat() * Gdx.graphics.getHeight();
        bombYs.add((int)height);
        bombXs.add(Gdx.graphics.getWidth());
    }
    //function to placebombs
    public void placeBombs(){
        if(bombCount<250){
            bombCount++;
        }else{
            bombCount=0;
            makeBomb();
        }

        bombRectangles.clear();
        for(int i=0;i<bombXs.size();i++){
            batch.draw(bomb,bombXs.get(i),bombYs.get(i));
            //move the bombX
            //move bomb quicker
            bombXs.set(i,bombXs.get(i)-6);
			bombRectangles.add(new Rectangle(bombXs.get(i),bombYs.get(i),bomb.getWidth(),bomb.getHeight()));
        }

    }

	@Override
	public void render () {
	    batch.begin();
	    //drawing stuff on the screen
	    batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        //coins
	    placeCoins();
		//bombs
		placeBombs();

	    if(Gdx.input.justTouched()){
	    	velocity = -10;
		}
	    if(pause <8){
	        pause++;
        }else {

	        pause=0;
            if (manState < 3) {
                manState++;
            } else {
                manState = 0;
            }
        }//pause states else

		velocity +=gravity;

	    manY -= velocity;

	    if(manY <=0){
	    	manY = 0;
		}
	    //drawing the dude in the middle of the screen by dividing the width and height by 2
	    //batch.draw(man[0],0,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
		//now as you saw before he's not completley  centered so we're gonna minus his width
		batch.draw(man[manState],Gdx.graphics.getWidth()/2 - man[manState].getWidth()/2,manY);

	    manRectangle = new Rectangle(Gdx.graphics.getWidth()/2 - man[manState].getWidth()/2,manY,man[manState].getWidth(),man[manState].getHeight());

	    //is the man colliding with coins?
	    for(int i=0;i<coinRectangles.size();i++){
	    	if(Intersector.overlaps(manRectangle,coinRectangles.get(i))){
	    		Gdx.app.log("Coin!","Collision!");
			}
		}

		//is the man colliding with bombs?
		for(int i=0;i<bombRectangles.size();i++){
			if(Intersector.overlaps(manRectangle,bombRectangles.get(i))){
				Gdx.app.log("Bomb!","Collision!");
			}
		}

	    batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
