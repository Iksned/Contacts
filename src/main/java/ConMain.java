
import concontrol.CatController;
import conview.choose.ChooseFrame;
import conview.impl.Main_Window;
import conview.View;


public class ConMain {
    public static void main(String[] args) {
        CatController controller = new CatController();
        ChooseFrame chooseFrame = new ChooseFrame(controller);
       // View view = new Main_Window(controller);

    }
}

