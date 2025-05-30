public class Principal {
    public static void main(String[] args) {
        BMais arvoreB = new BMais();

        for (int i = 0; i < 100; i++) {
            arvoreB.inserir(i,i);
//            arvoreB.exibirArvoreNiveis();
//            for(int j=0; j<2; j++)
//                System.out.println();
        }

        arvoreB.exibirArvoreNiveis();
    }
}