package uk.ac.york.student.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import uk.ac.york.student.GdxGame;


/**The LeaderboardScreen class extends the BaseScreen class and represents the leaderboard screen of the game.
 * This displays the top 10 scores and the name associated with them.
 */

public class LeaderboardScreen extends BaseScreen{

    private final Stage processor;

    public LeaderboardScreen(GdxGame game) {
        super(game);
        processor = new Stage(new ScreenViewport());
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
