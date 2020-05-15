// Singleton object which manage the ExplorerSwingView and TextAreaManager
// (Singleton Pattern)

import montefiore.ulg.ac.be.graphics.*;

public class GlobalManager {

    GlobalManager(){
    }

    private static ExplorerSwingView esv_instance = null;
    private static TextAreaManager tam_instance = null;

    public static ExplorerSwingView getEsv (GuiHandler gui) throws NullHandlerException {
        if(esv_instance == null){
            esv_instance= new ExplorerSwingView(gui);
            tam_instance = esv_instance.getTextAreaManager();
        }
        return esv_instance;
    }

    public static TextAreaManager getTam (GuiHandler gui) throws NullHandlerException {
        if(esv_instance == null){
            esv_instance= new ExplorerSwingView(gui);
            tam_instance = esv_instance.getTextAreaManager();
        }
        return tam_instance;
    }
}
