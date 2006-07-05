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

import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.event.MouseInputListener;

/**
 * Controller to navigate through a ComicModel displaying an image in a
 * ComicView.
 * 
 * @author Thomas Aglassinger.
 */
public class ComicController implements KeyListener, WindowListener,
		MouseInputListener {
	private ComicModel comicModel;

	private ComicView comicView;

	private Point lastMouseDragPoint;

	private boolean mouseDragged;

	public ComicController(ComicView newComicView, ComicModel newComicModel) {
		super();
		comicView = newComicView;
		comicModel = newComicModel;
		comicView.addKeyListener(this);
		comicView.addWindowListener(this);
		comicView.addMouseListener(this);
		comicView.addMouseMotionListener(this);
	}

	public void open(File comicFile) throws IOException, InterruptedException {
		comicModel.openZipFile(comicFile);
		comicView.setComicModel(comicModel);
		comicView.requestFocus();
	}

	private void performGoToImage() throws IOException, InterruptedException {
		GoToImageDialog goToImageDialog = new GoToImageDialog(comicView,
				comicView.getCurrentImageIndex(), comicModel.getImageCount());
		centerDialog(goToImageDialog);
		goToImageDialog.setVisible(true);
		int newImageIndex = goToImageDialog.getImageIndex();
		if (newImageIndex != GoToImageDialog.NO_IMAGE_INDEX) {
			comicView.setCurrentImageIndex(newImageIndex);
		}
	}

	private void performOpen() throws IOException, InterruptedException {
		FilenameFilter comicFilenameFilter = new ComicFilenameFilter();
		FileDialog openDialog = new FileDialog(comicView, "Open comic...",
				FileDialog.LOAD);
		openDialog.setFilenameFilter(comicFilenameFilter);
		openDialog.setVisible(true);
		String comicFilePath = openDialog.getFile();
		if (comicFilePath != null) {
			File comicFile = new File(openDialog.getDirectory(), comicFilePath);
			open(comicFile);
		}
	}

	private void performScrollDown() {
		comicView.scrollDown();
	}

	private void performScrollHome() {
		comicView.scrollHome();
	}

	private void performScrollLeft() {
		comicView.scrollLeft();
	}

	private void performScrollRight() {
		comicView.scrollRight();
	}

	private void performScrollUp() {
		comicView.scrollUp();
	}

	public void keyPressed(KeyEvent keyEvent) {
		// Do nothing.
	}

	public void showError(KeyEvent keyEvent, Throwable error) {
		System.err.println("cannot process key event: " + keyEvent);
		error.printStackTrace();
	}

	public void showError(MouseEvent mouseEvent, Throwable error) {
		System.err.println("cannot process key event: " + mouseEvent);
		error.printStackTrace();
	}

	public void keyReleased(KeyEvent keyEvent) {
		try {
			int keyCode = keyEvent.getKeyCode();
			if (keyCode == KeyEvent.VK_A) {
				performAbout();
			} else if (keyCode == KeyEvent.VK_DOWN) {
				performScrollDown();
			} else if ((keyCode == KeyEvent.VK_G) || (keyCode == KeyEvent.VK_L)) {
				performGoToImage();
			} else if (keyCode == KeyEvent.VK_HOME) {
				performScrollHome();
			} else if (keyCode == KeyEvent.VK_LEFT) {
				performScrollLeft();
			} else if ((keyCode == KeyEvent.VK_PAGE_DOWN)
					|| (keyCode == KeyEvent.VK_SPACE)) {
				performNext();
			} else if (keyCode == KeyEvent.VK_PAGE_UP) {
				performPrevious();
			} else if (keyCode == KeyEvent.VK_O) {
				performOpen();
			} else if ((keyCode == KeyEvent.VK_Q)
					|| (keyCode == KeyEvent.VK_ESCAPE)) {
				performQuit();
			} else if (keyCode == KeyEvent.VK_RIGHT) {
				performScrollRight();
			} else if (keyCode == KeyEvent.VK_UP) {
				performScrollUp();
			} else {
				//	System.out.println("ignoring key released: " + keyEvent);
			}
		} catch (Exception error) {
			showError(keyEvent, error);
		}
	}

	public void keyTyped(KeyEvent keyEvent) {
		// Do nothing.
	}

	private void performAbout() {
		AboutDialog aboutDialog = new AboutDialog(comicView);
		centerDialog(aboutDialog);
		aboutDialog.setVisible(true);
	}

	private void centerDialog(Dialog dialogToCenter) {
		int comicViewWidth = comicView.getWidth();
		int aboutDialogWidth = dialogToCenter.getWidth();
		int indentToCenterX = (comicViewWidth - aboutDialogWidth) / 2;
		int indentToCenterY = (comicView.getHeight() - dialogToCenter
				.getHeight()) / 2;
		Point comicViewLocation = comicView.getLocation();
		dialogToCenter.setLocation(comicViewLocation.x + indentToCenterX,
				comicViewLocation.y + indentToCenterY);
	}

	private void dispose() {
		comicView.removeMouseMotionListener(this);
		comicView.removeMouseListener(this);
		comicView.dispose();
	}

	private void performNext() throws IOException, InterruptedException {
		comicView.goNext();
	}

	private void performPrevious() throws IOException, InterruptedException {
		comicView.goPrevious();
	}

	private void performQuit() {
		// TODO: Use Swing.invokeLater()-alike for JDK 1.1
		dispose();
	}

	public void windowOpened(WindowEvent windowEvent) {
		// Do nothing.
	}

	public void windowClosing(WindowEvent windowEvent) {
		performQuit();
	}

	public void windowClosed(WindowEvent windowEvent) {
		// Do nothing.
	}

	public void windowIconified(WindowEvent windowEvent) {
		// Do nothing.
	}

	public void windowDeiconified(WindowEvent windowEvent) {
		// Do nothing.
	}

	public void windowActivated(WindowEvent windowEvent) {
		comicView.requestFocus();
	}

	public void windowDeactivated(WindowEvent windowEvent) {
		// Do nothing.
	}

	public void mouseDragged(MouseEvent mouseEvent) {
		comicView.scroll(mouseEvent.getX() - lastMouseDragPoint.x, mouseEvent
				.getY()
				- lastMouseDragPoint.y);
		lastMouseDragPoint = mouseEvent.getPoint();
		mouseDragged = true;
	}

	public void mouseMoved(MouseEvent mouseEvent) {
		// Do nothing.
	}

	public void mouseClicked(MouseEvent mouseEvent) {
		// Do nothing.
	}

	public void mousePressed(MouseEvent mouseEvent) {
		lastMouseDragPoint = mouseEvent.getPoint();
		mouseDragged = false;
	}

	public void mouseReleased(MouseEvent mouseEvent) {
		boolean leftButtonReleased = (mouseEvent.getButton() == MouseEvent.BUTTON1);

		if (!mouseDragged && leftButtonReleased) {
			try {
				performNext();
			} catch (Exception error) {
				showError(mouseEvent, error);
			}
		}
		lastMouseDragPoint = null;
		mouseDragged = false;
	}

	public void mouseEntered(MouseEvent mouseEvent) {
		// Do nothing.
	}

	public void mouseExited(MouseEvent mouseEvent) {
		// Do nothing.
	}

}
