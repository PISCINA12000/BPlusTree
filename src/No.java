public class No {
    public static final int N = 4;
    private int[] vInfo;
    private int[] vPos;
    private int TL;

    //Construtor
    public No(int info, int posArq) {
        this();
        this.vInfo[0] = info;
        this.vPos[0] = posArq;
        this.TL = 1;
    }

    public No(){
        this.vInfo = new int[N];
        this.vPos = new int[N];
        this.TL = 0;
    }

    //Métodos
    public int procurarPosicao(int num){
        int i=0;
        while(i<TL && num>vInfo[i])
            i++;
        return i;
    }

    public void remanejarExclusao(int pos){
        for (int i = pos; i < TL-1; i++) {
            vInfo[i] = vInfo[i + 1];
            vPos[i] = vPos[i + 1];
        }
    }

    public void remanejarInsercao(int pos){
        for(int i = TL-1; i > pos; i--) {
            vInfo[i] = vInfo[i - 1];
            vPos[i] = vPos[i - 1];
        }
    }

    public boolean isFolha(){
        return this instanceof NoFolha;
    }

    //Gets e Sets
    public int getvInfo(int pos) {
        return vInfo[pos];
    }

    public void setvInfo(int info, int pos) {
        this.vInfo[pos] = info;
    }

    public int getvPos(int pos) {
        return vPos[pos];
    }

    public void setvPos(int posArq, int pos) {
        this.vPos[pos] = posArq;
    }

    public int getTL() {
        return TL;
    }

    public void setTL(int TL) {
        this.TL = TL;
    }
}
