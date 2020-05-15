import montefiore.ulg.ac.be.graphics.*;

abstract class NodeTool {

    abstract Node inst_node_copy(Node node, FolderNode parent);
    abstract void make_deep_copy(Node node, ExplorerSwingView esv, Node parent, int depth);
    void add_node_to_tree(Node node, ExplorerSwingView esv, int depth){
        try {
            esv.addNodeToLastInsertedNode(node, depth);
        } catch (NoPreviousInsertedNodeException | LevelException e) {
            e.printStackTrace();
        }
    }
    void link_to_parent(Node node, FolderNode parent){
        parent.addChild(node);
    }


}

class FolderNodeTool extends NodeTool{

    @Override
    Node inst_node_copy(Node node, FolderNode parent) {
        return new FolderNode(node.getName() + "_copy", parent);
    }


    @Override
    void make_deep_copy(Node node, ExplorerSwingView esv, Node parent, int depth) {
        ((FolderNode) node).makeDeepCopy(esv, depth + 1, (FolderNode) parent);
    }
}

class FileNodeTool extends NodeTool{

    @Override
    Node inst_node_copy(Node node, FolderNode parent) {
        return new FileNode(node.getName() + "_copy", node.getText(), parent);
    }

    @Override
    void make_deep_copy(Node node, ExplorerSwingView esv, Node parent, int depth) {
    }
}

class AliasFileNodeTool extends NodeTool{

    @Override
    Node inst_node_copy(Node node, FolderNode parent) {
        return new AliasFileNode(((AliasFileNode) node).getFileNode(), node.getName() + "_copy", parent);
    }

    @Override
    void make_deep_copy(Node node, ExplorerSwingView esv, Node parent, int depth) {
    }
}
