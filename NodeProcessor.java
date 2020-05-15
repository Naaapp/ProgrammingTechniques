import montefiore.ulg.ac.be.graphics.*;

abstract class NodeProcessor {

    ExplorerSwingView esv;

    NodeProcessor(GuiHandler gui){
        try {
            this.esv = GlobalManager.getEsv(gui);
        } catch (NullHandlerException e) {
            e.printStackTrace();
        }
    }

    abstract void create(Node selectedNode) throws NullHandlerException;
    abstract void copy(Node selectedNode);

}

class FolderNodeProcessor extends NodeProcessor {

    FolderNodeProcessor(GuiHandler gui) {
        super(gui);
    }

    @Override
    void create(Node selectedNode) {
        String input = this.esv.folderMenuDialog();
        FolderNode new_node = new FolderNode(input, (FolderNode) selectedNode);
        try {
            this.esv.addNodeToSelectedNode(new_node);
        } catch (NoSelectedNodeException e) {
            e.printStackTrace();
        }
        ((FolderNode) selectedNode).addChild(new_node);
        this.esv.refreshTree();
    }

    @Override
    void copy(Node selectedNode) {
        FolderNode copy_node = new FolderNode(selectedNode.getName() + "_copy", selectedNode.getParent());
        try {
            esv.addNodeToParentNode(copy_node);
            ((FolderNode) selectedNode).makeDeepCopy( this.esv, 1, copy_node);
        } catch (NoSelectedNodeException | NoParentNodeException e) {
            e.printStackTrace();
        }
        selectedNode.getParent().addChild(copy_node);
        esv.refreshTree();
    }
}

class FileNodeProcessor extends NodeProcessor {

    FileNodeProcessor(GuiHandler gui) {
        super(gui);
    }

    @Override
    void create(Node selectedNode) {
        String[] input = this.esv.fileMenuDialog();
        FileNode new_node = new FileNode(input[0], input[1], (FolderNode) selectedNode);
        try {
            this.esv.addNodeToSelectedNode(new_node);
        } catch (NoSelectedNodeException e) {
            e.printStackTrace();
        }
        ((FolderNode) selectedNode).addChild(new_node);
        this.esv.refreshTree();
    }

    @Override
    void copy(Node selectedNode) {
        FileNode copy_node = new FileNode(selectedNode.getName()+"_copy",
                selectedNode.getText(), selectedNode.getParent());
        try {
            esv.addNodeToParentNode(copy_node);
        } catch (NoSelectedNodeException | NoParentNodeException e) {
            e.printStackTrace();
        }
        selectedNode.getParent().addChild(copy_node);
        esv.refreshTree();

    }
}

class AliasFileNodeProcessor extends NodeProcessor {

    AliasFileNodeProcessor(GuiHandler gui) {
        super(gui);
    }

    @Override
    void create(Node selectedNode){
        String file_name = selectedNode.getName() + "_alias";
        AliasFileNode new_node = new AliasFileNode((FileNode) selectedNode,file_name, selectedNode.getParent());
        try {
            this.esv.addNodeToParentNode(new_node);
        } catch (NoSelectedNodeException | NoParentNodeException e) {
            e.printStackTrace();
        }
        selectedNode.getParent().addChild(new_node);
        this.esv.refreshTree();
    }

    @Override
    void copy(Node selectedNode) {
        AliasFileNode copy_node = new AliasFileNode(((AliasFileNode) selectedNode).getFileNode(),
                selectedNode.getName()+"_copy", selectedNode.getParent());
        try {
            esv.addNodeToParentNode(copy_node);
        } catch (NoSelectedNodeException | NoParentNodeException e) {
            e.printStackTrace();
        }
        selectedNode.getParent().addChild(copy_node);
        esv.refreshTree();
    }
}

