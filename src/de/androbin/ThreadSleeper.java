package de.androbin;

public final class ThreadSleeper
{
	private int nanosLeft;
	
	public void reset()
	{
		nanosLeft = 0;
	}
	
	public void sleep( final long millis )
	{
		if ( millis <= 0L )
		{
			return;
		}
		
		try
		{
			Thread.sleep( millis );
		}
		catch ( final InterruptedException ignore )
		{
		}
	}
	
	public void sleep( final long millis, final int nanos )
	{
		nanosLeft += nanos;
		final long millisToWait = millis + nanosLeft / 1000000;
		nanosLeft %= 1000000;
		
		sleep( millisToWait );
	}
}