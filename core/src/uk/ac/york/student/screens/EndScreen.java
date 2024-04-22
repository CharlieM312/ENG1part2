package uk.ac.york.student.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import uk.ac.york.student.GdxGame;
import uk.ac.york.student.assets.fonts.FontManager;
import uk.ac.york.student.player.Player;
import uk.ac.york.student.player.PlayerMetrics;

@Getter
public class EndScreen extends BaseScreen {
    private final Stage processor;
    private final Player player;
    public EndScreen(GdxGame game) {
        super(game);
        throw new UnsupportedOperationException("This constructor is not supported (must pass in object args!)");
    }
    BitmapFont font = FontManager.getInstance().getFont();
    public EndScreen(GdxGame game, boolean shouldFadeIn, float fadeInTime, Object @NotNull [] args) throws InterruptedException {
        super(game);
        processor = new Stage(new ScreenViewport());
        player = (Player) args[0];
        Batch batch = processor.getBatch();
        PlayerMetrics metrics = player.getMetrics();
        float energyTotal = metrics.getEnergy().getTotal();
        float studyLevelTotal = metrics.getStudyLevel().getTotal();
        float happinessTotal = metrics.getHappiness().getTotal();
        float energyMax = metrics.getEnergy().getMaxTotal();
        float studyLevelMax = metrics.getStudyLevel().getMaxTotal();
        float happinessMax = metrics.getHappiness().getMaxTotal();
        float score = player.calculateScore(energyTotal, energyMax, studyLevelTotal, studyLevelMax, happinessTotal, happinessMax);
        String scoreString = String.valueOf(score);
        batch.begin();
        font.draw(processor.getBatch(), "Your score is " + scoreString, 500, 400);
        font.draw(processor.getBatch(), "Press Enter to exit", 500, 300);
        batch.end();
        System.out.println(score);
        String myscoreString = player.convertScoreToString(score);
        System.out.println(myscoreString);
        Gdx.input.setInputProcessor(processor);
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    Gdx.app.exit();
                }
                return true;
            }
        });
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
}
