package uk.ac.york.student.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Null;
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
import uk.ac.york.student.screens.Screens;
import java.io.*;
import uk.ac.york.student.ScoreFiles.ScoreFileManager;


import java.util.function.Supplier;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

public class UserCreationScreen extends BaseScreen {
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
        ENTER_USERNAME_LABEL("Name:"),
        USERNAME_INPUT(""),
        BACK_BUTTON("Head Back"),
        START_BUTTON("Start");


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
        private Label enterUsernameLabel;
        private TextField usernameInput;
        private TextButton backButton;
        private TextButton startButton;
    }

    public UserCreationScreen(GdxGame game) {
        this(game, false);
    }

    public UserCreationScreen(GdxGame game, boolean shouldFadeIn) {
        this(game, shouldFadeIn, 0.75f);
    }

    public UserCreationScreen(GdxGame game, boolean shouldFadeIn, float fadeInTime) {
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

        createEnterUsernameLabel();
        createUsernameInput();
        createBackButton();
        createStartButton();
        listenBackButton();
        listenStartButton();
        listenTextField();
        setupTable();
    }

    private void createEnterUsernameLabel() {
        screenData.enterUsernameLabel = new Label(Labels.ENTER_USERNAME_LABEL.getLabel(), craftacularSkin);
    }

    private void createUsernameInput() {
        screenData.usernameInput = new TextField(Labels.USERNAME_INPUT.getLabel(), craftacularSkin);
    }

    private void createStartButton() {
        screenData.startButton = new TextButton(Labels.START_BUTTON.getLabel(), craftacularSkin);
    }

    private void createBackButton() {
        screenData.backButton = new TextButton(Labels.BACK_BUTTON.getLabel(), craftacularSkin);
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

    private void listenTextField() {
        TextField usernameInput = screenData.usernameInput;
        usernameInput.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundManager.getSounds().get(Sounds.BUTTON_CLICK).play();
                
                // Checks if a comma was entered into text field.
                // This is an invalid character in the username as it is used
                // as a delimiter for values in the leaderboard file.
                if (Gdx.input.isKeyPressed(Keys.COMMA)) {
                    TextField usernameInput = screenData.usernameInput;
                    String text = usernameInput.getText();
                    StringBuffer sb = new StringBuffer(text);

                    // Removes invalid character from text field
                    sb.deleteCharAt(sb.length()-1);
                    text = sb.toString();
                    screenData.usernameInput.setText(text);
                    screenData.usernameInput.setCursorPosition(text.length());
                }
            }
        });
    }

    private void listenStartButton() {
        TextButton startButton = screenData.startButton;
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundManager.getSounds().get(Sounds.BUTTON_CLICK).play();

                TextField usernameInput = screenData.usernameInput;
                String username = usernameInput.getText();
                if (username.length() > 0 && ScoreFileManager.CreateNewTempEntry(username)) {
                    game.setScreen(Screens.GAME);
                }
            }
        });
    }

    private void setupTable() {
        Label enterUsernameLabel = screenData.enterUsernameLabel;
        TextField usernameInput = screenData.usernameInput;
        TextButton backButton = screenData.backButton;
        TextButton startButton = screenData.startButton;
        table.setFillParent(true);

        if (((DebugScreenPreferences) GamePreferences.DEBUG_SCREEN.getPreference()).isEnabled()) {
            table.setDebug(true);
        }

        table.setSkin(craftacularSkin);

        processor.addActor(table);

        table.row();
        table.add(enterUsernameLabel).colspan(2).fillX().uniformX().fillY().uniformY();
        table.row();
        table.add(usernameInput).colspan(2).fillX().uniformX().fillY().uniformY();
        table.row();
        table.add(startButton).fillX().uniformX();
        table.add(backButton).fillX().uniformX();
        
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