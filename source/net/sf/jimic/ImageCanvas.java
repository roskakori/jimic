// Jimic, a minimalistic eComic viewer.
// Copyright (C) 2006 Thomas Aglassinger
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
package net.sf.jimic;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Canvas to draw an image.
 * 
 * @author Thomas Aglassinger.
 */
public class ImageCanvas extends Canvas {
	private Image image;

	private int imageWidth;

	private int imageHeight;

	private Dimension preferredSize;

	private int topLeftX;

	private int topLeftY;

	public ImageCanvas() {
		preferredSize = new Dimension(0, 0);
	}

	public void setImage(Image newImage) {
		image = newImage;
		topLeftX = 0;
		topLeftY = 0;
		imageWidth = image.getWidth(null);
		imageHeight = image.getHeight(null);
		preferredSize = new Dimension(imageWidth, imageHeight);
	}

	public Dimension getPreferredSize() {
		return preferredSize;
	}

	public void setTopLeftDelta(int deltaX, int deltaY) {
		topLeftX += deltaX;
		topLeftY += deltaY;
	}

	public void scrollHome() {
		topLeftX = 0;
		topLeftY = 0;
	}

	public void update(Graphics g) {
		g.setColor(Color.GRAY);
		if (image != null) {
			g.fillRect(0, 0, getWidth(), topLeftY - 1);
			g.fillRect(0, topLeftY - 1, topLeftX - 1, imageHeight);
			g.fillRect(topLeftX + imageWidth, topLeftY - 1, getWidth(),
					imageHeight);
			g.fillRect(0, topLeftY + imageHeight, getWidth(), getHeight());
			g.drawImage(image, topLeftX, topLeftY, imageWidth, imageHeight,
					Color.BLACK, null);
		} else {
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}

	public void paint(Graphics g) {
		update(g);
	}

	/**
	 * Prevent the parent componet from losing the focus if the image is clicked
	 * with the mouse.
	 */
	public boolean isFocusTraversable() {
		return false;
	}
}