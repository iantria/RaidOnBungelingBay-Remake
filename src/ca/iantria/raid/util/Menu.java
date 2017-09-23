package ca.iantria.raid.util;

import org.newdawn.slick.Input;

import ca.iantria.raid.Main;
import ca.iantria.raid.interfaces.IMenuListener;

public class Menu {

	public Main main;
	public String[] options;
	public Input input;
	public float x;
	public float y;
	public int selectedIndex;
	public IMenuListener menuListener;
	public boolean selectionMade;

	public Menu(float x, float y, Main main, int selectedIndex,
			IMenuListener menuListener, String[] options) {
		this.x = x;
		this.y = y;
		this.main = main;
		this.selectedIndex = selectedIndex;
		this.menuListener = menuListener;
		this.options = options;
		this.input = main.input;
		input.clearKeyPressedRecord();
	}

	public void update() {
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			if (!selectionMade	&& selectedIndex != options.length - 1) {
				main.menuSound.play();
				selectedIndex++;
			}
		} else if (input.isKeyPressed(Input.KEY_UP)) {
			if (!selectionMade && selectedIndex != 0) {
				main.menuSound.play();
				selectedIndex--;
			}
		} else if (input.isKeyPressed(Input.KEY_ENTER)) {
			if (!selectionMade) {
				main.menuSound.play();
				selectionMade = true;
				if (menuListener != null) {
					menuListener.optionSelected(selectedIndex);
				}
			}
		} else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			if (!selectionMade) {
				main.menuSound.play();
				if (menuListener != null && selectedIndex == (options.length - 1)) {
					menuListener.optionSelected(options.length - 1);
				}
				selectedIndex = options.length -1;
			}
		}

		if (!selectionMade && input.isKeyPressed(Input.KEY_ENTER) ) {
			selectionMade = true;
			main.menuSound.play();
			if (menuListener != null) {
				menuListener.optionSelected(selectedIndex);
			}
		}
	}

	public void render() {
		main.drawer.translateGraphics(x, y);
		for (int i = 0; i < options.length; i++) {
			if (selectedIndex == i) {
				main.ttfMedium.drawString(
						100 - (main.ttfMedium.getWidth(options[i]) / 2),
						30 + (i * 50), options[i]);
				main.menuSelectionImage.draw(
						100 + main.ttfMedium.getWidth(options[i]) / 2 + 20,
						30 + (i * 50), 0.25f);
			} else {
				main.ttfSmall.drawString(
						100 - (main.ttfSmall.getWidth(options[i]) / 2),
						30 + (i * 50), options[i]);
			}
		}
		main.drawer.popGraphics();
	}
}
