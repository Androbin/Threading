package de.androbin.thread;

import java.util.*;

public final class LockedList<E> {
  private final Locked<List<E>> list;
  
  public LockedList() {
    this( new ArrayList<>() );
  }
  
  public LockedList( final List<E> list ) {
    this.list = new Locked<>( list );
  }
  
  public void add( final E element ) {
    list.write( items -> items.add( element ) );
  }
  
  public boolean contains( final E element ) {
    return list.readBack( items -> items.contains( element ) );
  }
  
  public E get( final int index ) {
    return list.readBack( items -> items.get( index ) );
  }
  
  public void remove( final int index ) {
    list.write( items -> items.remove( index ) );
  }
  
  public void remove( final E element ) {
    list.write( items -> items.remove( element ) );
  }
  
  public void set( final int index, final E element ) {
    list.write( items -> items.set( index, element ) );
  }
  
  public int size() {
    return list.readBack( List::size );
  }
}