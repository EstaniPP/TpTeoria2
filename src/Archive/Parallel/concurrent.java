package Parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class concurrent {
	
	public static void cambiar(Integer x) {
		x++;
	}
	
	
	// create a newWorkStealingPool
	public static void main(String[] args) {
		
		int b = 0;
		System.out.println(b);
		concurrent.cambiar(b);
		System.out.println(b);
		/*
		long start = System.currentTimeMillis();
		class Tarea implements Callable{
			Integer number;
			public Tarea(int n) {
				number = n;
			}
			@Override
			public Object call() throws Exception {
				System.out.println(Thread.currentThread().getName());
				Thread.sleep(number * 1000);
				return number;
			}
		}
		//ExecutorService executor = Executors.newWorkStealingPool();
		ExecutorService executor = Executors.newFixedThreadPool(8);

		ArrayList<Callable<Integer>> callables = new ArrayList<Callable<Integer>>();
		for(int i = 0; i < 7; i++) {
			callables.add(new Tarea(i));
		}
		try {
			List<Future<Integer>> resulta2 = executor.invokeAll(callables);
			for(Future<Integer> i : resulta2) {
				try {
					System.out.println(i.get());
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
        System.out.println("Tiempo transcurrido: " + (end - start) + "");
        */
	}
}
