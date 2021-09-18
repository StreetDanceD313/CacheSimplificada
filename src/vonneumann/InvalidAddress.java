package vonneumann;

class InvalidAddress extends Exception {
    private final int ender;
    public InvalidAddress(int e) {
        super();
        ender = e;
    }

    @Override
    public String toString() {
        return "Endereço " + ender + " inválido!";
    }
}