/**
 * @author madhavc
 *
 */
public interface RBT<K extends Comparable<K>, V> {
	
	public void insert(K key, V Value);
	
	public V delete(K key);
	
	public V lookUp(K key);

}
