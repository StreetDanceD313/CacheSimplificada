package vonneumann;

public class Cache extends Memoria{
    private RAM         ram;
    private int         ID_RAM;
    public  int         ID_FALTANTERAM;
    private boolean     TEM_ALTERACAO;

    public Cache(int nbits, RAM ram) {
        super(nbits);
        this.ram = ram;
        if(tam >= ram.getTam()){
           nbits = Integer.bitCount(ram.getTam()-1)-1;
           this.tam = (int)Math.pow(2,nbits);
           this.dados = new int[tam];
        }
        this.ID_RAM = 0;
        TEM_ALTERACAO = false;
    }

    @Override
    public int Read(int ender) throws InvalidAddress {
        ender = ID_FALTANTERAM + ender;
        if(ram.EhValido(ender)){
            int chaveEndereco = (ender & ((ram.getTam()-1) << Integer.bitCount(this.tam-1)));
            if(chaveEndereco != ID_RAM){
                System.out.println(
                        String.format(
                                "CACHE MISS READ (ENDER %s, CHAVE %s)",
                                Integer.toBinaryString(ender),
                                Integer.toBinaryString(chaveEndereco)
                        )
                );
                this.alteraEndereco(chaveEndereco);
            }else{
                System.out.println(
                        String.format(
                                "CACHE HIT READ (ENDER %s, CHAVE %s)",
                                Integer.toBinaryString(ender),
                                Integer.toBinaryString(chaveEndereco)
                        )
                );
            }

            return super.Read(ender - chaveEndereco);
        }
        throw new InvalidAddress(ender);
    }

    @Override
    public void Write(int ender, int valor) throws InvalidAddress {
        //1010>10
        if(ram.EhValido(ender)){
            int chaveEndereco = (ender & ((ram.getTam()-1) << Integer.bitCount(this.tam-1)));
            if(chaveEndereco != ID_RAM){
                System.out.println(
                        String.format(
                                "CACHE MISS WRITE(ID ENDER %s CHAVE %s CHAVE CACHE %s) ,VALOR %s)",
                                Integer.toBinaryString(ender),
                                Integer.toBinaryString(chaveEndereco),
                                Integer.toBinaryString(ender & (tam-1)),
                                valor
                        )
                );
                this.alteraEndereco(chaveEndereco);
            }else{
                System.out.println(
                        String.format(
                                "CACHE HIT WRITE(ID ENDER %s CHAVE %s CHAVE CACHE %s) ,VALOR %s)",
                                Integer.toBinaryString(ender),
                                Integer.toBinaryString(chaveEndereco),
                                Integer.toBinaryString(ender & (tam-1)),
                                valor
                        )
                );
            }
            TEM_ALTERACAO = true;
            super.Write(ender-chaveEndereco, valor);
        }else{
            throw new InvalidAddress(ender);
        }
    }

    public void alteraEndereco(int novoEndereco){
        //verifica se tem atualizações
        //faz as atualizações
        if(TEM_ALTERACAO){
            try{
                for(int i = 0; i<this.tam;i++) {
                    this.ram.Write(ID_RAM + i, this.dados[i]);
                }
                TEM_ALTERACAO = false;
            }catch(InvalidAddress e){
                System.out.println("ERRO DE CHAVE NO ALTERA ENDERECO DA CACHE - ATUALIZANDO VALORES - NOVO ENDERECO: " + novoEndereco);
                e.printStackTrace();
            }
        }
        //Atualiza Novo Endereço
        this.ID_RAM = novoEndereco;
        try{
            for(int i = 0; i<this.tam;i++) {
                this.dados[i] = ram.Read(i + ID_RAM);
            }
        }catch(InvalidAddress e){
            System.out.println("ERRO DE CHAVE NO ALTERA ENDERECO DA CACHE - CARREGANDO VALORES - NOVO ENDERECO: " + novoEndereco);
            e.printStackTrace();
        }
    }
}
