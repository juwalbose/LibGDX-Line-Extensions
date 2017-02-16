package com.juwalbose.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class ShapeRendererExtended extends ShapeRenderer {
	private final ImmediateModeRenderer renderer;
	
	public ShapeRendererExtended() {
		renderer = super.getRenderer();
	}
	
	/**
	 * Normal ShapeRenderer draws a Wedge not a arc. Segments determine how many lines are drawn to approximate the arc
	 * @param x
	 * @param y
	 * @param radius
	 * @param start
	 * @param degrees
	 * @param segments
	 * @param color
	 */
	public void arcNotWedge (float x, float y, float radius, float start, float degrees, int segments, Color color) {
		if (segments <= 0) throw new IllegalArgumentException("segments must be > 0.");
		float colorBits = color.toFloatBits();
		float theta = (2 * MathUtils.PI * (degrees / 360.0f)) / segments;
		float cos = MathUtils.cos(theta);
		float sin = MathUtils.sin(theta);
		float cx = radius * MathUtils.cos(start * MathUtils.degreesToRadians);
		float cy = radius * MathUtils.sin(start * MathUtils.degreesToRadians);
			for (int i = 0; i < segments; i++) {
				renderer.color(colorBits);
				renderer.vertex(x + cx, y + cy, 0);
				float temp = cx;
				cx = cos * cx - sin * cy;
				cy = sin * temp + cos * cy;
				renderer.color(colorBits);
				renderer.vertex(x + cx, y + cy, 0);
			}
	}
	
	/**
	 * Draws dotted line. 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param dotDist is the distance between individual dots eg, 5
	 */
	public void dottedLine(float x1, float y1, float x2, float y2, float dotDist) {
	    //shapetype needs to be point;
	    Vector2 vec2 = new Vector2(x2, y2).sub(new Vector2(x1, y1));
	    float length = vec2.len();
	    for(float i = 0; i < length; i += dotDist) {
	        vec2.clamp(length - i, length - i);
	        super.point(x1 + vec2.x, y1 + vec2.y, 0);
	    }
	}
	
	/**
	 * Draws dotted arc
	 * @param x
	 * @param y
	 * @param radius
	 * @param start start angle of arc
	 * @param degrees degrees of the arc
	 * @param dotsInPi how many dots will there be in 90 degrees
	 */
	public void dottedArcNotWedge (float x, float y, float radius, float start, float degrees, int dotsInPi) {
		if (dotsInPi <= 0) throw new IllegalArgumentException("dotsInPi must be > 0.");
		float dots=degrees*dotsInPi/90.0f;
		float theta = (2 * MathUtils.PI * (degrees / 360.0f)) / dots;
		float cos = MathUtils.cos(theta);
		float sin = MathUtils.sin(theta);
		float cx = radius * MathUtils.cos(start * MathUtils.degreesToRadians);
		float cy = radius * MathUtils.sin(start * MathUtils.degreesToRadians);
		for (int i = 0; i < dots; i++) {
					super.point(x + cx, y + cy, 0);
					float temp = cx;
					cx = cos * cx - sin * cy;
					cy = sin * temp + cos * cy;
					super.point(x + cx, y + cy, 0);
		}
	}
}
