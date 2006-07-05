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

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

/**
 * Jimic, a minimalistic eComic viewer geared towards handhelds and cell phones.
 * 
 * @author Thomas Aglassinger.
 */
public class Jimic {
	public static final String VERSION_TAG = "0.5";

	public static final String VERSION_DATE = "2006-07-05";

	private ComicModel comicModel;

	private ComicView comicView;

	private ComicController controller;

	public Jimic() {
		super();
		comicModel = new ComicModel();
		comicView = new ComicView();
		controller = new ComicController(comicView, comicModel);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		comicView.setSize(screenSize);
		comicView.setVisible(true);
	}

	public void open(File comicFile) throws IOException, InterruptedException {
		controller.open(comicFile);
	}

	public ComicModel getComicModel() {
		return comicModel;
	}

	/**
	 * Run Jimic application.
	 */
	public static void main(String[] arguments) throws InterruptedException,
			IOException {
		if (arguments.length <= 1) {
			Jimic jimic = new Jimic();
			if (arguments.length == 1) {
				File comicFile = new File(arguments[0]);
				jimic.open(comicFile);
			}
		} else {
			System.out.println("Jimic " + VERSION_TAG + " (" + VERSION_DATE + ")");
			System.out.println("Usage: java -jar Jomic.jar [/path/to/comic.cbz]");
		}
	}
}
