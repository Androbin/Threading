package de.androbin.thread;

import de.androbin.func.*;
import java.util.function.*;

public final class Timer {
  private final ThreadSleeper sleeper;
  
  public int delayMilli;
  public int delayMikro;
  
  public BooleanSupplier running;
  public BooleanSupplier active;
  
  public Timer() {
    sleeper = new ThreadSleeper();
  }
  
  public void run( final FloatConsumer action ) {
    int deltaMilli = delayMilli;
    int deltaMikro = delayMikro;
    
    while ( running.getAsBoolean() ) {
      if ( active.getAsBoolean() ) {
        final long before = System.currentTimeMillis();
        
        action.accept( deltaMilli / 1000f + deltaMikro / 1000000f );
        
        final long after = System.currentTimeMillis();
        final int diff = (int) ( after - before );
        
        final int sleepMilli;
        final int sleepMikro;
        
        if ( diff <= delayMilli ) {
          sleepMilli = delayMilli - diff;
          sleepMikro = delayMikro;
        } else {
          sleepMilli = 0;
          sleepMikro = 0;
        }
        
        sleeper.sleepMikro( sleepMilli, sleepMikro );
        
        deltaMilli = sleepMilli + diff;
        deltaMikro = sleepMikro;
      } else {
        sleeper.sleepMikro( delayMilli, delayMikro );
        
        deltaMilli = delayMilli;
        deltaMikro = delayMikro;
      }
    }
    
    sleeper.reset();
  }
  
  public void setFPS( final int fps ) {
    delayMilli = 1000 / fps;
    delayMikro = ( 1000000 / fps ) % 1000;
  }
}