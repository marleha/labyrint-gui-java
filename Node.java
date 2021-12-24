public class Node<T> {

    // Merket naar jeg var ferdig at jeg trengte getter og setter. 
    public Node<T> neste = null;
    T data; 

    public Node (T x) {
        data = x;
    }
}