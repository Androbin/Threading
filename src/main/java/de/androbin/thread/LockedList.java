package de.androbin.thread;

import java.util.*;
import java.util.function.*;

public final class LockedList<E> implements Iterable<E> {
  private final Locked<List<E>> list;
  
  public LockedList() {
    this( new ArrayList<>() );
  }
  
  public LockedList( final List<E> list ) {
    this.list = new Locked<>( list );
  }
  
  public boolean add( final E element ) {
    return list.writeBack( list -> list.add( element ) );
  }
  
  public void clear() {
    list.write( List::clear );
  }
  
  public boolean contains( final Object o ) {
    return list.readBack( list -> list.contains( o ) );
  }
  
  @ Override
  public void forEach( final Consumer< ? super E> action ) {
    list.read( list -> list.forEach( action ) );
  }
  
  public E get( final int index ) {
    return list.readBack( list -> list.get( index ) );
  }
  
  public boolean isEmpty() {
    return list.readBack( List::isEmpty );
  }
  
  @ Override
  public Iterator<E> iterator() {
    return new Iterator<E>() {
      private int index;
      
      @ Override
      public boolean hasNext() {
        return index < size();
      }
      
      @ Override
      public E next() {
        return get( index++ );
      }
    };
  }
  
  public E remove( final int index ) {
    return list.writeBack( list -> list.remove( index ) );
  }
  
  public boolean remove( final Object o ) {
    return list.writeBack( list -> list.remove( o ) );
  }
  
  public E set( final int index, final E element ) {
    return list.writeBack( list -> list.set( index, element ) );
  }
  
  public int size() {
    return list.readBack( List::size );
  }
  
  public E tryGet( final int index ) {
    return list.readBack( list -> index < list.size() ? list.get( index ) : null );
  }
}