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
    //Inserção na árvore
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

    //remover da árvore
    public void remover(int info){
        //na remoção eu tenho alguns casos para considerar

        No folha, noIndice;
        int pos, posNoIndice;

        folha = procurarFolha(info);
        if(folha == raiz){
          if(folha.getTL() == 1){
              //caso de ser o elemento da raiz a ser excluido e a raiz ficar com valor null
              raiz = null;
          }
          else {
              //caso de ser raiz mas a raiz nao ficar null
              pos = folha.procurarPosicao(info);
              folha.remanejarExclusao(pos);
              folha.setTL(folha.getTL()-1);
          }
        }
        else {
            pos = folha.procurarPosicao(info);
            //preciso ter certeza de que nao eh a primeira folha para substituir o primeiro elemento
            if(pos == 0 && folha != primeiraFolha()){
                noIndice = procurarNoPonteiro(info);
                posNoIndice = noIndice.procurarPosicao(info);
                noIndice.setvInfo(folha.getvInfo(pos + 1), posNoIndice);
            }
            folha.remanejarExclusao(pos);
            folha.setTL(folha.getTL()-1);
            if(folha.getTL() < (int)Math.ceil(No.N/2.0)-1){
                redistribuirConcatenar(folha);
            }
        }
    }

    public void redistribuirConcatenar(No no){
        No pai, irmaE, irmaD;
        int pos, posPai;

        pai = localizarPai(no, no.getvInfo(0));
        irmaE = localizarIrmaEsq(no);
        irmaD = localizarIrmaDir(no);
        if(no instanceof NoFolha){
            //verificar se consigo redistribuir com a esquerda
            if(irmaE != null && irmaE.getTL() > (int)Math.ceil(No.N/2.0)-1){
                //se entrou, entao eu posso redistribuir com ela
                pos = pai.procurarPosicao(irmaE.getvInfo(irmaE.getTL()-1));
                pai.setvInfo(irmaE.getvInfo(irmaE.getTL()-1), pos);
                no.remanejarInsercao(0);
                no.setvInfo(irmaE.getvInfo(irmaE.getTL()-1), 0);
                no.setTL(no.getTL()+1);
                irmaE.setTL(irmaE.getTL()-1);
            }
            //verificar se consigo redistribuir com a direita
            else if (irmaD != null && irmaD.getTL() > (int)Math.ceil(No.N/2.0)-1){
                //se entrou, entao eu posso redistribuir com ela
                pos = pai.procurarPosicao(irmaD.getvInfo(0));
                no.setvInfo(pai.getvInfo(pos), no.getTL());
                no.setTL(no.getTL()+1);
                irmaD.remanejarExclusao(0);
                irmaD.setTL(irmaD.getTL()-1);
                pai.setvInfo(irmaD.getvInfo(0), pos);
            }
            //se chegou aqui entao sera necessario a concatenacao
            else if (irmaE != null){
                //concatenar com a irma da esquerda
                //concatenarEsquerdaFolha(no, irmaE, pai);

                //achar o elemento a ser tirado do pai
                posPai = pai.procurarPosicao(no.getvInfo(0));
                pai.remanejarExclusao(posPai);
                pai.setTL(pai.getTL()-1);

                //enviar os elementos do No para a irmaE
                for(int i=0; i<no.getTL(); i++){
                    irmaE.setvInfo(no.getvInfo(i), irmaE.getTL());
                    irmaE.setTL(irmaE.getTL()+1);
                }

                //mudo o apontamento do 'pai' para a 'irmaE'
                ((NoPonteiro) pai).setvLig(irmaE, posPai);

                //mudar o encadeamento
                ((NoFolha) irmaE).setProx(((NoFolha) no).getProx());
                no = irmaE;
            }
            else if (irmaD != null){
                //concatenar com a irma da direita
                //concatenarDireitaFolha(no, irmaD, pai);

                //achar o elemento a ser tirado do pai
                posPai = pai.procurarPosicao(irmaD.getvInfo(0));
                pai.remanejarExclusao(posPai);
                pai.setTL(pai.getTL()-1);

                //enviar os elementos da irmaD para o no
                for(int i=0;i<irmaD.getTL(); i++){
                    no.setvInfo(irmaD.getvInfo(i), no.getTL());
                    no.setTL(no.getTL()+1);
                }

                //setar a 'pos' do vLig do 'pai' para o 'no'
                ((NoPonteiro) pai).setvLig(no, posPai);

                //mudar o encadeamento
                ((NoFolha) no).setProx(((NoFolha) irmaD).getProx());
            }
        }
        else{ //meu no eh uma instancia de NoPonteiro
            if(irmaE != null && irmaE.getTL() > (int)Math.ceil(No.N/2.0)-1){
                //se entrou, entao eu posso redistribuir com ela

                //descobrir no pai qual a posicao
                posPai = pai.procurarPosicao(no.getvInfo(0));

                //remanejar o 'no' para receber o elemento do 'pai' e a subarvore da irmaE
                no.remanejarInsercao(0);
                no.setvInfo(pai.getvInfo(posPai-1), 0);
                ((NoPonteiro) no).setvLig(((NoPonteiro) irmaE).getvLig(irmaE.getTL()), 0);
                no.setTL(no.getTL()+1);

                //seto o novo elemento a ser colocado no pai
                pai.setvInfo(irmaE.getvInfo(irmaE.getTL()-1), posPai-1);

                //retiro um elemento de irmaE
                irmaE.setTL(irmaE.getTL()-1);
            }
            //verificar se consigo redistribuir com a direita
            else if (irmaD != null && irmaD.getTL() > (int)Math.ceil(No.N/2.0)-1){
                //se entrou, entao eu posso redistribuir com ela

                //descobrir no 'pai' qual a posicao
                if(no.getTL()>0)
                    posPai = pai.procurarPosicao(no.getvInfo(no.getTL()-1));
                else
                    posPai = pai.procurarPosicao(no.getvInfo(0));

                //mando para o 'no' o elemento do 'pai' e a ligacao da 'irmaD'
                no.setvInfo(pai.getvInfo(posPai), no.getTL());
                no.setTL(no.getTL()+1);
                ((NoPonteiro) no).setvLig(((NoPonteiro) irmaD).getvLig(0), no.getTL());

                //mando o elemento da irmaD para o meu pai
                pai.setvInfo(irmaD.getvInfo(0), posPai);

                //remanejo a exclusao da irmaD e diminuo o seu tl
                irmaD.remanejarExclusao(0);
                irmaD.setTL(irmaD.getTL()-1);
            }
            //se chegou aqui entao sera necessario a concatenacao
            else if (irmaE != null){
                //concatenar com a irma da esquerda

                //encontro a posicao do elemento no 'pai'
                posPai = pai.procurarPosicao(no.getvInfo(0));

                //mandar para a 'irmaE' o elemento no 'pai'
                irmaE.setvInfo(pai.getvInfo(posPai-1), irmaE.getTL());
                irmaE.setTL(irmaE.getTL()+1);

                //mandar todos os elementos de 'no' para o 'irmaE'
                for(int i=0; i<no.getTL(); i++){
                    irmaE.setvInfo(no.getvInfo(i), irmaE.getTL());
                    ((NoPonteiro) irmaE).setvLig(((NoPonteiro) no).getvLig(i), irmaE.getTL());
                    irmaE.setTL(irmaE.getTL()+1);
                }
                ((NoPonteiro) irmaE).setvLig(((NoPonteiro) no).getvLig(no.getTL()), irmaE.getTL());

                //remanejar o 'pai' e arrumar a ligacao para o 'irmaE'
                pai.remanejarExclusao(posPai-1);
                pai.setTL(pai.getTL()-1);
                ((NoPonteiro) pai).setvLig(irmaE, posPai-1);

                //para possivelmente mudar a raiz se precisar
                no = irmaE;
            }
            else if (irmaD != null){
                //concatenar com a irma da direita

                //encontro a posicao do elemento no 'pai'
                if(no.getTL()>0)
                    posPai = pai.procurarPosicao(no.getvInfo(no.getTL()-1));
                else
                    posPai = pai.procurarPosicao(no.getvInfo(0));

                //mandar o elemento do 'pai' para o 'no'
                no.setvInfo(pai.getvInfo(posPai), no.getTL());
                no.setTL(no.getTL()+1);

                //mandar todos os elementos da 'irmaD' para o 'no'
                for(int i=0; i<irmaD.getTL(); i++){
                    no.setvInfo(irmaD.getvInfo(i), no.getTL());
                    ((NoPonteiro) no).setvLig(((NoPonteiro) irmaD).getvLig(i), no.getTL());
                    no.setTL(no.getTL()+1);
                }
                ((NoPonteiro) no).setvLig(((NoPonteiro) irmaD).getvLig(irmaD.getTL()), no.getTL());

                //remanejar o 'pai' e arrumar a sua ligacao para o 'no'
                pai.remanejarExclusao(posPai);
                pai.setTL(pai.getTL()-1);
                ((NoPonteiro) pai).setvLig(no, posPai);
            }
        }

        //chamar recursivamente caso o pai fique com menos elementos do que o permitido
        if(pai.getTL() < (int)Math.ceil(No.N/2.0)-1 && pai != raiz){
            redistribuirConcatenar(pai);
        }
        else if(pai == raiz && pai.getTL() == 0){
            raiz = no;
        }
    }

    public No procurarFolha(int info){
        No aux = raiz;
        int pos;
        while (!aux.isFolha()){
            //pos = aux.procurarPosicao(info);
            for(pos=0; pos<aux.getTL() && info>=aux.getvInfo(pos); pos++);
            if(aux instanceof NoPonteiro) {
                aux = ((NoPonteiro) aux).getvLig(pos);
            }
        }
        return aux;
    }

