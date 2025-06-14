public class Principal {
    public static void main(String[] args) {
        BMais arvoreB = new BMais();

        //insercoes
        for (int i = 0; i < 100; i++)
            arvoreB.inserir(i,i); //'info' e o 'posArq' ('posArq' não muda em nada a estrutura)

        System.out.println("Arvore:");
        arvoreB.exibirArvore();
        System.out.println("\nFolhas Crescente:");
        arvoreB.exibirFolhas();
        System.out.println("\nFolhas Reverso:");
        arvoreB.exibirFolhasRev();
        System.out.println("\nExibição In-Ordem:");
        arvoreB.inOrdem();

        //remoções e inserções
        //remover tudo e depois repovoar
        for(int i=0; i<100; i++) {
            //arvoreB.exibirArvore();
            arvoreB.remover(i);
        }
        for(int i=0; i<100; i++)
            arvoreB.inserir(i,i);
        System.out.println("\nArvore completa novamente 1:");
        arvoreB.exibirArvore();

        //tentar a exclusao ao contrario e depois repovoar
        for(int i=99; i>=0; i--) {
            //arvoreB.exibirArvore();
            arvoreB.remover(i);
        }
        for(int i=0; i<100; i++)
            arvoreB.inserir(i,i);
        System.out.println("\nArvore completa novamente 2:");
        arvoreB.exibirArvore();

        for(int i=10; i<20; i++){
            arvoreB.remover(i);
        }
        System.out.println("\nRemoção do 10 até o 19:");
        arvoreB.exibirArvore();

        for(int i=75; i>=50; i--){
            //arvoreB.exibirArvore();
            arvoreB.remover(i);
        }
//        arvoreB.exibirArvore();
//        arvoreB.remover(74);
        System.out.println("\nRemoção do 50 até o 75:");
        arvoreB.exibirArvore();
        System.out.println("\nFolhas Crescente:");
        arvoreB.exibirFolhas();
    }
}