package de.androbin.thread;

import java.util.*;
import java.util.function.*;

public final class LockedList<E> {
  private final Locked<List<E>> list;
  
  public LockedList() {
    this( new ArrayList<>() );
  }
  
  public LockedList( final List<E> list ) {
    this.list = new Locked<>( list );
  }
  
  public void add( final E element ) {
    list.write( list -> list.add( element ) );
  }
  
  public void clear() {
    list.write( List::clear );
  }
  
  public boolean contains( final E element ) {
    return list.readBack( list -> list.contains( element ) );
  }
  
  public void forEach( final Consumer<E> action ) {
    list.read( list -> list.forEach( action ) );
  }
  
  public E get( final int index ) {
    return list.readBack( list -> list.get( index ) );
  }
  
  public boolean isEmpty() {
    return list.readBack( List::isEmpty );
  }
  
  public void remove( final int index ) {
    list.write( list -> list.remove( index ) );
  }
  
  public void remove( final E element ) {
    list.write( list -> list.remove( element ) );
  }
  
  public void set( final int index, final E element ) {
    list.write( list -> list.set( index, element ) );
  }
  
  public int size() {
    return list.readBack( List::size );
  }
  
  public E tryGet( final int index ) {
    return list.readBack( list -> index < list.size() ? list.get( index ) : null );
  }
}