import concontrol.CatController;
import ConModel.Catalog;
import conview.View;

public class ConMain {
    public static void main(String[] args) {
        Catalog catalog = Catalog.getInstance();
        View view = new View();
        CatController catController = new CatController(catalog,view);

        catController.ShowWindow();

    }
}

