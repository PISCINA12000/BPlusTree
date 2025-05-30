public class NoPonteiro extends No {
    private No[] vLig;

    //Construtores
    NoPonteiro(No lig) {
        this();
        vLig[0] = lig;
    }
    NoPonteiro() {
        super();
        vLig = new No[N+1];
    }

    //Métodos
    @Override
    public void remanejarExclusao(int pos) {
        super.remanejarExclusao(pos); //chamo esse remanejar para arrumar o vInfo
        for (int i = pos; i < this.getTL()-1; i++) {
            vLig[i] = vLig[i+1];
        }
        vLig[getTL()-1] = vLig[getTL()];
        vLig[getTL()] = null;
        //ou posso também deixar o meu for ir até getTL() ao invés de getTL()-1
    }

    @Override
    public void remanejarInsercao(int pos) {
        super.remanejarInsercao(pos); //chamo o super para remanejar o vInfo
        //vLig[getTL()+1] = vLig[getTL()];
        for (int i = super.getTL()+1; i > pos; i--) {
            vLig[i] = vLig[i-1];
        }
        //ou posso apenas deixar o meu for iniciar de getTL() ao inves de getTL()-1
    }

    //Gets e Sets
    public No getvLig(int pos) {
        return vLig[pos];
    }

    public void setvLig(No lig, int pos) {
        this.vLig[pos] = lig;
    }
}
