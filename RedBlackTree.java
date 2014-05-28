
/**
 * @author madhavc-
 *
 */
public class RedBlackTree <K extends Comparable<K>,V> implements RBT<K,V> {
	
	protected final boolean RED = true;
    protected final boolean BLACK = false;
    
    private Node<K, V> nullLeaf;
    private Node<K, V> root;
    
    /**
     * Initializes a Red Black Tree
     * 
     * @pre true
     * @post an instance of RedBlackTree is created.
     */
    public RedBlackTree() {
        root = null;
        nullLeaf = new Node<K, V>(null,null,BLACK,null,null,null);
    }
    
    /**
     * This method adds the mapping to the tree.
     * 
     * @pre true
     * @post the key added matches the value added with it. If there was a
     *       previous mapping of the same key results are not guaranteed
     * 
     * @param key
     *            the key that will map to the value for look up
     * @param value
     *            the object to be stored
     */
	@SuppressWarnings("unchecked")
	@Override
	public void insert(K key, V value) {
		if(key == null || value == null){
			try {
				throw new Exception();
			} catch (Exception e) {
				System.out.println("Key or Value are of Invalid type.");
				e.printStackTrace();
				System.exit(0);
			}
		}
		if (root == null) {
            root = new Node<K, V>(key,value,BLACK,nullLeaf,nullLeaf,null);
            root.leftTree.parent = root;
            root.rightTree.parent = root;
        }
	
		
		//Call insertHelper method so we add recursively.
		
		insertHelper(root,key,value);
}
