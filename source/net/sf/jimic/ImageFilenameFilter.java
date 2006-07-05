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

import java.io.File;
import java.io.FilenameFilter;

/**
 * FilenameFilter to accept images processable by Jimic.
 * 
 * @author Thomas Aglassinger
 */
public class ImageFilenameFilter implements FilenameFilter {

	public ImageFilenameFilter() {
		super();
	}

	public boolean accept(File dir, String name) {
		boolean result = false;
		int lastDotIndex = name.lastIndexOf('.');
		if (lastDotIndex != -1) {
			String suffix = name.substring(lastDotIndex + 1).toLowerCase();
			result = suffix.equals("gif") || suffix.equals("jpg")
					|| suffix.equals("jpeg") || suffix.equals("png");
		}
		return result;
	}

}
