public class Principal {
    public static void main(String[] args) {
        BMais arvoreB = new BMais();

        //insercoes
        for (int i = 0; i < 100; i++) {
            arvoreB.inserir(i,i);
//            System.out.println("Arvore:");
//            arvoreB.exibirArvoreNiveis();
//            System.out.println("Folhas:");
//            arvoreB.exibirFolhas();
//            System.out.println();
        }

        System.out.println("Arvore:");
        arvoreB.exibirArvoreNiveis();
        System.out.println("Folhas Crescente:");
        arvoreB.exibirFolhas();
        System.out.println("\nFolhas Reverso:");
        arvoreB.exibirFolhasRev();

        //remocoes
        for(int i=0; i<100; i++){
            arvoreB.remover(i);
        }

        System.out.println("Arvore:");
        arvoreB.exibirArvoreNiveis();
        System.out.println("Folhas Crescente:");
        arvoreB.exibirFolhas();
        System.out.println("\nFolhas Reverso:");
        arvoreB.exibirFolhasRev();
    }
}