package Controllers;

import Views.PreGameMenu;

public class PreGameMenuController {
    private PreGameMenu view;

    public void setView(PreGameMenu view) {
        this.view = view;
    }

    public void handleStartGame(String selectedHero, String selectedWeapon, String selectedDuration) {
        // Parse duration (remove " Minutes" text)
        int duration = Integer.parseInt(selectedDuration.split(" ")[0]);

        // Start game with selected options
        System.out.println("Starting game with:");
        System.out.println("Hero: " + selectedHero);
        System.out.println("Weapon: " + selectedWeapon);
        System.out.println("Duration: " + duration + " minutes");

        // Add your game start logic here
        // You might want to pass these to your game screen
    }
}
