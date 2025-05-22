package Controllers.GameControllers;

import Models.GameAssetManager;
import Models.Player;
import Models.enums.Ability;
import Models.enums.GameState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class AbilitySelectionController {

    private final GameController gameController;

    private final Player player;


    public AbilitySelectionController(GameController gameController,Player player) {
        this.gameController = gameController;
        this.player = player;
    }


    public void showAbilitySelection() {
        Array<Ability> abilities = getRandomAbilities(3);

        Table abilityTable = new Table();
        abilityTable.setFillParent(true);
        abilityTable.center().top().padTop(100); // Move table down a bit from top

        for (Ability ability : abilities) {
            Table card = new Table();

            // Title (bold)
            Label title = new Label(ability.getName(), GameAssetManager.getInstance().getSkin(), "default");
            title.setFontScale(1.2f);
            title.setAlignment(Align.center);

            // Description (wrapped)
            Label description = new Label(ability.getDescription(), GameAssetManager.getInstance().getSkin(), "default");
            description.setWrap(true);
            description.setWidth(200);
            description.setAlignment(Align.center);

            // Button
            TextButton chooseButton = new TextButton("Choose", GameAssetManager.getInstance().getSkin());
            chooseButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    player.getAllAbilities().add(ability);
                    player.applyAbility(ability);
                    gameController.getView().getStage().getRoot().removeActor(abilityTable);
                    gameController.setGameState(GameState.PLAYING);
                }
            });

            // Compose card
            card.add(title).width(200).padBottom(10).row();
            card.add(description).size(100).width(200).padBottom(20).row();
            card.add(chooseButton).width(240).height(100).row();

            // Add each card to main table (side by side)
            abilityTable.add(card).pad(50).padTop(200);
        }

        gameController.getView().getStage().addActor(abilityTable);
    }



    public Array<Ability> getRandomAbilities(int count) {
        Array<Ability> all = new Array<>(Ability.values());
        all.shuffle();

        Array<Ability> selected = new Array<>();
        for (int i = 0; i < count && i < all.size; i++) {
            selected.add(all.get(i));
        }
        return selected;
    }

    public void update(){
        player.updateAbilityTimer(Gdx.graphics.getDeltaTime());
    }

}
