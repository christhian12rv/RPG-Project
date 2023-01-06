package utils;

public class PrintUtil {

		public void printStringLetraPorLetraSom(String str) {
			for(int i = 0; i<=str.length() - 1; i++) {
					java.awt.Toolkit.getDefaultToolkit().beep();
					System.out.print(str.charAt(i));
					try {
							Thread.sleep(15); 
					} catch (Exception e) {
							e.printStackTrace();
					}
			}
		}

		public void printStringLetraPorLetra(String str) {
			for(int i = 0; i<=str.length() - 1; i++) {
					System.out.print(str.charAt(i));
					try {
							Thread.sleep(5); 
					} catch (Exception e) {
							e.printStackTrace();
					}
			}
		}

		public void printStringLetraPorLetraLentoSom(String str) {
			for(int i = 0; i<=str.length() - 1; i++) {
					java.awt.Toolkit.getDefaultToolkit().beep();
					System.out.print(str.charAt(i));
					try {
							Thread.sleep(200); 
					} catch (Exception e) {
							e.printStackTrace();
					}
			}
		}

		public void textDelay(int x) {
			try {
				Thread.sleep(x); 
			} catch (Exception e) {
					e.printStackTrace();
			}
		}
}