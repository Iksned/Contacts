
import ConModel.Catalog;
import concontrol.CatController;
import conview.Main_Window;
import conview.View;


public class ConMain {
    public static void main(String[] args) {
        Catalog catalog = Catalog.getInstance();
        CatController controller = new CatController(catalog);
        View view = new Main_Window(controller);
    }
}

