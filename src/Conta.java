class Conta	{
    private int identificador;
    private static int contador = 0;
	private int	numero;
	private String	titular;
    private String agencia;
    private double	saldo;
	private Data DataAbertura;

    Conta(String titular) {
        this.titular = titular;
        this.saldo = 0.0;
        this.identificador = ++contador;
        this.DataAbertura = new Data();
    }

    public int getIdentificador() {
        return identificador;
    }

    public String getTitular() {
        return titular;
    }

    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public String getAgencia() {
        return agencia;
    }
    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public double getSaldo() {
        return saldo;
    }

    public Data getDataAbertura() {
        return DataAbertura;
    }
    public void setDataAbertura(Data dataAbertura) {
        this.DataAbertura = dataAbertura;
    }

    public void saca(double valor) {
        if (valor <= saldo) {
            saldo = saldo - valor;
            System.out.println("Saque de R$" + valor + " realizado com sucesso.");
        } else {
            System.out.println("Saldo insuficiente para o saque de R$" + valor);
        }
    }	

    public void deposita(double valor) {
        saldo = saldo + valor;
        System.out.println("Depósito de R$" + valor + " realizado com sucesso.");
    }

    public double getRendimento() {
        return saldo * 0.1; // Exemplo de rendimento de 10%
    }

    public String recuperaDadosParaImpressao() {

        String dados = "Identificador: " + this.identificador;
        dados += "\nTitular: " + this.titular;
        dados += "\nNúmero: " + this.numero;
        dados += "\nAgência: " + this.agencia;
        dados += "\nSaldo: R$" + this.saldo;
        dados += "\nData de Abertura: " + this.DataAbertura.formatada();
        return dados;
    }
    
    
}