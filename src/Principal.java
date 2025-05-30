public class Principal {
    public static void main(String[] args) {
        BMais arvoreB = new BMais();

        for (int i = 0; i < 1000; i++) {
            if(i==40){
                System.out.println("oi");
                arvoreB.exibirArvoreNiveis();
            }
            arvoreB.inserir(i,i);
            System.out.println(i);
        }

        arvoreB.exibirArvoreNiveis();
    }
}
