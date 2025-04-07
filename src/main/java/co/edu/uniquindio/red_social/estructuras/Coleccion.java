package co.edu.uniquindio.red_social.estructuras;

import java.util.Iterator;
/**
 *  Coleccion made by: Helen Giraldo y Miguel Vargas
 *  <p>
 *      Clase madre para todas las colecciones del proyecto,
 *      contiene los siguientes metodos:
 *      {@code size(), length(), isEmpty(), contains(T), iterator(), add(T), remove(T), update(T), containsAll(Coleccion<T>),addAll(Coleccion<T>), removeAll(Coleccion<T>)}
 *
 * </p>
 * <p>
 * Solo JhancaGOD
 **/

public interface Coleccion<T> extends Iterable<T> {

    // -------------------------- Consultas -----------------------------
    /** Devuelve el número de elementos en la colección
     *
     * @return int - Numero de elementos
     * */
    int size();

    /** Devuelve el número de elementos en la colección
     *
     * @return int - Numero de elementos
     * */
    default int length(){
        return size();
    }

    /** Verifica si la colección está vacía
     *
     * @return boolean - La lista está vacía o no
     */
    boolean isEmpty();

    /** Verifica si la colección contiene un elemento específico
     *
     * @param objeto Objeto a verificar
     * @return boolean - El objeto está en la lista o no
     */
    boolean contains(T objeto);

    /** Devuelve un iterador sobre los elementos de la colección
     *
     * @return Iterator - Iterador para recorrer la lista
     */
    Iterator<T> iterator();

    // -------------------------- Modificaciones -----------------------------

    /** Agrega un elemento a la colección
     *
     * @param objeto Objeto a añadir
     * @return boolean - El objeto fue creado correctamente o no
     */
    boolean add(T objeto);

    /** Elimina un elemento específico de la colección
     *
     * @param objeto Objeto que se desea eliminar
     * @return boolean - El objeto fue eliminado correctamente o no
     */
    boolean remove(T objeto);

    /** Actualiza el primer objeto que coincida en la lista
     *
     * @param objetoInicial Objeto a eliminar
     * @param objetoFinal Objeto a añadir
     * @return boolean - El objeto fue actualizado correctamente o no
     */
    boolean update(T objetoInicial, T objetoFinal);


    // -------------------------- Masivas -----------------------------

    /** Verifica si la colección contiene todos los elementos de otra colección
     *
     * @param coleccion Coleccion a verificar
     * @return boolean - Contiene todos los elementos o no
     */
    boolean containsAll(Coleccion<T> coleccion);

    /** Agrega todos los elementos de otra colección
     *
     * @param coleccion Coleccion a añadir
     * @return boolean - Se añadió correctamente o no
     */
    boolean addAll(Coleccion<T> coleccion);

    /** Elimina todos los elementos presentes en otra colección
     * 
     * @param coleccion
     * @return
     */
    boolean removeAll(Coleccion<T> coleccion);

}