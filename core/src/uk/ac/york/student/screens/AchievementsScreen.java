package uk.ac.york.student.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import uk.ac.york.student.GdxGame;
import uk.ac.york.student.assets.skins.SkinManager;
import uk.ac.york.student.assets.skins.Skins;
import uk.ac.york.student.audio.music.MusicManager;
import uk.ac.york.student.audio.sound.GameSound;
import uk.ac.york.student.audio.sound.SoundManager;
import uk.ac.york.student.audio.sound.Sounds;
import uk.ac.york.student.settings.*;

import java.util.function.Supplier;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

/**
 * The LoadingScreen class extends the BaseScreen class and represents the loading screen of the game.
 * It contains a Stage object, which is used to handle input events and draw the elements of the screen.
 * The class overrides the methods of the Screen interface, which are called at different points in the game's lifecycle.
 */
@Getter
public class AchievementsScreen extends BaseScreen {
     /**
     * A flag indicating whether the screen should fade in when shown.
     */
    private final boolean shouldFadeIn;

    /**
     * The time it takes for the screen to fade in, in seconds.
     * This variable is only used if {@link PreferencesScreen#shouldFadeIn} is true.
     */
    private final float fadeInTime;

    /**
     * The Stage instance for the LoadingScreen class.
     * This instance is used to handle input events and draw the elements of the screen.
     */
    private final Stage processor;
    /**
     * The skin used for the UI elements.
     * By default, this is {@link Skins#CRAFTACULAR} from {@link SkinManager}.
     */
    private final Skin craftacularSkin = SkinManager.getSkins().getResult(Skins.CRAFTACULAR);
    /**
     * Constructor for the LoadingScreen class.
     * This constructor initializes the BaseScreen with the provided game and creates a new Stage with a ScreenViewport.
     * @param game the GdxGame instance representing the game
     */
     /**
     * The sound that is played when a button is clicked.
     * By default, this is {@link Sounds#BUTTON_CLICK} from {@link SoundManager}
     */
    private final GameSound buttonClick = SoundManager.getSupplierSounds().getResult(Sounds.BUTTON_CLICK);

    public AchievementsScreen(GdxGame game) {
        this(game, false);
    }
    public AchievementsScreen(GdxGame game, boolean shouldFadeIn) {
        this(game, shouldFadeIn, 0.75f);
    }
    public AchievementsScreen(GdxGame game, boolean shouldFadeIn, float fadeInTime) {
        super(game);
        this.shouldFadeIn = shouldFadeIn;
        this.fadeInTime = fadeInTime;
        processor = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(processor);
    }

    /**
     * This method is called when the screen becomes the current screen for the game.
     * It is empty in the LoadingScreen class.
     */
    @Override
    public void show() {
        game.transitionScreen(Screens.MAIN_MENU);
        dispose();
    }
    
    /**
     * This method is called by the game's render loop.
     * In the LoadingScreen class, it sets the current screen of the game to the main menu screen.
     * @param v the time in seconds since the last render
     */
    @Override
    public void render(float v) {
        
    }

    /**
     * This method is called when the screen should resize itself.
     * It is empty in the LoadingScreen class.
     * @param i the new width in pixels
     * @param i1 the new height in pixels
     */
    @Override
    public void resize(int i, int i1) {

    }

    /**
     * This method is called when the game is paused.
     * It is empty in the LoadingScreen class.
     */
    @Override
    public void pause() {

    }

    /**
     * This method is called when the game is resumed from a paused state.
     * It is empty in the LoadingScreen class.
     */
    @Override
    public void resume() {

    }

    /**
     * This method is called when the screen is no longer the current screen for the game.
     * It is empty in the LoadingScreen class.
     */
    @Override
    public void hide() {

    }

    /**
     * This method is called when the screen should release all resources.
     * It is empty in the LoadingScreen class.
     */
    @Override
    public void dispose() {
        processor.dispose();
        craftacularSkin.dispose();
        buttonClick.dispose();
    }
}