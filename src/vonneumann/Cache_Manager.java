package vonneumann;

public class Cache_Manager{
    RAM ram;
    Cache[] caches;
    int qtdColunas;

    public Cache_Manager(RAM ram,int qtdColunas,int tamanhoBits) {
        this.ram = ram;
        this.qtdColunas = qtdColunas;


        int tamanhoCache = tamanhoBits / qtdColunas;
        this.caches = new Cache[tamanhoCache];
    }

    public void alteraEndereco(int ender){
        int r = (ender >> Integer.bitCount(qtdColunas-1)) & caches.length-1;
        if(caches[r] == null){
            caches[r] = new Cache(Integer.bitCount(qtdColunas-1),ram);
        }
        caches[r].alteraEndereco(ender);
    }

    public int Read(int enderecoMem) throws InvalidAddress {
        int r = (enderecoMem >> Integer.bitCount(qtdColunas-1)) & caches.length-1;
        if(caches[r] == null){
            caches[r] = new Cache(Integer.bitCount(qtdColunas-1),ram);
        }
        return caches[r].Read(enderecoMem);
    }

    public void Write(int enderecoMem,int novoValor) throws InvalidAddress{
        int r = (enderecoMem >> Integer.bitCount(qtdColunas-1)) & caches.length-1;
        if(caches[r] == null){
            caches[r] = new Cache(Integer.bitCount(qtdColunas-1),ram);
        }
        caches[r].Write(enderecoMem,novoValor);
    }
    public boolean estaCarregado(int ender) throws InvalidAddress {
        Boolean retorno = ram.EhValido(ender);
        if(retorno){
            retorno = false;
            int r = ender & (caches.length-1 << Integer.bitCount(qtdColunas-1));

            if(caches[r] != null){
                retorno = caches[r].EhValido(ender);
            }

        }
        return retorno;
    }
}
