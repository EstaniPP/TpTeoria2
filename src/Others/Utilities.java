package Others;

import java.awt.image.BufferedImage;



public class Utilities {
	private static int tiradasMinimas = 1000000;
	private static Double epsilonDesvio = 1e-8;
	private static Double epsilonMedia = 1e-8;	
	
	public static double[] getProbabiliades(ImageParser image) {
		double[] probabilidades = new double[256];
		int[] veces = new int[256];
		
		for(int i = 0; i < 256; i++) {
			probabilidades[i] = 0;
			veces[i] = 0;
		}
		
		for(int i = 0; i < 500; i++) {
			for(int j = 0; j < 500; j++) {
				veces[image.getRGB(j, i).getRed()]++;
			}
		}
		for(int i = 0; i < 256; i++) {
			probabilidades[i] = ((double) veces[i]) / (double) (500*500);
		}
		
		return probabilidades;
	}
	
	public static double getEntropiaSMemoria(ImageParser image) {
		double[] p = getProbabiliades(image);
		double suma = 0;
		for(int i = 0; i < 256; i++) {
			if(p[i] != 0) {
				suma += p[i] *  ((double)Math.log((double) p[i]) / (double)Math.log((double) 2));
			}
		}
		
		return -suma;
	}
	
	public static double getEntropiaCMemoria(ImageParser image) {
		double hCond = 0;
		double[] p = getProbabiliades(image);
		double[][] matCond = Utilities.getMatrizCondicional(image);
		for(int i = 0; i < 256; i++) {
				double hi = 0;
				for(int j = 0; j<256; j++) {
					if(matCond[j][i] != 0) {
						hi += (-1 * matCond[j][i] * ((double)Math.log((double) matCond[j][i]) / (double)Math.log((double) 2)));
					}
				}
				hCond  += (p[i] *  hi);
		}
		return hCond;
	}
	
	public static double[][] getMatrizCondicional(ImageParser image){
		double[][] matrizcond = new double[256][256];
		double[] tiradas = new double[256];
		
		for(int i=0;i<256;i++) {
			for(int j=0;j<256;j++) {
				matrizcond[i][j]=0;
			}
			tiradas[i]=0;
		}
		
		int coloranterior= image.getRGB(0, 0).getRed();
		for(int i = 0; i < 500; i++) {
			for(int j = 0; j < 500; j++) {
				int coloractual=image.getRGB(j, i).getRed();;
				if(j!=0 || i!=0) {
					matrizcond[coloractual][coloranterior]++;
					coloranterior=coloractual;
				}
				tiradas[coloranterior]++;
				if(j==499 && i==499) {
					tiradas[coloranterior]--;
				}
			}
		}
		
		for(int i=0;i<256;i++) {
			for(int j=0; j<256; j++) {
				if(tiradas[j]==0) {
					matrizcond[i][j]=0.0f;
				}else {
					matrizcond[i][j]=matrizcond[i][j]/tiradas[j];
				}
			}
		}
		
		return matrizcond;
	}
	

	private static boolean converge(double act, double ant, double epsilon) {
		if(Math.abs(act-ant)<epsilon) {
			return true;
		}
		return false;
	}
	
	private static int getColorMontecarlo(double[] probabilidadAcumulada) {
		double rand = Math.random();
		for(int i=0; i<256; i++) {
			if(rand<=probabilidadAcumulada[i]) {
				return i;
			}
		}
		return 255;
	}
	
	private static int getColorMontecarloCondicional(double[][] matrizAcumulada, int valor) {
		double rand = Math.random();
		for(int i=0; i<256; i++) {
			if(rand<=matrizAcumulada[i][valor]) {
				return i;
			}
		}
		return 255;
	}
	
	public static double[] getProcEstocasticos(ImageParser img) {
		double sumaMedia=0.0;
		double suma = 0.0;
		int tiradas=0;
		double act=0.0;
		double ant=-1.0;
		double mediaant=0.0;
		double mediaact = -1.0;
		
		double[] probabilidadAcumulada = Utilities.getProbabiliades(img);
		double[][] matrizAcumulada = Utilities.getMatrizCondicional(img);
		double sumaprob =0;
		for(int i=0; i<256; i++) {
			double sumacond =0;
			for(int j=1; j<256; j++) {
				sumacond+=matrizAcumulada[j][i];
				matrizAcumulada[j][i]=sumacond;
			}
			sumaprob+=probabilidadAcumulada[i];
			probabilidadAcumulada[i]=sumaprob;	 
		}
		int valor = Utilities.getColorMontecarlo(probabilidadAcumulada);
		
		while((!converge(act,ant, Utilities.epsilonDesvio) && !converge(mediaact,mediaant, Utilities.epsilonMedia)) || tiradas<Utilities.tiradasMinimas ) {
			valor = Utilities.getColorMontecarloCondicional(matrizAcumulada, valor);
			sumaMedia += (double) valor;
			tiradas++;
			suma += Math.pow(((double)valor-(sumaMedia /(double) tiradas)), 2);
			ant=act;
			act=Math.sqrt(suma/(double) tiradas);
			
			mediaant=mediaact;
			mediaact=sumaMedia/(double)tiradas;
		}
		double[] procEstocasticos = new double[]{mediaact,act};
		return procEstocasticos;
	}

	public static String getRepeticiones(ImageParser img) {
		int[] repeticiones = new int[256];
		for(int i=0;i<256;i++) {
			repeticiones[i]=0;
		}
		
		for(int j=0;j<500;j++) {
			for(int i=0;i<500;i++) {
				repeticiones[img.getRGB(i, j).getRed()]++;
			}
		}
		StringBuilder salida = new StringBuilder();
		for(int i=0;i<256;i++) {
			salida.append("El color: "+i+" se repitio "+repeticiones[i]+" veces<br />");
		}
		
		return salida.toString();
	}
}
