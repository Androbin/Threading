package de.androbin;

import java.util.concurrent.locks.*;
import java.util.function.*;

public final class Locked<T> {
  private final T value;
  private final ReadWriteLock lock;
  
  public Locked( final T value ) {
    this( value, false );
  }
  
  public Locked( final T value, final boolean fair ) {
    this.value = value;
    this.lock = new ReentrantReadWriteLock( fair );
  }
  
  private void accept( final Lock lock, final Consumer<T> consumer ) {
    lock.lock();
    
    try {
      consumer.accept( value );
    } finally {
      lock.unlock();
    }
  }
  
  private <S> S apply( final Lock lock, final Function<T, S> consumer ) {
    lock.lock();
    
    try {
      return consumer.apply( value );
    } finally {
      lock.unlock();
    }
  }
  
  public void read( final Consumer<T> consumer ) {
    accept( lock.readLock(), consumer );
  }
  
  public <S> S read( final Function<T, S> function ) {
    return apply( lock.readLock(), function );
  }
  
  public void write( final Consumer<T> consumer ) {
    accept( lock.writeLock(), consumer );
  }
  
  public <S> S write( final Function<T, S> function ) {
    return apply( lock.writeLock(), function );
  }
}