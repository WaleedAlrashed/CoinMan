package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

public class CoinMan extends ApplicationAdapter {
	SpriteBatch batch;
	//Our Background image
	Texture background;
	//man images
	Texture[] man;
	//get the man state on the screen
	int manState = 0;
	int pause = 0;

	//physics
	float gravity = 0.6f;
	float velocity = 0;
	int manY=0;

	//coin objects
	ArrayList<Integer> coinXs = new ArrayList<Integer>();
	ArrayList<Integer> coinYs = new ArrayList<Integer>();
	Texture coin;
	int coinCount;
	Random random;

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
	}

	public void makeCoin(){
		//randomizing the coin height
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		coinYs.add((int)height);
		coinXs.add(Gdx.graphics.getWidth());

	}
	@Override
	public void render () {
	    batch.begin();
	    //drawing stuff on the screen
	    batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

	    if(coinCount<100){
	    	coinCount++;
		}else{
	    	coinCount=0;
	    	makeCoin();
		}

	    for(int i=0;i<coinXs.size();i++){
	    	batch.draw(coin,coinXs.get(i),coinYs.get(i));
	    	//move the coinX
	    	coinXs.set(i,coinXs.get(i)-4);
		}
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


	    batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
