package com.juwalbose.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class GraphicsLineRenderer {
	Texture whiteSquare;
	Texture whiteCircle8;
	Texture lineDot8;
	Texture dashedLine8;
	public ImmediateModeRenderer20 renderer;
	public OrthographicCamera camera;
	
	public GraphicsLineRenderer() {
		dashedLine8 = new Texture("dashedline.png");
		lineDot8 = new Texture("linedot.png");
		whiteSquare = new Texture("whitesquare.png");
		whiteCircle8 = new Texture("whitecircle8x8.png");
		whiteSquare.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		lineDot8.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		lineDot8.setWrap(TextureWrap.Repeat, TextureWrap.ClampToEdge);
		dashedLine8.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		dashedLine8.setWrap(TextureWrap.Repeat, TextureWrap.ClampToEdge);
		whiteCircle8.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		whiteCircle8.setWrap(TextureWrap.Repeat, TextureWrap.ClampToEdge);
		
		renderer= new ImmediateModeRenderer20(false, true, 1);
	}

	/**
	 * @param thickness
	 * @param c
	 * @param centre
	 * @param radius
	 * @param startAngle
	 * @param arcAngle
	 */
	public void DrawTexturedArc(float thickness, Color c, Vector2 centre, float radius, float startAngle, float arcAngle){
		renderer.begin(camera.combined,GL20.GL_TRIANGLE_STRIP);
		whiteSquare.bind();
		float endAngle=startAngle+arcAngle;
		float colBits = c.toFloatBits();
		float cx=centre.x;
		float cy=centre.y;
		int segs = (int)(12 * Math.cbrt(radius));
	    float halfThick = thickness*0.5f;
	    float step = 360f / segs;
	    for (float angle=startAngle; angle<(endAngle+step); angle+=step) {
	         float tc = 0.5f;
	         if (angle==startAngle)
	            tc = 0f;
	         else if (angle>=endAngle)
	            tc = 1f;
	         
	         float fx = MathUtils.cosDeg(angle);
	         float fy = MathUtils.sinDeg(angle);
	         
	         float z = 0f;
	         renderer.color(colBits);
	         renderer.texCoord(tc, 1f);
	         renderer.vertex(cx + fx * (radius + halfThick), cy + fy * (radius + halfThick), z);
	         
	         renderer.color(colBits);
	         renderer.texCoord(tc, 0f);
	         renderer.vertex(cx + fx * (radius + -halfThick), cy + fy * (radius + -halfThick), z);
	      }
	    renderer.end();
	}
	
	/**
	 * Thickness closer to 8 gives better drawing. For bigger thickness we may need to use bigger images as texture.
	 * Check out the 16x16 & 64x64 textures provided. Bind & use them based on thickness
	 * @param thickness
	 * @param c
	 * @param centre
	 * @param radius
	 * @param startAngle
	 * @param arcAngle
	 */
	public void DrawTexturedDottedArc(float thickness, Color c, Vector2 centre, float radius, float startAngle, float arcAngle){
		renderer.begin(camera.combined,GL20.GL_TRIANGLE_STRIP);
		whiteCircle8.bind();
		float endAngle=startAngle+arcAngle;
		float circumference=(float) (2.0f*Math.PI*radius);
		float repeats= circumference/thickness;
		float colBits = c.toFloatBits();
		float cx=centre.x;
		float cy=centre.y;
		int segs = (int)(12 * Math.cbrt(radius));
	    float halfThick = thickness*0.5f;
	    float step = 360f / segs;
	    for (float angle=startAngle; angle<(endAngle+step); angle+=step) {
	         float tc = (angle/360.0f)*repeats;
	         
	         float fx = MathUtils.cosDeg(angle);
	         float fy = MathUtils.sinDeg(angle);
	         
	         float z = 0f;
	         renderer.color(colBits);
	         renderer.texCoord(tc, 1f);
	         renderer.vertex(cx + fx * (radius + halfThick), cy + fy * (radius + halfThick), z);
	         
	         renderer.color(colBits);
	         renderer.texCoord(tc, 0f);
	         renderer.vertex(cx + fx * (radius + -halfThick), cy + fy * (radius + -halfThick), z);
	      }
	    renderer.end();
	}
	
	/**
	 * @param thickness
	 * @param c
	 * @param start
	 * @param end
	 */
	public void DrawTexturedLine(float thickness, Color c, Vector2 start, Vector2 end){
		renderer.begin(camera.combined,GL20.GL_TRIANGLE_STRIP);
		whiteSquare.bind();
		Vector2 tmp=new Vector2();
		Vector2 t = tmp.set(end.y - start.y, start.x - end.x).nor();
		thickness *= 0.5f;
		float tx = t.x * thickness;
		float ty = t.y * thickness;
		float colBits = c.toFloatBits();
		
		renderer.color(colBits);
		renderer.texCoord(0, 1f);
		renderer.vertex(start.x + tx, start.y + ty, 0);
		renderer.color(colBits);
		renderer.texCoord(0, 0);
		renderer.vertex(start.x - tx, start.y - ty, 0);

		renderer.color(colBits);
		renderer.texCoord(1, 1f);
		renderer.vertex(end.x + tx, end.y + ty, 0);
		renderer.color(colBits);
		renderer.texCoord(1, 0);
		renderer.vertex(end.x - tx, end.y - ty, 0);

		renderer.color(colBits);
		renderer.texCoord(1, 1f);
		renderer.vertex(end.x + tx, end.y + ty, 0);
		renderer.color(colBits);
		renderer.texCoord(0, 1f);
		renderer.vertex(start.x + tx, start.y + ty, 0);

		renderer.color(colBits);
		renderer.texCoord(1, 0);
		renderer.vertex(end.x - tx, end.y - ty, 0);
		renderer.color(colBits);
		renderer.texCoord(0, 0);
		renderer.vertex(start.x - tx, start.y - ty, 0);
		renderer.end();
	}
	
	/**
	 * @param thickness
	 * @param c
	 * @param start
	 * @param end
	 */
	public void DrawTexturedDottedLine(float thickness, Color c, Vector2 start, Vector2 end){
		renderer.begin(camera.combined,GL20.GL_TRIANGLE_STRIP);
		whiteCircle8.bind();
		float distance= Vector2.dst(start.x, start.y, end.x, end.y);
		float repeats= distance/thickness;
		Vector2 tmp=new Vector2();
		Vector2 t = tmp.set(end.y - start.y, start.x - end.x).nor();
		thickness *= 0.5f;
		float tx = t.x * thickness;
		float ty = t.y * thickness;
		float colBits = c.toFloatBits();
		
		renderer.color(colBits);
		renderer.texCoord(0, 1f);
		renderer.vertex(start.x + tx, start.y + ty, 0);
		renderer.color(colBits);
		renderer.texCoord(0, 0);
		renderer.vertex(start.x - tx, start.y - ty, 0);

		renderer.color(colBits);
		renderer.texCoord(repeats, 1f);
		renderer.vertex(end.x + tx, end.y + ty, 0);
		renderer.color(colBits);
		renderer.texCoord(repeats, 0);
		renderer.vertex(end.x - tx, end.y - ty, 0);

		renderer.color(colBits);
		renderer.texCoord(repeats, 1f);
		renderer.vertex(end.x + tx, end.y + ty, 0);
		renderer.color(colBits);
		renderer.texCoord(0, 1f);
		renderer.vertex(start.x + tx, start.y + ty, 0);

		renderer.color(colBits);
		renderer.texCoord(repeats, 0);
		renderer.vertex(end.x - tx, end.y - ty, 0);
		renderer.color(colBits);
		renderer.texCoord(0, 0);
		renderer.vertex(start.x - tx, start.y - ty, 0);
		renderer.end();
	}
	
	/**
	 * @param thickness
	 * @param c
	 * @param start
	 * @param end
	 */
	public void DrawTexturedDashedLine(float thickness, Color c, Vector2 start, Vector2 end){
		dashedLine8.bind();
		DrawRepeatedTexture(thickness, c, start, end);
	}
	
	/**
	 * @param thickness
	 * @param c
	 * @param start
	 * @param end
	 */
	public void DrawTexturedLineDot(float thickness, Color c, Vector2 start, Vector2 end){
		lineDot8.bind();
		DrawRepeatedTexture(thickness, c, start, end);
	}
	
	/**
	 * This can be used to draw any repeating texture which is 64x8. Currently used for the dashed line & line dot
	 * @param thickness
	 * @param c
	 * @param start
	 * @param end
	 */
	private void DrawRepeatedTexture(float thickness, Color c, Vector2 start, Vector2 end){
		renderer.begin(camera.combined,GL20.GL_TRIANGLE_STRIP);
		float distance= Vector2.dst(start.x, start.y, end.x, end.y);
		float newLength=64*thickness/8;
		float repeats= distance/newLength;
		Vector2 tmp=new Vector2();
		Vector2 t = tmp.set(end.y - start.y, start.x - end.x).nor();
		thickness *= 0.5f;
		float tx = t.x * thickness;
		float ty = t.y * thickness;
		float colBits = c.toFloatBits();
		
		renderer.color(colBits);
		renderer.texCoord(0, 1f);
		renderer.vertex(start.x + tx, start.y + ty, 0);
		renderer.color(colBits);
		renderer.texCoord(0, 0);
		renderer.vertex(start.x - tx, start.y - ty, 0);

		renderer.color(colBits);
		renderer.texCoord(repeats, 1f);
		renderer.vertex(end.x + tx, end.y + ty, 0);
		renderer.color(colBits);
		renderer.texCoord(repeats, 0);
		renderer.vertex(end.x - tx, end.y - ty, 0);

		renderer.color(colBits);
		renderer.texCoord(repeats, 1f);
		renderer.vertex(end.x + tx, end.y + ty, 0);
		renderer.color(colBits);
		renderer.texCoord(0, 1f);
		renderer.vertex(start.x + tx, start.y + ty, 0);

		renderer.color(colBits);
		renderer.texCoord(repeats, 0);
		renderer.vertex(end.x - tx, end.y - ty, 0);
		renderer.color(colBits);
		renderer.texCoord(0, 0);
		renderer.vertex(start.x - tx, start.y - ty, 0);
		renderer.end();
	}
	
	/**
	 * Draws a x cross
	 * @param startPosition
	 * @param pointSize
	 * @param actionColor
	 */
	public void drawPoint(Vector2 startPosition, float pointSize,Color actionColor) {
		pointSize*=10;
		Vector2 startPos=new Vector2();
		startPos.x=startPosition.x-pointSize;
		startPos.y=startPosition.y-pointSize;
		Vector2 endPos=new Vector2();
		endPos.x=startPosition.x+pointSize;
		endPos.y=startPosition.y+pointSize;
		DrawTexturedLine(3, actionColor, startPos, endPos);
		Vector2 startPos1=new Vector2();
		startPos1.x=startPosition.x-pointSize;
		startPos1.y=startPosition.y+pointSize;
		Vector2 endPos1=new Vector2();
		endPos1.x=startPosition.x+pointSize;
		endPos1.y=startPosition.y-pointSize;
		DrawTexturedLine(3, actionColor, startPos1, endPos1);
	}

	public void dispose() {
		renderer.dispose();
	}
}
