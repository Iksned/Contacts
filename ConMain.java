
import concontrol.CatController;
import conview.Main_Window;
import conview.View;


public class ConMain {
    public static void main(String[] args) {
        CatController controller = new CatController();
        View view = new Main_Window(controller);
    }
}

