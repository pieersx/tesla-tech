package teslatech.interfaces;

public interface Operaciones<T> {
    public static final int MAX = 100;
    public void agregar(T elemento);       // Agrega un nuevo elemento
    public T buscarPorId(int id);          // Busca un elemento por su ID
}
