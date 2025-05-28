public class NoFolha extends No{
    private No ant;
    private No prox;

    //Construtor
    NoFolha(int info, int posArq) {
        super(info, posArq);
        this.prox = null;
        this.ant = null;
    }
    NoFolha(){
        super();
        this.ant = null;
        this.prox = null;
    }

    //Gets e Sets
    public No getAnt() {
        return ant;
    }

    public void setAnt(No ant) {
        this.ant = ant;
    }

    public No getProx() {
        return prox;
    }

    public void setProx(No prox) {
        this.prox = prox;
    }
}
