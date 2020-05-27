package com.mygdx.shortcut;

import com.badlogic.gdx.Game;

public class ShortcutGame extends Game {
	
	@Override
	public void create () {
		showMainMenuScreen();
	}

	public void showMainMenuScreen() {
		setScreen(new MainMenuScreen(this));
	}
	public void showGamePlayScreen() {
		setScreen(new GameplayScreen(this));
	}
	public void showInfoScreen() {
		setScreen(new InfoScreen(this));
	}
}
