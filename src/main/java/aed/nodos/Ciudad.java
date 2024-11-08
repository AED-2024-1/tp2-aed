package aed.nodos;

public class Ciudad {
    int id;
    int ganancia;
    int perdida;
    int superavit;

    public Ciudad(int num){
        id = num;
        ganancia = 0;
        perdida = 0;
        superavit = 0;
    }

    public void setPerdida(int perdida) {
        this.perdida = perdida;
    }
    public void setGanancia(int ganancia) {
        this.ganancia = ganancia;
    }
    public void setSuperavit(int superhabit) {
        this.superavit = superhabit;
    }

    public int getId() {
        return id;
    }
    public int getGanancia() {
        return ganancia;
    }
    public int getPerdida() {
        return perdida;
    }
    public int getSuperavit() {
        return superavit;
    }
}
