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

import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

/**
 * Frame to show a current image taken frome a ComicModel.
 * 
 * @author Thomas Aglassinger.
 */
public class ComicView extends Frame {
	private ImageCanvas imageView;

	private ComicModel comicModel;

	private int currentImageIndex;

	private int scrollNodge;

	public ComicView() throws HeadlessException {
		super("Jimic");
		imageView = new ImageCanvas();
		add(imageView);
		scrollNodge = 12;
	}

	public synchronized void addMouseListener(MouseListener l) {
		imageView.addMouseListener(l);
	}

	public synchronized void addMouseMotionListener(MouseMotionListener l) {
		imageView.addMouseMotionListener(l);
	}

	public synchronized void removeMouseListener(MouseListener l) {
		imageView.removeMouseListener(l);
	}

	public synchronized void removeMouseMotionListener(MouseMotionListener l) {
		imageView.removeMouseMotionListener(l);
	}

	public ImageCanvas getImageView() {
		return imageView;
	}

	public void setComicModel(ComicModel newComicModel) throws IOException,
			InterruptedException {
		comicModel = newComicModel;
		setCurrentImageIndex(0);
	}

	public void attemptToSetCurrentImageIndex(int newIndex) throws IOException,
			InterruptedException {
		if ((newIndex < 0) || (newIndex >= comicModel.getImageCount())) {
			beep();
		} else {
			setCurrentImageIndex(newIndex);
		}
	}

	private void beep() {
		Toolkit.getDefaultToolkit().beep();
	}

	public void setCurrentImageIndex(int newIndex) throws IOException,
			InterruptedException {
		currentImageIndex = newIndex;
		Image currentImage = comicModel.getImage(currentImageIndex);
		imageView.setImage(currentImage);
		imageView.repaint();
		setTitle("" + (currentImageIndex + 1) + "/"
				+ comicModel.getImageCount() + " - "
				+ comicModel.getComicName() + " - Jimic");
	}

	public int getCurrentImageIndex() {
		return currentImageIndex;
	}

	public void goNext() throws IOException, InterruptedException {
		attemptToSetCurrentImageIndex(getCurrentImageIndex() + 1);
	}

	public void goPrevious() throws IOException, InterruptedException {
		attemptToSetCurrentImageIndex(getCurrentImageIndex() - 1);
	}

	public void scroll(int deltaX, int deltaY) {
		imageView.setTopLeftDelta(deltaX, deltaY);
		imageView.repaint();
	}

	private void scrollByNodge(int deltaX, int deltaY) {
		scroll(deltaX * scrollNodge, deltaY * scrollNodge);
	}

	public void scrollDown() {
		scrollByNodge(0, -1);
	}

	public void scrollHome() {
		imageView.scrollHome();
		imageView.repaint();
	}

	public void scrollLeft() {
		scrollByNodge(1, 0);
	}

	public void scrollRight() {
		scrollByNodge(-1, 0);
	}

	public void scrollUp() {
		scrollByNodge(0, 1);
	}
}
