package utils;

public class PrintUtil {

		public void printStringLetraPorLetraSom(int delay, String str) {
			for(int i = 0; i<=str.length() - 1; i++) {
					java.awt.Toolkit.getDefaultToolkit().beep();
					System.out.print(str.charAt(i));
					try {
							Thread.sleep(delay); 
					} catch (Exception e) {
							e.printStackTrace();
					}
			}
		}

		public void printStringLetraPorLetra(int delay, String str) {
			for(int i = 0; i<=str.length() - 1; i++) {
					System.out.print(str.charAt(i));
					try {
							Thread.sleep(delay); 
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

		public void clearTerminal() {
			System.out.print("\033[H\033[2J");
		}
}