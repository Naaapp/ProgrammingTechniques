import montefiore.ulg.ac.be.graphics.*;

import java.awt.*;
import java.io.File;

public class GuiHandler implements ExplorerEventsHandler {

	private TextAreaManager tam;
	
    GuiHandler(String[] args) throws NullHandlerException {

		ExplorerSwingView esv = GlobalManager.getEsv(this);
        this.tam = GlobalManager.getTam(this);
        this.tam.clearAllText();
        
        try {
        	// First step to do before anything !!! 
            esv.setRootNode(new FolderNode("Root", null));
        } catch (RootAlreadySetException e) {
            e.printStackTrace();
        }
    }
	
	@Override
	public void createAliasEvent(Object selectedNode) {
		if(selectedNode instanceof FileNode) {
			AliasFileNodeProcessor fnp = new AliasFileNodeProcessor(this);
			fnp.create((FileNode) selectedNode);
		}
		else {
			System.out.println("Try to create an alias file of a non file : No effect");
		}
	}

	@Override
	public void createArchiveEvent(Object selectedNode) {
		// TODO Auto-generated method stub
	}

	@Override
	public void createCopyEvent(Object selectedNode) {
    	NodeProcessor np = null;
    	if(selectedNode instanceof FolderNode){
			np = new FolderNodeProcessor(this);
		} else if(selectedNode instanceof FileNode){
			np = new FileNodeProcessor(this);
		} else if(selectedNode instanceof AliasFileNode){
			np = new AliasFileNodeProcessor(this);
		}
		assert np != null;
		np.copy((Node) selectedNode);
	}

	@Override
	public void createFileEvent(Object selectedNode) {
    	if(selectedNode instanceof FolderNode) {
			FileNodeProcessor fnp = new FileNodeProcessor(this);
			fnp.create((FolderNode) selectedNode);
		}
		else {
			System.out.println("Try to create a file in a non folder : No effect");
		}
	}

	@Override
	public void createFolderEvent(Object selectedNode) {
    	if(selectedNode instanceof FolderNode) {
			FolderNodeProcessor fnp = new FolderNodeProcessor(this);
			fnp.create((FolderNode) selectedNode);
		}
    	else {
    		System.out.println("Try to create a folder in a non folder : No effect");
		}
	}

	@Override
	public void doubleClickEvent(Object selectedNode) {
    	this.tam.clearAllText();
    	String text = ((Node)selectedNode).getText();
    	if(text!=null){
			this.tam.appendText(text);
		}
    	else {
    		System.out.println("Double click on folder : No effect");
		}

	}

	@Override
	public void eventExit() {
    	System.exit(0);
	}
}
