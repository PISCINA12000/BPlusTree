public class BMais {
    private No raiz;

    //Construtores
    public BMais(No raiz) {
        this.raiz = raiz;
    }

    public BMais() {
        this(null);
    }

    //Métodos-------------------------------------------------------------
    public void inserir(int info, int posArq){
        No folha, pai;
        int pos;
        //primeiro caso, se a raiz for nulla
        if (raiz == null) {
            raiz = new NoFolha(info, posArq);
        }
        //segundo caso ele terá que procurar a folha correta para realizar a inserção
        else {
            //procuro a folha correta para a inserção
            folha = procurarFolha(info);
            //após achado a folha, eu encontro a posição ideal para inserção na folha
            pos = folha.procurarPosicao(info);
            //remanejo de acordo com a posição ideal de inserção
            folha.remanejarInsercao(pos);
            //setando os valores no espaço aberto
            folha.setvInfo(info, pos);
            folha.setvPos(posArq, pos);
            //aumentando o TL da folha
            folha.setTL(folha.getTL()+1);

            //verificar se a folha não ultrapassou a quantidade máxima de elementos
            if (folha.getTL() == No.N){ //se verdade, split
                pai = localizarPai(folha, info);
                split(folha, pai);
            }
        }
    }

    public No procurarFolha(int info){
        No aux = raiz;
        int pos;
        while (!aux.isFolha()){
            pos = aux.procurarPosicao(info);
            if(aux instanceof NoPonteiro) {
                aux = ((NoPonteiro) aux).getvLig(pos);
            }
        }
        return aux;
    }

    public No localizarPai(No folha, int info){
        No aux, pai;
        aux = pai = raiz;
        int pos;
        while(aux!=null && aux!=folha){
            pai = aux;
            pos = aux.procurarPosicao(info);
            if(aux instanceof NoPonteiro) {
                aux = ((NoPonteiro) aux).getvLig(pos);
            }
        }
        return pai;
    }

    public void split(No folha, No pai) {
        if (raiz == folha)
            if (folha instanceof NoFolha)
                splitRaizFolha(folha, pai);
            else
                splitRaizPonteiro(folha, pai);
        else
            if (folha instanceof NoFolha)
                splitNaoRaizFolha(folha, pai);
            else
                splitNaoRaizPonteiro(folha, pai);
    }
    private void splitRaizFolha(No folha, No pai) {
        NoFolha cx1 = new NoFolha(), cx2 = new NoFolha();
        NoPonteiro novoPai = new NoPonteiro();
        int max1, max2;
        max1 = No.N/2;
        max2 = folha.getTL();

        //preencher a caixa 1
        for (int i=0; i<max1; i++) {
            cx1.setvInfo(folha.getvInfo(i), i);
            cx1.setvPos(folha.getvPos(i), i);
            cx1.setTL(cx1.getTL()+1);
        }
        //preencher a caixa 2
        for (int i=max1; i<max2; i++){
            cx2.setvInfo(folha.getvInfo(i), i-max1);
            cx2.setvPos(folha.getvPos(i), i-max1);
            cx2.setTL(cx2.getTL()+1);
        }

        //preencher o pai
        novoPai.setvInfo(cx2.getvInfo(0),0); //seto a informação da raiz
        novoPai.setvPos(cx2.getvPos(0),0);
        novoPai.setvLig(cx1,0);
        novoPai.setvLig(cx2,1);
        novoPai.setTL(1);
        raiz = novoPai;
    }
    private void splitRaizPonteiro(No folha, No pai) {
        NoPonteiro cx1 = new NoPonteiro(), cx2 = new NoPonteiro();
        NoPonteiro novoPai = new NoPonteiro();
        int max1, max2;
        max1 = No.N/2;
        max2 = folha.getTL();

        //preencher a caixa 1
        for (int i=0; i<max1; i++) {
            cx1.setvInfo(folha.getvInfo(i), i);
            cx1.setvPos(folha.getvPos(i), i);
            cx1.setvLig(((NoPonteiro) folha).getvLig(i), i);
            cx1.setTL(cx1.getTL()+1);
        }
        cx1.setvLig(((NoPonteiro) folha).getvLig(max1), max1);
        //enviar o elemento que ficaria na primeira posição da cx2 para o novo pai
        novoPai.setvInfo(folha.getvInfo(max1),0);
        novoPai.setvPos(folha.getvPos(max1),0);
        //preencher a caixa 2
        int posCx = 0;
        for (int i=max1+1; i<max2; i++){
            cx2.setvInfo(folha.getvInfo(i), posCx);
            cx2.setvPos(folha.getvPos(i), posCx);
            cx2.setvLig(((NoPonteiro) folha).getvLig(i), posCx);
            posCx++;
            cx2.setTL(cx2.getTL()+1);
        }
        cx2.setvLig(((NoPonteiro) folha).getvLig(folha.getTL()), max1-1);

        //terminar de preencher o pai
        novoPai.setvLig(cx1, 0);
        novoPai.setvLig(cx2, 1);
        novoPai.setTL(1);
        raiz = novoPai;
    }
    private void splitNaoRaizFolha(No folha, No pai) {
        NoFolha cx1 = new NoFolha(), cx2 = new NoFolha();
        int max1, max2;
        max1 = No.N/2;
        max2 = folha.getTL();

        //preencher a cx1
        for (int i=0; i<max1; i++) {
            cx1.setvInfo(folha.getvInfo(i), i);
            cx1.setvPos(folha.getvPos(i), i);
            cx1.setTL(cx1.getTL()+1);
        }
        //preencher a cx2
        for (int i=max1; i<max2; i++){
            cx2.setvInfo(folha.getvInfo(i), i-max1);
            cx2.setvPos(folha.getvPos(i), i-max1);
            cx2.setTL(cx2.getTL()+1);
        }

        //preencher o pai com a nova informação
        int pos = pai.procurarPosicao(cx2.getvInfo(0));
        ((NoPonteiro)pai).remanejarInsercao(pos);
        pai.setvInfo(cx2.getvInfo(0), pos);
        pai.setvPos(cx2.getvPos(0), pos);
        ((NoPonteiro) pai).setvLig(cx1, pos);
        ((NoPonteiro) pai).setvLig(cx2, pos+1);
        pai.setTL(pai.getTL()+1); //adiciono pois o pai já existe

        if (pai.getTL() == No.N){
            folha = pai;
            pai = localizarPai(folha, folha.getvInfo(0));
            if (pai==folha) //a nova folha é raiz
                splitRaizPonteiro(folha, pai);
            else
                splitNaoRaizPonteiro(folha, pai);
        }
    }
    private void splitNaoRaizPonteiro(No folha, No pai) {
        NoPonteiro cx1 = new NoPonteiro(), cx2 = new NoPonteiro();
        int max1, max2;
        max1 = No.N/2;
        max2 = folha.getTL();

        //preencher a cx1
        for (int i=0; i<max1; i++) {
            cx1.setvInfo(folha.getvInfo(i), i);
            cx1.setvPos(folha.getvPos(i), i);
            cx1.setvLig(((NoPonteiro) folha).getvLig(i), i);
            cx1.setTL(cx1.getTL()+1);
        }
        //preencher com a ultima ligacao da cx1
        cx1.setvLig(((NoPonteiro) folha).getvLig(max1), max1);
        //preencher a cx2
        int posCx = 0;
        for (int i=max1+1; i<max2; i++){
            cx2.setvInfo(folha.getvInfo(i), posCx);
            cx2.setvPos(folha.getvPos(i), posCx);
            cx2.setvLig(((NoPonteiro) folha).getvLig(i), posCx);
            posCx++;
            cx2.setTL(cx2.getTL()+1);
        }
        cx2.setvLig(((NoPonteiro) folha).getvLig(max2), max1-1);

        //mandar para o pai o elemento que ficaria na pos 0 da cx2
        int pos = pai.procurarPosicao(folha.getvInfo(max1));
        ((NoPonteiro) pai).remanejarInsercao(pos);
        pai.setvInfo(folha.getvInfo(max1), pos);
        //terminar de preencher o pai
        ((NoPonteiro) pai).setvLig(cx1, pos);
        ((NoPonteiro) pai).setvLig(cx2, pos+1);
        pai.setTL(pai.getTL()+1);

        if (pai.getTL() == No.N){
            folha = pai;
            pai = localizarPai(folha, folha.getvInfo(0));
            if (pai==folha) //a nova folha é raiz
                splitRaizPonteiro(folha, pai);
            else
                splitNaoRaizPonteiro(folha, pai);
        }
    }

    public void exibirArvoreNiveis(){
        exibirArvoreNIVEIS(raiz, 0);
    }
    private void exibirArvoreNIVEIS(No folha, int nivel){
        //exibir os tabs para identação
        for (int i=0; i<nivel-1; i++) {
            System.out.print("\t");
        }
        if(nivel>0){
            System.out.print("-");
        }
        //exibir os valores do meu vetor
        for (int i = 0; i < folha.getTL(); i++) {
            System.out.print(folha.getvInfo(i) + " ");
        }
        System.out.println();

        //verifico se é ponteiro para chamar os seus filhos recursivamente
        if(folha instanceof NoPonteiro){
            for (int i = 0; i < folha.getTL(); i++) {
                exibirArvoreNIVEIS(((NoPonteiro) folha).getvLig(i), nivel+1);
            }
            exibirArvoreNIVEIS(((NoPonteiro) folha).getvLig(folha.getTL()), nivel+1);
        }
    }

    //Gets e Sets
    public No getRaiz() {
        return raiz;
    }

    public void setRaiz(No raiz) {
        this.raiz = raiz;
    }
}
