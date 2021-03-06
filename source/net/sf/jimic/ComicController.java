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

import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Controller to navigate through a ComicModel displaying an image in a
 * ComicView.
 * 
 * @author Thomas Aglassinger.
 */
public class ComicController implements KeyListener, WindowListener,
		MouseListener, MouseMotionListener {
	private ComicModel comicModel;

	private ComicView comicView;

	private Point lastMouseDragPoint;

	private boolean mouseDragged;

	private Frame helpFrame;

	private AboutDialog aboutDialog;

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
		try {
			comicView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			comicModel.openZipFile(comicFile);
			comicView.setComicModel(comicModel);
			comicView.requestFocus();
		} finally {
			comicView.setCursor(Cursor.getDefaultCursor());
		}
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

	private void performOpen() {
		FilenameFilter comicFilenameFilter = new ComicFilenameFilter();
		FileDialog openDialog = new FileDialog(comicView, "Open comic...",
				FileDialog.LOAD);
		openDialog.setFilenameFilter(comicFilenameFilter);
		openDialog.setVisible(true);
		String comicFilePath = openDialog.getFile();
		if (comicFilePath != null) {
			File comicFile = new File(openDialog.getDirectory(), comicFilePath);
			try {
				open(comicFile);
			} catch (Exception error) {
				showOpenError(comicFile, error);
			}
		}
	}

	public void showOpenError(File comicFile, Exception error) {
		showError("cannot open comic: " + comicFile.getAbsolutePath(), error);
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
		showError("cannot process key event: " + keyEvent, error);
	}

	public void showError(String message, Throwable error) {
		System.err.println("error: " + message);
		if (error != null) {
			error.printStackTrace();
		}
		Dialog errorDialog = new ErrorDialog(comicView, message, error);
		centerDialog(errorDialog);
		errorDialog.setVisible(true);
	}

	public void showError(MouseEvent mouseEvent, Throwable error) {
		showError("cannot process mouse event: " + mouseEvent, error);
	}

	public void keyReleased(KeyEvent keyEvent) {
		try {
			int keyCode = keyEvent.getKeyCode();
			if (keyCode == KeyEvent.VK_A) {
				performAbout();
			} else if (keyCode == KeyEvent.VK_DOWN) {
				performScrollDown();
			} else if (keyCode == KeyEvent.VK_F1) {
				performHelp();
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
				// System.out.println("ignoring key released: " + keyEvent);
			}
		} catch (Exception error) {
			showError(keyEvent, error);
		}
	}

	public void keyTyped(KeyEvent keyEvent) {
		// Do nothing.
	}

	private synchronized void performAbout() {
		if (aboutDialog == null) {
			aboutDialog = new AboutDialog(comicView);
			centerDialog(aboutDialog);
		}
		aboutDialog.setVisible(true);
	}

	private synchronized void performHelp() {
		if (helpFrame == null) {
			helpFrame = new HelpFrame();
			helpFrame.pack();
			helpFrame.setResizable(false);
		}
		helpFrame.setVisible(true);
	}

	private void centerDialog(Dialog dialogToCenter) {
		Dimension comicViewSize = comicView.getSize();
		Dimension dialogSize = dialogToCenter.getSize();
		int comicViewWidth = comicViewSize.width;
		int dialogWidth = dialogSize.width;
		int comicViewHeight = comicViewSize.height;
		int dialogHeight = dialogSize.height;
		int indentToCenterX = (comicViewWidth - dialogWidth) / 2;
		int indentToCenterY = (comicViewHeight - dialogHeight) / 2;
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
		System.exit(0);
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
		if (lastMouseDragPoint != null) {
			comicView.scroll(mouseEvent.getX() - lastMouseDragPoint.x,
					mouseEvent.getY() - lastMouseDragPoint.y);
			if (!mouseDragged) {
				comicView.setCursor(Cursor
						.getPredefinedCursor(Cursor.MOVE_CURSOR));
			}
			mouseDragged = true;
		} else {
			System.err
					.println("warning: ignoring drag because lastMouseDragPoint = null");
		}
		lastMouseDragPoint = mouseEvent.getPoint();
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

	/**
	 * Yield
	 * <code>true<code> if the <code>mouseEvent</code> involves the left mouse button.
	 */
	private boolean involvesLeftButton(MouseEvent mouseEvent) {
		return (mouseEvent.getModifiers() & MouseEvent.BUTTON1_MASK) != 0;
	}

	public void mouseReleased(MouseEvent mouseEvent) {
		boolean leftButtonReleased = involvesLeftButton(mouseEvent);

		if (!mouseDragged && leftButtonReleased && (comicModel != null)) {
			try {
				performNext();
			} catch (Exception error) {
				showError(mouseEvent, error);
			}
		}
		lastMouseDragPoint = null;
		mouseDragged = false;
		comicView.setCursor(Cursor.getDefaultCursor());
	}

	public void mouseEntered(MouseEvent mouseEvent) {
		// Do nothing.
	}

	public void mouseExited(MouseEvent mouseEvent) {
		// Do nothing.
	}

}
