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
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Dialog to show information about the application.
 *
 * @author Thomas Aglassinger
 */
public class AboutDialog extends Dialog implements KeyListener, WindowListener {
	private String[] LINES = new String[] { "Jimic",
			"Version " + Jimic.VERSION_TAG + " (" + Jimic.VERSION_DATE + ")",
			"<http://jimic.sourceforge.net>",
			"Copyright Thomas Aglassinger", "Distributed under the",
			"GNU General Public License" };

	public AboutDialog(Frame parent) {
		super(parent, "About Jimic", true);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();

		setLayout(layout);
		constraints.gridx = 0;
		constraints.gridy = 0;
		for (int lineIndex = 0; lineIndex < LINES.length; lineIndex += 1) {
			Label label = new Label(LINES[lineIndex]);
			label.setAlignment(Label.CENTER);
			constraints.gridy += 1;
			add(label, constraints);
			if (lineIndex == 0) {
				Font titleFont = label.getFont();
				Font newFont = titleFont.deriveFont(Font.BOLD);
				label.setFont(newFont);
			}
		}
		pack();
		setResizable(false);
		addWindowListener(this);
		addKeyListener(this);
	}

	public void windowOpened(WindowEvent e) {
		// Do nothing.
	}

	public void windowClosing(WindowEvent e) {
		removeKeyListener(this);
		removeWindowListener(this);
		dispose();
	}

	public void windowClosed(WindowEvent e) {
		// Do nothing.
	}

	public void windowIconified(WindowEvent e) {
		// Do nothing.
	}

	public void windowDeiconified(WindowEvent e) {
		// Do nothing.
	}

	public void windowActivated(WindowEvent windowEvent) {
		// Do nothing.
	}

	public void windowDeactivated(WindowEvent windowEvent) {
		// Do nothing.
	}

	public void keyTyped(KeyEvent e) {
		// Do nothing.
	}

	public void keyPressed(KeyEvent keyEvent) {
		// Do nothing.
	}

	public void keyReleased(KeyEvent keyEvent) {
		int keyCode = keyEvent.getKeyCode();
		if ((keyCode == KeyEvent.VK_ENTER) || (keyCode == KeyEvent.VK_ESCAPE)) {
			setVisible(false);
		}
	}
}
