public class Principal {
    public static void main(String[] args) {
        BMais arvoreB = new BMais();

        //insercoes
        for (int i = 0; i < 100; i++)
            arvoreB.inserir(i,i);

        System.out.println("Arvore:");
        arvoreB.exibirArvore();
        System.out.println("Folhas Crescente:");
        arvoreB.exibirFolhas();
        System.out.println("\nFolhas Reverso:");
        arvoreB.exibirFolhasRev();
        System.out.println("\nExibição In-Ordem:");
        arvoreB.inOrdem();

        //remocoes
//        for(int i=0; i<100; i++)
//            arvoreB.remover(i);
//
//        System.out.println("Arvore:");
//        arvoreB.exibirArvore();
//        System.out.println("Folhas Crescente:");
//        arvoreB.exibirFolhas();
//        System.out.println("\nFolhas Reverso:");
//        arvoreB.exibirFolhasRev();
    }
}