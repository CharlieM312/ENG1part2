package uk.ac.york.student.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
import uk.ac.york.student.utils.Wait;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;

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
    private final Texture clouds = new Texture(Gdx.files.internal("images/CloudsFormatted.png"));
private final Image cloudsImage = new Image(new TextureRegionDrawable(new TextureRegion(clouds)));
private final Texture backgroundTexture = new Texture(Gdx.files.internal("images/MapOverview.png"));
private final boolean cloudsEnabled = ((MainMenuCloudsPreferences) GamePreferences.MAIN_MENU_CLOUDS.getPreference()).isEnabled();
private float cycle = 0;
private final float cloudsSpeed = ((MainMenuCloudsPreferences) GamePreferences.MAIN_MENU_CLOUDS.getPreference()).getSpeed();
private final AtomicReference<Float> alpha = new AtomicReference<>(1f);
private final Texture vignetteTexture = new Texture(Gdx.files.internal("images/Vignette.png"));    
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
        if (shouldFadeIn) {
            processor.getRoot().getColor().a = 0;
            processor.getRoot().addAction(fadeIn(fadeInTime));
        }
        Table table = new Table();
      
        table.setFillParent(true);
        // If debug screen preferences are enabled, set the table to debug mode.
        if (((DebugScreenPreferences) GamePreferences.DEBUG_SCREEN.getPreference()).isEnabled()) {
            table.setDebug(true);
        }
        processor.addActor(table);
        TextButton exitButton = new TextButton("Back", craftacularSkin);
        table.row().pad(750,0,0,0);
        table.add(exitButton).fillX().uniformX();
        exitButton.addListener(new ChangeListener() {
            /**
             * This method is triggered when a change event occurs on the actor, in this case, when the exit button is clicked.
             * It first plays the button click sound.
             * Then, it schedules a task to be executed after a delay of 400 milliseconds.
             * The scheduled task disposes the button click sound and exits the application.
             *
             * @param event The {@link com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent} triggered by the actor. This is not used in the method.
             * @param actor The actor that triggered the {@link com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent}. This is not used in the method.
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundManager.getSounds().get(Sounds.BUTTON_CLICK).play();
                game.transitionScreen(Screens.MAIN_MENU);
            }
        });
         // Declare variables for width and height
         float width;
         float height;
 
         // Get the width and height of the screen
         float ratio = getRatio();
 
         // Calculate the new width and height for the background texture based on the ratio
         width = backgroundTexture.getWidth() * ratio;
         height = backgroundTexture.getHeight() * ratio;
 
         // Set the size of the clouds image to the new width and height
         cloudsImage.setSize(width, height);
    }
    
    /**
     * This method is called by the game's render loop.
     * In the LoadingScreen class, it sets the current screen of the game to the main menu screen.
     * @param v the time in seconds since the last render
     */
    @Override
    public void render(float delta) {
          // Set the clear color to black and clear the screen.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Enable blending and set the blend function to standard alpha blending.
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        // Get the batch from the stage's processor.
        Batch batch = processor.getBatch();
        batch.begin();

        // Calculate the ratio to maintain the aspect ratio of the background texture.
        float ratio = getRatio();

        // Calculate the new width and height for the background texture based on the ratio.
        float width = backgroundTexture.getWidth() * ratio;
        float height = backgroundTexture.getHeight() * ratio;

        // Draw the background texture.
        batch.draw(backgroundTexture, 0, 0, width, height);

        // If clouds are enabled, animate the clouds.
        if (cloudsEnabled) {
            // If the cycle exceeds the width of the screen, reset it to 0.
            // Otherwise, increment the cycle by the speed of the clouds.
            if (cycle > width) {
                cycle = 0;
            } else cycle += cloudsSpeed;

            // Set the position of the clouds image and draw it with respect to the fade out alpha.
            cloudsImage.setPosition(cycle, 0);
            cloudsImage.draw(batch, alpha.get());

            // Set the position of the second clouds image and draw it with respect to the fade out alpha.
            cloudsImage.setPosition(cycle - width, 0);
            cloudsImage.draw(batch, alpha.get());
        }

        // Draw the vignette texture over the entire screen.
        batch.draw(vignetteTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // End the batch.
        batch.end();

        // Update the stage's actors and draw the stage.
        processor.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        processor.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
            dispose();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            Wait.async(1500, TimeUnit.MILLISECONDS)
                .thenRun(() -> Gdx.app.postRunnable(() -> game.setScreen(Screens.GAME)));
        }

    }
    private float getRatio() {
        // Retrieve the width of the screen
        float screenWidth = Gdx.graphics.getWidth();
        // Retrieve the height of the screen
        float screenHeight = Gdx.graphics.getHeight();

        // Calculate the ratio of the screen width to the background texture width
        float widthRatio = screenWidth / backgroundTexture.getWidth();

        // Calculate the ratio of the screen height to the background texture height
        float heightRatio = screenHeight / backgroundTexture.getHeight();

        // Return the maximum ratio to maintain the aspect ratio of the background texture
        return Math.max(widthRatio, heightRatio);
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