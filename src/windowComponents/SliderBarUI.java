package windowComponents;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 * The {@code SliderBarUI} class provides the ability
 * to control the colors used to draw a {@link javax.swing.JSlider}
 * component. By default, it provides a blue-ish and cyan-ish
 * theme, with an oval-shaped slider thumb.
 * 
 * <p>It can be set via {@link javax.swing.JSlider}'s {@code setUI} function.
 * 
 * <p>It implements the {@link javax.swing.plaf.basic.BasicSliderUI} functionality.</p>
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
public class SliderBarUI extends BasicSliderUI {
	/**
	 * The main background color of the slider's track.
	 */
	private Color idleColor;
	
	/**
	 * This color will be used alongside {@link #activeColorEnd} to
	 * create a {@link java.awt.GradientPaint} for the slider's track.
	 * This color specifies the start point for the gradient. The gradient
	 * only paints the "active" part (left of the thumb).
	 */
	private Color activeColorStart;
	
	/**
	 * This color will be used alongside {@link #activeColorEnd} to
	 * create a {@link java.awt.GradientPaint} for the slider's track.
	 * This color specifies the end point for the gradient. The gradient
	 * only paints the "active" part (left of the thumb).
	 */
	private Color activeColorEnd;
	
	/**
	 * The color of the slider's thumb.
	 */
	private Color thumbColor;
	
	/**
	 * This constructor constructs a slider component UI by specifying the
	 * slider to paint and the colors to use to paint it.
	 * 
	 * <p>Both {@code activeColorStart} and {@code activeColorEnd} will be used
	 * to create a {@link java.awt.GradientPaint} for the slider's track.
	 * These colors specify the start and end points for the gradient. The
	 * gradient only paints the "active" part (left of the thumb).</p>
	 * 
	 * @param sliderBar  The slider to paint.
	 * @param idleColor  The color of the slider's background.
	 * @param activeColorStart  The start point of the gradient paint for the "active" part in
	 * the slider's track.
	 * @param activeColorEnd  The end point of the gradient paint for the "active" part in
	 * the slider's track.
	 * @param thumbColor  The color of the slider's thumb.
	 */
	public SliderBarUI(JSlider sliderBar, Color idleColor, Color activeColorStart, Color activeColorEnd,
			Color thumbColor) {
		super(sliderBar);
		this.idleColor = idleColor;
		this.activeColorStart = activeColorStart;
		this.activeColorEnd = activeColorEnd;
		this.thumbColor = thumbColor;
	}
	
	/**
	 * This constructor constructs a slider component UI by specifying the
	 * slider to paint. Default color values will be used.
	 * 
	 * @param sliderBar  The slider to paint.
	 */
	public SliderBarUI(JSlider sliderBar) {
		super(sliderBar);
		this.idleColor = new Color(200, 200, 200);
		this.activeColorStart = new Color(0, 0, 139);
		this.activeColorEnd = new Color(0, 255, 255);
		this.thumbColor = new Color(64, 224, 208);
	}
	
	@Override
	public void paintTrack(Graphics g) {
		Graphics2D painter = (Graphics2D) g.create();
		// Improves the drawing of curves and diagnoal lines.
		painter.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Thickness of the track.
		int arc = 8;
		// Dimensions of the track.
		int x = trackRect.x;
		int y = trackRect.y + (trackRect.height - arc) / 2;
		int width = trackRect.width;
		// The thumb's center (only the active part is drawn using activeColor).
		int thumbCenter = thumbRect.x + thumbRect.width / 2;
		
		// Painting the background.
		painter.setColor(idleColor);
		painter.fillRoundRect(x, y, width, arc, arc, arc);
		
		// Painting the active part with a gradient paint.
		GradientPaint gpaint = new GradientPaint(
			x, y, activeColorStart,
			x + width, y, activeColorEnd
		);
		painter.setPaint(gpaint);
		painter.fillRoundRect(x, y, thumbCenter - x, arc, arc, arc);
		painter.dispose();
	}
	
	@Override
	public void paintThumb(Graphics g) {
		Graphics2D painter = (Graphics2D) g.create();
		// Improves the drawing of curves and diagnoal lines.
		painter.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Dimensions of the thumb.
		int size = 16;
		int x = thumbRect.x + (thumbRect.width - size) / 2;
		int y = thumbRect.y + (thumbRect.height - size) / 2;
		
		// Painting the thumb.
		painter.setColor(thumbColor);
		painter.fillOval(x, y, size, size);
		painter.dispose();
	}
	
	@Override
	public TrackListener createTrackListener(JSlider slider) {
		return new TrackListener() {
			@Override
			public void mousePressed(MouseEvent e) {}
		};
	}
	
}
