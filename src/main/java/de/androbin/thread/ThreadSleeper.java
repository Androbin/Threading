package de.androbin.thread;

public final class ThreadSleeper {
  private int mikrosLeft;
  private int nanosLeft;
  
  public void reset() {
    mikrosLeft = 0;
    nanosLeft = 0;
  }
  
  public void sleepMilli( final long millis ) {
    if ( millis <= 0L ) {
      return;
    }
    
    try {
      Thread.sleep( millis );
    } catch ( final InterruptedException ignore ) {
    }
  }
  
  public void sleepMikro( final long millis, final int mikros ) {
    mikrosLeft += mikros;
    
    sleepMilli( millis + mikrosLeft / 1000 );
    
    mikrosLeft %= 1000;
  }
  
  public void sleepNano( final long millis, final int mikros, final int nanos ) {
    nanosLeft += nanos;
    mikrosLeft += mikros + nanosLeft / 1000;
    
    sleepMilli( millis + mikrosLeft / 1000 );
    
    mikrosLeft %= 1000;
    nanosLeft %= 1000;
  }
}