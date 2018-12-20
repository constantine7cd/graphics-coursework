package sample;

import AbstractLetter.AnimatedObject;
import AbstractLetter.AnimationExecutor;
import SentenceBuilder.SentenceBuilder;
import animation.FrameTime;
import render.Bitmap;
import scene.Camera;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import settings.Settings;
import scene.MScene;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.canvas.Canvas;
import utils.MFile;

public class Controller implements Initializable {

    @FXML
    Button pause;

    @FXML
    Button resume;

    @FXML
    TextField sentence;

    @FXML
    Button animate;

    @FXML
    Slider slider;

    Button b_a;
    Button b_b;
    Button b_v;
    Button b_g;
    Button b_d;
    Button b_e;
    Button b_yo;
    Button b_j;
    Button b_z;
    Button b_i;
    Button b_i_;
    Button b_k;
    Button b_l;
    Button b_m;
    Button b_n;
    Button b_o;
    Button b_p;
    Button b_r;
    Button b_s;
    Button b_u;
    Button b_f;
    Button b_h;
    Button b_c;
    Button b_ch;
    Button b_sh;
    Button b_sch;
    Button b_tz;
    Button b_ii;
    Button b_mz;
    Button b_ee;
    Button b_yu;
    Button b_ya;


    @FXML
    Canvas canvas;

    render.Display display;
    Camera camera;
    MScene scene;
    Bitmap texture;
    List <AnimatedObject> letters;

    String animationSentence;
    AnimationExecutor execute;


    public void change(int a)
    {
        a *= 2;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        display = new render.Display(800, 600);

        display.setCanvas(canvas);

        camera = new Camera(new structures.Matrix4f().InitPerspective((float)Math.toRadians(70.0f),
                (float)display.getBitmap().GetWidth()/(float)display.getBitmap().GetHeight(), 0.1f, 1000.0f));


        scene = new MScene(display, camera);

        try {
            texture = new Bitmap(new File(Settings.DIFFUSE_FILE));
        }
        catch (Exception e)
        {
            System.out.println("Bitmap load error\n");
        }

        animationSentence = new String();

        letters = new ArrayList<AnimatedObject>();

        for (int i = 0; i < Settings.LETTERS.length; ++i) {
            letters.add(new AnimatedObject(new MFile(Settings.RES_FOLDER, Settings.LETTERS_SOURCES[i]),
                    new MFile(Settings.RES_FOLDER, Settings.LETTERS_SOURCES[i]), texture,
                    Settings.LETTERS[i]));
        }

        this.execute = new AnimationExecutor();


    }

    @FXML
    private void handleOnKeyPressed(KeyEvent event)
    {
        if (event.getCode().equals(KeyCode.W)) {
            moveCameraAhead();
        }
        if (event.getCode().equals(KeyCode.S)) {
            moveCameraBack();
        }
        if (event.getCode().equals(KeyCode.A)) {
            moveCameraLeft();
        }
        if (event.getCode().equals(KeyCode.D)) {
            moveCameraRight();
        }
        if (event.getCode().equals(KeyCode.I)) {
            rotateCameraUp();
        }
        if (event.getCode().equals(KeyCode.K)) {
            rotateCameraDown();
        }
        if (event.getCode().equals(KeyCode.J)) {
            rotateCameraLeft();
        }
        if (event.getCode().equals(KeyCode.L)) {
            rotateCameraRight();
        }
    }


    public void executeAnimation()
    {
        animateSentence();
    }

    public void stopAnimation()
    {
        execute.pause();
    }

    public void continueAnimation()
    {
        execute.resume();
    }

    public void executeLetterA()
    {
        animateOne('А');
    }

    public void executeLetterB()
    {
        animateOne('Б');
    }

    public void executeLetterV()
    {
        animateOne('В');
    }

    public void executeLetterG()
    {
        animateOne('Г');
    }

    public void executeLetterD()
    {
        animateOne('Д');
    }

    public void executeLetterE()
    {
        animateOne('Е');
    }

    public void executeLetterYo()
    {
        animateOne('Ё');
    }

    public void executeLetterJ()
    {
        animateOne('Ж');
    }

    public void executeLetterZ()
    {
        animateOne('З');
    }

    public void executeLetterI()
    {
        animateOne('И');
    }

    public void executeLetterI_()
    {
        animateOne('Й');
    }

    public void executeLetterK()
    {
        animateOne('К');
    }

    public void executeLetterL()
    {
        animateOne('Л');
    }

    public void executeLetterM()
    {
        animateOne('М');
    }

    public void executeLetterN()
    {
        animateOne('Н');
    }

    public void executeLetterO()
    {
        animateOne('О');
    }

    public void executeLetterP()
    {
        animateOne('П');
    }

    public void executeLetterR()
    {
        animateOne('Р');
    }

    public void executeLetterS()
    {
        animateOne('С');
    }

    public void executeLetterT()
    {
        animateOne('Т');
    }

    public void executeLetterU()
    {
        animateOne('У');
    }

    public void executeLetterF()
    {
        animateOne('Ф');
    }

    public void executeLetterH()
    {
        animateOne('Х');
    }

    public void executeLetterC()
    {
        animateOne('Ц');
    }

    public void executeLetterCh()
    {
        animateOne('Ч');
    }

    public void executeLetterSh()
    {
        animateOne('Ш');
    }

    public void executeLetterSch()
    {
        animateOne('Щ');
    }

    public void executeLetterTZ()
    {
        animateOne('Ъ');
    }

    public void executeLetterII()
    {
        animateOne('Ы');
    }

    public void executeLetterMZ()
    {
        animateOne('Ь');
    }

    public void executeLetterEE()
    {
        animateOne('Э');
    }

    public void executeLetterYu()
    {
        animateOne('Ю');
    }

    public void executeLetterYa()
    {
        animateOne('Я');
    }

    public void moveCameraAhead()
    {
        camera.moveAhead(.5f);
    }

    public void moveCameraBack()
    {
        camera.moveBackward(.5f);
    }

    public void moveCameraLeft()
    {
        camera.moveLeft(.5f);
    }

    public void moveCameraRight()
    {
        camera.moveRight(.5f);
    }

    public void rotateCameraLeft()
    {
        camera.rotateLeft(.1f);
    }

    public void rotateCameraRight()
    {
        camera.rotateRight(.1f);
    }

    public void rotateCameraUp()
    {
        camera.rotateUp(.1f);
    }

    public void rotateCameraDown()
    {
        camera.rotateDown(.1f);
    }


    public void cleanupTextField()
    {
        sentence.clear();
    }

    public void sliderChanged()
    {
        FrameTime.setIncTime(Settings.INC_TIME * (float) slider.getValue());
    }


    public void animateSentence()
    {

        String sent = sentence.getText();

        if (this.execute.getAtimer() != null) {
            this.execute.pause();
        }

        List <AnimatedObject> lst = SentenceBuilder.extractObjectsFromString(sent, letters);

        AnimationExecutor exec = new AnimationExecutor();

        this.execute = exec;
        exec.executeList(lst, display, camera);
    }


    public void animateOne(char ch)
    {
        if (this.execute.getAtimer() != null) {
            this.execute.pause();
        }

        AnimationExecutor exec = new AnimationExecutor();
        this.execute = exec;
        exec.executeOne(letters.get(SentenceBuilder.getIndex(ch, Settings.LETTERS_C)), display, camera);
    }
}
