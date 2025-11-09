public enum TipoConta {
    CORRENTE, 
    POUPANCA;

    public static TipoConta fromString(String texto) {
        if (texto == null) return null;
        
        switch (texto.trim().toLowerCase()) {
            case "corrente":
                return CORRENTE;
            case "poupança":
            case "poupanca":
                return POUPANCA;
            default:
                throw new IllegalArgumentException("Tipo de conta inválido: " + texto);
        }
    }
}
