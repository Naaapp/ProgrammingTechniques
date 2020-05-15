import montefiore.ulg.ac.be.graphics.ExplorerSwingView;
import montefiore.ulg.ac.be.graphics.LevelException;
import montefiore.ulg.ac.be.graphics.NoPreviousInsertedNodeException;
import montefiore.ulg.ac.be.graphics.NullHandlerException;

import java.util.ArrayList;
import java.util.List;

abstract class Node {
    protected String name;
    protected FolderNode parent;
    abstract public String getText();
    abstract public String getName();
    abstract public FolderNode getParent();
}

class FileNode extends Node {
    private String text;


    public FileNode(String name, String text, FolderNode parent){
        this.name = name;
        this.text = text;
        this.parent = parent;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name ;
    }

    @Override
    public FolderNode getParent(){ return this.parent;}
}

class AliasFileNode extends Node {
    private FileNode file_node;
    private FolderNode parent;

    public AliasFileNode(FileNode file_node, String file_name, FolderNode parent){
        this.file_node = file_node;
        this.name = file_name;
        this.parent = parent;
    }

    @Override
    public String getText() {
        return this.file_node.getText();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name ;
    }

    @Override
    public FolderNode getParent(){ return this.parent;}

    public FileNode getFileNode(){ return this.file_node;}
}

class FolderNode extends Node {
    List<Node> children_list;

    public FolderNode(String name, FolderNode parent){
        this.name = name;
        this.children_list = new ArrayList<>();
        this.parent = parent;
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name ;
    }

    @Override
    public FolderNode getParent(){ return this.parent;}

    public void addChild(Node child) { children_list.add(child);}

    public void makeDeepCopy( ExplorerSwingView esv, int depth, FolderNode parent) {

        for(Node child : this.children_list) {
            NodeTool nt = null;
            if (child instanceof FolderNode) {
                nt = new FolderNodeTool();
            } else if (child instanceof FileNode) {
                nt = new FileNodeTool();
            } else if (child instanceof AliasFileNode) {
                nt = new AliasFileNodeTool();
            }
            assert nt != null;
            Node copy_child = nt.inst_node_copy(child, parent);
            nt.add_node_to_tree(copy_child,esv,depth);
            nt.link_to_parent(copy_child, parent);
            nt.make_deep_copy(child, esv, copy_child, depth);
        }
    }
}
