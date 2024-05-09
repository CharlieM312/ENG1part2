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
import uk.ac.york.student.ScoreFiles.ScoreFileManager;
import uk.ac.york.student.ScoreFiles.UserData;
import uk.ac.york.student.assets.skins.SkinManager;
import uk.ac.york.student.assets.skins.Skins;
import uk.ac.york.student.audio.music.MusicManager;
import uk.ac.york.student.audio.sound.GameSound;
import uk.ac.york.student.audio.sound.SoundManager;
import uk.ac.york.student.audio.sound.Sounds;
import uk.ac.york.student.settings.*;
import uk.ac.york.student.screens.Screens;

import java.util.ArrayList;
import java.util.function.Supplier;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

public class LeaderBoardScreen extends BaseScreen {
    @Getter
    private final Stage processor;

    private final boolean shouldFadeIn;

    private final float fadeInTime;

    private final Table table = new Table();

    private final GameSound buttonClick = SoundManager.getSupplierSounds().getResult(Sounds.BUTTON_CLICK);

    private final Skin craftacularSkin = SkinManager.getSkins().getResult(Skins.CRAFTACULAR);

    private final Texture stoneWallTexture = new Texture(Gdx.files.internal("images/StoneWall.png"));

    private final Texture bottomUpBlackGradient = new Texture(Gdx.files.internal("images/BottomUpBlackGradient.png"));

    private final ScreenData screenData = new ScreenData();

    private enum Labels {
        BACK_BUTTON("Head Back"),
        CLEAR_BUTTON("Clear"),
        LEADERBOARD_TITLE("Leaderboard"),
        USERNAME_TITLE("Username"),
        SCORE_TITLE("Score"),
        DEGREE_TITLE("Degree"),
        USERNAME("Username123"),
        SCORE("0"),
        DEGREE("First-class Honours");


        private final Supplier<String> label;

        public String getLabel() {
            return label.get();
        }

        public String getLabel(String @NotNull ... placeholders) {
            String localLabel = this.label.get();
            for (int i = 0; i < placeholders.length; i++) {
                localLabel = localLabel.replace("{}" + i + "}", placeholders[i]);
            }
            return localLabel;
        }

        Labels(String label) {
            this.label = () -> label;
        }
    }

    private static class ScreenData {
        private TextButton backButton;
        private TextButton clearButton;
        private Label usernameTitle;
        private Label scoreTitle;
        private Label degreeTitle;
        private Label username;
        private Label score;
        private Label degree;
    }

    public LeaderBoardScreen(GdxGame game) {
        this(game, false);
    }

    public LeaderBoardScreen(GdxGame game, boolean shouldFadeIn) {
        this(game, shouldFadeIn, 0.75f);
    }

    public LeaderBoardScreen(GdxGame game, boolean shouldFadeIn, float fadeInTime) {
        super(game);
        this.shouldFadeIn = shouldFadeIn;
        this.fadeInTime = fadeInTime;
        processor = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(processor);
    }

    public void show() {
        if (shouldFadeIn) {
            processor.getRoot().getColor().a = 0;
            processor.getRoot().addAction(fadeIn(fadeInTime));
        }

        createBackButton();
        createClearButton();
        listenBackButton();
        listenClearButton();
        createUsernameTitle();
        createScoreTitle();
        createDegreeTitle();
        createUsername();
        createScore();
        createDegree();
        setupTable();
    }

    private void createUsernameTitle() {
        screenData.usernameTitle = new Label(Labels.USERNAME_TITLE.getLabel(), craftacularSkin);
    }

    private void createScoreTitle() {
        screenData.scoreTitle = new Label(Labels.SCORE_TITLE.getLabel(), craftacularSkin);
    }

    private void createDegreeTitle() {
        screenData.degreeTitle = new Label(Labels.DEGREE_TITLE.getLabel(), craftacularSkin);
    }

    private void createUsername() {
        screenData.username = new Label(Labels.USERNAME.getLabel(), craftacularSkin);
    }

    private void createDegree() {
        screenData.degree = new Label(Labels.DEGREE.getLabel(), craftacularSkin);
    }

    private void createScore() {
        screenData.score = new Label(Labels.SCORE.getLabel(), craftacularSkin);
    }

    private void createBackButton() {
        screenData.backButton = new TextButton(Labels.BACK_BUTTON.getLabel(), craftacularSkin);
    }

    private void createClearButton() {
        screenData.clearButton = new TextButton(Labels.CLEAR_BUTTON.getLabel(), craftacularSkin);
    }

    private void listenClearButton() {
        TextButton clearButton = screenData.clearButton;
        clearButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundManager.getSounds().get(Sounds.BUTTON_CLICK).play();
                ScoreFileManager.clearLeaderboardFile();
            }
        });
    }

    private void listenBackButton() {
        TextButton backButton = screenData.backButton;
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundManager.getSounds().get(Sounds.BUTTON_CLICK).play();
                game.transitionScreen(Screens.MAIN_MENU);
            }
        });
    }

    private void setupTable() {
        TextButton backButton = screenData.backButton;
        TextButton clearButton = screenData.clearButton;
        Label userNameTitle = screenData.usernameTitle;
        Label scoreTitle = screenData.scoreTitle;
        Label degreeTitle = screenData.degreeTitle;
        Label username = screenData.username;
        Label score = screenData.score;
        Label degree = screenData.degree;

        table.setFillParent(true);

        if (((DebugScreenPreferences) GamePreferences.DEBUG_SCREEN.getPreference()).isEnabled()) {
            table.setDebug(true);
        }

        table.setSkin(craftacularSkin);

        processor.addActor(table);

        Cell<Label> titleCell = table.add(Labels.LEADERBOARD_TITLE.getLabel()).colspan(2).pad(0, 200, 100, 0);
        titleCell.getActor().setFontScale(1.5f);

        table.row();
        table.add(userNameTitle);
        table.add(scoreTitle);
        table.add(degreeTitle);
        table.row();
        
        ArrayList<UserData> leaderboardEntries = ScoreFileManager.getUserLeaderboardEntries();
        for (int i = 0; i < leaderboardEntries.size(); i++) {
            UserData leaderboardEntry = leaderboardEntries.get(i);
            table.add(leaderboardEntry.getUsername());
            table.add(Float.toString(leaderboardEntry.getScore()));
            table.add(leaderboardEntry.getDegree());
            table.row();
        }

        table.row().pad(500, 0, 0, 0);
        table.add(backButton).center();
        table.add(clearButton).center();
        table.row();
    }

    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Batch batch = processor.getBatch();
        batch.begin();

        int width = stoneWallTexture.getWidth() / 6;
        int height = stoneWallTexture.getHeight() / 6;

        for (int x = 0; x < Gdx.graphics.getWidth(); x += width) {
            for (int y = 0; y < Gdx.graphics.getHeight(); y += height) {
                batch.draw(stoneWallTexture, x, y, width, height);
            }
        }

        batch.draw(bottomUpBlackGradient, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.end();

        processor.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        processor.draw();
    }

    public void resize(int width, int height) {
        processor.getViewport().update(width, height, true);
    }

    public void pause() {}

    public void resume() {}

    public void hide() {}

    public void dispose() {
        processor.dispose();
        craftacularSkin.dispose();
        buttonClick.dispose();
    }
}