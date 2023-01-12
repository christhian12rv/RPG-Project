package utils;

import java.util.Scanner;

public class ScannerUtil {

	public int getInt(Scanner scanner, int min, int max) {
		Boolean opcaoInvalida = false;
		int x = 0;

		do {
			opcaoInvalida = false;
			
			try {
					x = scanner.nextInt();

					while (x < min || x > max) {
							System.out.print("Opção inválida. Digite novamente: ");
							x = scanner.nextInt();
					}
					
			} catch (Exception e) {
					System.out.println("Opção inválida. Digite novamente: ");
					scanner.nextLine();
					opcaoInvalida = true;
			}
		} while (opcaoInvalida);

		return x;
	}

	public int getInt(Scanner scanner) {
		Boolean opcaoInvalida = false;
		int x = 0;

		do {
			opcaoInvalida = false;
			
			try {
					x = scanner.nextInt();
			} catch (Exception e) {
					System.out.println("Opção inválida. Digite novamente: ");
					scanner.nextLine();
					opcaoInvalida = true;
			}
		} while (opcaoInvalida);

		return x;
	}
	
}
