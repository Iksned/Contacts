
import ConModel.Catalog;
import concontrol.CatController;
import conview.Main_Window;


public class ConMain {
    public static void main(String[] args) {
        Catalog catalog = Catalog.getInstance();
        Main_Window main_window = new Main_Window();
        CatController controller = new CatController(catalog,main_window);
        controller.initController();
    }
}

