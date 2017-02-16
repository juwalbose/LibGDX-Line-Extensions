package com.juwalbose.lineextensions;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.juwalbose.utils.GraphicsLineRenderer;
import com.juwalbose.utils.ShapeRendererExtended;

public class DrawingScene extends ApplicationAdapter {
	ShapeRendererExtended shapeRenderer;
	Vector2 offset=new Vector2(400,300);
	GraphicsLineRenderer graphicsRenderer;
	public OrthographicCamera camera;
	public Viewport viewport;
	public final int screenWidth=1920;
	public final int screenHeight=1080;
	
	@Override
	public void create () {
		shapeRenderer=new ShapeRendererExtended();
		graphicsRenderer=new GraphicsLineRenderer();
		camera=new OrthographicCamera(screenWidth, screenHeight);
		camera.position.set(screenWidth/2, screenHeight/2, 2);
		viewport = new StretchViewport(screenWidth, screenHeight, camera);
		
		graphicsRenderer.camera=camera;//set the camera for the renderer
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.identity();
		//shapeRenderer.rotate(0, 1, 0, 20);
		shapeRenderer.translate(offset.x, offset.y, 0);
		
		shapeRenderer.begin(ShapeType.Line);
		//arc is line type 
		shapeRenderer.arcNotWedge(50, 0, 100, 0, 360 ,40,Color.BLUE);
		shapeRenderer.arcNotWedge(20, 0, 30, 60, 60 ,40,Color.GREEN);
		shapeRenderer.arcNotWedge(70, 0, 30, 60, 60 ,40,Color.GREEN);
		shapeRenderer.end();
		
		shapeRenderer.begin(ShapeType.Point);
		//dotted line & arc is point type
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.dottedArcNotWedge(50, 100, 140, 240, 60,40);
		shapeRenderer.setColor(Color.GOLD);
		shapeRenderer.dottedLine(-60, 50, 160, 50, 5);
		shapeRenderer.end();
		
		//now let us try the graphics renderer. we need to enable blending
		Gdx.gl.glEnable(GL20.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	    
	    graphicsRenderer.DrawTexturedLine(8, Color.BLUE, new Vector2(1100,300), new Vector2(1300,300));
	   
	    graphicsRenderer.DrawTexturedDottedLine(8, Color.RED, new Vector2(900,450), new Vector2(1500,450));
	    
	    graphicsRenderer.DrawTexturedArc(8, Color.RED, new Vector2(1200,400),200,0,360);
	    
	    graphicsRenderer.DrawTexturedDottedArc(8, Color.GREEN, new Vector2(1200,500),200,-10,200);
	    
	    graphicsRenderer.drawPoint(new Vector2(1100,400),2.0f, Color.CYAN);
	    graphicsRenderer.drawPoint(new Vector2(1300,400),2.0f, Color.CYAN);
	    
	    graphicsRenderer.DrawTexturedLineDot(8, Color.GOLD, new Vector2(800,150), new Vector2(1600,150));
	    
	    graphicsRenderer.DrawTexturedDashedLine(8, Color.GOLDENROD, new Vector2(800,200), new Vector2(1600,200));
	    
	}
	
	@Override
	public void resize (int width, int height) {
		viewport.update(width, height);
	}
	
	@Override
	public void dispose () {
		graphicsRenderer.dispose();
	}
}
