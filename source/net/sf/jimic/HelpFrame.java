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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Frame to show keyboard help.
 * 
 * @author Thomas Aglassinger
 */
public class HelpFrame extends Frame implements KeyListener, WindowListener {
	private static final String[] HELP_TEXT = new String[] {
			"A - Show dialog with information ",
			" - about the application such as",
			" - version number and copyright.", "Cursor keys - Scroll image.",
			"G or L - Go to image by number.", "O - Open CBZ/ZIP comic file.",
			"Page up, - Go to previous/next image.", "Page down,", "Space",
			"Q or Esc - Quit the application." };

	public HelpFrame() throws HeadlessException {
		super("Keyboard help - Jimic");
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();

		setLayout(layout);
		constraints.anchor = GridBagConstraints.WEST;
		for (int y = 0; y < HELP_TEXT.length; y += 1) {
			String line = HELP_TEXT[y];
			int hyphenIndex = line.indexOf('-');
			String key;
			String description;

			if (hyphenIndex >= 0) {
				key = line.substring(0, hyphenIndex - 1);
				description = line.substring(hyphenIndex + 1);
			} else {
				key = line;
				description = "";
			}
			constraints.gridx = 0;
			constraints.gridy = y;
			add(new Label(key), constraints);
			constraints.gridx = 1;
			add(new Label(description), constraints);
		}
		addWindowListener(this);
		addKeyListener(this);
	}

	public void dispose() {
		removeKeyListener(this);
		removeWindowListener(this);
		super.dispose();
	}

	public void windowOpened(WindowEvent windowEvent) {
		// Do nothing.
	}

	public void windowClosing(WindowEvent windowEvent) {
		dispose();
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
