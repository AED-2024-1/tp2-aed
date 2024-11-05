package aed.nodos;

public class Ciudad {
    int id;
    int ganancia;
    int perdida;
    int superhabit;

    public Ciudad(int num){
        id = num;
        ganancia = 0;
        perdida = 0;
        superhabit = 0;
    }

    public void setPerdida(int perdida) {
        this.perdida = perdida;
    }
    public void setGanancia(int ganancia) {
        this.ganancia = ganancia;
    }
    public void setSuperhabit(int superhabit) {
        this.superhabit = superhabit;
    }
    public int getGanancia() {
        return ganancia;
    }
    
    public int getPerdida() {
        return perdida;
    }
    public int getSuperhabit() {
        return superhabit;
    }
}
