/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package menuscript;

import java.util.HashMap;
import menu.Coord;
import menu.MENUText_field;
import menu.MENUsimplebutton_field;
import menu.TextureFrame;

public class PersonSelector {
	static TextureFrame s_modelTexframe;
	static TextureFrame s_driverTexframe;
	static TextureFrame s_trailersTexframe;
	static HashMap s_Faces;
	static HashMap s_Trailers;
	static int s_counter;

	public PersonSelector() {
		if (s_counter == 0) {
			FillMap();
		}

		s_counter += 1;
	}

	public void ApplyModelToPicture(MENUText_field pic, String modelname) {
		Coord coord = (Coord) s_Faces.get(modelname);
		if (coord != null)
			s_modelTexframe.ApplyToPicture(pic, coord.x, coord.y);
		else
			s_modelTexframe.ApplyToPicture(pic, 2, 2);
	}

	public void ApplyDriverToPicture(MENUText_field pic, int faceindex,
			int gender) {
		int index = faceindex + ((gender == 1) ? 20 : 0);
		s_driverTexframe.ApplyToPicture(pic, index % 8, index / 8 + 3);
	}

	public void ApplyTrailerToPatch(MENUsimplebutton_field button, int index) {
		Coord c = (Coord) s_Trailers.get(new Integer(index));
		if (c == null) {
			return;
		}
		s_trailersTexframe
				.ApplyToPatch(button.nativePointer, c.x, c.y, "photo");
	}

	private void FillMap() {
		s_modelTexframe = new TextureFrame();
		s_modelTexframe.Init(4, 4, 512, 512);
		s_driverTexframe = new TextureFrame();
		s_driverTexframe.Init(8, 8, 512, 512);
		s_trailersTexframe = new TextureFrame();
		s_trailersTexframe.Init(4, 5, 512, 512);

		s_Faces = new HashMap();
		s_Faces.put("SECRETARY", new Coord(0, 0));
		s_Faces.put("IVAN_NEW", new Coord(1, 0));
		s_Faces.put("MATTHEW", new Coord(2, 0));

		s_Trailers = new HashMap();
		s_Trailers.put(new Integer(0), new Coord(1, 0));
		s_Trailers.put(new Integer(1), new Coord(2, 2));
		s_Trailers.put(new Integer(2), new Coord(0, 0));
		s_Trailers.put(new Integer(3), new Coord(1, 0));
		s_Trailers.put(new Integer(4), new Coord(1, 2));

		s_Trailers.put(new Integer(5), new Coord(3, 0));
		s_Trailers.put(new Integer(6), new Coord(0, 1));
		s_Trailers.put(new Integer(7), new Coord(0, 1));

		s_Trailers.put(new Integer(8), new Coord(0, 3));
		s_Trailers.put(new Integer(9), new Coord(1, 3));

		s_Trailers.put(new Integer(10), new Coord(2, 3));
		s_Trailers.put(new Integer(11), new Coord(2, 3));
		s_Trailers.put(new Integer(12), new Coord(2, 3));
		s_Trailers.put(new Integer(13), new Coord(2, 3));
		s_Trailers.put(new Integer(14), new Coord(2, 3));
		s_Trailers.put(new Integer(15), new Coord(2, 3));
	}

	protected void finalize() throws Throwable {
		super.finalize();
		s_counter -= 1;
		if (s_counter != 0)
			return;
		s_modelTexframe = null;
		s_Faces = null;
	}
}