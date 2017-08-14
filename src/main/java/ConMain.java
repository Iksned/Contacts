
import concontrol.CatController;
import conview.choose.ChooseFrame;
import conview.impl.Login_Frame;
import conview.impl.Main_Window;
import conview.View;


public class ConMain {
    public static void main(String[] args) {
        for (int i = 0;i<3;i++)
        {
          // CatController controller = new CatController();
           Thread t =  new Thread(new Runnable() {
                @Override
                public void run() {
                    new Login_Frame(new CatController());
                }
            });
           t.start();
        }
        //ChooseFrame chooseFrame = new ChooseFrame(controller);
       // View view = new Main_Window(controller);

    }
}

