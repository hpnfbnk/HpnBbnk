package com.hyphen.fbnk.bbnk.crypt;


public class Well512
{
	int counter = 0;
	/* initialize state to random bits */
	int[] state = new int[16];
	/* init should also reset this to 0 */
	int index = 0;
	/* return 32 bit random number */
	
	private static Well512 ro;
	
	private Well512(){}

	public static synchronized Well512 getInstance(){
		if(ro == null){
			ro	= new Well512();
		}
		return ro;
	}
	public int WELLRNG512()
	{
		int a=0, b=0, c=0, d=0;
	
		a = state[index];
		c = state[(index+13)&15];
		b = a^c^(a<<16)^(c<<15);
		c = state[(index+9)&15];
		c ^= (c>>>11);
		a = state[index] = b^c;
		d = a^((a<<5)&0xDA442D24);
	
		index = (index + 15)&15;
		a = state[index];
		state[index] = a^b^d^(a<<2)^(b<<18)^(c<<28);
		
		return state[index];
	}
	
	public void initWLLING512(int[] rnos)
	{
		int bit_pos = (rnos[0] & 0x0f) + 1;
	
		counter = 0;
		index   = 0;
		
		for(int i = 0; i < state.length; i++)
		{
			state[i] = rnos[i] << bit_pos;
		}
	}
	
	public void initWELL()
	{
		counter = 0;
		index = 0;
	}
	
	public int[] getWELL512()
	{
		counter++;
		
		int[] wnos = new int[2];
		wnos[0] = WELLRNG512();
		wnos[1] = counter;
		
		return wnos;
	}
	
	public int countWELL512()
	{
		return counter;
	}
}

