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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Dialog to show error message and wait for user to acknowedge it.
 * 
 * @author Thomas Aglassinger.
 */
public class ErrorDialog extends Dialog implements ActionListener, KeyListener,
		WindowListener {

	private Button okButton;

	public ErrorDialog(Frame owner, String message, Throwable error) {
		super(owner, "Jimic error");
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(layout);
		constraints.gridx = 0;
		constraints.gridy = 0;
		Label messageLabel = new Label(message);
		add(messageLabel, constraints);
		constraints.gridy += 1;
		if (error != null) {
			Label errorLabel = new Label(error.getMessage());
			add(errorLabel, constraints);
			constraints.gridy += 1;
		}
		okButton = new Button("Ok");
		add(okButton, constraints);
		okButton.addActionListener(this);
		okButton.addKeyListener(this);
		addWindowListener(this);
		pack();
		setResizable(false);
	}

	public void windowOpened(WindowEvent e) {
		// Do nothing.
	}

	public void windowClosing(WindowEvent e) {
		removeWindowListener(this);
		okButton.removeKeyListener(this);
		okButton.addActionListener(this);
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

	public void actionPerformed(ActionEvent actionEvent) {
		setVisible(false);
	}
}
