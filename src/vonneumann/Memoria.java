package vonneumann;

abstract public class Memoria{

    protected int tam = 0;
    protected int [] dados;

    public Memoria(int nbits) {
        tam = (int) Math.pow(2,nbits);
        dados = new int[tam];
    }

    public int Read(int ender) throws InvalidAddress {
        if (EhValido(ender))
            return dados[ender];
        else
            throw new InvalidAddress(ender);
    }

    public void Write(int ender, int valor)  throws InvalidAddress {
        if (EhValido(ender))
            dados[ender] = valor;
        else
            throw new InvalidAddress(ender);
    }

    protected boolean EhValido(int ender) throws InvalidAddress {
        return (ender >= 0 && ender < tam);
    }

    public int getTam() {
        return tam;
    }
}
