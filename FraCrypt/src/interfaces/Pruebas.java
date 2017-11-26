package interfaces;

public class Pruebas {

	public static void main(String[] args) {
		Package src = Package.getPackage("src");
		Package[] packs = src.getPackages();
		for(Package p : packs) {
			System.out.println(p.getName());
		}

	}

}
