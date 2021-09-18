// von Neumann Básico
// PSCF - PUCPR (Prof. Luiz Lima)

package vonneumann;

public class CPU {

	private final Cache_Manager memoria;
	private final IO es;
	private int PC = 0;
	private int regA = 0;
	private int regB = 0;

	public CPU(Cache_Manager memoria, IO es) throws InvalidAddress {
		this.memoria = memoria;
		this.es = es;
	}

	public void Run(int ender) throws InvalidAddress {
		PC = ender;
		memoria.alteraEndereco(PC);
		regA = memoria.Read(PC++);
		regB = memoria.Read(PC);

		for (int i=regA; i<regB; ++i) {
			memoria.Write(i, i-regA+1);
			es.Output("cpu> " + i + " -> " + (i-regA+1));
		}
	}
}
