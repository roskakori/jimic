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

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Dialog to select the number of an image to go to.
 * 
 * @author Thomas Aglassinger
 */
public class GoToImageDialog extends Dialog implements WindowListener,
		ActionListener, KeyListener {
	/** Index indicating that no image has been entered. */
	public static final int NO_IMAGE_INDEX = -1;

	private int imageIndex;

	private int imageCount;

	private TextField imageIndexField;

	private Button okButton;

	public GoToImageDialog(Frame parent, int defaultImageIndex,
			int newImageCount) {
		super(parent, "Go to image", true);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		Label label = new Label("Go to image (1.." + newImageCount + "):");
		String defaultImageIndexText = Integer.toString(defaultImageIndex);

		okButton = new Button("Ok");
		okButton.addActionListener(this);
		imageIndexField = new TextField(defaultImageIndexText, 6);
		imageIndexField.addKeyListener(this);
		imageIndex = NO_IMAGE_INDEX;
		imageCount = newImageCount;
		setLayout(layout);
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(label, constraints);
		constraints.gridy += 1;
		add(imageIndexField, constraints);
		constraints.gridy += 1;
		add(okButton, constraints);
		pack();
		setResizable(false);
		addWindowListener(this);
	}

	public int getImageIndex() {
		return imageIndex;
	}

	public void windowOpened(WindowEvent windowEvent) {
		imageIndexField.requestFocus();
	}

	public void windowClosing(WindowEvent windowEvent) {
		dispose();
	}

	public void dispose() {
		if (imageIndexField != null) {
			imageIndexField.removeKeyListener(this);
		}
		if (okButton != null) {
			okButton.removeActionListener(this);
		}
		removeWindowListener(this);
		super.dispose();
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
		// Do nothing.
	}

	public void windowDeactivated(WindowEvent windowEvent) {
		// Do nothing.
	}

	public void actionPerformed(ActionEvent actionEvent) {
		performOk();
	}

	private void performOk() {
		String imageIndexText = imageIndexField.getText();
		try {
			int newImageIndex = Integer.parseInt(imageIndexText);
			if ((newImageIndex < 1) || (newImageIndex > imageCount)) {
				throw new IllegalArgumentException("image index is "
						+ newImageIndex + " but must be between 1 and "
						+ imageCount);
			}
			imageIndex = newImageIndex - 1;
			setVisible(false);
		} catch (Exception error) {
			Toolkit.getDefaultToolkit().beep();
			System.err.println("cannot go to image: " + imageIndexText);
			error.printStackTrace();
		}
	}

	public void keyTyped(KeyEvent e) {
		// Do nothing.
	}

	public void keyPressed(KeyEvent keyEvent) {
		// Do nothing.
	}

	public void keyReleased(KeyEvent keyEvent) {
		int keyCode = keyEvent.getKeyCode();
		if (keyCode == KeyEvent.VK_ENTER) {
			performOk();
		} else if (keyCode == KeyEvent.VK_ESCAPE) {
			setVisible(false);
		}
	}
}
