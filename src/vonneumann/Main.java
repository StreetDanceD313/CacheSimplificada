// von Neumann Básico
// PSCF - PUCPR (Prof. Luiz Lima)
//Alunos: Carlos Goulart e André Martins

package vonneumann;

public class Main {
	public static void main(String [] args) throws InvalidAddress {

		IO io = new IO(System.out);
		RAM ram = new RAM(8);
		Cache_Manager cache_manager = new Cache_Manager(ram,4,16);
		CPU cpu = new CPU(cache_manager, io);
		try {
			final int start = 10;
			ram.Write(start, 68);
			ram.Write(start+1, 192);
			cpu.Run(start);
		} catch (InvalidAddress e) {
			System.err.println("Erro: " + e);
		}
		System.out.println("TERMINOU");
	}
}
