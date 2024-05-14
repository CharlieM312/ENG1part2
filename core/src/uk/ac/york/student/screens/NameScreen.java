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
import java.util.jar.Attributes.Name;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;


/**The LeaderboardScreen class extends the BaseScreen class and represents the leaderboard screen of the game.
 * This displays the top 10 scores and the name associated with them.
 */

public class NameScreen extends BaseScreen{
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

    public NameScreen(GdxGame game) {
        this(game, false);
    }

    public NameScreen(GdxGame game, boolean shouldFadeIn) {
        this(game, shouldFadeIn, 0.75f);
    }
    public NameScreen(GdxGame game, boolean shouldFadeIn, float fadeInTime) {
        super(game);
        this.shouldFadeIn = shouldFadeIn;
        this.fadeInTime = fadeInTime;
        processor = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(processor);
        
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public Stage getProcessor() {
        return processor;
    }
    
}
