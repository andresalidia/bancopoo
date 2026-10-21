
class ContaTeste {
    public static void main(String[] args) {

        Conta c1 = new Conta("João da Silva");

        c1.setNumero(12345);
        c1.setAgencia("001");
        c1.deposita(1000.0);
        // Configurando a data de abertura
        Data data = c1.getDataAbertura();
        data.dia = 15;
        data.mes = 6;
        data.ano = 2023;

        System.out.println(c1.recuperaDadosParaImpressao());

        c1.deposita(500.0);
        System.out.println("Saldo após depósito: R$" + c1.getSaldo());

        c1.saca(200.0);
        System.out.println("Saldo após saque: R$" + c1.getSaldo());

        double rendimento = c1.getRendimento();
        System.out.println("Rendimento calculado: R$" + rendimento);
    }
}