//    public No procurarNoPonteiro(int info){
//        No aux = raiz;
//        int pos;
//        boolean achou = false;
//        while(aux!=null && !achou){
//            if(aux instanceof NoPonteiro){
//                pos = aux.procurarPosicao(info);
//                if(info == aux.getvInfo(pos))
//                    achou = true;
//                else
//                    aux = ((NoPonteiro) aux).getvLig(pos);
//            }
//            else
//                aux = null;
//        }
//        return aux;
//    }

    public No procurarNoPonteiro(int info){
        No aux = raiz;
        int pos;
        boolean achou = false;

        while (aux instanceof NoPonteiro && !achou) {
            pos = aux.procurarPosicao(info);
            if (pos<aux.getTL() && aux.getvInfo(pos)==info)
                achou = true;
            else
                aux = ((NoPonteiro) aux).getvLig(pos);
        }

        if(aux instanceof NoPonteiro)
            return aux;
        return null;
    }

    public No localizarPai(No folha, int info){
        No aux, pai;
        aux = pai = raiz;
        int pos;
        while(aux!=null && !aux.isFolha() && aux!=folha){
            pai = aux;
            pos = 0;
            for(int i=0; i<aux.getTL() && info>=aux.getvInfo(i); i++)
                pos++;
            if(aux instanceof NoPonteiro) {
                aux = ((NoPonteiro) aux).getvLig(pos);
            }
        }
        return pai;
    }

    public No localizarIrmaEsq(No folha){
        //tratar quando o folha eh o seu proprio pai
        No pai = localizarPai(folha, folha.getvInfo(0));
        if(pai == folha) //a folha eh raiz
            return null;
        else{
            int pos = 0;
            for(int i=0; i<pai.getTL() && folha.getvInfo(0)>=pai.getvInfo(i); i++){
                pos++;
            }
            if(pos > 0){
                return ((NoPonteiro) pai).getvLig(pos-1);
            }
            return null;
        }
    }

    public No localizarIrmaDir(No folha){
        //tratar quando o folha eh o seu proprio pai
        No pai = localizarPai(folha, folha.getvInfo(0));
        if(pai == folha) //a folha eh raiz
            return null;
        else{
            int pos = 0;
            for(int i=0; i<pai.getTL() && folha.getvInfo(0)>=pai.getvInfo(i); i++){
                pos++;
            }
            if(pos < pai.getTL()){
                return ((NoPonteiro) pai).getvLig(pos+1);
            }
            return null;
        }
    }

    public void split(No folha, No pai) {
        //tratar os 4 casos de splits diferentes
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
        max1 = (int)Math.ceil(No.N/2.0);
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
        //setar o ant e o prox
        cx1.setProx(cx2);
        cx2.setAnt(cx1);

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
        int posParaPai = (int)Math.ceil(No.N/2.0);
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
        cx2.setvLig(((NoPonteiro) folha).getvLig(folha.getTL()), posParaPai-1);

        //terminar de preencher o pai
        novoPai.setvLig(cx1, 0);
        novoPai.setvLig(cx2, 1);
        novoPai.setTL(1);
        raiz = novoPai;
    }
    private void splitNaoRaizFolha(No folha, No pai) {
        NoFolha cx1 = new NoFolha(), cx2 = new NoFolha();
        int max1, max2;
        max1 = (int)Math.ceil(No.N/2.0);
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
        //setar o ant e o prox
        cx1.setProx(cx2);
        cx2.setAnt(cx1);

        //preencher o pai com a nova informação
        int pos = pai.procurarPosicao(cx2.getvInfo(0));
        ((NoPonteiro)pai).remanejarInsercao(pos);
        pai.setvInfo(cx2.getvInfo(0), pos);
        pai.setvPos(cx2.getvPos(0), pos);
        ((NoPonteiro) pai).setvLig(cx1, pos);
        ((NoPonteiro) pai).setvLig(cx2, pos+1);
        pai.setTL(pai.getTL()+1); //adiciono pois o pai já existe

        //arrumar as ligações
        if(pos-1 >= 0) {
            cx1.setAnt(((NoPonteiro) pai).getvLig(pos - 1));
            ((NoFolha)((NoPonteiro) pai).getvLig(pos - 1)).setProx(cx1);
        }
        if(pos+2 <= pai.getTL()) {
            cx2.setProx(((NoPonteiro) pai).getvLig(pos + 2));
            ((NoFolha)((NoPonteiro) pai).getvLig(pos+2)).setProx(cx2);
        }

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
        int posParaPai = (int)Math.ceil(No.N/2.0);
        int posCx = 0;
        for (int i=max1+1; i<max2; i++){
            cx2.setvInfo(folha.getvInfo(i), posCx);
            cx2.setvPos(folha.getvPos(i), posCx);
            cx2.setvLig(((NoPonteiro) folha).getvLig(i), posCx);
            posCx++;
            cx2.setTL(cx2.getTL()+1);
        }
        cx2.setvLig(((NoPonteiro) folha).getvLig(max2), posParaPai-1);

        //mandar para o pai o elemento que ficaria na pos 0 da cx2
        int pos = pai.procurarPosicao(folha.getvInfo(max1));
        ((NoPonteiro) pai).remanejarInsercao(pos);
        pai.setvInfo(folha.getvInfo(max1), pos);
        pai.setvPos(folha.getvPos(max1), pos);
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

    public void exibirArvore(){
        exibirArvore(raiz, 0);
    }
    private void exibirArvore(No folha, int nivel){
        //exibir os tabs para identação
        for (int i=0; i<nivel; i++) {
            System.out.print("\t");
        }
        System.out.print("|");
        //exibir os valores do meu vetor
        for (int i = 0; i < folha.getTL(); i++) {
            System.out.print(folha.getvInfo(i) + "|");
        }
        System.out.println();

        //verifico se é ponteiro para chamar os seus filhos recursivamente
        if(folha instanceof NoPonteiro){
            for (int i = 0; i < folha.getTL(); i++) {
                exibirArvore(((NoPonteiro) folha).getvLig(i), nivel+1);
            }
            exibirArvore(((NoPonteiro) folha).getvLig(folha.getTL()), nivel+1);
        }
    }

    public void inOrdem(){
        inOrdem(raiz);
    }

    private void inOrdem(No no){
        if(no != null){
            if(no instanceof NoPonteiro){ //andar pra 'esquerda'
                for(int i=0; i<no.getTL(); i++){
                    inOrdem(((NoPonteiro) no).getvLig(i));
                    System.out.println(no.getvInfo(i));
                }
            }
            else{ //quando eu chego na 'folha' eu apenas a exibo
                for(int i=0; i<no.getTL(); i++){
                    System.out.println(no.getvInfo(i));
                }
            }

            if(no instanceof NoPonteiro){ //andar para a 'direita'
                inOrdem(((NoPonteiro) no).getvLig(no.getTL()));
            }
        }
    }

    public void exibirFolhas(){
        No aux = primeiraFolha();
        //cheguei até a primeira folha
        while (aux != null){
            System.out.print("|");
            for (int i = 0; i < aux.getTL(); i++) {
                System.out.print(aux.getvInfo(i) + "|");
            }
            System.out.println();
            aux = ((NoFolha) aux).getProx();
        }
    }

    public void exibirFolhasRev(){
        No aux = ultimaFolha(); //irá mudar aqui em relação ao outro metodo
        while (aux != null){
            System.out.print("|");
            for (int i = 0; i < aux.getTL(); i++) {
                System.out.print(aux.getvInfo(i) + "|");
            }
            System.out.println();
            aux = ((NoFolha) aux).getAnt(); //irá mudar aqui em relação ao outro metodo
        }
    }

    public No primeiraFolha(){
        //metodo que retorna a primeira folha mais a esquerda
        No aux = raiz;
        while (aux instanceof NoPonteiro) {
            aux = ((NoPonteiro) aux).getvLig(0);
        }
        return aux;
    }

    public No ultimaFolha(){
        //metodo que retorna a ultima folha mais a direita
        No aux = raiz;
        while (aux instanceof NoPonteiro) {
            aux = ((NoPonteiro) aux).getvLig(aux.getTL());
        }
        return aux;
    }

    //Gets e Sets
    public No getRaiz() {
        return raiz;
    }

    public void setRaiz(No raiz) {
        this.raiz = raiz;
    }
}
